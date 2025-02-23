package net.villagerzock.projektarbeit.client.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class DrawUtil {
    public static void drawInfoField(DrawContext context, int x, int y,int titleHeight,int width, Text text,Color color){
        context.drawTextWrapped(MinecraftClient.getInstance().textRenderer, text,x + 5,y + titleHeight,width, color.getColor());
    }
}
