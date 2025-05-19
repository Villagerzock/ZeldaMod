package net.villagerzock.projektarbeit.config.entries;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.config.ConfigEntry;

public class EnumEntry<T extends Enum<T>> extends ConfigEntry<T> {
    private final Class<T> enumeration;
    private T value;
    public EnumEntry(Text title, Identifier id,Class<T> enumeration) {
        super(title, id);
        this.enumeration = enumeration;
        value = enumeration.getEnumConstants()[0];
    }
    public EnumEntry(Identifier id,Class<T> enumeration) {
        super(id);
        this.enumeration = enumeration;
        value = enumeration.getEnumConstants()[0];
    }

    @Override
    public JsonPrimitive serialize() {
        return new JsonPrimitive(getValue().name());
    }

    @Override
    public void deserialize(JsonElement object) {
        value = T.valueOf(enumeration,object.getAsString());
    }

    @Override
    public ClickableWidget getObject() {
        ButtonWidget buttonWidget = ButtonWidget.builder(title.copy().append(Text.literal(": " + value.name())),button -> {
            value = enumeration.getEnumConstants()[Math.floorMod(value.ordinal() + 1,enumeration.getEnumConstants().length)];
            button.setMessage(title.copy().append(Text.literal(": " + value.name())));
        }).build();
        return buttonWidget;
    }

    @Override
    public T getValue() {
        return value;
    }
}
