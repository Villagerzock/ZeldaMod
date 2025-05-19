package net.villagerzock.projektarbeit.example;

import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.ISerializer;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IType;

public class ExampleType implements IType<ExampleDatadrivenRegistry> {
    public static final ExampleType INSTANCE = new ExampleType();
    @Override
    public ISerializer<ExampleDatadrivenRegistry> getSerializer() {
        return ExampleSerializer.INSTANCE;
    }

    @Override
    public boolean shouldSendToClient() {
        return false;
    }
}
