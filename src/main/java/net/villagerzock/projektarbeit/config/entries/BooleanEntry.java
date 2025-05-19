package net.villagerzock.projektarbeit.config.entries;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.config.ConfigEntry;

public class BooleanEntry extends ConfigEntry<Boolean> {
    Boolean value = true;
    public BooleanEntry(Identifier id) {
        super(id);
    }

    @Override
    public JsonPrimitive serialize() {
        return new JsonPrimitive(getValue());
    }

    @Override
    public int getObjectWidth() {
        return super.getObjectWidth() + MinecraftClient.getInstance().textRenderer.getWidth(title);
    }

    @Override
    public void deserialize(JsonElement object) {
        value = object.getAsBoolean();
    }

    @Override
    public ClickableWidget getObject() {
        CheckboxWidget widget = new CheckboxWidget(0,0,20,20, title, value){
            @Override
            public void onPress() {
                super.onPress();
                value = active;
            }

            @Override
            public boolean isFocused() {
                return super.isHovered();
            }
        };
        return widget;
    }

    @Override
    public Boolean getValue() {
        return value;
    }
}
