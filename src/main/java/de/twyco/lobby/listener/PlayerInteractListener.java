package de.twyco.lobby.listener;

import de.twyco.lobby.inventorys.ServerSelectorInventory;
import de.twyco.lobby.items.ServerSelectorItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        event.setCancelled(true);
        final Action action = event.getAction();
        if (action.equals(Action.PHYSICAL) || event.getHand() != EquipmentSlot.HAND || event.getItem() == null) {
            return;
        }
        final Player player = event.getPlayer();
        if (event.getItem().isSimilar(ServerSelectorItemStack.getServerSelector())) {
            player.openInventory(ServerSelectorInventory.getServerSelectorInventory(player));
        }
    }
}
