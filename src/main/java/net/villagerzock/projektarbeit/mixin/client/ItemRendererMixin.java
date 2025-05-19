package net.villagerzock.projektarbeit.mixin.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.villagerzock.projektarbeit.item.CustomModel;
import net.villagerzock.projektarbeit.item.CustomModelProvider;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",at = @At(value = "HEAD"),argsOnly = true)
    public BakedModel customItemRendering(BakedModel value, ItemStack stack, ModelTransformationMode mode, boolean leftHanded, MatrixStack ms, VertexConsumerProvider provider,int light, int overlay){
        if (stack.getItem() instanceof CustomModel modelItem){
            BakedModel bakedModel = modelItem.getCustomModel(value,stack,mode,leftHanded,ms,provider,light,overlay);
            if (bakedModel != null){
                return bakedModel;
            }
        }else if (stack.getItem() instanceof CustomModelProvider modelProvider){
            BakedModel bakedModel = modelProvider.provideModel(stack).getCustomModel(value,stack,mode,leftHanded,ms,provider,light,overlay);
            if (bakedModel != null){
                return bakedModel;
            }
        }

        return value;
    }

}
