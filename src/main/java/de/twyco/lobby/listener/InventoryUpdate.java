package de.twyco.lobby.listener;

import de.twyco.lobby.Lobby;
import de.twyco.lobby.inventorys.ServerSelectorInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.ArrayList;

public class InventoryUpdate implements Listener {

    private static ArrayList<Player> playerWithOpenInventory = null;

    public InventoryUpdate() {
        playerWithOpenInventory = new ArrayList<>();
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!event.getView().getTitle().equals(ServerSelectorInventory.inventoryTitle)) {
            return;
        }
        playerWithOpenInventory.add((Player) event.getPlayer());
        Lobby.startRefreshPlayerCount();
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!event.getView().getTitle().equals(ServerSelectorInventory.inventoryTitle)) {
            return;
        }
        playerWithOpenInventory.remove((Player) event.getPlayer());
        if (playerWithOpenInventory.isEmpty()) {
            Lobby.stopRefreshPlayerCount();
        }
    }

    public static boolean hasOpenInventory(Player player) {
        return playerWithOpenInventory.contains(player);
    }

}
