package net.villagerzock.projektarbeit.config;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;

public class ModConfig extends Config {
    public ModConfig() {
        super(Identifier.of(Main.MODID,"config"));
        entries.add(new ClientConfig());
        entries.add(new GameplayConfig());
        entries.add(new ServerConfig());
    }

    @Override
    public Type type() {
        return Type.BOTH;
    }
}
