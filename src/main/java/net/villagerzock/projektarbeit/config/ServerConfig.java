package net.villagerzock.projektarbeit.config;

import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.config.entries.BooleanEntry;

public class ServerConfig extends Config{
    public ServerConfig() {
        super(Identifier.of(Main.MODID,"server"));
        entries.add(new BooleanEntry(Identifier.of(Main.MODID,"is_zelda_mode")));
    }

    @Override
    public Type type() {
        return Type.SERVER;
    }
}
