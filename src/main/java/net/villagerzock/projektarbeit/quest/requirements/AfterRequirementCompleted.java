package net.villagerzock.projektarbeit.quest.requirements;

import net.villagerzock.projektarbeit.quest.Requirement;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IHaveASerializerAndType;

public interface AfterRequirementCompleted extends IHaveASerializerAndType<AfterRequirementCompleted> {
    void onRequirementCompleted(Requirement requirement);
}
