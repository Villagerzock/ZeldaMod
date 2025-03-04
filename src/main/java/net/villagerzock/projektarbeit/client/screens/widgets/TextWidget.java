package net.villagerzock.projektarbeit.client.screens.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;
import net.villagerzock.projektarbeit.client.screens.Color;

import javax.sound.midi.MidiChannel;

public class TextWidget extends AbstractPositionedWidget implements Drawable,Selectable {
    private final Text text;
    private final Color color;
    public TextWidget(int x, int y, int width, int height, Text text, Color color) {
        super(x, y, width, height);
        this.text = text;
        this.color = color;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.enableScissor(getX(),getY(),getX() + getHeight(),getY() + getHeight());
        context.drawTextWrapped(MinecraftClient.getInstance().textRenderer, text,getX(),getY(),getWidth(),color.getColor());
        context.disableScissor();
    }

    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }

    @Override
    public SelectionType getType() {
        return null;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }
}
