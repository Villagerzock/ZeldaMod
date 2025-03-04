package net.villagerzock.projektarbeit.item;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.villagerzock.projektarbeit.item.model.AppliedBakedModel;
import net.villagerzock.projektarbeit.item.model.BakedModelFusion;
import net.villagerzock.projektarbeit.item.model.Transform;

public interface FuseableItem extends CustomModelItem{
    @Override
    default BakedModel getCustomModel(BakedModel value, ItemStack stack, ModelTransformationMode mode, boolean leftHanded, MatrixStack ms, VertexConsumerProvider provider, int light, int overlay){
        if (stack.getItem() instanceof CustomModelItem modelItem){
            BakedModel bakedModel = modelItem.getCustomModel(value,stack,mode,leftHanded,ms,provider,light,overlay);
            if (bakedModel != null){
                if (stack.getItem() instanceof CustomModelItem fusedModelItem){
                    BakedModel fusedBakedModel = fusedModelItem.getCustomModel(value,stack,mode,leftHanded,ms,provider,light,overlay);
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
        return value;
    }
    BakedModel getModel(BakedModel value, ItemStack stack, ModelTransformationMode mode, boolean leftHanded, MatrixStack ms, VertexConsumerProvider provider, int light, int overlay);
    default void fuse(ItemStack stack,ItemStack stackToFuse){
        NbtCompound compound = stack.getOrCreateNbt();
        NbtCompound fuseCompound = new NbtCompound();
        stackToFuse.writeNbt(fuseCompound);
        compound.put("fusedItem",fuseCompound);

        stack.setNbt(compound);
    }
}
