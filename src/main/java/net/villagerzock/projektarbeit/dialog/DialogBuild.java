package net.villagerzock.projektarbeit.dialog;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.network.PacketByteBuf;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IHaveASerializerAndType;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.ISerializer;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IType;

public class DialogBuild implements IHaveASerializerAndType<DialogBuild> {

    @Override
    public ISerializer<DialogBuild> getSerializer() {
        return null;
    }

    @Override
    public IType<DialogBuild> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements IType<DialogBuild>{
        public static final Type INSTANCE = new Type();
        @Override
        public ISerializer<DialogBuild> getSerializer() {
            return Serializer.INSTANCE;
        }

        @Override
        public boolean shouldSendToClient() {
            return true;
        }
    }
    public static class Serializer implements ISerializer<DialogBuild>{
        public static final Serializer INSTANCE = new Serializer();
        @Override
        public void write(DialogBuild object, PacketByteBuf byteBuf) {
        }

        @Override
        public DialogBuild read(JsonElement jsonElement) {
            JsonArray array = jsonElement.getAsJsonArray();

            return null;
        }

        @Override
        public DialogBuild read(PacketByteBuf packetByteBuf) {
            return null;
        }
    }
}
