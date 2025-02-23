package net.villagerzock.projektarbeit;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.C2SPlayChannelEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.*;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.impl.screenhandler.client.ClientNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.block.Blocks;
import net.villagerzock.projektarbeit.client.MainClient;
import net.villagerzock.projektarbeit.commands.HorseCommand;
import net.villagerzock.projektarbeit.commands.QuestCommand;
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

public class Main implements ModInitializer {
    public static final String MODID = "zelda";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static final Identifier SEND_DATA_DRIVEN_REGISTRY_DATA_TO_CLIENT = Identifier.of(MODID,"send_data_driven_registry_data_to_client");
    public static final Identifier DATA_DRIVEN_REGISTRY_COMING = Identifier.of(MODID,"data_driven_registry_data_coming");
    public static final Identifier PLAYER_MOVEMENT_EVENT = Identifier.of(MODID,"player_movement_event");
    public static final Identifier GIVE_QUEST_TO_PLAYER = Identifier.of(MODID,"give_quest_to_player");
    public static final Identifier GET_QUESTS = Identifier.of(MODID,"get_quests");
    @Override
    public void onInitialize() {
        // Registering Registries
        ItemGroups.registerItemGroups();

        Items.registerAllItems();
        Blocks.registerBlocks();

        //Requirements.registerAll();

        // Registering Events
        registerReloadListenersForDataDrivenRegistry();
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(Main::syncDataPackContents);
        ClientPlayNetworking.registerGlobalReceiver(DATA_DRIVEN_REGISTRY_COMING, MainClient::RegistryComing);
        ClientPlayNetworking.registerGlobalReceiver(SEND_DATA_DRIVEN_REGISTRY_DATA_TO_CLIENT,MainClient::RegistryDataPacket);
        ClientPlayNetworking.registerGlobalReceiver(GIVE_QUEST_TO_PLAYER,MainClient::giveQuestToPlayer);
        ServerPlayNetworking.registerGlobalReceiver(PLAYER_MOVEMENT_EVENT, Main::emitPlayerMovementEvent);
        ServerPlayNetworking.registerGlobalReceiver(GET_QUESTS,Main::playerJoined);
        CommandRegistrationCallback.EVENT.register(new HorseCommand());
        CommandRegistrationCallback.EVENT.register(new QuestCommand());
        PlayerEvents.MOVEMENT_EVENT.register(Main::onPlayerMovement);

    }

    private static void playerJoined(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf byteBuf, PacketSender sender) {
        if (handler.getPlayer() instanceof IPlayerEntity iPlayer){
            for (QuestState state : iPlayer.getQuests()){
                System.out.println("Sending Quest to Player");
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeIdentifier(Registries.quests.getId(state.getType()));
                sender.sendPacket(GIVE_QUEST_TO_PLAYER, buf);
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
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeIdentifier(registry.getFabricId());
            ServerPlayNetworking.send(player,DATA_DRIVEN_REGISTRY_COMING,buf);
            sendDataPackContents(player,registry);
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
        for (T serializerAndType : registry){
            PacketByteBuf byteBuf = PacketByteBufs.create();
            Identifier identifier = registry.getId(serializerAndType);
            System.out.println("Sending Registry Entry called: " + identifier.toString());
            byteBuf.writeIdentifier(identifier);
            serializerAndType.getSerializer().write(serializerAndType,byteBuf);
            ServerPlayNetworking.send(player,SEND_DATA_DRIVEN_REGISTRY_DATA_TO_CLIENT,byteBuf);
        }
    }

    private static void registerReloadListenersForDataDrivenRegistry(){
        for (DataDrivenRegistry<?> registry : Registries.dataDrivenRegistries){
            ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(registry);
        }
    }
}
