package de.twyco.lobby.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if(Bukkit.getOnlinePlayers().size() == Bukkit.getMaxPlayers()){
            Player player = event.getPlayer();
            if(player.hasPermission("lobby.bypassLimit")){
                event.allow();
            }
        }
    }
}
