package de.twyco.lobby.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public abstract class ListenerHelp {

    public boolean cancelEvent(Player player) {
        if(!player.isOp()){
            return true;
        }
        return !player.getGameMode().equals(GameMode.CREATIVE);
    }

}
