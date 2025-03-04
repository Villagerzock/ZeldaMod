package net.villagerzock.projektarbeit.client.screens.widgets;

import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;

public abstract class AbstractPositionedParentElement extends AbstractParentElement implements Positioned, Drawable, Selectable {
    private int x;
    private int y;
    private int height;
    private int width;

    public AbstractPositionedParentElement(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    @Override
    public SelectionType getType() {
        return SelectionType.NONE;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }

    public abstract <T extends Element> void addChildren(T... children);
    @Override
    public int getX() {
        return x;
    }
    @Override
    public void setX(int x) {
        this.x = x;
    }
    @Override
    public int getY() {
        return y;
    }
    @Override
    public void setY(int y) {
        this.y = y;
    }
    @Override
    public int getHeight() {
        return height;
    }
    @Override
    public void setHeight(int height) {
        this.height = height;
    }
    @Override
    public int getWidth() {
        return width;
    }
    @Override
    public void setWidth(int width) {
        this.width = width;
    }
}
