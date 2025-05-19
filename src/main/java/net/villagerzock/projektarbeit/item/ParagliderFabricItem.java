package net.villagerzock.projektarbeit.item;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.item.model.TextureReplacedModel;

public class ParagliderFabricItem extends Item implements CustomModel {
    public ParagliderFabricItem(Settings settings) {
        super(settings);
    }

    @Override
    public BakedModel getCustomModel(BakedModel value, ItemStack stack, ModelTransformationMode mode, boolean leftHanded, MatrixStack ms, VertexConsumerProvider provider, int light, int overlay) {
        return new TextureReplacedModel(value,Identifier.of(Main.MODID,"paraglider/fabrics/nostalgic"));
    }
}
