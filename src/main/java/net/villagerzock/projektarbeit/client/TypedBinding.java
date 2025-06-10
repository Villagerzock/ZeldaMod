package net.villagerzock.projektarbeit.client;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public abstract class TypedBinding<T> extends KeyBinding {
    private final T type;
    public TypedBinding(String translationKey, InputUtil.Type inputType, int code, String category, T type) {
        super(translationKey, inputType, code, category);
        this.type = type;
    }
    public abstract TypedBindingHandler<T> getHandler();
    public T getType() {
        return type;
    }
    public boolean checkOther(KeyBinding other){
        return false;
    }
}
