package net.villagerzock.projektarbeit.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;


public class ItemGroups {
    public static final ItemGroup INGREDIENTS = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Main.MODID,"ingredients"),
            FabricItemGroup.builder().displayName(Text.translatable("zelda.item_group.ingredients"))
                    .icon(() -> new ItemStack(Items.Ingredients.ACORN.getItem())).entries((displayContext, entries) -> {
                        entries.add(new ItemStack(net.minecraft.item.Items.ACACIA_BOAT));
                    }).build());
    public static void registerItemGroups(){
        Main.LOGGER.info("Registering Item Groups for " + Main.MODID);
    }
    public static RegistryKey<ItemGroup> getKey(ItemGroup group){
        return Registries.ITEM_GROUP.getKey(group).get();
    }
}
