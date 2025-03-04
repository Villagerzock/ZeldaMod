package net.villagerzock.projektarbeit.item.model;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.AffineTransformation;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppliedBakedModel implements BakedModel {
    private BakedModel model;
    private Transform transform;
    public AppliedBakedModel(BakedModel model, Transform transform){

    }
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        List<BakedQuad> quads = model.getQuads(state,face,random);
        List<BakedQuad> transformedQuads = new ArrayList<>();
        for (BakedQuad quad : quads){
            transformedQuads.add(applyTransform(quad));
        }
        return transformedQuads;
    }
    private BakedQuad applyTransform(BakedQuad quad){
        int[] vertexData = quad.getVertexData().clone();

        MatrixStack ms = new MatrixStack();
        ms.translate(transform.getPosition().x,transform.getPosition().y,transform.getPosition().z);
        ms.scale(transform.getScale().x,transform.getScale().y,transform.getScale().z);
        ms.multiply(transform.getRotation());

        for (int i = 0; i < 4; i++) {
            int index = i * 8;
            float x = Float.intBitsToFloat(vertexData[index]);
            float y = Float.intBitsToFloat(vertexData[index + 1]);
            float z = Float.intBitsToFloat(vertexData[index] + 2);
            Vector3f pos = new Vector3f(x,y,z);
            ms.peek().getPositionMatrix().transformPosition(pos);

            vertexData[index] = Float.floatToRawIntBits(pos.x);
            vertexData[index + 1] = Float.floatToRawIntBits(pos.y);
            vertexData[index + 2] = Float.floatToRawIntBits(pos.z);
        }
        return new BakedQuad(vertexData,quad.getColorIndex(),quad.getFace(),quad.getSprite(),quad.hasShade());
    }

    @Override
    public boolean useAmbientOcclusion() {
        return model.useAmbientOcclusion();
    }

    @Override
    public boolean hasDepth() {
        return model.hasDepth();
    }

    @Override
    public boolean isSideLit() {
        return model.isSideLit();
    }

    @Override
    public boolean isBuiltin() {
        return model.isBuiltin();
    }

    @Override
    public Sprite getParticleSprite() {
        return model.getParticleSprite();
    }

    @Override
    public ModelTransformation getTransformation() {
        return model.getTransformation();
    }

    @Override
    public ModelOverrideList getOverrides() {
        return model.getOverrides();
    }
}
