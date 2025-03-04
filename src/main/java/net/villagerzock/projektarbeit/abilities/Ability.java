package net.villagerzock.projektarbeit.abilities;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.client.screens.Color;
import org.joml.Vector2i;

import java.util.Map;

public abstract class Ability {
    public abstract Color getTintColor();
    public enum InteractMode{
        LEFT_HAND,
        RIGHT_HAND,
        NONE
        ;
    }
    public abstract InteractMode[] allowedInteractions();
    public abstract Map<InteractMode, Vector2i> getInteractionPosition();
    public abstract Map<InteractMode, KeyBinding> getBindings();
    public abstract void onAbilityActivated(PlayerEntity player, World world);
    public abstract void onAbilityUsed(PlayerEntity player, World world, InteractMode mode);
    public static Identifier getTexture(Identifier identifier){
        return identifier.withPrefixedPath("ability");
    }
    public static String getTranslationKey(Identifier identifier){
        return identifier.toTranslationKey("ability");
    }
    public static String getDescriptionKey(Identifier identifier){
        return identifier.toTranslationKey("ability","desc");
    }
}
