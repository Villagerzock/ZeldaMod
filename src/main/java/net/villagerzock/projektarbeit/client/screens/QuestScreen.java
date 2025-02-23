package net.villagerzock.projektarbeit.client.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.iMixins.IPlayerEntity;
import net.villagerzock.projektarbeit.quest.QuestState;

public class QuestScreen extends Screen {
    public static final Identifier TEXTURE = new Identifier(Main.MODID,"textures/gui/screens/quest_screen.png");
    private int scrollAmount = 0;
    public QuestScreen() {
        super(Text.empty());
    }
    public int getScrollBarHeight(){
        int fieldHeight = client.getWindow().getScaledHeight() - 16;
        if (client.player instanceof IPlayerEntity player){
            float amountOfQuestsOnScreen = fieldHeight / 34f;
            return Math.round(fieldHeight /  ((float) player.getQuests().size() / amountOfQuestsOnScreen));
        }
        return 0;
    }
    public boolean isBigEnoughToScroll(){
        int fieldHeight = client.getWindow().getScaledHeight() - 16;
        if (client.player instanceof IPlayerEntity player){
            return player.getQuests().size() * 34 > fieldHeight;
        }
        return true;
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        MatrixStack ms = context.getMatrices();
        super.render(context, mouseX, mouseY, delta);
        context.drawNineSlicedTexture(TEXTURE,0,0, client.getWindow().getScaledWidth() / 2,client.getWindow().getScaledHeight(),8,8,8,8,147,166,0,0);
        if (MinecraftClient.getInstance().player instanceof IPlayerEntity player){
            NbtCompound compound = new NbtCompound();
            MinecraftClient.getInstance().player.writeNbt(compound);
            int y = 8 + scrollAmount;
            context.enableScissor(8,8,client.getWindow().getScaledWidth() / 2 - 8,client.getWindow().getScaledHeight() - 8);
            for (QuestState state : player.getQuests()){
                context.drawNineSlicedTexture(TEXTURE,8,y, client.getWindow().getScaledWidth() / 2 - 20,32,4,4,4,4,16,16,149,0);
                ms.push();
                ms.translate(12,y + 8,0);
                ms.scale(2,2,2);
                context.drawText(client.textRenderer,state.getType().getTitle(),0,0,0xffffffff,true);
                ms.pop();
                y += 34;
            }
            int x = client.getWindow().getScaledWidth() / 2;
            context.fill(x-9,8 - scrollAmount,x-11,8 - scrollAmount + getScrollBarHeight(),0xFF4F4F4F);
            context.disableScissor();
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        boolean isBigEnough = isBigEnoughToScroll();
        if (scrollAmount + (amount * 10) <= 0 && isBigEnough)
            scrollAmount += (int) (amount * 10);
        return super.mouseScrolled(mouseX, mouseY, amount);
    }
}