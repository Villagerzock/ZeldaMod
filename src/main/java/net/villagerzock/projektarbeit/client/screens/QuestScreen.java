package net.villagerzock.projektarbeit.client.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.HoveredTooltipPositioner;
import net.minecraft.client.gui.tooltip.TooltipPositioner;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.client.WrappedText;
import net.villagerzock.projektarbeit.iMixins.IPlayerEntity;
import net.villagerzock.projektarbeit.quest.QuestCategory;
import net.villagerzock.projektarbeit.quest.QuestState;
import net.villagerzock.projektarbeit.quest.Requirement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            return Math.round(fieldHeight /  ((float) player.getQuests().size() / amountOfQuestsOnScreen)) + playerQuests.size() * 34;
        }
        return 0;
    }
    public boolean isBigEnoughToScroll(){
        int fieldHeight = client.getWindow().getScaledHeight() - 16;
        if (client.player instanceof IPlayerEntity player){
            return (player.getQuests().size() * 34) + scrollAmount + playerQuests.size() * 34 > fieldHeight;
        }
        return true;
    }
    private int animationTick = 0;
    private QuestState selectedState;
    private Map<QuestCategory,List<QuestState>> playerQuests;

    @Override
    protected void init() {
        super.init();
        if (client.player instanceof IPlayerEntity player){
            playerQuests = QuestCategory.findChildren(player);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTextWrapped(client.textRenderer,Text.literal("test"),0,0,50,0xFF6EC7FE);
        MatrixStack ms = context.getMatrices();
        super.render(context, mouseX, mouseY, delta);
        context.drawNineSlicedTexture(TEXTURE,0,0, client.getWindow().getScaledWidth() / 2,client.getWindow().getScaledHeight(),8,8,8,8,147,166,0,0);
        context.drawNineSlicedTexture(TEXTURE,client.getWindow().getScaledWidth() / 2,0, client.getWindow().getScaledWidth() / 2,client.getWindow().getScaledHeight() * 3 / 8,8,8,8,8,147,166,0,0);
        context.drawNineSlicedTexture(TEXTURE,client.getWindow().getScaledWidth() / 2,client.getWindow().getScaledHeight() * 3 / 8, client.getWindow().getScaledWidth() / 2,client.getWindow().getScaledHeight() * 5 / 8,8,8,8,8,147,166,0,0);

        if (MinecraftClient.getInstance().player instanceof IPlayerEntity player){
            NbtCompound compound = new NbtCompound();
            MinecraftClient.getInstance().player.writeNbt(compound);
            int y = 8 + scrollAmount;
            context.enableScissor(8,8,client.getWindow().getScaledWidth() / 2 - 8,client.getWindow().getScaledHeight() - 8);
            for (QuestCategory category : playerQuests.keySet()){
                y += 10;
                context.drawItem(category.icon,12,y);
                context.drawText(client.textRenderer,category.title,28,y + 8 - client.textRenderer.fontHeight / 2,0xFFFFFFFF,true);
                y += 24;
                for (QuestState state : playerQuests.get(category)){
                    context.drawNineSlicedTexture(TEXTURE,8,y, client.getWindow().getScaledWidth() / 2 - 20,32,4,4,4,4,16,16,149,0);
                    ms.push();
                    ms.translate(12,y + 8,0);
                    ms.scale(2,2,2);
                    context.drawText(client.textRenderer,state.getType().getTitle(),0,0,0xFF6EC7FE,true);
                    ms.pop();
                    y += 34;
                }
            }
            int x = client.getWindow().getScaledWidth() / 2;
            context.fill(x-9,8 - scrollAmount,x-11,8 - scrollAmount + getScrollBarHeight(),0xFF6EC7FE);
            context.disableScissor();
        }
        QuestState currentState = getSelectedState(mouseX,mouseY);
        if (currentState != null){
            context.drawTooltip(client.textRenderer,client.textRenderer.wrapLines(currentState.getType().getBody(),200), HoveredTooltipPositioner.INSTANCE,(int) mouseX,(int) mouseY);
        }
        if (selectedState != null){
            int y = client.getWindow().getScaledHeight() * 3 / 8 + 16;
            int x = client.getWindow().getScaledWidth() / 2 + 16;
            int width = client.getWindow().getScaledWidth()-16;
            ms.push();
            ms.translate(x,y,0);
            ms.scale(2,2,2);
            context.drawTextWrapped(client.textRenderer,selectedState.getType().getTitle(), 0,0,width,0xFF6EC7FE);
            y += client.textRenderer.getWrappedLinesHeight(selectedState.getType().getTitle(),width) * 2;
            ms.pop();
            context.drawHorizontalLine(x,width,y+=5,0xFF4F4F4F);
            y += 5;
            WrappedText text = WrappedText.of(WrappedText.of(selectedState.getType().getBody()).subtext(animationTick,false));
            text.append("\n\n\n");
            Main.LOGGER.info("Amount of Requirements in Quest: " + selectedState.getType().getRequirements().size());
            for (Requirement requirement : selectedState.getType().getRequirements()){
                TextColor color = TextColor.fromFormatting(Formatting.RED);
                if (selectedState.isRequirementCompleted(requirement))
                    color = TextColor.fromFormatting(Formatting.DARK_GREEN);
                if (requirement.getCompletionDisplay(color,client.player) == Requirement.EMPTY_TEXT)
                    continue;
                text.append(requirement.getCompletionDisplay(color,client.player));
            }
            context.drawTextWrapped(client.textRenderer,text,x,y,width,0xFF6EC7FE);
            animationTick++;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        QuestState currentState = getSelectedState(mouseX,mouseY);
        if (currentState != null){
            selectedState = currentState;
            animationTick = 0;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        boolean isBigEnough = isBigEnoughToScroll();
        if (scrollAmount + (amount * 8) <= 0 && (isBigEnough || amount > 0))
            scrollAmount += (int) (amount * 8);
        return super.mouseScrolled(mouseX, mouseY, amount);
    }
    public QuestState getSelectedState(double mouseX, double mouseY){
        if (mouseX >= 8 && mouseY >= 8){
            if (mouseX <= (double) client.getWindow().getScaledWidth() / 2 - 12 && mouseY <= (double) client.getWindow().getScaledHeight() - 8){
                double localMouseY = mouseY - 8;
                int selectedIndex = (int) Math.floor(localMouseY / 34);
                if (client.player instanceof IPlayerEntity player){
                    List<QuestState> states = new ArrayList<>();
                    for (QuestCategory category : playerQuests.keySet()){
                        states.add(null);
                        states.addAll(playerQuests.get(category));
                    }
                    if (selectedIndex < states.size()){
                        return states.get(selectedIndex);
                    }
                }
            }
        }
        return null;
    }
}