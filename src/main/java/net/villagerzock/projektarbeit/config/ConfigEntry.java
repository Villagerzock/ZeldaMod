package net.villagerzock.projektarbeit.config;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.ISerializer;

public abstract class ConfigEntry<S> {
    public final Text title;
    public final Identifier id;
    public ConfigEntry(Text title, Identifier id){
        this.id = id;
        this.title = title;
    }
    public ConfigEntry(Identifier id){
        this.id = id;
        this.title = Text.translatable(id.toTranslationKey("config"));
    }
    public abstract JsonObject serialize();
    public abstract void deserialize(JsonObject object);
    public abstract  <T extends Element> T getObject();
    public abstract S getValue();
}
