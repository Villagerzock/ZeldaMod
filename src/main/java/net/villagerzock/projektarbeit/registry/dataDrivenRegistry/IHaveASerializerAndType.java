package net.villagerzock.projektarbeit.registry.dataDrivenRegistry;

public interface IHaveASerializerAndType<T extends IHaveASerializerAndType<T>> {
    ISerializer<T> getSerializer();
    IType<T> getType();
}
