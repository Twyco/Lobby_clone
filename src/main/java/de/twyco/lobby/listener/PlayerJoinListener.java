package de.twyco.lobby.listener;

import de.twyco.lobby.items.ServerSelectorItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        player.getInventory().clear();
        player.getInventory().setItem(4, ServerSelectorItemStack.getServerSelector());
        event.setJoinMessage("");
    }
}
