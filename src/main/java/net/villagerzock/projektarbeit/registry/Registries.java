package net.villagerzock.projektarbeit.registry;

import com.mojang.serialization.Lifecycle;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.quest.Quest;
import net.villagerzock.projektarbeit.quest.Requirement;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.DataDrivenRegistry;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IHaveASerializerAndType;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IType;

public class Registries {
    public static final SimpleRegistry<DataDrivenRegistry<?>> dataDrivenRegistries;
    public static final DataDrivenRegistry<Quest> quests;
    public static final SimpleRegistry<Requirement.RequirementType> requirements;
    static {
        dataDrivenRegistries = create("data_driven_registries");
        quests = createDataDriven("quests",Quest.Type.INSTANCE);
        requirements = create("requirements");
    }
    public static <T> SimpleRegistry<T> create(String name){
        return new SimpleRegistry<>(RegistryKey.ofRegistry(Identifier.of(Main.MODID,name)), Lifecycle.stable());
    }
    public static <T extends IHaveASerializerAndType<T>> DataDrivenRegistry<T> createDataDriven(String name,IType<T> type){
        return createDataDriven(Identifier.of(Main.MODID,name),type);
    }
    public static <T extends IHaveASerializerAndType<T>> DataDrivenRegistry<T> createDataDriven(Identifier name, IType<T> type){
        DataDrivenRegistry<T> result = new DataDrivenRegistry<>(name, Lifecycle.stable(),type);
        if (dataDrivenRegistries.containsId(name)) {
            dataDrivenRegistries.set(dataDrivenRegistries.getRawId(dataDrivenRegistries.get(name)),RegistryKey.of(dataDrivenRegistries.getKey(),name),result,Lifecycle.stable());
            return result;
        }
        return Registry.register(dataDrivenRegistries,name,result);
    }
}
