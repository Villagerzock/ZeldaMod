package net.villagerzock.projektarbeit;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
@FunctionalInterface
public interface ReloadListener extends IdentifiableResourceReloadListener, SynchronousResourceReloader {
    @Override
    default Identifier getFabricId() {
        return Identifier.of(Main.MODID,"reloadListener");
    }
}
