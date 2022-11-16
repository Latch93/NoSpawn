package LatchInvDrop;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class LatchInvDropPlayerConfig {
    public static final LatchInvDrop plugin = getPlugin(LatchInvDrop.class);
    // Set up players.yml configuration file
    public void setup(){
        FileConfiguration invDropCfg;
        File invDropPlayersFile;
        // if the LatchInvDrop folder does not exist, create the LatchInvDrop folder
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
        }
        invDropPlayersFile = new File(plugin.getDataFolder(),  Constants.PLUGIN_PLAYERS_FILE_NAME + ".yml");
        invDropCfg = YamlConfiguration.loadConfiguration(invDropPlayersFile);
        //if the players.yml does not exist, create it
        if(!invDropPlayersFile.exists()){
            try {

                invDropCfg.save(invDropPlayersFile);
            }
            catch(IOException e){
                System.out.println(ChatColor.RED + "Could not create the " + Constants.PLUGIN_PLAYERS_FILE_NAME + ".yml file");
            }
        }
    }
}

