package me.valdoveste.erase_uuid_on_death.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CustomConfig {
    private static File file;
    private static FileConfiguration customFile;

    public static void setupFile(){
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Erase_UUID_On_Death")).getDataFolder(), "players.yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){
                System.out.println("Could not create the players.yml file. " + e);
            }
        }

        customFile = YamlConfiguration.loadConfiguration((file));

    }

    public static void addPlayer(List<String> playerList){
        customFile.set("PlayersKeepLevel", playerList);
        try {
            customFile.save(file);
            reloadFile();
        }catch (IOException e){
            System.out.println("Could not save/reload the file. " + e);
        }
    }

    public static FileConfiguration getFile() {
        return customFile;
    }

    public static void saveFile() {
        try {
            customFile.save(file);
        }catch (IOException e){
            System.out.println("Could not save the file. " + e);
        }
    }

    public static void reloadFile(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
