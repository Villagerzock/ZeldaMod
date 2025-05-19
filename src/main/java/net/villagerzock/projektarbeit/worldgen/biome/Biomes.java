package net.villagerzock.projektarbeit.worldgen.biome;

import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.villagerzock.projektarbeit.Main;

public class Biomes {
    public static final RegistryKey<Biome> HYRULE_FIELD = RegistryKey.of(RegistryKeys.BIOME,
            new Identifier(Main.MODID, "hyrule_field"));

    public static void bootstrap(Registerable<Biome> context){
        context.register(HYRULE_FIELD,hyruleField(context));
    }

    private static Biome hyruleField(Registerable<Biome> context) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.CREATURE,new SpawnSettings.SpawnEntry(EntityType.WOLF,5,4,4));

        DefaultBiomeFeatures.addBatsAndMonsters(spawnBuilder);
        DefaultBiomeFeatures.addSnowyMobs(spawnBuilder);

        GenerationSettings.LookupBackedBuilder biomeBuilder =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));


        DefaultBiomeFeatures.addDefaultOres(biomeBuilder);
        DefaultBiomeFeatures.addEmeraldOre(biomeBuilder);
        DefaultBiomeFeatures.addSnowySpruceTrees(biomeBuilder);
        DefaultBiomeFeatures.addSweetBerryBushesSnowy(biomeBuilder);

        return new Biome.Builder()
                .precipitation(true)
                .downfall(0.4f)
                .temperature(0.2f)
                .generationSettings(biomeBuilder.build())
                .effects(new BiomeEffects.Builder()
                        .waterColor(0xe82e3b)
                        .waterFogColor(0xbf1b26)
                        .skyColor(0x30c918)
                        .grassColor(0xffffff)
                        .foliageColor(0xffffff)
                        .fogColor(0xffffff)
                        .moodSound(BiomeMoodSound.CAVE)
                        .music(MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_FOREST))
                        .build()
                ).build();
    }

}
