package de.twyco.lobby.inventorys;

import de.twyco.lobby.Lobby;
import de.twyco.lobby.listener.InventoryUpdate;
import de.twyco.lobby.server.ServerInstance;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class ServerSelectorInventory {

    public static final String inventoryTitle = ChatColor.GOLD.toString() + ChatColor.BOLD + "Server Selection";

    public static Inventory getServerSelectorInventory(final Player player) {
        final Inventory inv = Bukkit.createInventory(player, 45, ServerSelectorInventory.inventoryTitle);
        ArrayList<ArrayList<ItemStack>> allServerTypesItems = getServerItems(player);
        setServer(inv, allServerTypesItems.get(0), 13);
        setServer(inv, allServerTypesItems.get(1), 31);
        setServer(inv, allServerTypesItems.get(2), 40);
        return inv;
    }

    public static void updatePlayerCount() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (InventoryUpdate.hasOpenInventory(player)) {
                updatePlayerCount(player);
            }
        }
    }

    private static void updatePlayerCount(Player player) {
        Inventory inv = player.getOpenInventory().getTopInventory();
        ArrayList<ArrayList<ItemStack>> allServerTypesItems = getServerItems(player);
        updateServerItems(inv, allServerTypesItems.get(0), 13);
        updateServerItems(inv, allServerTypesItems.get(1), 31);
        updateServerItems(inv, allServerTypesItems.get(2), 40);
    }

    private static ArrayList<ArrayList<ItemStack>> getServerItems(Player player){
        ArrayList<ArrayList<ItemStack>> allServerTypesItems = new ArrayList<>();
        final ArrayList<ItemStack> gameServer = new ArrayList<>();
        final ArrayList<ItemStack> lobbyServer = new ArrayList<>();
        final ArrayList<ItemStack> buildServer = new ArrayList<>();
        for (ServerInstance server : Lobby.getServerList()) {
            switch (server.getServerType()) {
                case GameServer: {
                    if (player.hasPermission(server.getSeePermission())) {
                        gameServer.add(server.getItem(player));
                    }
                    break;
                }
                case LobbyServer: {
                    if (player.hasPermission(server.getSeePermission())) {
                        lobbyServer.add(server.getItem(player));
                    }
                    break;
                }
                case BuildServer: {
                    if (player.hasPermission(server.getSeePermission())) {
                        buildServer.add(server.getItem(player));
                    }
                    break;
                }
            }
        }
        allServerTypesItems.add(gameServer);
        allServerTypesItems.add(lobbyServer);
        allServerTypesItems.add(buildServer);
        return allServerTypesItems;
    }

    private static void setServer(Inventory inventory, List<ItemStack> items, int itemSlot) {
        int gap = (items.size() % 2 == 0) ? itemSlot : -1;
        itemSlot -= items.size() / 2;
        for (ItemStack itemStack : items) {
            inventory.setItem(itemSlot, itemStack);
            itemSlot++;
            itemSlot += (itemSlot == gap) ? 1 : 0;
        }
    }

    private static void updateServerItems(Inventory inventory, List<ItemStack> items, int itemSlot) {
        int gap = (items.size() % 2 == 0) ? itemSlot : -1;
        itemSlot -= items.size() / 2;
        for (ItemStack itemStack : items) {
            updateItemMeta(inventory, itemStack, itemSlot);
            itemSlot++;
            itemSlot += (itemSlot == gap) ? 1 : 0;
        }
    }

    private static void updateItemMeta(Inventory inventory, ItemStack newItem, int itemSlot) {
        ItemStack oldItem = inventory.getItem(itemSlot);
        if (oldItem == null || oldItem.isSimilar(newItem) || checkHeadItem(oldItem, newItem)) {
            return;
        }
        oldItem.setItemMeta(newItem.getItemMeta());
    }

    public static boolean checkHeadItem(ItemStack clickedItem, ItemStack serverItem) {
        ItemMeta clickedItemMeta = clickedItem.getItemMeta();
        ItemMeta serverItemMeta = serverItem.getItemMeta();
        if (!(clickedItemMeta instanceof SkullMeta) || !(serverItemMeta instanceof SkullMeta)) {
            return false;
        }
        SkullMeta clickedSkullMeta = (SkullMeta) clickedItemMeta;
        SkullMeta serverSkullMeta = (SkullMeta) serverItemMeta;
        return serverSkullMeta.hashCode() == clickedSkullMeta.hashCode();
    }

}
