package de.twyco.lobby.util;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.twyco.lobby.Lobby;
import de.twyco.lobby.inventorys.ServerSelectorInventory;
import de.twyco.lobby.server.ServerInstance;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class PluginMessage implements PluginMessageListener {

    private final Lobby plugin;
    private final HashMap<String, Integer> serverPlayerCount;

    public PluginMessage() {
        plugin = Lobby.getInstance();
        serverPlayerCount = new HashMap<>();
    }

    @Override
    public void onPluginMessageReceived(String channel, @NotNull Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput input = ByteStreams.newDataInput(message);
        String subChannel = input.readUTF();
        if (subChannel.equals("PlayerCount")) {
            String server = input.readUTF();
            int playerCount = input.readInt();
            serverPlayerCount.put(server, playerCount);
            ServerSelectorInventory.updatePlayerCount();
        }
    }

    public void connect(Player player, ServerInstance targetServer) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(targetServer.getServerName());
        player.sendPluginMessage(plugin, "BungeeCord", output.toByteArray());
    }


    public void updateAllCurrentPlayerCount() {
        for (ServerInstance server : Lobby.getServerList()) {
            String serverName = server.getServerName();
            updateCurrentPlayerCount(serverName);
        }
    }

    private void updateCurrentPlayerCount(String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("PlayerCount");
        output.writeUTF(server);
        plugin.getServer().sendPluginMessage(plugin, "BungeeCord", output.toByteArray());
    }

    public int getCurrentPlayerCount(ServerInstance server) {
        return serverPlayerCount.getOrDefault(server.getServerName(), -999);
    }
}
