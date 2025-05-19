package net.villagerzock.projektarbeit.client.screens.slots;

import com.mojang.datafixers.util.Pair;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class BackgroundIconSlot extends Slot {
    private final Identifier icon;
    public BackgroundIconSlot(Inventory inventory, int index, int x, int y,Identifier icon) {
        super(inventory, index, x, y);
        this.icon = icon;
    }

    @Override
    public void setStack(ItemStack stack) {
        super.setStack(stack);
    }

    @Override
    public @Nullable Pair<Identifier, Identifier> getBackgroundSprite() {
        return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, icon);
    }
}
