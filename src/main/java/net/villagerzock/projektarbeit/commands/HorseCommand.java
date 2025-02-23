package net.villagerzock.projektarbeit.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;

import java.util.UUID;
import java.util.stream.Stream;

public class HorseCommand implements CommandRegistrationCallback {
    @Override
    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        commandDispatcher.register(
                CommandManager.literal("horse")
                        .then(CommandManager.literal("register")
                                .then(CommandManager.argument("horse", EntityArgumentType.entity())
                                        .then(CommandManager.argument("name", StringArgumentType.string())
                                                .executes(this::registerHorse)
                                        )
                                )
                        )
                        .then(CommandManager.literal("get")
                                .then(CommandManager.argument("horse",StringArgumentType.string())
                                        .suggests((context,suggestionsBuilder) ->{
                                            MinecraftServer server = context.getSource().getServer();
                                            NbtCompound registeredHorses = server.getDataCommandStorage().get(Identifier.of(Main.MODID,"registered_horses"));
                                            System.out.println("Suggesting Horse Names1");
                                            if (registeredHorses.contains(context.getSource().getPlayer().getUuid().toString())){
                                                System.out.println("Suggesting Horse Names2");
                                                if (registeredHorses.get(context.getSource().getPlayer().getUuid().toString()) instanceof NbtCompound playersHorses){
                                                    System.out.println("Suggesting Horse Names3");
                                                    for (String name : playersHorses.getKeys()){
                                                        StringBuilder builder = new StringBuilder();
                                                        builder.append("\"");
                                                        builder.append(name);
                                                        builder.append("\"");
                                                        suggestionsBuilder.suggest(builder.toString());
                                                    }
                                                }
                                            }
                                            return suggestionsBuilder.buildFuture();
                                        })
                                        .executes(this::getHorse)
                                )
                        )
                        .then(CommandManager.literal("data"))
        );
    }

    private int getHorse(CommandContext<ServerCommandSource> context) {
        MinecraftServer server = context.getSource().getServer();
        NbtCompound registeredHorses = server.getDataCommandStorage().get(Identifier.of(Main.MODID,"registered_horses"));
        if (registeredHorses.contains(context.getSource().getPlayer().getUuid().toString())){
            if (registeredHorses.get(context.getSource().getPlayer().getUuid().toString()) instanceof NbtCompound playersHorses){
                if (playersHorses.get(StringArgumentType.getString(context,"horse")) instanceof NbtCompound horse){
                    HorseEntity horseEntity;
                    if (horse.getBoolean("is_in_stable")){
                        horseEntity = new HorseEntity(EntityType.HORSE,context.getSource().getWorld());
                        if (horse.get("data") instanceof NbtCompound compound){
                            horseEntity.readNbt(compound);
                        }
                        context.getSource().getWorld().addEntities(Stream.of(horseEntity));
                    }else {
                        horseEntity = getEntityByUUID(server,UUID.fromString(horse.getString("uuid")), HorseEntity.class);
                        if (horseEntity == null){
                            context.getSource().sendError(Text.literal("It seems your Horse is Dead! you can still see it because you can revive it"));
                            return 1;
                        }
                    }
                    horseEntity.teleport(context.getSource().getWorld(),context.getSource().getPlayer().getX(),context.getSource().getPlayer().getY(),context.getSource().getPlayer().getZ(), PositionFlag.VALUES,0,0);
                }
            }
        }
        return 0;
    }
    public static <T extends Entity> T getEntityByUUID(MinecraftServer server, UUID uuid, Class<T> type){
        for (ServerWorld world : server.getWorlds()){
            Entity entity = world.getEntity(uuid);
            if (entity != null){
                return (T) entity;
            }
        }
        return null;
    }
    private int registerHorse(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) throws CommandSyntaxException {
        if (!serverCommandSourceCommandContext.getSource().getServer().getDataCommandStorage().getIds().toList().contains(Identifier.of(Main.MODID,"registered_horses"))){
            System.out.println("Creating Command Storage");
            serverCommandSourceCommandContext.getSource().getServer().getDataCommandStorage().set(Identifier.of(Main.MODID,"registered_horses"),new NbtCompound());
        }
        NbtCompound registeredHorses = serverCommandSourceCommandContext.getSource().getServer().getDataCommandStorage().get(Identifier.of(Main.MODID,"registered_horses"));
        UUID uuid = serverCommandSourceCommandContext.getSource().getPlayer().getUuid();
        String name = StringArgumentType.getString(serverCommandSourceCommandContext,"name");
        Entity entity = EntityArgumentType.getEntity(serverCommandSourceCommandContext,"horse");
        if (entity instanceof HorseEntity horse){
            if (!registeredHorses.contains(uuid.toString())){
                registeredHorses.put(uuid.toString(),new NbtCompound());
            }
            if (registeredHorses.get(uuid.toString()) instanceof NbtCompound playersHorses){
                if (playersHorses.contains(name)){
                    serverCommandSourceCommandContext.getSource().sendError(Text.literal("A horse with this name was already Registered"));
                    return 1;
                }else {
                    NbtCompound compound = new NbtCompound();
                    compound.putString("uuid",horse.getUuid().toString());
                    compound.putBoolean("is_in_stable",false);
                    NbtCompound horseData = new NbtCompound();
                    entity.writeNbt(horseData);
                    compound.put("data",horseData);
                    playersHorses.put(name,compound);
                    serverCommandSourceCommandContext.getSource().getServer().getDataCommandStorage().set(Identifier.of(Main.MODID,"registered_horses"),registeredHorses);
                    serverCommandSourceCommandContext.getSource().sendMessage(Text.literal("§5You successfully Registered this Horse"));
                }
            }
        }else {
            serverCommandSourceCommandContext.getSource().sendError(Text.literal("The entity you selected is not a Horse!"));
            return 2;
        }
        return 0;
    }
}
