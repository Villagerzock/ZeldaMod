package net.villagerzock.projektarbeit.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.argument.RegistryEntryArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.NbtTextContent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.villagerzock.projektarbeit.commands.arguments.RegistryArgumentType;
import net.villagerzock.projektarbeit.events.PlayerEvents;
import net.villagerzock.projektarbeit.iMixins.IPlayerEntity;
import net.villagerzock.projektarbeit.mixin.PlayerEntityMixin;
import net.villagerzock.projektarbeit.quest.Quest;
import net.villagerzock.projektarbeit.quest.QuestState;
import net.villagerzock.projektarbeit.registry.Registries;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class QuestCommand implements CommandRegistrationCallback {
    @Override
    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        commandDispatcher.register(
                CommandManager.literal("quest")
                        .executes(this::storageTest)
                        .then(
                                CommandManager.literal("show")
                                        .then(
                                                CommandManager.argument("quest", IdentifierArgumentType.identifier())
                                                        .suggests((context, builder) -> RegistryArgumentType.getSuggestions(Registries.quests,builder))
                                                        .executes(this::showQuest)
                                        )
                        )
                        .then(
                                CommandManager.literal("give")
                                        .then(
                                                CommandManager.argument("players",EntityArgumentType.players())
                                                        .then(
                                                                CommandManager.argument("quest", IdentifierArgumentType.identifier())
                                                                        .suggests((context, builder) -> RegistryArgumentType.getSuggestions(Registries.quests,builder))
                                                                        .executes(this::giveQuest)
                                                        )
                                        )
                        )
        );
    }

    private int storageTest(CommandContext<ServerCommandSource> context) {
        context.getSource().sendMessage(Text.literal(((NbtList) context.getSource().getServer().getDataCommandStorage().get(Identifier.of("minecraft","test")).get("test")).get(0).getClass().getCanonicalName()));
        return 0;
    }

    private int giveQuest(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<ServerPlayerEntity> playerEntities = EntityArgumentType.getPlayers(context,"players");
        Quest quest = RegistryArgumentType.getEntry(context,"quest",Registries.quests);
        for (ServerPlayerEntity player : playerEntities){
            if (player instanceof IPlayerEntity entity){
                entity.addQuest(new QuestState(quest));
            }
        }
        return 0;
    }

    private int showQuest(CommandContext<ServerCommandSource> context) {
        Quest quest = RegistryArgumentType.getEntry(context,"quest",Registries.quests);
        context.getSource().sendMessage(Text.literal("Title: ").append(quest.getTitle()));
        context.getSource().sendMessage(Text.literal("Body: ").append(quest.getTitle()));
        context.getSource().sendMessage(Text.literal("Targets: ").append(Text.literal(this.getTargetString(quest))));
        return 0;
    }

    private String getTargetString(Quest quest) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        Iterator<BlockPos> iterator = Arrays.stream(quest.getTargets()).iterator();
        while (iterator.hasNext()){
            BlockPos pos = iterator.next();
            builder.append("[");
            builder.append(pos.getX());
            builder.append(", ");
            builder.append(pos.getY());
            builder.append(", ");
            builder.append(pos.getZ());
            builder.append("]");
            if (iterator.hasNext()){
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
