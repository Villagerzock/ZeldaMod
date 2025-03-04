package net.villagerzock.projektarbeit.quest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.iMixins.IPlayerEntity;
import net.villagerzock.projektarbeit.quest.requirements.AfterRequirementCompleted;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IHaveASerializerAndType;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.ISerializer;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IType;

import java.util.List;

public abstract class Requirement implements IHaveASerializerAndType<Requirement> {
    private final IRequirement requirement;
    public static final MutableText EMPTY_TEXT = Text.empty();

    public Requirement(AfterRequirementCompleted completed){
        requirement = ()->{
            completed.onRequirementCompleted(this);
        };
    }



    public abstract boolean onEmit(QuestState state, PlayerEntity player);
    public static AfterRequirementCompleted readThen(JsonObject object){
        return null;
    }
    public Text getCompletionDisplay(TextColor completionColor, ClientPlayerEntity player){
        return EMPTY_TEXT;
    }
    @Override
    public abstract RequirementType getType();
    @Override
    public abstract RequirementSerializer getSerializer();
    public abstract static class RequirementType implements IType<Requirement>{
        private Identifier type;
        public Identifier getType() {
            return type;
        }

        public void setType(Identifier type) {
            this.type = type;
        }

        @Override
        public boolean shouldSendToClient() {
            return false;
        }
        public void emit(MinecraftServer server){
            for (ServerPlayerEntity entity : server.getPlayerManager().getPlayerList()){
                emit(entity);
            }
        }
        public void emit(ServerPlayerEntity entity){
            if (entity instanceof IPlayerEntity player){
                for (QuestState quest : player.getQuests()){
                    List<Requirement> requirements = quest.getType().getRequirements();
                    for (int i = 0; i < requirements.size(); i++) {
                        if (quest.getCompletedRequirements().contains(i))
                            continue;
                        Requirement requirement = requirements.get(i);
                        if (requirement.getType() == this){
                            if (requirement.onEmit(quest,entity))
                                quest.complete(requirement);
                        }
                    }
                }
            }
        }
    }
    public abstract static class RequirementSerializer implements ISerializer<Requirement>{

        @Override
        public void write(Requirement object, PacketByteBuf byteBuf) {

        }

        @Override
        public Requirement read(JsonElement jsonElement) {
            return null;
        }
        public Requirement read(JsonObject object){
            return read(object.getAsJsonObject(),readThen(object.getAsJsonObject().getAsJsonObject("then")));
        }
        public abstract Requirement read(JsonObject object, final AfterRequirementCompleted requirementCompleted);

        @Override
        public Requirement read(PacketByteBuf packetByteBuf) {
            return null;
        }
    }

    public interface IRequirement{
        void emit();
    }
    public interface IHaveRequirements{
        void onRequirementFinished(Requirement requirement);
    }
}
