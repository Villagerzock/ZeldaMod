package net.villagerzock.projektarbeit.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Items {
    public  interface EnumItem<T extends  Item>{
        T getItem();
        void addGroupItem(T Item);
        List<ItemStack> getGroupItems();
        ItemGroup getGroup();
    }
    public enum Ingredients implements EnumItem<Item> {
        ACORN(new Item(new FabricItemSettings()));
        private final Item item;
        private final List<ItemStack> items = new ArrayList<>();
        Ingredients(Item item){
            this.item = register(name(),item);
            addGroupItem(item);
        }

        @Override
        public Item getItem() {
            return item;
        }

        @Override
        public List<ItemStack> getGroupItems() {
            return items;
        }

        @Override
        public ItemGroup getGroup() {
            return ItemGroups.INGREDIENTS;
        }

        @Override
        public void addGroupItem(Item Item) {
            items.add(new ItemStack(item));
        }
    }
    private static final Map<ItemGroup,List<EnumItem<?>>> items = new HashMap<>();

    private static <T extends Item> T register(String name, T item){
        T finalItem = Registry.register(Registries.ITEM, Identifier.of(Main.MODID,name.toLowerCase()),item);
        return item;
    }
    private static void loadEnum(EnumItem<?> item){
        List<EnumItem<?>> itemList = items.getOrDefault(item.getGroup(),new ArrayList<>());
        if (itemList.isEmpty()){
            items.put(item.getGroup(),itemList);
        }
        itemList.add(item);
    }

    public static void registerAllItems(){
        Main.LOGGER.info("Registering Items for " + Main.MODID);

        //Loading the EnumItems
        loadEnum(Ingredients.ACORN);

        items.forEach((group, enumItems) -> {
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.getKey(group)).register((fabricItemGroupEntries -> {
                Items.modifyEntries(fabricItemGroupEntries,group);
            }));
        });
    }
    private static void modifyEntries(FabricItemGroupEntries fabricItemGroupEntries,ItemGroup group) {
        for (EnumItem<?> enumItem : items.get(group)){
            for (ItemStack stack : enumItem.getGroupItems()){
                fabricItemGroupEntries.add(stack);
            }
        }
    }
}
