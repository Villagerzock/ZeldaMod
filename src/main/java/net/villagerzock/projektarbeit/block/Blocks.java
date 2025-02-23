package net.villagerzock.projektarbeit.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;


public class Blocks {
    //public static final Block

    private static <T extends Block> T registerBlockWithItem(String name, T block){
        registerBlockItem(name,block);
        return registerBlock(name,block);
    }
    private static<T extends Block> T registerBlock(String name, T block){
        return Registry.register(Registries.BLOCK,Identifier.of(Main.MODID,name),block);
    }
    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM, Identifier.of(Main.MODID,name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerBlocks(){
        Main.LOGGER.info("Registering Blocks for " + Main.MODID);
    }
}
