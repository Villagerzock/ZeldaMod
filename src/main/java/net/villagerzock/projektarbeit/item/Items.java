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

import java.util.*;

public class Items {
    public  interface EnumItem{
        Item getItem();
        default ItemStack getGroupItem(){
            return new ItemStack(getItem());
        }
        ItemGroup getGroup();
    }
    public enum KeyItems implements EnumItem{
        PARAGLIDER_FABRIC(new ParagliderFabricItem(new FabricItemSettings()))
        ;
        private final Item item;

        KeyItems(Item item) {
            this.item = register(name(),item);
        }

        @Override
        public Item getItem() {
            return item;
        }


        @Override
        public ItemGroup getGroup() {
            return ItemGroups.KEY_ITEMS;
        }
    }
    public enum Weapons implements EnumItem {
        MASTER_SWORD(new Item(new FabricItemSettings())),
        MASTER_SWORD_BROKEN(new Item(new FabricItemSettings()))
        ;
        private final Item item;
        Weapons(Item item){
            this.item = item;
        }
        @Override
        public Item getItem() {
            return item;
        }

        @Override
        public ItemGroup getGroup() {
            return ItemGroups.WEAPONS;
        }
    }
    public enum Shields implements EnumItem {
        HYLIAN_SHIELD(new Item(new FabricItemSettings()))
        ;
        private final Item item;
        Shields(Item item){
            this.item = item;
        }
        @Override
        public Item getItem() {
            return item;
        }

        @Override
        public ItemGroup getGroup() {
            return ItemGroups.WEAPONS;
        }
    }
    public enum Ingredients implements EnumItem {
        ACORN(new Item(new FabricItemSettings())),
        AEROCUDA_EYEBALL,
        AEROCUDA_WIND,
        AMBER,
        ANCIENT_AROWANA,
        ANCIENT_BLADE,
        ARMORANTH,
        ARMORED_CARP,
        ARMORED_PORGY,
        BIG_HEARTED_RADISH,
        BIRD_EGG,
        BLACK_BOKOBLIN_HORN,
        BLACK_HINOX_HORN,
        BLACK_HORRIBLIN_HORN,
        BLACK_LIZALFOS_HORN,
        BLACK_LIZALFOS_TAIL,
        BLACK_MOBLIN_HORN,
        BLADED_RHINO_BEETLE,
        BLUE_BOKOBLIN_HORN,
        BLUE_BOSS_BOKOBLIN_HORN,
        BLUE_HINOX_HORN,
        BLUE_HORRIBLIN_HORN,
        BLUE_LIZALFOS_HORN,
        BLUE_LIZALFOS_TAIL,
        BLUE_MOBLIN_HORN,
        BLUE_NIGHTSHADE,
        BLUE_MANED_LYNEL_MACE_HORN,
        BLUE_MANED_LYNEL_SABER_HORN,
        BLUE_WHITE_FROX_FANG,
        BOKOBLIN_FANG,
        BOKOBLIN_GUTS,
        BOKOBLIN_HORN,
        BOMB_FLOWER,
        BOSS_BOKOBLIN_FANG,
        BOSS_BOKOBLIN_GUTS,
        BOSS_BOKOBLIN_HORN,
        BRIGHBLOOM_SEED,
        BRIGHTCAP,
        BRIGHT_EYED_CRAB,
        CAPTAIN_CONSTRUCT_1_HORN,
        CAPTAIN_CONSTRUCT_2_HORN,
        CAPTAIN_CONSTRUCT_3_HORN,
        CAPTAIN_CONSTRUCT_4_HORN,
        CHICKALOO_TREE_NUT,
        CHILLFIN_TROUT,
        CHILLSHROOM,
        CHUCHU_JELLY,
        COLOD_DARNER,
        COOL_SAFFLINA,
        COURSER_BEE_HONEY,
        DARK_CLUMP,
        DAZZLE_FRUIT,
        DEEP_FIREFLY,
        DINRAALS_CLAW,
        DINRAALS_HORN,
        DINRAALS_SCALE,
        ELECTRIC_DARNER,
        ELECTRIC_KEESE_EYEBALL,
        ELECTRIC_KEESE_WING,
        ELECTRIC_LIZALFOS_HORN,
        ELECTRIC_LIZALFOS_TAIL,
        ELECTRIC_SAFFLINA,
        ENDURA_CARROT,
        ENDURA_SHROOM,
        ENERGETIC_RHINO_BEETLE,
        FAIRY,
        FAROSHS_CLAW,
        FAROSHS_HORN,
        FAROSHS_SCALE,
        FIRE_FRUIT,
        FIRE_KEESE_WING,
        FIRE_LIKE_STONE,
        FIRE_BREATH_LIZALFOS_HORN,
        FIRE_BREATH_LIZALFOS_TAIL,
        FIREPROOF_LIZARD,
        FLEET_LOTUS_SEEDS,
        FLINT,
        FORTIFIED_PUMPKIN,
        FRESH_MILK,
        FROX_FANG,
        FROX_FINGERNAIL,
        FROX_GUTS,
        GIANT_BRIGHTBLOOM_SEED,
        GIBDO_BONE,
        GIBDO_GUTS,
        GIBDO_WING,
        GLEEOK_FLAME_HORN,
        GLEEOK_FROST_HORN,
        GLEEOK_GUTS,
        GLEEOK_THUNDER_HORN,
        GLEEOK_WING,
        GLOWING_CAVE_FISH,
        GOAT_BUTTER,
        GORON_SPICE,
        HATENO_CHEESE,
        HEARTY_BASS,
        HEARTY_LIZARD,
        HEARTY_RADISH,
        HEARTY_SALMON,
        HEARTY_TRUFFLE,
        HIGHTAIL_LIZARD,
        HINOX_GUTS,
        HINOX_TOENAIL,
        HINOX_TOOTH,
        HORRIBLIN_CLAW,
        HORRIBLIN_GUTS,
        HORRIBLIN_HORN,
        HOT_FOOTED_FROG,
        HYDROMELON,
        HYLIAN_RICE,
        HYLIAN_SHROOM,
        HYLIAN_TOMATO,
        HYRULE_BASS,
        HYRULE_HERB,
        ICE_FRUIT,
        ICE_KEESE_EYEBALL,
        ICE_KEESE_WING,
        ICE_LIKE_STONE,
        ICE_BREATH_LIZALFOS_HORN,
        IRONSHELL_CRAB,
        IRONSHROOM,
        KEESE_EYEBALL,
        KEESE_WING,
        KOROK_FROND,
        LIKE_LIKE_STONE,
        LIGHT_DRAGONS_CLAW,
        LIGHT_DRAGONS_HORN,
        LIGHT_DRAGONS_SCALE,
        LIGHT_DRAGONS_TALON,
        LIZALFOS_HORN,
        LIZALFOS_TALON,
        LIZALFOS_TAIL,
        LYNEL_GUTS,
        LYNEL_HOOF
        ;
        private final Item item;
        private static final List<ItemStack> items = new ArrayList<>();
        Ingredients(Item item){
            this.item = register(name(),item);
        }
        Ingredients(){
            this.item = register(name(),new Item(new FabricItemSettings()));
        }
        @Override
        public Item getItem() {
            return item;
        }

