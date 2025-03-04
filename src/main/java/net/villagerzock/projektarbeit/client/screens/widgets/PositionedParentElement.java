package net.villagerzock.projektarbeit.client.screens.widgets;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PositionedParentElement extends AbstractPositionedParentElement {
    public PositionedParentElement(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    private final List<Element> children = new ArrayList<>(){
        @Override
        public boolean add(Element element) {
            boolean result = super.add(element);
            update();
            return result;
        }

        @Override
        public boolean addAll(Collection<? extends Element> c) {
            boolean result = super.addAll(c);
            update();
            return result;
        }
    };

    @Override
    public List<? extends Element> children() {
        return children;
    }

    @Override
    public <T extends Element> void addChildren(T... children) {
        this.children.addAll(Arrays.stream(children).toList());
    }
    public void update(){
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

    }
}
