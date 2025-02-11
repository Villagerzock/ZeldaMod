package net.villagerzock.projektarbeit.registry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.villagerzock.projektarbeit.Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class DataDrivenRegistry<T extends IHaveASerializerAndType<T>> extends SimpleRegistry<T> implements IdentifiableResourceReloadListener, SynchronousResourceReloader {
    public DataDrivenRegistry(RegistryKey<? extends Registry<T>> key, Lifecycle lifecycle) {
        super(key, lifecycle);
    }

    @Override
    public Identifier getFabricId() {
        return getKey().getRegistry();
    }

    @Override
    public void reload(ResourceManager manager) {
        Iterator<T> iterator = this.iterator();
        while (iterator().hasNext()){
            T next = iterator.next();
            Identifier id = getId(next);
            for (Identifier fileName : manager.findResources(id.getPath(),path -> path.getPath().endsWith(".json")).keySet()){
                try (InputStream stream = manager.getResource(fileName).get().getInputStream()) {
                    try {
                        Reader reader = new InputStreamReader(stream,"UTF-8");
                        JsonElement element = new Gson().fromJson(reader, JsonElement.class);
                        Registry.register(this,fileName,next.getSerializer().read(element));
                    }catch (JsonSyntaxException e){
                        throw new RuntimeException(e);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
