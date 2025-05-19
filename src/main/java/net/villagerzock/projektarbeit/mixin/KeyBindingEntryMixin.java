package net.villagerzock.projektarbeit.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.screen.option.ControlsListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.MutableText;
import net.villagerzock.projektarbeit.client.TypedBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;

@Mixin(ControlsListWidget.KeyBindingEntry.class)
public class KeyBindingEntryMixin {
    @Shadow
    @Final
    private ButtonWidget editButton;

    @Shadow @Final private KeyBinding binding;

    @Shadow private boolean duplicate;

    @Redirect(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/option/KeyBinding;equals(Lnet/minecraft/client/option/KeyBinding;)Z"
            )
    )
    public boolean updateInject(KeyBinding instance, KeyBinding other, @Local MutableText mutableText){
        Predicate<KeyBinding> extraCheck;
        if (this.binding instanceof TypedBinding<?> typedBinding){
            extraCheck = (o) ->{
                if (o instanceof TypedBinding<?> keyBinding){
                    return keyBinding.getType() == typedBinding.getType() || typedBinding.checkOther(keyBinding);
                }
                return false;
            };
        } else {
            extraCheck = (o) ->{
                return !(other instanceof TypedBinding<?>);
            };
        }

        return instance.equals(other) && extraCheck.test(other);
    }
}
