package net.villagerzock.projektarbeit.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class PlayerEvents {
    public static final Event<Consumer<PlayerEntity>> MOVEMENT_EVENT = EventFactory.createArrayBacked(Consumer.class,consumers -> player -> emitEvent(consumers, consumer -> consumer.accept(player)));
    private static <T> void emitEvent(T[] ts,Consumer<T> runnable){
        for (T t : ts){
            runnable.accept(t);
        }
    }
}
