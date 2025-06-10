package net.villagerzock.projektarbeit.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerWarningScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.MusicSound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.Sounds;
import net.villagerzock.projektarbeit.client.screens.widgets.MainMenuButton;
import net.villagerzock.projektarbeit.item.Items;
import org.jetbrains.annotations.Nullable;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.math.Vec3d;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    private int tick = 0;
    protected TitleScreenMixin(Text title) {
        super(title);
    }
    @Inject(method = "init",cancellable = true,at = @At("HEAD"))
    public void initInject(CallbackInfo ci){
        ci.cancel();
        addDrawableChild(new MainMenuButton(client.getWindow().getScaledWidth() / 2 - 75,client.getWindow().getScaledHeight() - 100, 150,20,Text.translatable("menu.singleplayer"),(button)->{
            this.client.setScreen(new SelectWorldScreen(this));
        }));
        addDrawableChild(new MainMenuButton(client.getWindow().getScaledWidth() / 2 - 75,client.getWindow().getScaledHeight() - 80, 150,20,Text.translatable("menu.multiplayer"),(button)->{
            Screen screen = this.client.options.skipMultiplayerWarning ? new MultiplayerScreen(this) : new MultiplayerWarningScreen(this);
            this.client.setScreen((Screen)screen);
        }));
        addDrawableChild(new MainMenuButton(client.getWindow().getScaledWidth() / 2 - 75,client.getWindow().getScaledHeight() - 60, 150,20,Text.translatable("menu.options"),(button)->{
            this.client.setScreen(new OptionsScreen(this, this.client.options));
        }));
        addDrawableChild(new MainMenuButton(client.getWindow().getScaledWidth() / 2 - 75,client.getWindow().getScaledHeight() - 40, 150,20,Text.translatable("menu.quit"),(button)->{
            this.client.scheduleStop();
        }));
    }
    @Inject(method = "render",cancellable = true,at = @At("HEAD"))
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        context.fill(0,0,client.getWindow().getScaledWidth(),client.getWindow().getScaledHeight(),0xff000000);
        Main.drawBackground(context);
        MatrixStack ms = context.getMatrices();
        ms.push();
        ms.translate(0, 0, 2000);
        super.render(context, mouseX, mouseY, delta);
        ms.pop();
        ci.cancel();

        ms.push();
        ms.translate(client.getWindow().getScaledWidth() / 2f,client.getWindow().getScaledHeight() - 160,0);
        Quaternionf rotation = new Quaternionf(new AxisAngle4f(
                (float) Math.toRadians(tick/16),
                0.0f,0.0f,1.0f
        ));
        tick = Math.floorMod(tick + 1, 5760);
        ms.scale(1f,1.075f,1f);
        ms.multiply(rotation);
        ms.translate(-64f,-64f,0f);
        context.drawTexture(Identifier.of(Main.MODID,"textures/gui/title_background.png"),0,0,0,0,128,128,128,128);
        ms.pop();
        context.drawTexture(Identifier.of(Main.MODID,"textures/gui/title_foreground.png"),client.getWindow().getScaledWidth() / 2 - 64,client.getWindow().getScaledHeight() - 224,0,0,128,128,128,128);
    }

    @Override
    public void removed() {
        super.removed();
    }

    @Override
    public @Nullable MusicSound getMusic() {
        return Sounds.TITLE_SCREEN_MUSIC;
    }
}
