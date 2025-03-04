package net.villagerzock.projektarbeit.abilities;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.villagerzock.projektarbeit.client.screens.Color;
import org.joml.Vector2i;

import java.util.Map;

public class FuseAbility extends Ability {
    @Override
    public Color getTintColor() {
        return new Color(0xFF65D9C9);
    }

    @Override
    public InteractMode[] allowedInteractions() {
        return new InteractMode[0];
    }

    @Override
    public Map<InteractMode, Vector2i> getInteractionPosition() {
        return Map.of();
    }

    @Override
    public Map<InteractMode, KeyBinding> getBindings() {
        return Map.of();
    }

    @Override
    public void onAbilityActivated(PlayerEntity player, World world) {

    }

    @Override
    public void onAbilityUsed(PlayerEntity player, World world, InteractMode mode) {

    }


}
