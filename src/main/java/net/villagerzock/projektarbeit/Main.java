package net.villagerzock.projektarbeit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.networking.v1.C2SPlayChannelEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.*;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.impl.screenhandler.client.ClientNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.model.SpriteAtlasManager;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.SpriteLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.villagerzock.projektarbeit.abilities.Abilities;
import net.villagerzock.projektarbeit.abilities.Ability;
import net.villagerzock.projektarbeit.block.Blocks;
import net.villagerzock.projektarbeit.client.MainClient;
import net.villagerzock.projektarbeit.commands.HorseCommand;
import net.villagerzock.projektarbeit.commands.QuestCommand;
import net.villagerzock.projektarbeit.commands.TestCommand;
import net.villagerzock.projektarbeit.events.PlayerEvents;
import net.villagerzock.projektarbeit.iMixins.IPlayerEntity;
import net.villagerzock.projektarbeit.item.ItemGroups;
import net.villagerzock.projektarbeit.item.Items;
import net.villagerzock.projektarbeit.quest.Quest;
import net.villagerzock.projektarbeit.quest.QuestState;
import net.villagerzock.projektarbeit.quest.requirements.DistanceToBlockPosRequirement;
import net.villagerzock.projektarbeit.quest.requirements.Requirements;
import net.villagerzock.projektarbeit.registry.Registries;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.DataDrivenRegistry;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IHaveASerializerAndType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class Main implements ModInitializer {
    public static final String MODID = "zelda";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static final Identifier SEND_DATA_DRIVEN_REGISTRY_DATA_TO_CLIENT = Identifier.of(MODID,"send_data_driven_registry_data_to_client");
    public static final Identifier DATA_DRIVEN_REGISTRY_COMING = Identifier.of(MODID,"data_driven_registry_data_coming");
    public static final Identifier PLAYER_MOVEMENT_EVENT = Identifier.of(MODID,"player_movement_event");
    public static final Identifier GIVE_QUEST_TO_PLAYER = Identifier.of(MODID,"give_quest_to_player");
    public static final Identifier GET_QUESTS = Identifier.of(MODID,"get_quests");
    public static final Identifier ABILITY_INTERACTION = Identifier.of(MODID,"ability_interaction");
    public static final Identifier UPDATE_SERVER_CONFIG = Identifier.of(MODID,"update_server_config");
    public static final Identifier PARAGLIDER_ATLAS = new Identifier("paraglider", "atlases/paraglider");
    public static final Identifier UPDATE_ABILITY = new Identifier(MODID,"update_ability");
    @Override
    public void onInitialize() {
        // Registering Registries
        ItemGroups.registerItemGroups();
        Items.registerAllItems();
        Blocks.registerBlocks();
        Abilities.staticRegister();
        Sounds.registerAll();

        //Requirements.registerAll();

        // Registering Events
        registerReloadListenersForDataDrivenRegistry();
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener((ReloadListener) Main::onClientReload);

        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(Main::syncDataPackContents);
        ClientPlayNetworking.registerGlobalReceiver(DATA_DRIVEN_REGISTRY_COMING, MainClient::RegistryComing);
        ClientPlayNetworking.registerGlobalReceiver(SEND_DATA_DRIVEN_REGISTRY_DATA_TO_CLIENT,MainClient::RegistryDataPacket);
        ClientPlayNetworking.registerGlobalReceiver(GIVE_QUEST_TO_PLAYER,MainClient::giveQuestToPlayer);
        ClientPlayNetworking.registerGlobalReceiver(UPDATE_ABILITY,MainClient::updateAbility);
        ServerPlayNetworking.registerGlobalReceiver(PLAYER_MOVEMENT_EVENT, Main::emitPlayerMovementEvent);
        ServerPlayNetworking.registerGlobalReceiver(GET_QUESTS,Main::playerJoined);
        ServerPlayNetworking.registerGlobalReceiver(ABILITY_INTERACTION,Main::abilityInteraction);
        ServerPlayNetworking.registerGlobalReceiver(UPDATE_SERVER_CONFIG,Main::updateServerConfig);
        ServerPlayNetworking.registerGlobalReceiver(UPDATE_ABILITY,Main::updateAbility);
        CommandRegistrationCallback.EVENT.register(new HorseCommand());
        CommandRegistrationCallback.EVENT.register(new QuestCommand());
        CommandRegistrationCallback.EVENT.register(new TestCommand());
        PlayerEvents.MOVEMENT_EVENT.register(Main::onPlayerMovement);

    }

    public static void updateAbility(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        if (player instanceof IPlayerEntity playerEntity){
            playerEntity.setCurrentAbility(Registries.abilities.get(buf.readInt()));
        }
    }

    private static void onClientReload(ResourceManager manager){
    }
    private static void updateServerConfig(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf byteBuf, PacketSender sender){
        if (player.hasPermissionLevel(2)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Identifier id = byteBuf.readIdentifier();
            File file = new File(new File(new File(FabricLoader.getInstance().getConfigDir().toFile(),id.getNamespace()),"server"),id.getPath() + ".json");
            file.mkdirs();
            JsonObject object = gson.fromJson(byteBuf.readString(), JsonObject.class);
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(object,writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void drawBackground(DrawContext context){
        MatrixStack ms = context.getMatrices();
        ms.push();
        int scale = 20;
        ms.scale(scale,scale,scale);
        ms.translate(0,-10,0);
        //context.drawItem(new ItemStack(Items.TITLE_SCREEN),0,0);
        ms.pop();
    }
    private static void abilityInteraction(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf byteBuf, PacketSender sender) {
        Ability ability = Registries.abilities.get(byteBuf.readIdentifier());
        String type = byteBuf.readString();
        System.out.println(type);
        if (!Arrays.stream(Ability.InteractMode.values()).toList().contains(type)){
            ability.onAbilityActivated(player,player.getWorld());
        }else {
            Ability.InteractMode mode = Ability.InteractMode.valueOf(type);
            int index = byteBuf.readInt();
            KeyBinding keyBinding = ability.getBindings().get(mode).get(index);
            ability.onAbilityUsed(player,player.getWorld(),keyBinding);
        }
    }

    private static void playerJoined(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf byteBuf, PacketSender sender) {
        if (handler.getPlayer() instanceof IPlayerEntity iPlayer){
            for (QuestState state : iPlayer.getQuests()){
                System.out.println("Sending Quest to Player");
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeIdentifier(Registries.quests.getId(state.getType()));
                sender.sendPacket(GIVE_QUEST_TO_PLAYER, buf);
            }
            if (player instanceof IPlayerEntity playerEntity){
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBoolean(true);
                buf.writeInt(Registries.abilities.getRawId(playerEntity.getCurrentAbility()));
                sender.sendPacket(UPDATE_ABILITY,buf);
                for (Ability ability : playerEntity.getUnlockedAbilities()){
                    buf = PacketByteBufs.create();
                    buf.writeBoolean(false);
                    buf.writeInt(Registries.abilities.getRawId(ability));
                    sender.sendPacket(UPDATE_ABILITY,buf);
                }
            }
        }
    }


    private static void emitPlayerMovementEvent(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,PacketByteBuf buf, PacketSender packetSender) {
        PlayerEvents.MOVEMENT_EVENT.invoker().accept(player);
    }


    private static void onPlayerMovement(PlayerEntity player) {
        if (player instanceof ClientPlayerEntity clientPlayer){
            ClientPlayNetworking.send(PLAYER_MOVEMENT_EVENT,PacketByteBufs.create());
        }else if (player instanceof ServerPlayerEntity serverPlayer){
            DistanceToBlockPosRequirement.RequirementType.INSTANCE.emit(serverPlayer);
        }

    }

    private static void syncDataPackContents(ServerPlayerEntity player, boolean b) {
        for (DataDrivenRegistry<? extends IHaveASerializerAndType<?>> registry : Registries.dataDrivenRegistries){
            if (registry.getType().shouldSendToClient()){
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeIdentifier(registry.getFabricId());
                ServerPlayNetworking.send(player,DATA_DRIVEN_REGISTRY_COMING,buf);
                sendDataPackContents(player,registry);
            }
        }
        if (player instanceof IPlayerEntity iPlayer){
            for (QuestState state : iPlayer.getQuests()){
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeIdentifier(Registries.quests.getId(state.getType()));
                ServerPlayNetworking.send(player, GIVE_QUEST_TO_PLAYER, buf);
            }
        }
    }
    private static <T extends IHaveASerializerAndType<T>> void sendDataPackContents(ServerPlayerEntity player, DataDrivenRegistry<T> registry){
        Main.LOGGER.info("Synchronizing DataDrivenRegistryContentents for Registry: " + registry.getFabricId());
        for (T serializerAndType : registry){
            Main.LOGGER.info("Synchronizing Data: " +  registry.getId(serializerAndType));
            try {
                PacketByteBuf byteBuf = PacketByteBufs.create();
                Identifier identifier = registry.getId(serializerAndType);
                System.out.println("Sending Registry Entry called: " + identifier.toString());
                byteBuf.writeIdentifier(identifier);
                serializerAndType.getSerializer().write(serializerAndType,byteBuf);
                ServerPlayNetworking.send(player,SEND_DATA_DRIVEN_REGISTRY_DATA_TO_CLIENT,byteBuf);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private static void registerReloadListenersForDataDrivenRegistry(){
        for (DataDrivenRegistry<?> registry : Registries.dataDrivenRegistries){
            ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(registry);
        }
    }
}
