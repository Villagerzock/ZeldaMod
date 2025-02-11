package net.villagerzock.projektarbeit.registry;

public interface IType<T> {
    ISerializer<T> getSerializer();
}
