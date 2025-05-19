package net.villagerzock.projektarbeit.client;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.villagerzock.projektarbeit.abilities.Ability;

public class AbilityKeyBinding extends TypedBinding<Ability> {

    public AbilityKeyBinding(String translationKey, InputUtil.Type inputType, int code, String category, Ability type) {
        super(translationKey, inputType, code, category, type);
    }
}
