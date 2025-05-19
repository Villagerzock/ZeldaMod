package net.villagerzock.projektarbeit.client.screens;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenPos;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.Util;
import net.villagerzock.projektarbeit.abilities.Ability;
import net.villagerzock.projektarbeit.client.MainClient;
import net.villagerzock.projektarbeit.client.screens.widgets.Positioned;
import net.villagerzock.projektarbeit.iMixins.IPlayerEntity;
import net.villagerzock.projektarbeit.registry.Registries;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CircleSelectScreen extends Screen {
    public static final Identifier SMALL_CIRCLE = new Identifier(Main.MODID,"textures/gui/small_circle.png");
    public static final Identifier OUTSIDE = new Identifier(Main.MODID,"textures/gui/circle/");
    private static final Vector2i[] POSITIONS = {
            new Vector2i(131,0),
            new Vector2i(179,40),
            new Vector2i(179,131),
            new Vector2i(131,179),
            new Vector2i(40,179),
            new Vector2i(0,131),
            new Vector2i(0,40),
            new Vector2i(40,0)
    };
    public CircleSelectScreen() {
        super(Text.empty());
    }
    public List<Ability> getAbilitiesOnPage(int page, IPlayerEntity player){
        return Arrays.stream(player.getUnlockedAbilities()).toList();
    }
    public static void drawLine(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));
        // Zeichne viele kleine Punkte entlang der Linie (Bresenham-artig, aber simpel)
        for (int i = 0; i <= steps; i++) {
            float t = (float) i / steps;
            int x = Math.round(x1 + dx * t);
            int y = Math.round(y1 + dy * t);
            context.fill(x, y, x + 1, y + 1, color);
        }
    }
    public int getSelected(int x1, int y1, int x2, int y2){
        int dx = x2 - x1;
        int dy = y2 -y1;

        double angleRad = Math.atan2(dy,dx);
        double angleDeg = Math.toDegrees(angleRad) + 180;
        return Math.floorMod((int) Math.floor(angleDeg / 45) - 2,8);
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        MatrixStack ms = context.getMatrices();
        int x = client.getWindow().getScaledWidth() / 2;
        int y = client.getWindow().getScaledHeight() / 2;
        if (client.player instanceof IPlayerEntity player){
            List<Ability> abilities = getAbilitiesOnPage(0,player);
            int sel = abilities.contains(player.getCurrentAbility()) ? abilities.indexOf(player.getCurrentAbility()) : -1;
            for (int i : Util.range(8)){
                System.out.println(getSelected(x,y,mouseX,mouseY) + " == " + i);
                if (getSelected(x,y,mouseX,mouseY) == i){
                    context.setShaderColor(1f,216f / 255f, 0f,1);
                }else if (sel == i) {
                    context.setShaderColor(114f / 255f,137f / 255,218f / 255f,1f);
                }
                context.drawTexture(OUTSIDE.withSuffixedPath((i+1) + ".png"),x-128,y-128,0,0,256,256);
                context.setShaderColor(1f,1f,1f,1f);
               if (i < abilities.size()){
                   Identifier texture = Registries.abilities.getId(abilities.get(i)).withPrefixedPath("gui/abilities/").withSuffixedPath(".png");
                   context.drawTexture(texture,x - 128 + POSITIONS[i].x,y - 128 + POSITIONS[i].y,0,0,32,32,32,32);
               }
            }

            context.drawTexture(SMALL_CIRCLE,x - 4,y-4,0,0,8,8,8,8);
            drawLine(context,x,y,mouseX,mouseY,0xFFFFD800);
        }

    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (KeyBindingHelper.getBoundKeyOf(MainClient.ACTIVATE_ABILITY).getCode() == keyCode){
            close();
            MainClient.activateAbilityWasDown = 0;
        }
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
