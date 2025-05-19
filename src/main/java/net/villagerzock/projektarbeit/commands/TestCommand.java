package net.villagerzock.projektarbeit.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.abilities.Ability;
import net.villagerzock.projektarbeit.commands.arguments.RegistryArgumentType;
import net.villagerzock.projektarbeit.iMixins.IPlayerEntity;
import net.villagerzock.projektarbeit.registry.Registries;

public class TestCommand implements CommandRegistrationCallback {
    @Override
    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        commandDispatcher.register(CommandManager.literal("test_feature")
                .then(CommandManager.literal("abilities")
                        .then(CommandManager.argument("ability", IdentifierArgumentType.identifier())
                                .suggests(((commandContext, suggestionsBuilder) -> RegistryArgumentType.getSuggestions(Registries.abilities,suggestionsBuilder)))
                                .executes(this::abilities)
                        )));
    }

    private int abilities(CommandContext<ServerCommandSource> context) {
        Ability ability = Registries.abilities.get(IdentifierArgumentType.getIdentifier(context,"ability"));
        if (context.getSource().getPlayer() instanceof IPlayerEntity player){
            player.addUnlockedAbility(ability);
            player.setCurrentAbility(ability);
        }
        return 0;
    }
}
