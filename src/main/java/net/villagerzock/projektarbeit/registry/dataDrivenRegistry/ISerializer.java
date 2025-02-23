package net.villagerzock.projektarbeit.registry.dataDrivenRegistry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public interface ISerializer<T extends IHaveASerializerAndType<T>> {
    void write(T object, PacketByteBuf byteBuf);
    T read(JsonElement jsonElement);
    T read(PacketByteBuf packetByteBuf);
    public static BlockPos serializeBlockPos(JsonArray array){
        int x = array.get(0).getAsInt();
        int y = array.get(1).getAsInt();
        int z = array.get(2).getAsInt();
        return new BlockPos(x,y,z);
    }
}
