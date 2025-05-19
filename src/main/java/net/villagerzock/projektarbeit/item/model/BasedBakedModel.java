package net.villagerzock.projektarbeit.item.model;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.BasicBakedModel;
import net.minecraft.client.render.model.CubeFace;
import net.minecraft.client.render.model.json.ModelElementTexture;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.AffineTransformation;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

public class BasedBakedModel implements BakedModel {
    private final BakedModel base;
    public BasedBakedModel(BakedModel base){
        this.base = base;
    }
    public float[] getPositionMatrix(Vector3f from, Vector3f to) {
        float[] fs = new float[Direction.values().length];
        fs[CubeFace.DirectionIds.WEST] = from.x() / 16.0F;
        fs[CubeFace.DirectionIds.DOWN] = from.y() / 16.0F;
        fs[CubeFace.DirectionIds.NORTH] = from.z() / 16.0F;
        fs[CubeFace.DirectionIds.EAST] = to.x() / 16.0F;
        fs[CubeFace.DirectionIds.UP] = to.y() / 16.0F;
        fs[CubeFace.DirectionIds.SOUTH] = to.z() / 16.0F;
        return fs;
    }
    public int[] packVertexData(
            ModelElementTexture texture,
            Sprite sprite,
            Direction direction,
            float[] positionMatrix,
            AffineTransformation orientation,
            @Nullable net.minecraft.client.render.model.json.ModelRotation rotation,
            boolean shaded
    ) {
        int[] is = new int[32];

        for (int i = 0; i < 4; i++) {
            this.packVertexData(is, i, direction, texture, positionMatrix, sprite, orientation, rotation, shaded);
        }

        return is;
    }
    private void packVertexData(
            int[] vertices,
            int cornerIndex,
            Direction direction,
            ModelElementTexture texture,
            float[] positionMatrix,
            Sprite sprite,
            AffineTransformation orientation,
            @Nullable net.minecraft.client.render.model.json.ModelRotation rotation,
            boolean shaded
    ) {
        CubeFace.Corner corner = CubeFace.getFace(direction).getCorner(cornerIndex);
        Vector3f vector3f = new Vector3f(positionMatrix[corner.xSide], positionMatrix[corner.ySide], positionMatrix[corner.zSide]);
        this.rotateVertex(vector3f, rotation);
        this.transformVertex(vector3f, orientation);
        this.packVertexData(vertices, cornerIndex, vector3f, sprite, texture);
    }
    private void packVertexData(int[] vertices, int cornerIndex, Vector3f position, Sprite sprite, ModelElementTexture modelElementTexture) {
        int i = cornerIndex * 8;
        vertices[i] = Float.floatToRawIntBits(position.x());
        vertices[i + 1] = Float.floatToRawIntBits(position.y());
        vertices[i + 2] = Float.floatToRawIntBits(position.z());
        vertices[i + 3] = -1;
        vertices[i + 4] = Float.floatToRawIntBits(sprite.getFrameU((double)modelElementTexture.getU(cornerIndex)));
        vertices[i + 4 + 1] = Float.floatToRawIntBits(sprite.getFrameV((double)modelElementTexture.getV(cornerIndex)));
    }
    public void transformVertex(Vector3f vertex, AffineTransformation transformation) {
        if (transformation != AffineTransformation.identity()) {
            this.transformVertex(vertex, new Vector3f(0.5F, 0.5F, 0.5F), transformation.getMatrix(), new Vector3f(1.0F, 1.0F, 1.0F));
        }
    }
    private void transformVertex(Vector3f vertex, Vector3f origin, Matrix4f transformationMatrix, Vector3f scale) {
        Vector4f vector4f = transformationMatrix.transform(new Vector4f(vertex.x() - origin.x(), vertex.y() - origin.y(), vertex.z() - origin.z(), 1.0F));
        vector4f.mul(new Vector4f(scale, 1.0F));
        vertex.set(vector4f.x() + origin.x(), vector4f.y() + origin.y(), vector4f.z() + origin.z());
    }
    private static final float MIN_SCALE = 1.0F / (float)Math.cos((float) (Math.PI / 8)) - 1.0F;
    private static final float MAX_SCALE = 1.0F / (float)Math.cos((float) (Math.PI / 4)) - 1.0F;
    private void rotateVertex(Vector3f vector, @Nullable net.minecraft.client.render.model.json.ModelRotation rotation) {
        if (rotation != null) {
            Vector3f vector3f;
            Vector3f vector3f2;
            switch (rotation.axis()) {
                case X:
                    vector3f = new Vector3f(1.0F, 0.0F, 0.0F);
                    vector3f2 = new Vector3f(0.0F, 1.0F, 1.0F);
                    break;
                case Y:
                    vector3f = new Vector3f(0.0F, 1.0F, 0.0F);
                    vector3f2 = new Vector3f(1.0F, 0.0F, 1.0F);
                    break;
                case Z:
                    vector3f = new Vector3f(0.0F, 0.0F, 1.0F);
                    vector3f2 = new Vector3f(1.0F, 1.0F, 0.0F);
                    break;
                default:
                    throw new IllegalArgumentException("There are only 3 axes");
            }

            Quaternionf quaternionf = new Quaternionf().rotationAxis(rotation.angle() * (float) (Math.PI / 180.0), vector3f);
            if (rotation.rescale()) {
                if (Math.abs(rotation.angle()) == 22.5F) {
                    vector3f2.mul(MIN_SCALE);
                } else {
                    vector3f2.mul(MAX_SCALE);
                }

                vector3f2.add(1.0F, 1.0F, 1.0F);
            } else {
                vector3f2.set(1.0F, 1.0F, 1.0F);
            }

            this.transformVertex(vector, new Vector3f(rotation.origin()), new Matrix4f().rotation(quaternionf), vector3f2);
        }
    }
    public BakedModel getBase() {
        return base;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return base.getQuads(state,face,random);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return base.useAmbientOcclusion();
    }

    @Override
    public boolean hasDepth() {
        return base.hasDepth();
    }

    @Override
    public boolean isSideLit() {
        return base.isSideLit();
    }

    @Override
    public boolean isBuiltin() {
        return base.isBuiltin();
    }

    @Override
    public Sprite getParticleSprite() {
        return base.getParticleSprite();
    }

    @Override
    public ModelTransformation getTransformation() {
        return base.getTransformation();
    }

    @Override
    public ModelOverrideList getOverrides() {
        return base.getOverrides();
    }
}
