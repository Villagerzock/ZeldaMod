package net.villagerzock.projektarbeit.abilities;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.registry.Registries;

public class Abilities {
    public static final FuseAbility FUSE_ABILITY = register("fuse",new FuseAbility());
    public static <T extends Ability> T register(String name, T ability){
        return Registry.register(Registries.abilities, Identifier.of(Main.MODID,name),ability);
    }
    public static void staticRegister(){

    }
}
