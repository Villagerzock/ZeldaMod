package net.villagerzock.projektarbeit.registry.dataDrivenRegistry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class DataDrivenRegistry<T extends IHaveASerializerAndType<T>> extends SimpleRegistry<T> implements IdentifiableResourceReloadListener, SynchronousResourceReloader {
    private final IType<T> type;
    public DataDrivenRegistry(Identifier name, Lifecycle lifecycle,IType<T> type) {
        super(RegistryKey.ofRegistry(name), lifecycle);
        this.type = type;
    }

    @Override
    public RegistryEntry.Reference<T> set(int i, RegistryKey<T> registryKey, T object, Lifecycle lifecycle) {
        return super.set(i, registryKey, object, lifecycle);
    }
    public T set(Identifier id, T object){
        if (containsId(id)){
            set(getRawId(get(id)),RegistryKey.of(this.getKey(),id), object,Lifecycle.stable());
        }else {
            add(RegistryKey.of(this.getKey(),id),object,Lifecycle.stable());
        }
        return object;
    }

    @Override
    public Identifier getFabricId() {
        return getKey().getValue();
    }

    public IType<T> getType() {
        return type;
    }

    @Override
    public void reload(ResourceManager manager) {
        try {
            Main.LOGGER.info("Reloading for Registry: " + getFabricId());
            for (Identifier fileName : manager.findResources(getFabricId().getPath(),path -> path.getPath().endsWith(".json")).keySet()){
                String path = fileName.getPath().replaceFirst(getFabricId().getPath() + "/","");
                Identifier file = Identifier.of(fileName.getNamespace(),path.substring(0,path.lastIndexOf(".json")));
                Main.LOGGER.info("Loading file: " + file);
                try (InputStream stream = manager.getResource(fileName).get().getInputStream()) {
                    try {
                        Reader reader = new InputStreamReader(stream,"UTF-8");
                        JsonElement element = new Gson().fromJson(reader, JsonElement.class);
                        set(file,type.getSerializer().read(element));
                    }catch (JsonSyntaxException e){
                        throw new RuntimeException(e);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public @Nullable T get(@Nullable Identifier id) {
        return super.get(id);
    }
}
