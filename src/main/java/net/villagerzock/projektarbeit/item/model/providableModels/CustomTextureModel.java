package net.villagerzock.projektarbeit.item.model.providableModels;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.item.CustomModel;
import net.villagerzock.projektarbeit.item.model.TextureReplacedModel;

public class CustomTextureModel implements CustomModel {
    @Override
    public BakedModel getCustomModel(BakedModel value, ItemStack stack, ModelTransformationMode mode, boolean leftHanded, MatrixStack ms, VertexConsumerProvider provider, int light, int overlay) {
        return new TextureReplacedModel(value, Identifier.of(Main.MODID,"paraglider/fabrics/default"));
    }
}
