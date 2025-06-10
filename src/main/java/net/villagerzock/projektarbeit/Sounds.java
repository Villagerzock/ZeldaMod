package net.villagerzock.projektarbeit;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class Sounds {
    public static final MusicSound TITLE_SCREEN_MUSIC = registerMusic("title_screen",0,0);
    private static MusicSound registerMusic(String name,int min, int max){
        return new MusicSound(registerSoundEvent(name),min,max,true);
    }
    private static RegistryEntry<SoundEvent> registerSoundEvent(String name){
        Identifier id = Identifier.of(Main.MODID,name);
        return Registry.registerReference(Registries.SOUND_EVENT,id,SoundEvent.of(id));
    }
    public static void registerAll(){

    }
}
