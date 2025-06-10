package net.villagerzock.projektarbeit.client.ui;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.abilities.Ability;
import net.villagerzock.projektarbeit.client.MainClient;
import net.villagerzock.projektarbeit.iMixins.IPlayerEntity;
import net.villagerzock.projektarbeit.registry.Registries;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class Original implements HudRenderCallback {
    private final HeartDrawer drawer = new HeartDrawer(20,new Vector2i(30,10));
    @Override
    public void onHudRender(DrawContext drawContext, float v) {
        MinecraftClient client = MinecraftClient.getInstance();
        drawer.draw(drawContext);
        int y = drawer.getFinalY();
        //System.out.println(y);

        if (client.player instanceof IPlayerEntity player){
            if (player.getCurrentAbility() != null){
                Identifier texture = Registries.abilities.getId(player.getCurrentAbility()).withPrefixedPath("gui/abilities/").withSuffixedPath(".png");
                Text key = MainClient.ACTIVATE_ABILITY.getBoundKeyLocalizedText().copy().fillStyle(Style.EMPTY.withFont(Identifier.of(Main.MODID, "keys")));
                drawContext.drawTexture(texture,30 + client.textRenderer.getWidth(key),y + 3,0,0,32,32,32,32);
                drawContext.drawText(client.textRenderer,key,26,y + 15,0xffffffff,true);
                y += 36;
            }
            if (player.getCurrentAbility() != null){
                Ability ability = player.getCurrentAbility();
                if (ability.getBindings().containsKey(Ability.InteractMode.LEFT_HAND)){
                    int ay = client.getWindow().getScaledHeight() - 256;
                    int ax = client.getWindow().getScaledWidth() / 2 - 128;
                    for (KeyBinding keyBinding : ability.getBindings().get(Ability.InteractMode.LEFT_HAND)){
                        if (ability.canAbilityPartBeUsed(client.player,client.world,keyBinding)){
                            drawContext.drawText(client.textRenderer,ability.getBindingName(keyBinding).copy().append(keyBinding.getBoundKeyLocalizedText().copy().fillStyle(Style.EMPTY.withFont(Identifier.of(Main.MODID, "keys")))),ax,ay,0xffffffff,true);
                            ay -= 12;
                        }
                    }
                }
            }
        }
    }
}
