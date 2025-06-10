package net.villagerzock.projektarbeit.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.ScreenshotRecorder;

import java.util.HashMap;
import java.util.Map;

public class UIKeyBinding extends TypedBinding<Class<Screen>> {

    public UIKeyBinding(String translationKey, InputUtil.Type inputType, int code, String category, Class<Screen> type) {
        super(translationKey, inputType, code, category, type);
    }
    public static final TypedBindingHandler<Class<Screen>> HANDLER = new TypedBindingHandler<>();
    @Override
    public TypedBindingHandler<Class<Screen>> getHandler() {
        return HANDLER;
    }
}
