package com.codermason.config;

import com.codermason.FactionTag;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import javax.xml.parsers.FactoryConfigurationError;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * created by codermason on 12/2/14
 */
public class Config {

    private Plugin plugin;

    private File configFile, dataFolder;
    private FileConfiguration config;

    public Config(Plugin plugin) {
        this.plugin = plugin;

        this.dataFolder = plugin.getDataFolder();
        this.configFile = new File(dataFolder, "config.yml");
        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    private void checkFiles(File[] files, FileConfiguration[] configs) {
        if(files.length != configs.length) return;

        for(int x=0;x<files.length;x++) {
            File f = files[x];
            FileConfiguration config = configs[x];
            if (!f.exists()) {
                FactionTag.getInstance().log(Level.WARNING, "Could not find file " + f.getName() + "! Attempting to create...");
                try {
                    config.save(f);
                }catch (IOException e) {
                    FactionTag.getInstance().log(Level.SEVERE, "Could not create file " + f.getName() + "!");
                    FactionTag.getInstance().shutdown("Unable to load files");
                }
                FactionTag.getInstance().log(Level.INFO, "Created file " + f.getName() + " successfully!");
            }
        }
    }

    public File getConfigFile() {
        return configFile;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public String getFormat() {
        String raw = getConfig().getString("format");
        if(raw == null || raw.isEmpty()) {
            getConfig().set("format", "&4[{faction}]&f ");
            saveFile(configFile, config);
        }
        return getConfig().getString("format");
    }

    public void saveFile(File f, FileConfiguration config) {
        try {
            config.save(f);
        }catch(Exception e) {
            FactionTag.getInstance().log(Level.WARNING, "Could not save file " + f.getName() + "!");
        }
    }
}
