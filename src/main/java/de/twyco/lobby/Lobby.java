package de.twyco.lobby;

import de.twyco.lobby.listener.*;
import de.twyco.lobby.server.ServerInstance;
import de.twyco.lobby.server.ServerType;
import de.twyco.lobby.util.PluginMessage;
import de.twyco.lobby.util.config.Comments;
import de.twyco.lobby.util.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class Lobby extends JavaPlugin {

    private static Lobby instance;
    private static PluginMessage pluginMessage;
    private static List<ServerInstance> serverList;
    private static ServerInstance thisServer;
    private static Config settings;
    private static int refreshTask = -1;

    public void onEnable() {
        instance = this;
        try {
            settings = new Config("settings.yml", getDataFolder(), "settings.yml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setSettingsComments();
        serverList = new ArrayList<>();
        pluginMessage = new PluginMessage();
        FileConfiguration conf = settings.getFileConfiguration();
        String thisServerName = conf.getString("serverName");
        List<String> servers = conf.getStringList("servers");
        if (servers.isEmpty()) {
            getLogger().info("No server are set in settings.yml");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            for (String server : servers) {
                if (!conf.isSet("server." + server)) {
                    continue;
                }
                //Get server settings
                String path = "server." + server + ".";
                String displayName = conf.getString(path + "displayName");
                String seePermission = conf.getString(path + "permission.seePermission");
                String joinPermission = conf.getString(path + "permission.joinPermission");
                int maxPlayerCount = conf.getInt(path + "maxPlayerCount");
                ServerType serverType = ServerType.valueOf(conf.getString(path + "serverType"));

                //Get serveritem settings
                if (serverType.equals(ServerType.LobbyServer)) {
                    if (server.equals(thisServerName)) {
                        path = "item.gen_cur_lobby.";
                    } else {
                        path = "item.gen_lobby.";
                    }
                } else {
                    path = "item." + server + ".";
                }
                String material = conf.getString(path + "material");
                URL textureURL = null;
                if(conf.isSet(path + "texture")){
                    try {
                        textureURL = new URL(conf.getString(path + "texture", ""));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }
                int amount = conf.getInt(path + "amount");
                String itemName = conf.getString(path + "itemName");
                List<String> description = conf.getStringList(path + "description");
                ServerInstance serverInstance = new ServerInstance(server, displayName, seePermission, joinPermission, maxPlayerCount, material, amount, itemName, description, serverType, textureURL);
                if (server.equals(thisServerName)) {
                    thisServer = serverInstance;
                }
                serverList.add(serverInstance);
            }
        }
        registerListener();

    }

    public void onDisable() {
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", pluginMessage);
    }

    private void registerListener() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", pluginMessage);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new CancelEventsListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryUpdate(), this);
    }


    private void setSettingsComments() {
        FileConfiguration conf = settings.getFileConfiguration();
        conf.setComments("serverName", Comments.getServerNameComments());
        conf.setComments("server", Comments.getServerComments());
        conf.setComments("servers", Comments.getServersComments());
        conf.setComments("item", Comments.getItemComments());
        settings.save();
    }

    public static Lobby getInstance() {
        return Lobby.instance;
    }

    public static PluginMessage getPluginMessage() {
        return Lobby.pluginMessage;
    }

    public static List<ServerInstance> getServerList() {
        return serverList;
    }

    public static ServerInstance getThisServer() {
        return thisServer;
    }

    public static void stopRefreshPlayerCount() {
        if (refreshTask != -1) {
            Bukkit.getScheduler().cancelTask(refreshTask);
            refreshTask = -1;
        }
    }

    public static void startRefreshPlayerCount() {
        PluginMessage pluginMessage = Lobby.getPluginMessage();
        if (refreshTask == -1) {
            refreshTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Lobby.getInstance(), pluginMessage::updateAllCurrentPlayerCount, 0L, 10L);
        }
    }
}
