package net.villagerzock.projektarbeit.quest.requirements;

import com.google.gson.JsonObject;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.villagerzock.projektarbeit.quest.QuestState;
import net.villagerzock.projektarbeit.quest.Requirement;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.ISerializer;

public class DistanceToBlockPosRequirement extends Requirement {
    private final BlockPos positon;
    private final double distance;
    public DistanceToBlockPosRequirement(AfterRequirementCompleted completed,BlockPos positon, double distance) {
        super(completed);
        this.positon = positon;
        this.distance = distance;
    }

    @Override
    public boolean onEmit(QuestState state, PlayerEntity entity) {
        Position playerPosition = entity.getPos();
        return positon.isWithinDistance(playerPosition,distance);
    }

    @Override
    public Text getCompletionDisplay(TextColor completionColor, ClientPlayerEntity player) {
        return Text.translatable("requirement.zelda.distance_to").append(Text.literal(": ").append(Text.literal(String.valueOf((int) Math.sqrt(positon.getSquaredDistance(player.getBlockPos()))))).append(Text.literal(" ").append(Text.translatable("requirement.zelda.blocks"))));
    }

    @Override
    public RequirementSerializer getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RequirementType getType() {
        return RequirementType.INSTANCE;
    }
    public static class RequirementType extends Requirement.RequirementType {
        public static final RequirementType INSTANCE = new RequirementType();
        @Override
        public ISerializer<Requirement> getSerializer() {
            return Serializer.INSTANCE;
        }
    }
    public static class Serializer extends RequirementSerializer{
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public Requirement read(JsonObject object, AfterRequirementCompleted requirementCompleted) {
            BlockPos pos = ISerializer.serializeBlockPos(object.getAsJsonArray("pos"));
            double range = object.get("range").getAsDouble();
            return new DistanceToBlockPosRequirement(requirementCompleted,pos,range);
        }
    }

}
