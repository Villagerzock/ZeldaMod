package net.villagerzock.projektarbeit.mixin;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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
    @Shadow public float strideDistance;
    @Unique
    private final List<QuestState> quests = new ArrayList<>();
    private final List<Identifier> completedQuests = new ArrayList<>();
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
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
    }

    @Inject(method = "writeCustomDataToNbt",at = @At("HEAD"))
    private void writeNbt(NbtCompound nbt, CallbackInfo ci){
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
        nbt.put("completedQuests",completedQuests);
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
