package net.villagerzock.projektarbeit.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Config extends ConfigCategory{
    public Config(Identifier id) {
        super(id,null);
    }
    public enum Type{
        CLIENT,
        SERVER,
        NONE
        ;
    }

    public abstract Type type();
    public void save(){
        switch (type()){
            case CLIENT:
                saveToFile(saveToJson());
                break;
            case SERVER:
                saveToServer(saveToJson());
                break;
            case NONE:
                break;
        }
    }
    private JsonObject saveToJson(){
        JsonObject jsonObject = new JsonObject();
        for (ConfigEntry<?> childEntry : entries){
            jsonObject.add(childEntry.id.toString(),childEntry.serialize());
        }
        return jsonObject;
    }
    private void saveToFile(JsonObject object){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(new File(new File(FabricLoader.getInstance().getConfigDir().toFile(),id.getNamespace()),"client"),id.getPath() + ".json");

        if (!file.exists()){
            file.getParentFile().mkdir();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(object,writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void saveToServer(JsonObject object){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeIdentifier(id);
        buf.writeString(object.toString());
    }
    public void load(){
        switch (type()){
            case SERVER:
                break;
            case CLIENT:
                break;
            case NONE:
                break;
        }
    }
    public void loadFromServer(){

    }
    public void loadFromClient(){

    }
    public static void loadFromJson(){

    }
}
