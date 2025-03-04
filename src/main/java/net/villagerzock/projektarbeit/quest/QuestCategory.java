package net.villagerzock.projektarbeit.quest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.iMixins.IPlayerEntity;
import net.villagerzock.projektarbeit.registry.Registries;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IHaveASerializerAndType;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.ISerializer;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestCategory implements IHaveASerializerAndType<QuestCategory> {
    public final Text title;
    public final ItemStack icon;
    public static Map<QuestCategory,List<QuestState>> findChildren(IPlayerEntity player){
        Main.LOGGER.info("Finding Categories");
        Map<QuestCategory,List<QuestState>> result = new HashMap<>();
        for (QuestCategory category : Registries.quest_categories){
            List<QuestState> states = category.children(player.getQuests());
            result.put(category,states);
            Main.LOGGER.info("Getting Category: " + category.title.getString());
        }
        return result;
    }
    public QuestCategory(Text title, ItemStack icon) {
        this.title = title;
        this.icon = icon;
    }

    public List<QuestState> children(List<QuestState> states){
        List<QuestState> result = new ArrayList<>();
        Main.LOGGER.info("Amount of Quests on Player: " + states.size());
        for (QuestState state : states){
            Main.LOGGER.info(state.getType().getCategory() + " == " + Registries.quest_categories.getId(this));
            if (Registries.quest_categories.getId(state.getType().getCategory()) == Registries.quest_categories.getId(this)){
                result.add(state);
            }
        }
        return result;
    }

    @Override
    public ISerializer<QuestCategory> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public IType<QuestCategory> getType(){
        return Type.INSTANCE;
    }

    public static class Type implements IType<QuestCategory> {
        public static final Type INSTANCE = new Type();
        @Override
        public ISerializer<QuestCategory> getSerializer() {
            return Serializer.INSTANCE;
        }

        @Override
        public boolean shouldSendToClient() {
            return true;
        }
    }
    public static class Serializer implements ISerializer<QuestCategory>{
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(QuestCategory object, PacketByteBuf byteBuf) {
            byteBuf.writeText(object.title);
            NbtCompound compound = new NbtCompound();
            object.icon.writeNbt(compound);
            byteBuf.writeString(compound.asString());
        }

        @Override
        public QuestCategory read(JsonElement jsonElement) {
            JsonObject object = jsonElement.getAsJsonObject();
            Text title = Text.Serializer.fromJson(object.get("title"));
            ItemStack icon = iconFromJson(object.getAsJsonObject("icon"));
            return new QuestCategory(title,icon);
        }

        @Override
        public QuestCategory read(PacketByteBuf packetByteBuf) {
            try {
                Text title = packetByteBuf.readText();
                ItemStack icon = ItemStack.fromNbt(StringNbtReader.parse(packetByteBuf.readString()));
                return new QuestCategory(title,icon);
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        public static ItemStack iconFromJson(JsonObject json) {
            Item item = JsonHelper.getItem(json, "item");
            if (json.has("data")) {
                throw new JsonParseException("Disallowed data tag found");
            } else {
                ItemStack itemStack = new ItemStack(item);
                if (json.has("nbt")) {
                    try {
                        NbtCompound nbtCompound = StringNbtReader.parse(JsonHelper.asString(json.get("nbt"), "nbt"));
                        itemStack.setNbt(nbtCompound);
                    } catch (CommandSyntaxException var4) {
                        CommandSyntaxException commandSyntaxException = var4;
                        throw new JsonSyntaxException("Invalid nbt tag: " + commandSyntaxException.getMessage());
                    }
                }

                return itemStack;
            }
        }
    }
}
