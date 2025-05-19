package net.villagerzock.projektarbeit.client.ui;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.villagerzock.projektarbeit.config.ClientConfig;
import net.villagerzock.projektarbeit.config.ConfigCategory;
import net.villagerzock.projektarbeit.config.ModConfig;

public class UI implements HudRenderCallback {
    @Override
    public void onHudRender(DrawContext drawContext, float v) {
        switch (new ModConfig().getCategory("client").getEntry("ui-mode", ClientConfig.UIMode.ORIGINAL)){
            case ORIGINAL:
                new Original().onHudRender(drawContext,v);
                break;
            case MINECRAFTY:
                new Minecrafty().onHudRender(drawContext,v);
                break;
        }
    }
}
