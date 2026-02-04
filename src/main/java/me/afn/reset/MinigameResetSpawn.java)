package me.afn.reset;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MinigameResetSpawn extends JavaPlugin implements Listener {

    private File dataFile;
    private FileConfiguration dataConfig;
    private List<String> inMinigame = new ArrayList<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        createDataFile();
        inMinigame = dataConfig.getStringList("players");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerSpawn(PlayerSpawnLocationEvent e) {
        Player p = e.getPlayer();
        if (inMinigame.contains(p.getUniqueId().toString())) {
            Location lobbyLoc = getLobbyLocation();
            if (lobbyLoc != null) {
                e.setSpawnLocation(lobbyLoc); // Paksa spawn ke lobby
                setInMinigame(p, false);
                Bukkit.getScheduler().runTaskLater(this, () -> 
                    p.sendMessage("§c[System] Kamu dikembalikan ke Lobby!"), 20L);
            }
        }
    }

    public void setInMinigame(Player p, boolean status) {
        String uuid = p.getUniqueId().toString();
        if (status) { if (!inMinigame.contains(uuid)) inMinigame.add(uuid); }
        else { inMinigame.remove(uuid); }
        saveData();
    }

    private Location getLobbyLocation() {
        return new Location(
            Bukkit.getWorld(getConfig().getString("lobby.world", "world")),
            getConfig().getDouble("lobby.x"),
            getConfig().getDouble("lobby.y"),
            getConfig().getDouble("lobby.z"),
            (float) getConfig().getDouble("lobby.yaw"),
            (float) getConfig().getDouble("lobby.pitch")
        );
    }

    private void createDataFile() {
        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            try { dataFile.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    private void saveData() {
        dataConfig.set("players", inMinigame);
        try { dataConfig.save(dataFile); } catch (IOException e) { e.printStackTrace(); }
    }
}

