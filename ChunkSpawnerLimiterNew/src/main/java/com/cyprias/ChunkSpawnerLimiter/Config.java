package com.cyprias.ChunkSpawnerLimiter;

import java.util.List;
import java.util.Objects;

public class Config {
	public static boolean getBoolean(String property) {
		return Plugin.getInstance().getConfig().getBoolean(property);
	}

	public static int getInt(String property) {
		return Plugin.getInstance().getConfig().getInt(property);
	}

	public static String getString(String property) {
		return Objects.requireNonNull(Plugin.getInstance().getConfig().getString(property));
	}

	
	public static boolean contains(String property) {
		return Plugin.getInstance().getConfig().contains(property);
	}
	
	public static  List<String> getStringList(String property) {
		return Plugin.getInstance().getConfig().getStringList(property);
	}
}
