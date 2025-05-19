package net.villagerzock.projektarbeit.client.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.joml.Vector2i;

public class HeartDrawer {
    private final int rowAmount;
    private final Vector2i position;
    public static final Identifier TEXTURE = new Identifier("minecraft","textures/gui/icons.png");

    public HeartDrawer(int rowAmount, Vector2i position) {
        this.rowAmount = rowAmount;
        this.position = position;
    }
    private int integerCeilDiv(int i, int i1){
        if(Math.floor((double) i / i1) != (double) i / i1){
            return (int) (Math.floor((double) i / i1) + 1);
        }
        return i / i1;
    }
    private int integerCeilMod(int i, int i1){
        if(Math.floorMod(i, i1) != (double) i % i1){
            return (Math.floorMod(i,i1)) + 1;
        }
        return i % i1;
    }
    public int getHeight(){
        int hearts = (int) (Math.ceil(MinecraftClient.getInstance().player.getMaxHealth()) / 4);
        return (9 * integerCeilDiv(hearts,rowAmount));
    }
    public int getFinalY(){
        return getHeight() + position.y;
    }
    public void draw(DrawContext context){
        double hearts = Math.ceil(MinecraftClient.getInstance().player.getMaxHealth() / 4);
        double health = MinecraftClient.getInstance().player.getHealth() / 4.0d;
        for (int i = 0; i < hearts; i++) {
            int x = 9 * integerCeilMod(i,rowAmount);
            int y = 9 * Math.floorDiv(i,rowAmount);
            context.drawTexture(TEXTURE,x + position.x, y + position.y,16,0,9,9);
            if (i < Math.floor(health)){
                context.drawTexture(TEXTURE,x + position.x, y + position.y,52,0,9,9);
            }else if (i == Math.floor(health)){
                switch (Math.floorMod((int) MinecraftClient.getInstance().player.getHealth(),4)){
                    case 1:
                        context.drawTexture(TEXTURE,x + position.x, y + position.y,52,0,4,4);
                        break;
                    case 2:
                        context.drawTexture(TEXTURE,x + position.x, y + position.y,52,0,4,9);
                        break;
                    case 3:
                        context.drawTexture(TEXTURE,x + position.x, y + position.y,52,0,4,9);
                        context.drawTexture(TEXTURE,x + position.x + 4, y + position.y + 4, 56,4,4,4);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