        @Override
        public ItemGroup getGroup() {
            return ItemGroups.INGREDIENTS;
        }
    }
    public static class DefaultEnumItem implements EnumItem{
        private final Item item;
        private final ItemGroup tab;
        public DefaultEnumItem(Item item, ItemGroup tab){
            this.item = item;
            this.tab = tab;
        }

        @Override
        public Item getItem() {
            return item;
        }

        @Override
        public ItemGroup getGroup() {
            return tab;
        }
    }
    public static final Item TITLE_SCREEN = register("title_screen",new Item(new FabricItemSettings()));
    private static final Map<ItemGroup,List<EnumItem>> items = new HashMap<>();

    private static <T extends Item> T register(String name, T item){
        T finalItem = Registry.register(Registries.ITEM, Identifier.of(Main.MODID,name.toLowerCase()),item);
        return item;
    }
    private static <T extends Enum<?> & EnumItem> void loadEnum(Class<T> type){
        System.out.println("Hi");
        T[] itemList = type.getEnumConstants();
        for (EnumItem item : itemList) {
            List<EnumItem> items = Items.items.get(item.getGroup());
            if (!Items.items.containsKey(item.getGroup())){
                items = Items.items.getOrDefault(item.getGroup(), new ArrayList<>());
            }
            items.add(item);
            Items.items.put(item.getGroup(), items);
        }
    }
    public static <T extends Enum<T> & EnumItem> void registerItemForTab(Class<T> tab,Item item){
        T[] itemList = tab.getEnumConstants();
        if (itemList.length > 0){
            Items.items.get(itemList[0].getGroup()).add(new DefaultEnumItem(item,itemList[0].getGroup()));
        }
    }
    public static void registerAllItems(){
        Main.LOGGER.info("Registering Items for " + Main.MODID);

        //Loading the EnumItems
        loadEnum(Ingredients.class);
        loadEnum(KeyItems.class);

        items.forEach((group, enumItems) -> {
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.getKey(group)).register((fabricItemGroupEntries -> {
                Items.modifyEntries(fabricItemGroupEntries,group);
            }));
        });
    }
    private static void modifyEntries(FabricItemGroupEntries fabricItemGroupEntries,ItemGroup group) {
        for (EnumItem enumItem : items.get(group)){
            ItemStack stack = enumItem.getGroupItem();
            fabricItemGroupEntries.add(stack);
        }
    }
}
