package net.villagerzock.projektarbeit.item;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.villagerzock.projektarbeit.item.model.AppliedBakedModel;
import net.villagerzock.projektarbeit.item.model.BakedModelFusion;
import net.villagerzock.projektarbeit.item.model.Transform;

public interface Fuseable extends CustomModel {
    @Override
    default BakedModel getCustomModel(BakedModel value, ItemStack stack, ModelTransformationMode mode, boolean leftHanded, MatrixStack ms, VertexConsumerProvider provider, int light, int overlay){
        if (isFused(stack)){
            if (stack.getItem() instanceof CustomModel modelItem){
                BakedModel bakedModel = modelItem.getCustomModel(value,stack,mode,leftHanded,ms,provider,light,overlay);
                ItemStack fusedStack = ItemStack.fromNbt(stack.getNbt().getCompound("fusedStack"));
                if (bakedModel != null){
                    if (fusedStack.getItem() instanceof CustomModel fusedModelItem){
                        BakedModel fusedBakedModel = fusedModelItem.getCustomModel(value,fusedStack,mode,leftHanded,ms,provider,light,overlay);
                        if (fusedBakedModel != null){
                            AppliedBakedModel model = new AppliedBakedModel(bakedModel, Transform.EMPTY);
                            AppliedBakedModel fusedModel = new AppliedBakedModel(fusedBakedModel, Transform.DEFAULT);
                            if (bakedModel instanceof AppliedBakedModel appliedBakedModel){
                                model = appliedBakedModel;
                            }
                            if (fusedBakedModel instanceof AppliedBakedModel appliedBakedModel){
                                fusedModel = appliedBakedModel;
                            }
                            return new BakedModelFusion(model,fusedModel);
                        }
                    }
                    return bakedModel;
                }
            }
        }
        return value;
    }
    default boolean isFused(ItemStack stack){
        return stack.hasNbt() && stack.getNbt().contains("fusedStack");
    }
    BakedModel getModel(BakedModel value, ItemStack stack, ModelTransformationMode mode, boolean leftHanded, MatrixStack ms, VertexConsumerProvider provider, int light, int overlay);
    default void fuse(ItemStack stack,ItemStack stackToFuse){
        if (isFused(stackToFuse))
            return;
        NbtCompound compound = stack.getOrCreateNbt();
        NbtCompound fuseCompound = new NbtCompound();
        stackToFuse.writeNbt(fuseCompound);
        compound.put("fusedStack",fuseCompound);

        stack.setNbt(compound);
    }
}
