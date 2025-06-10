package net.villagerzock.projektarbeit.client.screens.widgets;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.villagerzock.projektarbeit.Main;

public class MainMenuButton extends PressableWidget {
    private final PressAction pressAction;
    public MainMenuButton(int x, int y, int width, int height, Text text, PressAction pressAction) {
        super(x,y,width,height, text);
        this.pressAction = pressAction;
    }

    @Override
    public void onPress() {
        pressAction.onPress(this);
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        this.drawMessage(context, MinecraftClient.getInstance().textRenderer, 0xFF00FF90);
        if (this.isHovered()){
            context.drawTexture(Identifier.of(Main.MODID,"textures/gui/arrow_right.png"),getX() + width / 2 - client.textRenderer.getWidth(this.getMessage()) / 2 - 18, getY() +height / 2 - 8, 0,0,16,16,16,16);
            context.drawTexture(Identifier.of(Main.MODID,"textures/gui/arrow_left.png"),getX() + width / 2 + client.textRenderer.getWidth(this.getMessage()) / 2 + 2, getY() +height / 2 - 8, 0,0,16,16,16,16);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
    @Environment(EnvType.CLIENT)
    public interface PressAction {
        void onPress(MainMenuButton button);
    }
}
