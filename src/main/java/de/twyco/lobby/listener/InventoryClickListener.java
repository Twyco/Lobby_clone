package de.twyco.lobby.listener;

import de.twyco.lobby.Lobby;
import de.twyco.lobby.inventorys.ServerSelectorInventory;
import de.twyco.lobby.server.ServerInstance;
import de.twyco.lobby.util.PluginMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener extends ListenerHelp implements Listener {

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (cancelEvent(player)) {
            event.setCancelled(true);
        }
        if (event.getView().getTitle().equals(ServerSelectorInventory.inventoryTitle)) {
            event.setCancelled(true);
        } else {
            return;
        }
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) {
            return;
        }
        for (ServerInstance server : Lobby.getServerList()) {
            ItemStack serverItem = server.getItem(player);
            boolean connect = ServerSelectorInventory.checkHeadItem(serverItem, clickedItem) || serverItem.isSimilar(clickedItem);
            if (connect && player.hasPermission(server.getJoinPermission()) && !server.equals(Lobby.getThisServer())) {
                PluginMessage pluginMessage = Lobby.getPluginMessage();
                if (server.getMaxPlayerCount() > pluginMessage.getCurrentPlayerCount(server)) {
                    Lobby.getPluginMessage().connect(player, server);
                }
            }
        }
    }
}
