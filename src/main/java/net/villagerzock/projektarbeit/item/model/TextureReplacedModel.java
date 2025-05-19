package net.villagerzock.projektarbeit.item.model;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelElementTexture;
import net.minecraft.client.texture.Sprite;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.villagerzock.projektarbeit.Util;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextureReplacedModel extends BasedBakedModel{
    private final Sprite sprite;
    public TextureReplacedModel(BakedModel base, Identifier sprite) {
        super(base);
        this.sprite = MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(sprite);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        List<BakedQuad> quads = new ArrayList<>();
        for (BakedQuad quad : super.getQuads(state, face, random)){
            //System.out.println(Arrays.toString(quad.getVertexData()));
            //BakedQuad newQuad = new BakedQuad(packVertexData(new ModelElementTexture(new float[]{0.0F, 0.0F, 16.0F, 16.0F},0),sprite,face,getPositionMatrix());
            quads.add(quad);
        }
        return quads;
    }
}
