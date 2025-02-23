package net.villagerzock.projektarbeit.commands.arguments;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.registry.Registry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class RegistryArgumentType {
    public static <T> T getEntry(CommandContext<?> context, String name, Registry<T> registry){
        Identifier id = context.getArgument(name,Identifier.class);
        return registry.get(id);
    }

    public static  <T> CompletableFuture<Suggestions> getSuggestions(Registry<T> registry,SuggestionsBuilder builder) {
        for (Identifier identifier : registry.getIds()){
            builder.suggest(identifier.toString());
        }
        return builder.buildFuture();
    }
}
