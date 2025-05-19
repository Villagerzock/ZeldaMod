package net.villagerzock.projektarbeit.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.client.screens.slots.BackgroundIconSlot;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends AbstractRecipeScreenHandler<RecipeInputInventory> {
    public PlayerScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }

    @Inject(method = "<init>",at = @At("RETURN"))
    public void init(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo ci){
        this.addSlot(new BackgroundIconSlot(inventory,41,77, 44, Identifier.of(Main.MODID,/*"item/empty_raurus_arm"*/"item/empty_paraglider_fabric")));
        this.addSlot(new BackgroundIconSlot(inventory,42,77, 26, Identifier.of(Main.MODID,/*"item/empty_paraglider"*/"item/empty_paraglider_fabric")));
        this.addSlot(new BackgroundIconSlot(inventory,43,77, 8, Identifier.of(Main.MODID,/*"item/empty_purah_pad"*/"item/empty_paraglider_fabric")));
    }

}
