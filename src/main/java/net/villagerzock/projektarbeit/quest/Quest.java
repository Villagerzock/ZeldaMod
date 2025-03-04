package net.villagerzock.projektarbeit.quest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.registry.Registries;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IHaveASerializerAndType;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.ISerializer;
import net.villagerzock.projektarbeit.registry.dataDrivenRegistry.IType;

import java.util.ArrayList;
import java.util.List;

public class Quest implements IHaveASerializerAndType<Quest> {
    private final Text Title;
    private final Text Body;
    private final QuestCategory category;
    private final BlockPos[] targets;
    private final List<Requirement> requirements;

    public Quest(Text title, Text body, QuestCategory category, BlockPos[] targets, List<Requirement> requirements) {
        Title = title;
        Body = body;
        this.category = category;
        this.targets = targets;
        this.requirements = requirements;
    }
    public List<Requirement> getRequirements() {
        return requirements;
    }

    @Override
    public ISerializer<Quest> getSerializer() {
        return Serializer.INSTANCE;
    }

    public QuestCategory getCategory() {
        return category;
    }

    @Override
    public IType<Quest> getType() {
        return Type.INSTANCE;
    }

    public Text getTitle() {
        return Title;
    }

    public Text getBody() {
        return Body;
    }

    public BlockPos[] getTargets() {
        return targets;
    }

    public static class Type implements IType<Quest>{
        public static final Type INSTANCE = new Type();
        @Override
        public ISerializer<Quest> getSerializer() {
            return Serializer.INSTANCE;
        }

        @Override
        public boolean shouldSendToClient() {
            return true;
        }
    }
    public static class Serializer implements ISerializer<Quest>{
        public static final Serializer INSTANCE = new Serializer();
        @Override
        public void write(Quest object, PacketByteBuf byteBuf) {
            byteBuf.writeText(object.getTitle());
            byteBuf.writeText(object.getBody());
            byteBuf.writeInt(object.getTargets().length);
            for (BlockPos blockPos : object.getTargets()){
                byteBuf.writeBlockPos(blockPos);
            }
            byteBuf.writeInt(object.requirements.size());
            for (Requirement requirement : object.requirements){
                try {
                    byteBuf.writeIdentifier(requirement.getType().getType());
                    requirement.getSerializer().write(requirement,byteBuf);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            byteBuf.writeIdentifier(Registries.quest_categories.getId(object.category));
        }

        @Override
        public Quest read(JsonElement jsonElement) {
            Main.LOGGER.info("Reading Quest from Json and Category is: ");
            JsonObject object = jsonElement.getAsJsonObject();
            Text title = Text.Serializer.fromJson(object.get("title"));
            Text body = Text.Serializer.fromJson(object.get("body"));
            JsonArray targetsArray = object.get("targets").getAsJsonArray();
            BlockPos[] targets = new BlockPos[targetsArray.size()];
            int i = 0;
            for (JsonElement element : targetsArray){
                JsonArray blockPosArray = element.getAsJsonArray();
                targets[i] = ISerializer.serializeBlockPos(blockPosArray);
                i++;
            }
            JsonArray requirementsArray = object.getAsJsonArray("requirements");
            List<Requirement> requirements = new ArrayList<>();
            for (JsonElement element : requirementsArray){
                JsonObject requirementObject = element.getAsJsonObject();
                Identifier type = Identifier.tryParse(requirementObject.get("type").getAsString());
                Requirement.RequirementType requirementType = Registries.requirements.get(type);
                if (requirementType == null)
                    continue;
                Requirement requirement = requirementType.getSerializer().read(requirementObject);
                requirements.add(requirement);
            }
            Identifier categoryID = Identifier.tryParse(object.get("category").getAsString());
            QuestCategory category = Registries.quest_categories.get(categoryID);

            return new Quest(title,body,category,targets,requirements);
        }

        @Override
        public Quest read(PacketByteBuf packetByteBuf) {

            Text title = packetByteBuf.readText();
            Text body = packetByteBuf.readText();
            BlockPos[] targets = new BlockPos[packetByteBuf.readInt()];

            for (int i = 0; i<targets.length; i++){
                targets[i] = packetByteBuf.readBlockPos();
            }

            int capacity = packetByteBuf.readInt();
            List<Requirement> requirements = new ArrayList<>();
            for (int i = 0; i<capacity; i++){
                Identifier type = packetByteBuf.readIdentifier();
                Requirement requirement = Registries.requirements.get(type).getSerializer().read(packetByteBuf);
                requirements.add(requirement);
            }
            Identifier id = packetByteBuf.readIdentifier();
            Main.LOGGER.info("CategoryID: " + id.toString());
            QuestCategory category = Registries.quest_categories.get(id);
            return new Quest(title,body,category,targets,List.of());
        }
    }
}
