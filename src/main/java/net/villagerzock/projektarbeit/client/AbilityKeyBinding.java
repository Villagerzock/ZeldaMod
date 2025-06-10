package net.villagerzock.projektarbeit.client;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.villagerzock.projektarbeit.abilities.Ability;

import java.util.Map;

public class AbilityKeyBinding extends TypedBinding<Ability> {

    public AbilityKeyBinding(String translationKey, InputUtil.Type inputType, int code, String category, Ability type) {
        super(translationKey, inputType, code, category, type);
    }
    public static final TypedBindingHandler<Ability> HANDLER = new TypedBindingHandler<>();
    @Override
    public TypedBindingHandler<Ability> getHandler() {
        return HANDLER;
    }
}
