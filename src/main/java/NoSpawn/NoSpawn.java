package NoSpawn;

import NoSpawn.Api.Api;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class NoSpawn extends JavaPlugin implements Listener {
    public Logger logger = getLogger();
    public Server server = getServer();

    @Override
    public void onEnable() {
        logger.info("NoSpawn is enabled");
        server.getPluginManager().registerEvents(this, this);
        NoSpawnConfig noSpawnConfigCfgm = new NoSpawnConfig();
        noSpawnConfigCfgm.setup();
    }


    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        FileConfiguration configCfg = Api.getFileConfiguration(Api.getConfigFile("config"));
        List<String> noSpawnMobList = new ArrayList<>();
        String entityType = e.getEntity().getType().toString();
        noSpawnMobList.addAll(configCfg.getConfigurationSection("mobs").getKeys(false));
        if (noSpawnMobList.contains(entityType)) {
            int globalLightLevel = configCfg.getInt("globalMinimumLightLevel");
            boolean useGlobalLightLevel = configCfg.getBoolean("useGlobalLightLevel");
            String mobsConstant = "mobs.";
            String lightLevelConstant = ".lightLevel";
            String worldsConstant = ".worlds.";
            int entityLightLevel = e.getLocation().getBlock().getLightLevel();
            if (Boolean.TRUE.equals(useGlobalLightLevel)) {
                if (entityLightLevel > globalLightLevel) {
                    e.setCancelled(true);
                }
            } else {
                int lightLevel = configCfg.getInt(mobsConstant + entityType + lightLevelConstant);
                if (lightLevel < entityLightLevel && Boolean.TRUE.equals(configCfg.getBoolean(mobsConstant + entityType + worldsConstant + e.getEntity().getWorld().getName()))) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        FileConfiguration configCfg = Api.getFileConfiguration(Api.getConfigFile("config"));
        List<String> noSpawnMobList = new ArrayList<>();
        String entityType = e.getEntity().getType().toString();
        noSpawnMobList.addAll(configCfg.getConfigurationSection("mobs").getKeys(false));
        if (noSpawnMobList.contains(entityType)) {
            int globalLightLevel = configCfg.getInt("globalMinimumLightLevel");
            boolean useGlobalLightLevel = configCfg.getBoolean("useGlobalLightLevel");
            String mobsConstant = "mobs.";
            String lightLevelConstant = ".lightLevel";
            String worldsConstant = ".worlds.";
            int entityLightLevel = e.getLocation().getBlock().getLightLevel();
            if (Boolean.TRUE.equals(useGlobalLightLevel)) {
                if (entityLightLevel > globalLightLevel) {
                    e.setCancelled(true);
                }
            } else {
                int lightLevel = configCfg.getInt(mobsConstant + entityType + lightLevelConstant);
                if (lightLevel < entityLightLevel && Boolean.TRUE.equals(configCfg.getBoolean(mobsConstant + entityType + worldsConstant + e.getEntity().getWorld().getName()))) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("NoSpawn is disabled");
    }


}
