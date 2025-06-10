package net.villagerzock.projektarbeit.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.villagerzock.projektarbeit.client.TypedBinding;
import net.villagerzock.projektarbeit.iMixins.IKeyBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin implements IKeyBinding {
    @Shadow @Final private static Map<InputUtil.Key, KeyBinding> KEY_TO_BINDINGS;


    @Shadow private InputUtil.Key boundKey;

    @Shadow @Final private static Map<String, KeyBinding> KEYS_BY_ID;


    @Inject(method = "updateKeysByCode",at = @At("HEAD"),cancellable = true)
    private static void updateKeyByCodeInject(CallbackInfo ci){
        KEY_TO_BINDINGS.clear();
        Iterator<KeyBinding> var0 = KEYS_BY_ID.values().iterator();
        while (var0.hasNext()){
            KeyBinding keyBinding = (KeyBinding)var0.next();
            if (keyBinding instanceof IKeyBinding iKeyBinding){
                if (keyBinding instanceof TypedBinding<?> typedBinding){
                    if (!typedBinding.getHandler().getBindings().containsKey(typedBinding.getType())){
                        typedBinding.getHandler().put(typedBinding.getType(),new HashMap<>());
                    }
                    typedBinding.getHandler().getBindings().get(typedBinding.getType()).put(iKeyBinding.getBoundKey(),typedBinding);
                }else {
                    KEY_TO_BINDINGS.put(iKeyBinding.getBoundKey(),keyBinding);
                }
            }
        }
        ci.cancel();
    }
    @Override
    public InputUtil.Key getBoundKey() {
        return boundKey;
    }
}
