package NoSpawn.Api;

import NoSpawn.NoSpawn;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class Api {
    public static final NoSpawn plugin = getPlugin(NoSpawn.class);
    public static File getConfigFile(String fileName){
        return new File(plugin.getDataFolder(), fileName + ".yml");
    }

    public static FileConfiguration getFileConfiguration(File file){
        return YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration loadConfig(String fileName) {
        return YamlConfiguration.loadConfiguration(getConfigFile(fileName));
    }
}
