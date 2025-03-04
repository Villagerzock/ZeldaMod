package net.villagerzock.projektarbeit.item.model;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BakedModelFusion implements BakedModel {
    private final BakedModel[] models;
    public BakedModelFusion(BakedModel... models){
        this.models = models;
    }
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        List<BakedQuad> quads = new ArrayList<>();
        for (BakedModel bakedModel : models){
            quads.addAll(bakedModel.getQuads(state,face,random));
        }
        return quads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return models[0].useAmbientOcclusion();
    }

    @Override
    public boolean hasDepth() {
        return models[0].hasDepth();
    }

    @Override
    public boolean isSideLit() {
        return models[0].isSideLit();
    }

    @Override
    public boolean isBuiltin() {
        return models[0].isBuiltin();
    }

    @Override
    public Sprite getParticleSprite() {
        return models[0].getParticleSprite();
    }

    @Override
    public ModelTransformation getTransformation() {
        return models[0].getTransformation();
    }

    @Override
    public ModelOverrideList getOverrides() {
        return models[0].getOverrides();
    }
}
