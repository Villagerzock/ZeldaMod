package net.villagerzock.projektarbeit.client.screens.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.EntryListWidget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ScrollLayout extends ElementListWidget<ScrollLayout.ElementEntry> {
    public ScrollLayout(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        super(client, width, height, top, bottom, itemHeight);
    }
    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }
    public <T extends ClickableWidget> void addChildren(T... children){
        for (ClickableWidget clickableWidget : children){
            addEntry(new ElementEntry(clickableWidget, this));
        }
    }
    public <T> void addChildren(EntryConstructor<T> constructor, T... children){
        for (T widget : children){
            addEntry(constructor.construct(widget,this));
        }
    }
    public interface EntryConstructor<T>{
        ElementEntry construct(T widget, ScrollLayout layout);
    }
    public static class ElementEntry extends ElementListWidget.Entry<ElementEntry> {
        private final List<ClickableWidget> children;
        private final ScrollLayout parent;
        public ElementEntry(ClickableWidget widget,ScrollLayout parent){
            children = List.of(widget);
            this.parent = parent;
        }
        public int getWidth(ClickableWidget widget){
            return widget.getWidth();
        }
        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.children.forEach((widget) -> {
                widget.setY(y);
                widget.setX(parent.width / 2 - getWidth(widget) / 2);
                widget.render(context, mouseX, mouseY, tickDelta);
            });
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            return super.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return children;
        }

        @Override
        public List<? extends Element> children() {
            return children;
        }
    }
}
