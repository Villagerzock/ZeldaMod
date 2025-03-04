package net.villagerzock.projektarbeit.item;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public interface CustomModelItem {
    BakedModel getCustomModel(BakedModel value, ItemStack stack, ModelTransformationMode mode, boolean leftHanded, MatrixStack ms, VertexConsumerProvider provider, int light, int overlay);
}
