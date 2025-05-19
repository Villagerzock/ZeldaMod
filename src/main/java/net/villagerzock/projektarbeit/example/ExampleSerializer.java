package net.villagerzock.projektarbeit.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketByteBuf;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.ISerializer;

public class ExampleSerializer implements ISerializer<ExampleDatadrivenRegistry> {
    public static final ExampleSerializer INSTANCE = new ExampleSerializer();
    @Override
    public void write(ExampleDatadrivenRegistry object, PacketByteBuf byteBuf) {
        // Writing a String into the ByteBuf
        byteBuf.writeString(object.getName());
    }

    @Override
    public ExampleDatadrivenRegistry read(JsonElement jsonElement) {
        // Making a JsonObject from the JspnElement, this can also be a JsonArray, it just specifies the Base Type of the File
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        // Reading the Name from the JsonObject and constructing an ExampleDatadrivenRegistry Object out of it
        return new ExampleDatadrivenRegistry(jsonObject.get("name").getAsString());
    }

    @Override
    public ExampleDatadrivenRegistry read(PacketByteBuf packetByteBuf) {
        // Reading the Name from the ByteBuf and constructing an ExampleDatadrivenRegistry Object out of it
        return new ExampleDatadrivenRegistry(packetByteBuf.readString());
    }
}
