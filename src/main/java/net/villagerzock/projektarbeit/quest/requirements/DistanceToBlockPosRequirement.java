package net.villagerzock.projektarbeit.quest.requirements;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.PlayerEntity;
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
