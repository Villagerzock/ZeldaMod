package net.villagerzock.projektarbeit.config.entries;

import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.client.screens.Color;
import net.villagerzock.projektarbeit.client.screens.widgets.HorizontalLayout;
import net.villagerzock.projektarbeit.client.screens.widgets.TextWidget;
import net.villagerzock.projektarbeit.config.ConfigEntry;

public class StringEntry extends ConfigEntry<String> {
    public StringEntry(Text title, Identifier id) {
        super(title, id);
    }

    @Override
    public JsonObject serialize() {
        return null;
    }

    @Override
    public void deserialize(JsonObject object) {

    }

    @Override
    public <T extends Element> T getObject() {
        return (T) new TextFieldWidget(MinecraftClient.getInstance().textRenderer, 0,0,100,20,title);
    }

    @Override
    public String getValue() {
        return "";
    }
}
