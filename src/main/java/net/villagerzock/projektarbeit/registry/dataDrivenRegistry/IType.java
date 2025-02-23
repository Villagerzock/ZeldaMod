package net.villagerzock.projektarbeit.registry.dataDrivenRegistry;

import net.minecraft.util.Identifier;

public interface IType<T extends IHaveASerializerAndType<T>> {
    ISerializer<T> getSerializer();
    boolean shouldSendToClient();
}
