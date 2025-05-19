package net.villagerzock.projektarbeit.item;

import net.minecraft.item.ItemStack;

public interface CustomModelProvider {
    CustomModel provideModel(ItemStack stack);
}
