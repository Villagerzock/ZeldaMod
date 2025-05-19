package net.villagerzock.projektarbeit.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public interface CustomModel {
    BakedModel getCustomModel(BakedModel value, ItemStack stack, ModelTransformationMode mode, boolean leftHanded, MatrixStack ms, VertexConsumerProvider provider, int light, int overlay);
    static BakedModel getModelForID(ModelIdentifier id){
        MinecraftClient client = MinecraftClient.getInstance();
        return client.getItemRenderer().getModels().getModelManager().getModel(id);
    }
}
