package net.villagerzock.projektarbeit.config;

import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;

public class ServerConfig extends Config{
    public ServerConfig() {
        super(Identifier.of(Main.MODID,"server"));
    }

    @Override
    public Type type() {
        return Type.SERVER;
    }
}
