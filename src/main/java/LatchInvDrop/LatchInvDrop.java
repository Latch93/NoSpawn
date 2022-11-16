package LatchInvDrop;

import LatchInvDrop.Api.Api;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class LatchInvDrop extends JavaPlugin implements Listener {
    public Logger logger = getLogger();
    public Server server = getServer();

    @Override
    public void onEnable() {
        logger.info(Constants.PLUGIN_NAME + " is enabled");
        server.getPluginManager().registerEvents(this, this);
        createConfigurationFiles();
    }

    @Override
    public void onDisable() {
        getLogger().info(Constants.PLUGIN_NAME + " is disabled");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) throws IOException {
        FileConfiguration configCfg = Api.getFileConfiguration(Api.getConfigFile(Constants.PLUGIN_CONFIG_FILE_NAME));
        String worldName = e.getEntity().getWorld().getName();
        if (Boolean.TRUE.equals(configCfg.getBoolean(worldName + ".enabled"))){
            String playerUUID = e.getEntity().getUniqueId().toString();
            FileConfiguration playersCfg = Api.getFileConfiguration(Api.getConfigFile(Constants.PLUGIN_PLAYERS_FILE_NAME));
            playersCfg.set(Constants.PLAYERS_YML_STRING + playerUUID + ".uuid", e.getEntity().getUniqueId().toString());
            playersCfg.set(Constants.PLAYERS_YML_STRING + playerUUID + ".name", e.getEntity().getName());
            int count = 0;
            List<String> slotList = configCfg.getStringList(worldName + ".slotsToSave");
            playersCfg.set(Constants.PLAYERS_YML_STRING + playerUUID + "." + worldName + ".inventory", null);
            List<ItemStack> itemsToDrop = new ArrayList<>();
            for (ItemStack itemInInventory : e.getEntity().getInventory()){
                if (slotList.contains(String.valueOf(count))){
                    playersCfg.set(Constants.PLAYERS_YML_STRING + playerUUID + "." + worldName + ".inventory.slot-" + count, itemInInventory);
                } else if (itemInInventory != null){
                    itemsToDrop.add(itemInInventory);
                }
                count++;
            }
            if (Boolean.TRUE.equals(configCfg.getBoolean(worldName  + ".itemsDropOnDeath"))) {
                e.getDrops().clear();
                for (ItemStack itemToDrop : itemsToDrop) {
                    e.getDrops().add(itemToDrop);
                }
            } else {
                e.getDrops().clear();
            }
            playersCfg.save(Api.getConfigFile(Constants.PLUGIN_PLAYERS_FILE_NAME));
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) throws IOException {
        FileConfiguration playersCfg = Api.getFileConfiguration(Api.getConfigFile(Constants.PLUGIN_PLAYERS_FILE_NAME));
        String worldName = "." + e.getPlayer().getWorld().getName();
        FileConfiguration configCfg = Api.getFileConfiguration(Api.getConfigFile(Constants.PLUGIN_CONFIG_FILE_NAME));
        if (Boolean.TRUE.equals(configCfg.getBoolean(e.getPlayer().getWorld().getName() + ".enabled")) && playersCfg.isSet(Constants.PLAYERS_YML_STRING + e.getPlayer().getUniqueId().toString() + worldName + ".inventory")){
            Inventory playerInventory = e.getPlayer().getInventory();
            for(String slot : Objects.requireNonNull(playersCfg.getConfigurationSection(Constants.PLAYERS_YML_STRING + e.getPlayer().getUniqueId().toString() + worldName + ".inventory")).getKeys(false)) {
                int slotNumber = Integer.parseInt(slot.split("-")[1]);
                assert false;
                playerInventory.setItem(slotNumber, playersCfg.getItemStack(Constants.PLAYERS_YML_STRING + e.getPlayer().getUniqueId().toString() + worldName +  ".inventory." + "slot-" + slotNumber));
            }
            playersCfg.set(Constants.PLAYERS_YML_STRING + e.getPlayer().getUniqueId().toString() + worldName + ".inventory", null);
        }
    }
    public static void createConfigurationFiles(){
        LatchInvDropConfig invDropConfigCfgm = new LatchInvDropConfig();
        LatchInvDropPlayerConfig invDropPlayerCfgm = new LatchInvDropPlayerConfig();
        invDropConfigCfgm.setup();
        invDropPlayerCfgm.setup();
    }

}
