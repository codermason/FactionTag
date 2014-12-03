package com.codermason.listeners;

import com.codermason.FactionTag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * created by codermason on 12/2/14
 */
public class LoginEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        FactionTag.getInstance().updateSuffix(e.getPlayer());
    }

}
