package net.villagerzock.projektarbeit.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.villagerzock.projektarbeit.entity.custom.BokoblinEntity;

// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class BokoblinModel<T extends BokoblinEntity> extends SinglePartEntityModel<T> {
	private final ModelPart bokoblin;
	private final ModelPart body;
	private final ModelPart head;
	public BokoblinModel(ModelPart root) {
		this.bokoblin = root.getChild("bokoblin");
		this.body = this.bokoblin.getChild("body");
		this.head = this.body.getChild("torso").getChild("head");

	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bokoblin = modelPartData.addChild("bokoblin", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 10.0F, 0.0F));

		ModelPartData body = bokoblin.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, 0.0F, -3.5F, 12.0F, 7.0F, 7.0F, new Dilation(0.0F))
		.uv(24, 54).cuboid(-2.0F, 5.0F, 3.5F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -7.0F, 0.5F));

		ModelPartData bone5 = body.addChild("bone5", ModelPartBuilder.create().uv(42, 56).cuboid(-2.5F, -0.5F, -0.5F, 5.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(18, 45).cuboid(-3.0F, 2.0F, 0.0F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 5.0F, 3.5F));

		ModelPartData torso = body.addChild("torso", ModelPartBuilder.create().uv(0, 14).cuboid(-6.0F, -7.0F, 0.0F, 12.0F, 7.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -3.5F));

		ModelPartData head = torso.addChild("head", ModelPartBuilder.create().uv(0, 28).cuboid(-5.0F, -4.5F, -1.0F, 10.0F, 5.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 54).cuboid(0.0F, -7.5F, 0.0F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.5F, 7.0F));

		ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create().uv(32, 28).cuboid(-5.0F, 0.0F, 0.0F, 10.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.5F, -1.0F));

		ModelPartData left_ear = head.addChild("left_ear", ModelPartBuilder.create().uv(30, 47).cuboid(-6.0F, -4.0F, 0.0F, 6.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -0.5F, 2.0F));

		ModelPartData right_ear = head.addChild("right_ear", ModelPartBuilder.create().uv(42, 47).cuboid(0.0F, -4.0F, 0.0F, 6.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, -0.5F, 2.0F));

		ModelPartData nose = head.addChild("nose", ModelPartBuilder.create().uv(12, 53).cuboid(-2.5F, -1.5F, 0.0F, 5.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(22, 59).cuboid(-3.0F, -2.0F, 0.1F, 6.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 5.0F));

		ModelPartData left_arm = torso.addChild("left_arm", ModelPartBuilder.create().uv(38, 0).cuboid(-4.0F, -2.5F, -2.5F, 4.0F, 5.0F, 5.0F, new Dilation(0.0F))
		.uv(18, 39).cuboid(-8.0F, -1.5F, -1.5F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-6.0F, -4.5F, 2.5F));

		ModelPartData arm_seg_left = left_arm.addChild("arm_seg_left", ModelPartBuilder.create().uv(38, 10).cuboid(-8.0F, 0.0F, -1.75F, 8.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.0F, -1.5F, 0.25F));

		ModelPartData hand_left = arm_seg_left.addChild("hand_left", ModelPartBuilder.create().uv(0, 49).cuboid(-3.0F, -1.0F, -1.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.0F, 1.0F, -0.75F));

		ModelPartData bone = hand_left.addChild("bone", ModelPartBuilder.create().uv(54, 47).cuboid(-3.0F, 0.0F, -0.75F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(56, 0).cuboid(-3.0F, 0.0F, -0.25F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, -1.0F, -0.25F));

		ModelPartData bone2 = hand_left.addChild("bone2", ModelPartBuilder.create().uv(54, 53).cuboid(-3.0F, 0.0F, -0.75F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(54, 50).cuboid(-3.0F, 0.0F, -0.25F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, -1.0F, 1.25F));

		ModelPartData bone3 = hand_left.addChild("bone3", ModelPartBuilder.create().uv(54, 53).cuboid(-3.0F, 0.0F, -0.75F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(54, 50).cuboid(-3.0F, 0.0F, -0.25F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, 1.25F));

		ModelPartData right_arm = torso.addChild("right_arm", ModelPartBuilder.create().uv(38, 0).mirrored().cuboid(0.0F, -2.5F, -2.5F, 4.0F, 5.0F, 5.0F, new Dilation(0.0F)).mirrored(false)
		.uv(18, 39).mirrored().cuboid(4.0F, -1.5F, -1.5F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(6.0F, -4.5F, 2.5F));

		ModelPartData arm_seg_right = right_arm.addChild("arm_seg_right", ModelPartBuilder.create().uv(38, 10).mirrored().cuboid(0.0F, 0.0F, -1.75F, 8.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(8.0F, -1.5F, 0.25F));

		ModelPartData hand_right = arm_seg_right.addChild("hand_right", ModelPartBuilder.create().uv(0, 49).mirrored().cuboid(0.0F, -1.0F, -1.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(8.0F, 1.0F, -0.75F));

		ModelPartData bone6 = hand_right.addChild("bone6", ModelPartBuilder.create().uv(54, 47).mirrored().cuboid(0.0F, 0.0F, -0.75F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(56, 0).mirrored().cuboid(0.0F, 0.0F, -0.25F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.0F, -1.0F, -0.25F));

		ModelPartData bone7 = hand_right.addChild("bone7", ModelPartBuilder.create().uv(54, 53).mirrored().cuboid(0.0F, 0.0F, -0.75F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(54, 50).mirrored().cuboid(0.0F, 0.0F, -0.25F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.0F, -1.0F, 1.25F));

		ModelPartData bone8 = hand_right.addChild("bone8", ModelPartBuilder.create().uv(54, 53).mirrored().cuboid(0.0F, 0.0F, -0.75F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(54, 50).mirrored().cuboid(0.0F, 0.0F, -0.25F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 1.0F, 1.25F));

		ModelPartData left_leg = body.addChild("left_leg", ModelPartBuilder.create().uv(32, 36).cuboid(-3.0F, 0.0F, -3.5F, 6.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 7.0F, 0.0F));

		ModelPartData leg_seg_left = left_leg.addChild("leg_seg_left", ModelPartBuilder.create().uv(38, 16).cuboid(-2.0F, 0.0F, -5.0F, 4.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 2.5F));

		ModelPartData bone4 = leg_seg_left.addChild("bone4", ModelPartBuilder.create().uv(0, 39).cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 5.0F, 5.0F, new Dilation(-0.01F)), ModelTransform.pivot(0.0F, 5.0F, -5.0F));

		ModelPartData cube_r1 = bone4.addChild("cube_r1", ModelPartBuilder.create().uv(34, 54).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 5.0F, 0.1745F, 0.0F, 0.0F));

		ModelPartData right_leg = body.addChild("right_leg", ModelPartBuilder.create().uv(32, 36).mirrored().cuboid(-3.0F, 0.0F, -3.5F, 6.0F, 4.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.0F, 7.0F, 0.0F));

		ModelPartData leg_seg_right = right_leg.addChild("leg_seg_right", ModelPartBuilder.create().uv(38, 16).mirrored().cuboid(-2.0F, 0.0F, -5.0F, 4.0F, 5.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 4.0F, 2.5F));

		ModelPartData bone9 = leg_seg_right.addChild("bone9", ModelPartBuilder.create().uv(0, 39).mirrored().cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 5.0F, 5.0F, new Dilation(-0.01F)).mirrored(false), ModelTransform.pivot(0.0F, 5.0F, -5.0F));

		ModelPartData cube_r2 = bone9.addChild("cube_r2", ModelPartBuilder.create().uv(34, 54).mirrored().cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 2.0F, 5.0F, 0.1745F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(BokoblinEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bokoblin.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return bokoblin;
	}
}