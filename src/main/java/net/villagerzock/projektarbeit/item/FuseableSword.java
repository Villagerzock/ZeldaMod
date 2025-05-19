package net.villagerzock.projektarbeit.item;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;

public class FuseableSword extends SwordItem implements Fuseable {
    public FuseableSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public BakedModel getModel(BakedModel value, ItemStack stack, ModelTransformationMode mode, boolean leftHanded, MatrixStack ms, VertexConsumerProvider provider, int light, int overlay) {
        return value;
    }

    @Override
    public Text getName(ItemStack stack) {
        if (isFused(stack)){
            return super.getName(stack).copy().append(Text.literal(" ").append(Text.translatable("fuse.type.sword")));
        }
        return super.getName(stack);
    }
}
