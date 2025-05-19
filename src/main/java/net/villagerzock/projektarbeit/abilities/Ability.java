package net.villagerzock.projektarbeit.abilities;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.client.screens.Color;
import net.villagerzock.projektarbeit.events.PlayerEvents;
import org.joml.Vector2i;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class Ability {
    public abstract Color getTintColor();
    public enum InteractMode{
        LEFT_HAND,
        RIGHT_HAND,
        ;
    }
    public void tick(PlayerEntity player, World world){

    }
    public abstract Map<InteractMode, List<KeyBinding>> getBindings();
    public abstract void onAbilityActivated(PlayerEntity player, World world);
    public abstract void onAbilityUsed(PlayerEntity player, World world, KeyBinding binding);
    public abstract boolean canAbilityPartBeUsed(PlayerEntity player, World world, KeyBinding binding);
    public static String getTranslationKey(Identifier identifier){
        return identifier.toTranslationKey("ability");
    }
    public static String getDescriptionKey(Identifier identifier){
        return identifier.toTranslationKey("ability","desc");
    }
}
