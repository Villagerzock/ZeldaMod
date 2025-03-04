package net.villagerzock.projektarbeit.config;

import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class ConfigCategory extends ConfigEntry<List<ConfigEntry<?>>> {
    public List<ConfigEntry<?>> entries = new ArrayList<>();
    public ConfigCategory(Text title,Identifier id, ConfigCategory parent){
        super(title,id);
        this.parent = parent;
    }
    public ConfigCategory(Identifier id, ConfigCategory parent){
        super(id);
        this.parent = parent;
    }
    private final ConfigCategory parent;
    public ConfigCategory getParent(){
        return parent;
    }

    @Override
    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        for (ConfigEntry<?> entry : entries){
            object.add(entry.id.toString(),entry.serialize());
        }
        return object;
    }

    @Override
    public void deserialize(JsonObject object) {
        for (ConfigEntry<?> entry : entries){
            entry.deserialize(object.getAsJsonObject(entry.id.toString()));
        }
    }
    public List<ClickableWidget> getElements(){

        List<ClickableWidget> elements = new ArrayList<>();
        for (ConfigEntry<?> entry : entries){
            if (MinecraftClient.getInstance().world != null){
                if (entry instanceof Config config){
                    if (config.type() != Config.Type.CLIENT){
                        continue;
                    }
                }
            }
            elements.add(entry.getObject());
        }
        return elements;
    }
    @Override
    public <T extends Element> T getObject() {
        return (T) ButtonWidget.builder(title, button -> {
            MinecraftClient.getInstance().setScreen(new ConfigScreen(this));
        }).build();
    }

    @Override
    public List<ConfigEntry<?>> getValue() {
        return entries;
    }
}
