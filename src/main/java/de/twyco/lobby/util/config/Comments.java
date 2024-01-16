package de.twyco.lobby.util.config;

import java.util.ArrayList;
import java.util.List;

public class Comments {

    private static final List<String> serverNameComments;
    private static final List<String> serverComments;
    private static final List<String> serversComments;
    private static final List<String> itemComments;

    static {
        serverNameComments = new ArrayList<>();
        serverNameComments.add("+----------------------------------------------------------------------------------------------+ #");
        serverNameComments.add("|                                                                                              | #");
        serverNameComments.add("|                                      IMPORTANT SETTINGS                                      | #");
        serverNameComments.add("|                                                                                              | #");
        serverNameComments.add("+----------------------------------------------------------------------------------------------+ #");
        serverNameComments.add("");
        serverNameComments.add("The name of the server, must be set.");

        serverComments = new ArrayList<>();
        serverComments.add("+----------------------------------------------------------------------------------------------+ #");
        serverComments.add("|                                                                                              | #");
        serverComments.add("|                                       Server SETTINGS                                        | #");
        serverComments.add("|                                                                                              | #");
        serverComments.add("+----------------------------------------------------------------------------------------------+ #");
        serverComments.add("");
        serverComments.add("The following block defines the settings for all other server on this network.");
        serverComments.add(" ");
        serverComments.add("- Please set up the permissions to join or see (in the ServerSelector) the server;");
        serverComments.add("  the maxPlayerCount of the server and serverType as above for each Server.");
        serverComments.add("- IMPORTANT: Please make sure that the 'name' matches the name in the BungeeCord Config!");
        serverComments.add("  OTHERWISE, THE PLAYER ARE NOT ABLE TO JOIN THE SERVER.");
        serverComments.add("");
        serverComments.add("server:");
        serverComments.add(" dummyServer:");
        serverComments.add("   displayName: Dummy Server 1");
        serverComments.add("   permission:");
        serverComments.add("     seePermission: dummyServer.see");
        serverComments.add("     joinPermission: dummyServer.join");
        serverComments.add("   maxPlayerCount: 0");
        serverComments.add("   serverType: Lobby");

        serversComments = new ArrayList<>();
        serversComments.add("The following list defines witch server will be shown in the server selector item.");
        serversComments.add("servers:");
        serversComments.add("  - dummyServer");

        itemComments = new ArrayList<>();
        itemComments.add("+----------------------------------------------------------------------------------------------+ #");
        itemComments.add("|                                                                                              | #");
        itemComments.add("|                                         Item SETTINGS                                        | #");
        itemComments.add("|                                                                                              | #");
        itemComments.add("+----------------------------------------------------------------------------------------------+ #");
        itemComments.add("");
        itemComments.add("The following block defines the settings for the items of the server, in the serverSelector Item");
        itemComments.add("EXPERIMENTAL: ");
        itemComments.add("- If material is set to 'PLAYER_HEAD', you can add texture: 'URL', like in the example");
        itemComments.add(" ");
        itemComments.add("- 'Item: gen_lobby': defines the item for a Lobby Server.");
        itemComments.add("- 'Item: gen_cur_lobby': defines the item for the Lobby you are on Server.");
        itemComments.add(" ");
        itemComments.add("- Please set up the material, amount, item name and the description.");
        itemComments.add("- Please make sure that no two items have the same name.");
        itemComments.add("- Each description row represents a description row of the item ingame.");
        itemComments.add("- {NAME} will be replaced, with the displayName of the Server, set above.");
        itemComments.add("- {CUR_PLAYER} will be replaced, with the current player count of the server.");
        itemComments.add("- {MAX_PLAYER} will be replaced, with the max player count, set above.");
        itemComments.add("- For color use '&', following by the minecraft color code");
        itemComments.add("  (https://www.spigotmc.org/attachments/example2-png.402641/).");
        itemComments.add("");
        itemComments.add("item:");
        itemComments.add("  dummyServer:");
        itemComments.add("    material: PLAYER_HEAD");
        itemComments.add("    texture: 'https://textures.minecraft.net/texture/da12a8a7cbb8ab8cac021bf4874357f5fb28f27e52726196103a7313a7fc3acf' #Stxgi Skin");
        itemComments.add("    amount: 1");
        itemComments.add("    itemName: '&5{NAME}'");
        itemComments.add("    description:");
        itemComments.add("      - '&1{CUR_PLAYER} players are currently online.'");
        itemComments.add("      - '&1{MAX_PLAYER} can join maximum'");
        itemComments.add("      - '&3Row 3'");
    }

    public static List<String> getServerNameComments() {
        return serverNameComments;
    }

    public static List<String> getServerComments() {
        return serverComments;
    }

    public static List<String> getServersComments() {
        return serversComments;
    }

    public static List<String> getItemComments() {
        return itemComments;
    }
}
