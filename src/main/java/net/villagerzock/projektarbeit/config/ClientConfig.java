package net.villagerzock.projektarbeit.config;

import net.minecraft.util.Identifier;
import net.minecraft.util.Nameable;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.config.entries.EnumEntry;

public class ClientConfig extends Config{
    public ClientConfig() {
        super(Identifier.of(Main.MODID,"client"));
        entries.add(new EnumEntry<>(Identifier.of(Main.MODID,"ui-mode"),UIMode.class));
    }
    public enum UIMode {
        ORIGINAL,
        MINECRAFTY
        ;
    }

    @Override
    public Type type() {
        return Type.CLIENT;
    }
}
