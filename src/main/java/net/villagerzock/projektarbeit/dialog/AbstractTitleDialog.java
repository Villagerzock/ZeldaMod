package net.villagerzock.projektarbeit.dialog;

public abstract class AbstractTitleDialog extends Dialog {
    public abstract int getIntroTicks();
    public abstract int getStayingTicks();
    public abstract int getOutroTicks();
    @Override
    public final boolean needMouse() {
        return false;
    }
}
