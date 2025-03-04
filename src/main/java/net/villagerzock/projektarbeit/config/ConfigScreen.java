package net.villagerzock.projektarbeit.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.villagerzock.projektarbeit.client.screens.widgets.ScrollLayout;

public class ConfigScreen extends Screen {
    private final ConfigCategory config;
    private final Screen parentScreen;
    public ConfigScreen(ConfigCategory config) {
        super(config.title);
        this.config = config;
        this.parentScreen = MinecraftClient.getInstance().currentScreen;
    }
    public ConfigScreen(ConfigCategory config, Screen parentScreen) {
        super(config.title);
        this.config = config;
        this.parentScreen = parentScreen;
    }

    @Override
    protected void init() {
        int y = client.getWindow().getScaledHeight() - 30;
        int x = client.getWindow().getScaledWidth() / 2;
        ButtonWidget cancelButton = ButtonWidget.builder(Text.translatable("gui.cancel"),button -> {
            close();
        }).build();
        cancelButton.setY(y);
        cancelButton.setX(x - 160);
        ButtonWidget saveAndCloseButton = ButtonWidget.builder(Text.translatable("gui.save_and_close"),button -> {
            close();
            save();
        }).build();
        saveAndCloseButton.setY(y);
        saveAndCloseButton.setX(x + 10);
        ScrollLayout scrollLayout = new ScrollLayout(client ,client.getWindow().getScaledWidth(),client.getWindow().getScaledHeight(),32,client.getWindow().getScaledHeight() - 60, 25);
        scrollLayout.addChildren(config.getElements().toArray(ClickableWidget[]::new));
        addDrawableChild(scrollLayout);

        addDrawableChild(cancelButton);
        addDrawableChild(saveAndCloseButton);
    }
    public void save(){
        ConfigCategory configCategory = config;
        while (!(configCategory instanceof Config)){
            configCategory = configCategory.getParent();
        }
        if (configCategory instanceof Config config){
            config.save();
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        client.setScreen(parentScreen);
    }
}
