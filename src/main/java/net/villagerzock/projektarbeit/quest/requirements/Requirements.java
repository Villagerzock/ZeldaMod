package net.villagerzock.projektarbeit.quest.requirements;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.quest.Requirement;
import net.villagerzock.projektarbeit.registry.Registries;

public class Requirements {
    public static final DistanceToBlockPosRequirement.RequirementType PLAYER_IN_DISTANCE_TO_BLOCK_POS = register("player_in_distance_to_block_pos", DistanceToBlockPosRequirement.RequirementType.INSTANCE);
    public static  <T extends Requirement.RequirementType> T register(String name, T instance){
        instance.setType(Identifier.of("minecraft",name));
        return Registry.register(Registries.requirements, Identifier.of("minecraft",name),instance);
    }
    public static void registerAll(){

    }
}
