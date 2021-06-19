package com.cyprias.ChunkSpawnerLimiter;

import com.cyprias.ChunkSpawnerLimiter.listeners.EntityListener;
import com.cyprias.ChunkSpawnerLimiter.listeners.WorldListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
	private static Plugin instance = null;
	public static String chatPrefix = "&4[&bCSL&4]&r ";

	public void onEnable() {
		instance = this;

		getConfig().options().copyDefaults(true);
		saveDefaultConfig();

		// Register our event listener.
		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getServer().getPluginManager().registerEvents(new WorldListener(), this);
	}

	public void onDisable() {
		getServer().getScheduler().cancelTasks(this);
	}
	
	public static Plugin getInstance() {
		return instance;
	}


	public static int scheduleSyncRepeatingTask(Runnable run, long delay) {
		return scheduleSyncRepeatingTask(run, delay, delay);
	}
	public static int scheduleSyncRepeatingTask(Runnable run, long start, long delay) {
		return instance.getServer().getScheduler().scheduleSyncRepeatingTask(instance, run, start, delay);
	}
	public static void cancelTask(int taskID) {
		instance.getServer().getScheduler().cancelTask(taskID);
	}
	
	
}
