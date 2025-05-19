package net.villagerzock.projektarbeit.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.block.custom.BranchedLog;
import net.villagerzock.projektarbeit.item.Items;


public enum Blocks {
    SKY_OAK_LOG(new BranchedLog(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.OAK_LOG)),Items.Ingredients.class)
    ;
    private final Block block;
    private final Item blockItem;
    <T extends Enum<T> & Items.EnumItem> Blocks(Block block, Class<T> tab){
        this.blockItem = registerBlockItem(name().toLowerCase(),block,tab);
        this.block = registerBlock(name().toLowerCase(),block);
    }
    Item getBlockItem(){
        return blockItem;
    }
    Block getBlock(){
        return block;
    }
    private static<T extends net.minecraft.block.Block> T registerBlock(String name, T block){
        return Registry.register(Registries.BLOCK,Identifier.of(Main.MODID,name),block);
    }
    private static <T extends net.minecraft.block.Block, I extends Enum<I> & Items.EnumItem> Item registerBlockItem(String name, T block, Class<I> tab){
        Item item = new BlockItem(block, new FabricItemSettings());
        Items.registerItemForTab(tab,item);
        return Registry.register(Registries.ITEM, Identifier.of(Main.MODID,name), item);
    }

    public static void registerBlocks(){
        Main.LOGGER.info("Registering Blocks for " + Main.MODID);
    }
}
