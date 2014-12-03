package com.codermason.listeners;

import com.codermason.FactionTag;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * created by codermason on 12/2/14
 */
public class FactionEvents implements Listener {

    @EventHandler
    public void onNameChange(EventFactionsNameChange e) {
        for(MPlayer player : e.getFaction().getMPlayersWhereOnline(true)) {
            updatePlayer(player);
        }
    }

    @EventHandler
    public void onCreate(EventFactionsCreate e) {
        updatePlayer(e.getMSender());
    }

    @EventHandler
    public void onDisband(EventFactionsDisband e) {
        for(MPlayer player : e.getFaction().getMPlayersWhereOnline(true)) {
            updatePlayer(player);
        }
    }

    @EventHandler
    public void onJoin(EventFactionsMembershipChange e) {
        updatePlayer(e.getMPlayer());
    }

    public void updatePlayer(MPlayer player) {
        Player p = Bukkit.getPlayer(player.getName());
        if(p != null) {
            FactionTag.getInstance().updateSuffix(p);
        }
    }

}
