package com.codermason;

import ca.wacos.nametagedit.NametagAPI;
import com.codermason.config.Config;
import com.codermason.listeners.FactionEvents;
import com.codermason.listeners.LoginEvents;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.logging.Level;

/**
 * created by codermason on 12/2/14
 */
public class FactionTag extends JavaPlugin {

    private Listener[] listeners = {new LoginEvents(), new FactionEvents()};

    private Config config;

    private static FactionTag instance;

    public void onEnable() {
        instance = this;

        if(!checkNametagEdit()) {
            shutdown("Unable to hook into NametagEdit");
        } else {
            log(Level.INFO, "Hooked into NametagEdit!");
        }

        if(!checkFactions()) {
            shutdown("Unable to hook into Factions");
        } else {
            log(Level.INFO, "Hooked into Factions!");
        }

        if(!checkMassiveCore()) {
            shutdown("Unable to hook into MassiveCore");
        } else {
            log(Level.INFO, "Hooked into MassiveCore!");
        }

        this.config = new Config(this);

        registerListeners();
    }

    public void onDisable() {}

    public void log(Level level, Object o) {
        getLogger().log(level, o.toString());
    }

    public Config getLocalConfig() {
        return config;
    }

    public String getFactionName(UUID uuid) {
        MPlayer player = MPlayer.get(uuid);
        return player.getFactionName();
    }

    public void shutdown(String reason) {
        log(Level.SEVERE, "FactionTag is now shutting down! Reason: " + reason);
        this.getServer().getPluginManager().disablePlugin(this);
    }

    private void registerListeners() {
        for(Listener l : listeners) {
            this.getServer().getPluginManager().registerEvents(l, this);
        }
    }

    private boolean checkFactions() {
        return getServer().getPluginManager().getPlugin("Factions") != null;
    }

    private boolean checkNametagEdit() {
        return getServer().getPluginManager().getPlugin("NametagEdit") != null;
    }

    private boolean checkMassiveCore() {
        return getServer().getPluginManager().getPlugin("MassiveCore") != null;
    }

    public void updateSuffix(Player player) {
        String faction = getFactionName(player.getUniqueId());
        if(!faction.isEmpty()) {
            String format = getLocalConfig().getFormat();
            NametagAPI.setPrefix(player.getName(), ChatColor.translateAlternateColorCodes('&', format.replace("{faction}", faction)));
        }
    }

    public static FactionTag getInstance() {
        return instance;
    }

}
