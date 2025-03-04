package net.villagerzock.projektarbeit.quest;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class QuestState {
    private final Quest type;
    private final List<Integer> completedRequirements = new ArrayList<>();

    public QuestState(Quest type) {
        this.type = type;
    }
    public void complete(Requirement requirement){
        completedRequirements.add(type.getRequirements().indexOf(requirement));
    }

    public List<Integer> getCompletedRequirements() {
        return completedRequirements;
    }
    public List<Requirement> getNonCompletedRequirements(){
        List<Requirement> requirements = type.getRequirements();
        for (int i : completedRequirements){
            requirements.remove(i);
        }
        return requirements;
    }
    public boolean isRequirementCompleted(Requirement requirement){
        return completedRequirements.contains(type.getRequirements().indexOf(requirement));
    }

    public Quest getType() {
        return type;
    }
    public void writeNbt(NbtCompound compound){
        NbtList completedRequirements = new NbtList();
        for (int i : this.completedRequirements){
            completedRequirements.add(NbtInt.of(i));
        }
        NbtList requirementData = new NbtList();
        for (Requirement requirement : type.getRequirements()){

        }
        compound.put("completedRequirements",completedRequirements);
    }
    public void readNbt(NbtCompound compound){
        completedRequirements.clear();
        /*NbtList completedRequirements = (NbtList) compound.get("completedRequirements");
        for (NbtElement nbtElement : completedRequirements){
            this.completedRequirements.add(((NbtInt) nbtElement).intValue());
        }*/
    }
}
