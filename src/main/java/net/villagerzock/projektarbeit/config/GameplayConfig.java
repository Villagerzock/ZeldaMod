package net.villagerzock.projektarbeit.config;

import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;

public class GameplayConfig extends Config{
    public GameplayConfig() {
        super(Identifier.of(Main.MODID,"gameplay"));
    }

    @Override
    public Type type() {
        return Type.BOTH;
    }
}
