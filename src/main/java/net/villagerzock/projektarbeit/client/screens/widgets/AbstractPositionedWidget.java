package net.villagerzock.projektarbeit.client.screens.widgets;

import net.minecraft.client.gui.Element;

public abstract class AbstractPositionedWidget implements Element, Positioned {
    private int x;
    private int y;
    private int height;
    private int width;
    public AbstractPositionedWidget(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }
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
