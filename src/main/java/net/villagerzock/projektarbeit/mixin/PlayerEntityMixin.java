package net.villagerzock.projektarbeit.mixin;

import io.netty.handler.codec.serialization.ObjectDecoder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.abilities.Abilities;
import net.villagerzock.projektarbeit.abilities.Ability;
import net.villagerzock.projektarbeit.abilities.FuseAbility;
import net.villagerzock.projektarbeit.abilities.PlayerAbilityHandler;
import net.villagerzock.projektarbeit.events.PlayerEvents;
import net.villagerzock.projektarbeit.iMixins.IPlayerEntity;
import net.villagerzock.projektarbeit.quest.Quest;
import net.villagerzock.projektarbeit.quest.QuestState;
import net.villagerzock.projektarbeit.registry.Registries;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IPlayerEntity {
    @Unique
    private final List<QuestState> quests = new ArrayList<>();
    private final List<Identifier> completedQuests = new ArrayList<>();
    private final List<Ability> unlockedAbilities = new ArrayList<>();
    private Ability selectedAbility = null;
    private final PlayerAbilityHandler abilityHandler = new PlayerAbilityHandler();
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Ability getCurrentAbility() {
        return selectedAbility;
    }

    @Override
    public PlayerAbilityHandler getAbilityHandler() {
        return abilityHandler;
    }

    @Override
    public void setCurrentAbility(Ability selectedAbility) {
        if (!unlockedAbilities.contains(selectedAbility)){
            return;
        }
        if (getAsPlayerEntity() instanceof ServerPlayerEntity serverPlayer){
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(true);
            buf.writeInt(Registries.abilities.getRawId(selectedAbility));
            ServerPlayNetworking.send(serverPlayer,Main.UPDATE_ABILITY,buf);
        }else if (getAsPlayerEntity() instanceof ClientPlayerEntity clientPlayer){
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(Registries.abilities.getRawId(selectedAbility));
            ClientPlayNetworking.send(Main.UPDATE_ABILITY,buf);
        }
        this.selectedAbility = selectedAbility;
    }

    @Inject(method = "tick",at = @At("HEAD"))
    private void tickMovementInject(CallbackInfo ci){
        if (getAsPlayerEntity() instanceof ClientPlayerEntity entity){
            if ((getVelocity().horizontalLengthSquared() * 100) > 0.1d) {
                PlayerEvents.MOVEMENT_EVENT.invoker().accept(entity);
            }
        }
    }

    private PlayerEntity getAsPlayerEntity(){
        return ((PlayerEntity) (Object) this);
    }

    @Inject(method = "readCustomDataFromNbt",at = @At("HEAD"))
    private void readNbt(NbtCompound nbt, CallbackInfo ci){
        try {
            if (nbt.get("quests") instanceof NbtList list){
                quests.clear();
                for (NbtElement element : list){
                    if (element instanceof NbtCompound compound){
                        QuestState state = new QuestState(Registries.quests.get(Identifier.tryParse(compound.getString("type"))));
                        state.readNbt(compound);
                        addQuest(state);
                    }
                }
            }
            String selectedAbility = nbt.getString("SelectedAbility");
            this.selectedAbility = Registries.abilities.get(Identifier.tryParse(selectedAbility));
            unlockedAbilities.clear();
            if (nbt.get("UnlockedAbilities") instanceof NbtList list){
                for (NbtElement element : list){
                    if (element instanceof NbtString string){
                        String ability = string.asString();
                        unlockedAbilities.add(Registries.abilities.get(Identifier.tryParse(ability)));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Inject(method = "writeCustomDataToNbt",at = @At("HEAD"))
    private void writeNbt(NbtCompound nbt, CallbackInfo ci){
        try {
            NbtList quests = new NbtList();
            for (QuestState quest : this.quests){
                NbtCompound compound = new NbtCompound();
                Identifier id = Registries.quests.getId(quest.getType());
                if (completedQuests.contains(id)){
                    quests.remove(quest);
                    continue;
                }
                compound.putString("type",id.toString());
                quest.writeNbt(compound);
                quests.add(compound);
            }
            nbt.put("quests", quests);
            NbtList completedQuests = new NbtList();
            for (Identifier id : this.completedQuests){
                completedQuests.add(NbtString.of(id.toString()));
            }

            nbt.putString("SelectedAbility",Registries.abilities.getId(selectedAbility).toString());
            NbtList abilities = new NbtList();
            for (Ability ability : unlockedAbilities){
                abilities.add(NbtString.of(Registries.abilities.getId(ability).toString()));
            }
            nbt.put("UnlockedAbilities",abilities);
            nbt.put("completedQuests",completedQuests);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<QuestState> getQuests() {
        return quests;
    }

    @Override
    public void completeQuest(Quest quest) {
        this.completedQuests.add(Registries.quests.getId(quest));
    }

    @Override
    public boolean isQuestCompleted(Quest quest) {
        return this.completedQuests.contains(Registries.quests.getId(quest));
    }

    @Override
    public Ability[] getUnlockedAbilities() {
        return unlockedAbilities.toArray(Ability[]::new);
    }

    @Override
    public void addUnlockedAbility(Ability ability) {
        if (unlockedAbilities.contains(ability)) {
            return;
        }
        if (getAsPlayerEntity() instanceof ServerPlayerEntity serverPlayer){
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(false);
            buf.writeInt(Registries.abilities.getRawId(selectedAbility));
            ServerPlayNetworking.send(serverPlayer,Main.UPDATE_ABILITY,buf);
        }
        unlockedAbilities.add(ability);
    }

    @Override
    public void addQuest(QuestState identifier) {
        if (hasQuest(identifier) || isQuestCompleted(identifier.getType())) {
            Main.LOGGER.info("Couldnt Give Quest to Player because he already had it");
            return;
        }
        if (getAsPlayerEntity() instanceof ServerPlayerEntity serverPlayer) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeIdentifier(Registries.quests.getId(identifier.getType()));
            try {
                ServerPlayNetworking.send(serverPlayer, Main.GIVE_QUEST_TO_PLAYER, buf);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        quests.add(identifier);
    }

    @Override
    public boolean hasQuest(QuestState identifier) {
        Identifier stateID = Registries.quests.getId(identifier.getType());
        for (QuestState state : quests){
            Identifier currentStateID = Registries.quests.getId(state.getType());
            if (currentStateID == stateID)
                return true;
        }
        return false;
    }
}
