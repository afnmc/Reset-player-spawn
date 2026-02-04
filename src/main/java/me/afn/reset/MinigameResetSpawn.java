package me.afn.reset;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MinigameResetSpawn extends JavaPlugin implements Listener {

    private Location lobby;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadLobby();
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("MinigameResetSpawn enabled (1.21.4)");
    }

    private void loadLobby() {
        String worldName = getConfig().getString("lobby.world");
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            getLogger().severe("Lobby world not found: " + worldName);
            return;
        }

        lobby = new Location(
                world,
                getConfig().getDouble("lobby.x"),
                getConfig().getDouble("lobby.y"),
                getConfig().getDouble("lobby.z"),
                (float) getConfig().getDouble("lobby.yaw"),
                (float) getConfig().getDouble("lobby.pitch")
        );
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Delay kecil biar aman dari join glitch
        Bukkit.getScheduler().runTaskLater(this, () -> {
            if (lobby != null && player.isOnline()) {
                player.teleport(lobby);
            }
        }, 5L);
    }
}
