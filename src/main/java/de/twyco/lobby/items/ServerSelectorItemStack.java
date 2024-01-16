package de.twyco.lobby.items;

import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;

public class ServerSelectorItemStack {
    public static ItemStack getServerSelector() {
        final ItemStack itemStack = new ItemStack(Material.COMPASS);
        itemStack.setAmount(1);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.GOLD + "Server Selector");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
