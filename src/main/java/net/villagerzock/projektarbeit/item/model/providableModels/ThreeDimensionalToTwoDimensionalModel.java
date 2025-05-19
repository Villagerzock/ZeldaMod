package net.villagerzock.projektarbeit.item.model.providableModels;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.item.CustomModel;

public class ThreeDimensionalToTwoDimensionalModel implements CustomModel {
    private final ModelIdentifier inHandModel;

    public ThreeDimensionalToTwoDimensionalModel(ModelIdentifier inHandModel) {
        this.inHandModel = inHandModel;
    }

    @Override
    public BakedModel getCustomModel(BakedModel value, ItemStack stack, ModelTransformationMode mode, boolean leftHanded, MatrixStack ms, VertexConsumerProvider provider, int light, int overlay) {
        if (mode == ModelTransformationMode.GUI){
            return value;
        }
        return CustomModel.getModelForID(this.inHandModel);
    }
}
