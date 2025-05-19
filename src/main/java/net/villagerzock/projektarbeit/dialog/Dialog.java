package net.villagerzock.projektarbeit.dialog;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.registry.Registries;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IHaveASerializerAndType;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.ISerializer;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IType;

import java.util.List;

public abstract class Dialog implements ISerializer<Dialog.Instance>, IType<Dialog.Instance> {
    public abstract void render(DrawContext context,float delta);
    public abstract boolean needMouse();
    public abstract boolean pausesGame();
    public abstract List<Element> getChildren();
    @Override
    public final boolean shouldSendToClient() {
        return true;
    }

    @Override
    public final ISerializer<Instance> getSerializer() {
        return this;
    }

    @Override
    public Instance read(JsonElement jsonElement) {
        JsonObject object = jsonElement.getAsJsonObject();
        Dialog dialog = Registries.dialogs.get(Identifier.tryParse(object.get("type").getAsString()));
        return null;
    }

    @Override
    public Instance read(PacketByteBuf packetByteBuf) {
        return null;
    }

    @Override
    public final void write(Instance object, PacketByteBuf byteBuf) {

    }
    public static abstract class Instance implements IHaveASerializerAndType<Instance> {
        private Dialog type;
        private Instance next;
    }
}
