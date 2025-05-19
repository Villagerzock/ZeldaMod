package net.villagerzock.projektarbeit.registry.nestedRegistry;

import com.mojang.serialization.Lifecycle;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.datagen.DataGen;
import net.villagerzock.projektarbeit.registry.Registries;
import org.apache.commons.lang3.NotImplementedException;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DataGenRegistry extends SimpleRegistry<SimpleRegistry<DataGen<?>>> {
    public DataGenRegistry(RegistryKey key, Lifecycle lifecycle) {
        super(key, lifecycle);
    }
    public <T> void register(RegistryKey<T> key,DataGen<T> entry){
        if (!containsId(key.getRegistry())){
            SimpleRegistry<DataGen<?>> registry = new SimpleRegistry<>(RegistryKey.ofRegistry(key.getRegistry()),Lifecycle.stable());
            register(key.getRegistry(),registry);
        }
        SimpleRegistry<DataGen<?>> registry = get(key.getRegistry());
        Registry.register(registry,key.getValue(),entry);
    }
    private void register(Identifier id, SimpleRegistry<DataGen<?>> entry){
        super.add(RegistryKey.of(this.getKey(),id),entry,Lifecycle.stable());
    }

    @Override
    public RegistryEntry.Reference<SimpleRegistry<DataGen<?>>> add(RegistryKey<SimpleRegistry<DataGen<?>>> key, SimpleRegistry<DataGen<?>> entry, Lifecycle lifecycle) {
        throw new NotImplementedException("Please use DataGenRegistry#register and not Registry#register thx");
    }

    public void registerForEach(RegistryBuilder registryBuilder){
        List<RegistryKey<Registry<Object>>> registryKeys = new ArrayList<>();
        for (Identifier entry : this.getIds()){
            registryKeys.add(RegistryKey.ofRegistry(entry));
        }
        for (RegistryKey<Registry<Object>> registryKey : registryKeys){
            this.forEachOfType(registryKey,(dataGen) ->{
                registryBuilder.addRegistry(registryKey,dataGen::bootstrap);
            });
        }
    }
    public <T> void forEachOfType(RegistryKey<Registry<T>> key, Consumer<DataGen<T>> consumer){
        if (containsId(key.getRegistry())){
            SimpleRegistry<DataGen<?>> registry = get(key.getRegistry());
            for (DataGen<?> o : registry) {
                consumer.accept((DataGen<T>) o);
            }
        }
    }
}
