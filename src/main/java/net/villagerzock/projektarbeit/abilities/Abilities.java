package net.villagerzock.projektarbeit.abilities;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.registry.Registries;

public class Abilities {
    public static final FuseAbility ASCENT = register("ascent",new FuseAbility());
    public static final FuseAbility CAMERA = register("camera",new FuseAbility());
    public static final FuseAbility ULTRA_HAND = register("ultra_hand",new FuseAbility());
    public static final FuseAbility AUTO_BUILD = register("auto_build",new FuseAbility());
    public static final FuseAbility FUSE = register("fuse",new FuseAbility());
    public static final FuseAbility QUEST_LOG = register("quest_log",new FuseAbility());
    public static final FuseAbility RECALL = register("recall",new FuseAbility());
    public static <T extends Ability> T register(String name, T ability){
        return Registry.register(Registries.abilities, Identifier.of(Main.MODID,name),ability);
    }
    public static void staticRegister(){

    }
}
