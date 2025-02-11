package net.villagerzock.projektarbeit.registry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketByteBuf;

public interface ISerializer<T> {
    void write(T object, PacketByteBuf byteBuf);
    T read(JsonElement jsonElement);
    T read(PacketByteBuf packetByteBuf);
}
