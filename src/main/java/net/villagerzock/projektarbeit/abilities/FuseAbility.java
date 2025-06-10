package net.villagerzock.projektarbeit.abilities;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.villagerzock.projektarbeit.client.MainClient;
import net.villagerzock.projektarbeit.client.screens.Color;
import net.villagerzock.projektarbeit.item.Fuseable;
import org.joml.Vector2i;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuseAbility extends Ability {
    @Override
    public Color getTintColor() {
        return new Color(0xFF65D9C9);
    }


    @Override
    public Map<InteractMode, List<KeyBinding>> getBindings() {
        Map<InteractMode, List<KeyBinding>> bindings = new HashMap<>();
        List<KeyBinding> left = new ArrayList<>();
        List<KeyBinding> right = new ArrayList<>();
        left.add(MainClient.FUSE_OFFHAND);
        right.add(MainClient.FUSE_MAINHAND);
        bindings.put(InteractMode.RIGHT_HAND,right);
        bindings.put(InteractMode.LEFT_HAND,left);
        return bindings;
    }

    @Override
    public void onAbilityActivated(PlayerEntity player, World world) {

    }

    @Override
    public void onAbilityUsed(PlayerEntity player, World world, KeyBinding binding) {
    }

    @Override
    public boolean canAbilityPartBeUsed(PlayerEntity player, World world, KeyBinding binding) {
        if (binding == MainClient.FUSE_MAINHAND){
            return player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof Fuseable fuseable && fuseable.isFused(player.getStackInHand(Hand.MAIN_HAND));
        }else if (binding == MainClient.FUSE_OFFHAND){
            return player.getStackInHand(Hand.OFF_HAND).getItem() instanceof Fuseable fuseable && fuseable.isFused(player.getStackInHand(Hand.OFF_HAND));
        }
        return true;
    }

    @Override
    public ItemStack getIconStack(PlayerEntity player, World world, KeyBinding binding) {
        return null;
    }

    @Override
    public Text getBindingName(KeyBinding binding) {
        return Text.empty();
    }
}
