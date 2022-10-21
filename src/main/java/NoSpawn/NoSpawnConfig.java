package NoSpawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class NoSpawnConfig {
    public static final NoSpawn plugin = getPlugin(NoSpawn.class);
    // Set up discordTextConfig.yml configuration file
    public void setup(){
        FileConfiguration noSpawnCfg;
        File noSpawnFile;
        // if the DiscordText folder does not exist, create the DiscordText folder
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
        }
        noSpawnFile = new File(plugin.getDataFolder(),  "config.yml");
        noSpawnCfg = YamlConfiguration.loadConfiguration(noSpawnFile);
        //if the discordTextConfig.yml does not exist, create it
        if(!noSpawnFile.exists()){
            try {
                List<EntityType> noSpawnMobs = new ArrayList<>();
                noSpawnCfg.set("globalMinimumLightLevel", 7);
                noSpawnCfg.set("useGlobalLightLevel", true);
                noSpawnMobs.add(EntityType.CREEPER);
                noSpawnMobs.add(EntityType.ZOMBIE);
                noSpawnMobs.add(EntityType.SKELETON);
                String mobs = "mobs.";
                String lightLevel = ".lightLevel";
                String worlds = ".worlds.";
                for (EntityType entityType : noSpawnMobs){
                    noSpawnCfg.set(mobs + entityType + lightLevel, 7);
                    for (World world : Bukkit.getServer().getWorlds()){
                        noSpawnCfg.set(mobs + entityType + worlds + world.getName(), true);
                    }
                }
                noSpawnCfg.save(noSpawnFile);
            }
            catch(IOException e){
                System.out.println(ChatColor.RED + "Could not create the config.yml file");
            }
        }
    }
}

