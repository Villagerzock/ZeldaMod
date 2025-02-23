package net.villagerzock.projektarbeit.iMixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.quest.Quest;
import net.villagerzock.projektarbeit.quest.QuestState;

import java.util.List;

public interface IPlayerEntity {
    List<QuestState> getQuests();
    void addQuest(QuestState state);
    boolean hasQuest(QuestState state);
    void completeQuest(Quest quest);
    boolean isQuestCompleted(Quest quest);
}
