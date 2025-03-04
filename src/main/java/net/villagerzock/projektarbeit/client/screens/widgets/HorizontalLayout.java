package net.villagerzock.projektarbeit.client.screens.widgets;

import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class HorizontalLayout extends PositionedParentElement {
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

    public HorizontalLayout(int x, int y, int width, int height,Element children) {
        super(x, y, width, height);
        addChildren(children);
    }
    public HorizontalLayout(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void update() {
        int x = getX();
        for (Element element : children){
            if (element instanceof Positioned positioned){
                positioned.setX(x);
                x += positioned.getWidth();
            }
        }
    }
}
