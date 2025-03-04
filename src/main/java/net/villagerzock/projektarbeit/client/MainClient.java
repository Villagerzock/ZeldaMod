package net.villagerzock.projektarbeit.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.abilities.Ability;
import net.villagerzock.projektarbeit.client.ui.UI;
import net.villagerzock.projektarbeit.iMixins.IPlayerEntity;
import net.villagerzock.projektarbeit.quest.QuestState;
import net.villagerzock.projektarbeit.registry.Registries;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.DataDrivenRegistry;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IHaveASerializerAndType;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.ISerializer;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IType;
import org.lwjgl.glfw.GLFW;

public class MainClient implements ClientModInitializer {
    public static KeyBinding ACTIVATE_ABILITY;
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(new UI());
        ACTIVATE_ABILITY = new KeyBinding(
                "key.zelda.activate_ability",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "key.category.zelda.ability"
        );

        KeyBindingHelper.registerKeyBinding(ACTIVATE_ABILITY);
        ClientTickEvents.END_CLIENT_TICK.register(MainClient::clientTick);
    }
    private static boolean isPlayerThere = false;
    private static void clientTick(MinecraftClient client) {
        if (client.player != null){
            if (!isPlayerThere){
                isPlayerThere = true;
                ClientPlayNetworking.send(Main.GET_QUESTS, PacketByteBufs.create());
            }
        }else {
            isPlayerThere = false;
        }
        if (client.player instanceof IPlayerEntity player){
            Ability playerAbility = player.getCurrentAbility();
            if (playerAbility != null){
                while (ACTIVATE_ABILITY.wasPressed()){
                    playerAbility.onAbilityActivated(client.player, client.world);
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeIdentifier(Registries.abilities.getId(playerAbility));
                    buf.writeString("activate");
                    ClientPlayNetworking.send(Main.ABILITY_INTERACTION,buf);
                }
                playerAbility.getBindings().forEach((mode, keyBinding) -> {
                    while (keyBinding.wasPressed()){
                        playerAbility.onAbilityUsed(client.player,client.world,mode);
                        PacketByteBuf buf = PacketByteBufs.create();
                        buf.writeIdentifier(Registries.abilities.getId(playerAbility));
                        buf.writeString(mode.name());
                        ClientPlayNetworking.send(Main.ABILITY_INTERACTION,buf);
                    }
                });
            }
        }

    }

    private static Identifier currentRegistryId;
    public static void RegistryComing(MinecraftClient client, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        currentRegistryId = packetByteBuf.readIdentifier();
    }

    public static void RegistryDataPacket(MinecraftClient client, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        registryDataPacketWithT(packetByteBuf);
    }
    private static <T extends IHaveASerializerAndType<T>> void registryDataPacketWithT(PacketByteBuf buf){
        Identifier id = buf.readIdentifier();
        IType<T> type = (IType<T>) Registries.dataDrivenRegistries.get(currentRegistryId).getType();
        DataDrivenRegistry<T> registry = (DataDrivenRegistry<T>) Registries.dataDrivenRegistries.get(currentRegistryId);
        ISerializer<T> serializer = type.getSerializer();
        T serialierAndType = serializer.read(buf);
        registry.set(id,serialierAndType);
        System.out.println("Received " + currentRegistryId.toString() + " DataDrivenRegistry data  called " + id.toString());
    }

    public static void giveQuestToPlayer(MinecraftClient client, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf buf, PacketSender packetSender) {
        if (client.player instanceof IPlayerEntity player){
            Main.LOGGER.info("Received Quest given to me");
            player.addQuest(new QuestState(Registries.quests.get(buf.readIdentifier())));
        }
    }
}
