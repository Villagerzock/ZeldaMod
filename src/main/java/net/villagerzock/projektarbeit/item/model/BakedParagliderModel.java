package net.villagerzock.projektarbeit.item.model;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.BasicBakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.Util;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BakedParagliderModel extends BasedBakedModel {
    private final Identifier paragliderFabricID;

    public BakedParagliderModel(BakedModel baseModel,Identifier paragliderFabricID) {
        super(baseModel);
        this.paragliderFabricID = paragliderFabricID;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        List<BakedQuad> quads = getBase().getQuads(state,face,random);
        for (int i : Util.range(quads.size())){
            BakedQuad quad = quads.get(i);
            if (quad.getSprite().getAtlasId() == Identifier.of(Main.MODID,"paraglider/base.png")){
                Sprite sprite = MinecraftClient.getInstance().getSpriteAtlas(Identifier.of(Main.MODID,"paraglider")).apply(paragliderFabricID);
                quads.set(i,new BakedQuad(quad.getVertexData(),quad.getColorIndex(),quad.getFace(),sprite,quad.hasShade()));
            }
        }
        return quads;
    }
}
