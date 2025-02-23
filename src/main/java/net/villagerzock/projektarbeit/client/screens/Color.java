package net.villagerzock.projektarbeit.client.screens;

import net.villagerzock.projektarbeit.item.Items;
import org.joml.Vector4i;

public class Color {
    private final int color;

    public Color(int colorCode) {
        this.color = colorCode;
    }
    public Color(Vector4i color){
        int r = (color.x * 255) & 0xFF;
        int g = (color.y * 255) & 0xFF;
        int b = (color.z * 255) & 0xFF;
        int a = (color.w * 255) & 0xFF;
        this.color = (r << 24) | (g << 16) | (b << 8) | a;
    }
    public int getColor(){
        return color;
    }
    public String getColorCode(){
        return "#" + Integer.toHexString(color);
    }
    public Vector4i getColorVec(){
        int r = ((color >> 24) & 0xFF) / 255;
        int g = ((color >> 16) & 0xFF) / 255;
        int b = ((color >> 8) & 0xFF) / 255;
        int a = (color & 0xFF) / 255;
        return new Vector4i(r,g,b,a);
    }
}
