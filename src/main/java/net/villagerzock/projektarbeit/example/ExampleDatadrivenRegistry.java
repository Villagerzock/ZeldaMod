package net.villagerzock.projektarbeit.example;

import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IHaveASerializerAndType;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.ISerializer;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IType;

public class ExampleDatadrivenRegistry implements IHaveASerializerAndType<ExampleDatadrivenRegistry> {
    // making a Simple Name Field
    private final String name;

    public ExampleDatadrivenRegistry(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public ISerializer<ExampleDatadrivenRegistry> getSerializer() {
        // Returning the Global Serializer Instance
        return ExampleSerializer.INSTANCE;
    }

    @Override
    public IType<ExampleDatadrivenRegistry> getType() {
        // Returning the Global Type Instance
        return ExampleType.INSTANCE;
    }
}
