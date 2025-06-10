package net.villagerzock.projektarbeit.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import java.util.HashMap;
import java.util.Map;

public class TypedBindingHandler<T> {
    public Map<T, Map<InputUtil.Key, KeyBinding>> bindings = new HashMap<>();
    public Map<T,Map<InputUtil.Key,KeyBinding>> getBindings(){
        return bindings;
    }
    public void put(Object key, Map<InputUtil.Key, KeyBinding> value){
        getBindings().put((T) key,value);
    }
}
