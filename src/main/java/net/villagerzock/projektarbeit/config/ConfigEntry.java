package net.villagerzock.projektarbeit.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.client.screens.widgets.ScrollLayout;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.ISerializer;

public abstract class ConfigEntry<S> {
    public static <T> ConfigEntry<T> getDefault(){
        return null;
    }
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
    public abstract JsonElement serialize();
    public abstract void deserialize(JsonElement object);
    public abstract ClickableWidget getObject();
    public abstract S getValue();
    public int getObjectWidth() {
        return getObject().getWidth();
    }
    public static class ConfigElementEntry extends ScrollLayout.ElementEntry{
        private final ConfigEntry<?> entry;
        public ConfigElementEntry(ConfigEntry<?> entry, ScrollLayout parent) {
            super(entry.getObject(), parent);
            this.entry = entry;
        }

        @Override
        public int getWidth(ClickableWidget widget) {
            return entry.getObjectWidth();
        }
    }
}
