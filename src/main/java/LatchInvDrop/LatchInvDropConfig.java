package LatchInvDrop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class LatchInvDropConfig {
    public static final LatchInvDrop plugin = getPlugin(LatchInvDrop.class);
    // Set up config.yml configuration file
    public void setup(){
        FileConfiguration invDropCfg;
        File invDropFile;
        // if the LatchInvDrop folder does not exist, create the LatchInvDrop folder
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
        }
        invDropFile = new File(plugin.getDataFolder(),  Constants.PLUGIN_CONFIG_FILE_NAME + ".yml");
        invDropCfg = YamlConfiguration.loadConfiguration(invDropFile);
        //if the config.yml does not exist, create it
        if(!invDropFile.exists()){
            try {
                for (World world : Bukkit.getWorlds()) {
                    invDropCfg.set(world.getName() + ".enabled", false);
                    invDropCfg.set(world.getName() + ".itemsDropOnDeath", true);
                    List<Integer> defaultSlotsToSave = new ArrayList<>();
                    defaultSlotsToSave.add(0);
                    defaultSlotsToSave.add(1);
                    defaultSlotsToSave.add(2);
                    defaultSlotsToSave.add(3);
                    defaultSlotsToSave.add(4);
                    defaultSlotsToSave.add(5);
                    defaultSlotsToSave.add(6);
                    defaultSlotsToSave.add(7);
                    defaultSlotsToSave.add(8);
                    defaultSlotsToSave.add(36);
                    defaultSlotsToSave.add(37);
                    defaultSlotsToSave.add(38);
                    defaultSlotsToSave.add(39);
                    defaultSlotsToSave.add(40);
                    invDropCfg.set(world.getName() + ".slotsToSave", defaultSlotsToSave);
                }
                invDropCfg.save(invDropFile);
            }
            catch(IOException e){
                System.out.println(ChatColor.RED + "Could not create the " + Constants.PLUGIN_CONFIG_FILE_NAME + ".yml file");
            }
        }
    }
}

