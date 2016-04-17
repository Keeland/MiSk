package me.keeland.keelansk.utils;

import org.bukkit.Bukkit;

public class ReflectionUtils {
	
	public static String getVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + ".";
	}

}
