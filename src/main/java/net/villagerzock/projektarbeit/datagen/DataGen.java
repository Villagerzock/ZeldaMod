package net.villagerzock.projektarbeit.datagen;

import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.registry.Registries;

import javax.imageio.spi.RegisterableService;

public interface DataGen<T> {
    void bootstrap(Registerable<T> context);
    static <T> RegistryKey<T> register(Identifier id, DataGen<T> dataGen,RegistryKey<Registry<T>> type){
        RegistryKey<T> key = RegistryKey.of(type,
                id);
        Registries.dataGen.register(key,dataGen);
        return key;
    }
}
