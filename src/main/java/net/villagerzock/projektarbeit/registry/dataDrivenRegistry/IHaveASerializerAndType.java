package net.villagerzock.projektarbeit.registry;

public interface IHaveASerializerAndType<T> {
    ISerializer<T> getSerializer();
    IType<T> getType();
}
