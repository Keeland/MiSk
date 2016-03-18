package me.keeland.keelansk.utils;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import me.keeland.keelansk.Main;

public class DataUtils {
	
    public static Integer fakeMaxPlayers;

    private static File CFG = new File(Main.getInstance().getDataFolder(), "config.yml");

	public static boolean usecilentsideworldborders;

    public static void loadConfig() {
        loadDefaultFiles(new String[]{"config.yml"});
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(CFG);

        usecilentsideworldborders = yaml.getBoolean("usecilentsideworldborders");

    }

    public static void loadDefaultFiles(String[] names) {
        for (String name : names) {
            File file = new File(Main.getInstance().getDataFolder() + File.separator + name);
            if (!file.exists()) {
                Main.getInstance().saveResource(name, true);
            }
        }
    }
    
}