package net.villagerzock.projektarbeit.item.model.providableModels;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.item.CustomModel;
import net.villagerzock.projektarbeit.item.model.BakedModelFusion;

public class CapsuleModel implements CustomModel {
    private static final ModelIdentifier capsuleModel = new ModelIdentifier(Identifier.of(Main.MODID,"capsule"),"fixed");

    @Override
    public BakedModel getCustomModel(BakedModel value, ItemStack stack, ModelTransformationMode mode, boolean leftHanded, MatrixStack ms, VertexConsumerProvider provider, int light, int overlay) {
        return new BakedModelFusion(value,CustomModel.getModelForID(capsuleModel));
    }
}
