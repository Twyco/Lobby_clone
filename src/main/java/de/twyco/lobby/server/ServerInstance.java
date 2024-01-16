package de.twyco.lobby.server;

import de.twyco.lobby.Lobby;
import de.twyco.lobby.util.PluginMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerInstance {

    protected final String serverName;
    protected final String serverDisplayName;
    protected final String seePermission;
    protected final String joinPermission;
    protected final int maxPlayerCount;
    protected final Material itemMaterial;
    protected final int itemAmount;
    protected final String itemName;
    protected final List<String> itemDescription;
    protected final ServerType serverType;
    protected final URL textureUrl;

    public ServerInstance(String serverName, String serverDisplayName, String seePermission, String joinPermission, int maxPlayerCount, String itemMaterial, int itemAmount, String itemName, List<String> itemDescription, ServerType serverType, URL textureUrl) {
        this.serverName = serverName;
        this.serverDisplayName = serverDisplayName;
        this.seePermission = seePermission;
        this.joinPermission = joinPermission;
        this.maxPlayerCount = maxPlayerCount;
        this.serverType = serverType;
        this.itemAmount = itemAmount;
        this.itemName = itemName;
        this.itemDescription = itemDescription;

        Material material = Material.getMaterial(itemMaterial);
        if (material == null) {
            throw new IllegalArgumentException(itemMaterial + " is not a Material");
        }
        this.itemMaterial = material;
        this.textureUrl = textureUrl;
    }

    private String replaceStringVariables(String str) {
        PluginMessage pluginMessage = Lobby.getPluginMessage();
        int currentPlayerCount = pluginMessage.getCurrentPlayerCount(this);

        str = str.replace('&', '§');
        str = str.replace("{NAME}", this.serverDisplayName);
        str = str.replace("{CUR_PLAYER}", String.valueOf(currentPlayerCount));
        str = str.replace("{MAX_PLAYER}", String.valueOf(this.maxPlayerCount));
        return str;
    }

    public ItemStack getItem(Player player) {
        ItemStack item = new ItemStack(itemMaterial, itemAmount);
        ItemMeta itemMeta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        for (String des : itemDescription) {
            lore.add(replaceStringVariables(des));
        }
        if(!player.hasPermission(joinPermission)){
            lore.add(ChatColor.RED + "You do not have permission to join this server!");
        }

        if(textureUrl == null || !itemMaterial.equals(Material.PLAYER_HEAD)){
            if (itemMeta == null) return item;
            itemMeta.setDisplayName(replaceStringVariables(itemName));
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
        }else {
            if (!(itemMeta instanceof SkullMeta)) {
                return item;
            }
            SkullMeta skullMeta = (SkullMeta) itemMeta;
            skullMeta.setDisplayName(replaceStringVariables(itemName));
            skullMeta.setLore(lore);

            PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
            PlayerTextures textures = profile.getTextures();
            textures.setSkin(textureUrl, PlayerTextures.SkinModel.CLASSIC);
            skullMeta.setOwnerProfile(profile);

            item.setItemMeta(skullMeta);
        }
        return item;
    }

    public String getServerName() {
        return this.serverName;
    }

    public String getSeePermission() {
        return this.seePermission;
    }

    public String getJoinPermission() {
        return this.joinPermission;
    }

    public int getMaxPlayerCount() {
        return this.maxPlayerCount;
    }

    public ServerType getServerType() {
        return serverType;
    }
}
