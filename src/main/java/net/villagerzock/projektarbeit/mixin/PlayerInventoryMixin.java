package net.villagerzock.projektarbeit.mixin;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.collection.DefaultedList;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Unique
    public final DefaultedList<ItemStack> keySlots = DefaultedList.ofSize(3, ItemStack.EMPTY);
    @Shadow @Final public DefaultedList<ItemStack> main;

    @Shadow @Final public DefaultedList<ItemStack> armor;

    @Shadow @Final public DefaultedList<ItemStack> offHand;

    @Shadow @Final private List<DefaultedList<ItemStack>> combinedInventory = ImmutableList.of(this.main, this.armor, this.offHand, this.keySlots);

    @Inject(method = "writeNbt",at = @At("HEAD"))
    public void saveNBT(NbtList nbtList, CallbackInfoReturnable<NbtList> cir){
        for (int i = 0; i < this.keySlots.size(); i++) {
            if (!this.keySlots.get(i).isEmpty()) {
                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.putByte("Slot", (byte)(i + 151));
                this.keySlots.get(i).writeNbt(nbtCompound);
                nbtList.add(nbtCompound);
            }
        }
    }
    /**
     * @author "Minecraft"
     * @reason I dont Know
     */
    @Overwrite
    public void readNbt(NbtList nbtList) {
        this.main.clear();
        this.armor.clear();
        this.offHand.clear();

        for (int i = 0; i < nbtList.size(); i++) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 255;
            ItemStack itemStack = ItemStack.fromNbt(nbtCompound);
            if (!itemStack.isEmpty()) {
                System.out.println(j);
                if (j >= 0 && j < this.main.size()) {
                    this.main.set(j, itemStack);
                } else if (j >= 100 && j < this.armor.size() + 100) {
                    this.armor.set(j - 100, itemStack);
                } else if (j >= 150 && j < this.offHand.size() + 150) {
                    this.offHand.set(j - 150, itemStack);
                }else if (j >= 151 && j < this.keySlots.size() + 151){
                    this.keySlots.set(j - 151,itemStack);
                }
            }
        }
    }
    @Redirect(method = "setStack", at= @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerInventory;combinedInventory:Ljava/util/List;",opcode = Opcodes.GETFIELD))
    public List<DefaultedList<ItemStack>> setStack(PlayerInventory instance){
        return this.combinedInventory;
    }
}
