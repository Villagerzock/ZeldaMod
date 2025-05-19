package net.villagerzock.projektarbeit;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class Util {
    public static Integer[] range(int range){
        Integer[] result = new Integer[range];
        for (int i = 0; i < range; i++) {
            result[i] = i;
        }
        return result;
    }
    public static void requestServer(Identifier packet, Request<MinecraftClient, ClientPlayNetworkHandler> request){
    }
    public interface Request<ENV, HANDLER>{
        void get(ENV environment, HANDLER handler, PacketByteBuf buf, PacketSender sender);
    }
}
