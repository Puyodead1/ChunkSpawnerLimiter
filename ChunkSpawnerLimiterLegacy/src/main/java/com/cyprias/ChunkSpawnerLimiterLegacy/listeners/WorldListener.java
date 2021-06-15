package com.cyprias.ChunkSpawnerLimiterLegacy.listeners;

import com.cyprias.ChunkSpawnerLimiterLegacy.ChatUtils;
import com.cyprias.ChunkSpawnerLimiterLegacy.Config;
import com.cyprias.ChunkSpawnerLimiterLegacy.Logger;
import com.cyprias.ChunkSpawnerLimiterLegacy.Plugin;
import com.cyprias.ChunkSpawnerLimiterLegacy.compare.MobGroupCompare;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class WorldListener implements Listener {
	HashMap<Chunk, Integer> chunkTasks = new HashMap<>();

	static class inspectTask extends BukkitRunnable {
		Chunk c;
		public inspectTask(Chunk c) {
			this.c = c;
		}

		@Override
		public void run() {
			Logger.debug("Active check " + c.getX() + " " + c.getZ());
			if (!c.isLoaded()){
				Plugin.cancelTask(taskID);
				return;
			}
			checkChunk(c, null);
		}

		int taskID;
		public void setId(int taskID) {
			this.taskID = taskID;
		}
	}

	@EventHandler
	public void onChunkLoadEvent(final ChunkLoadEvent e) {
		Logger.debug("ChunkLoadEvent " + e.getChunk().getX() + " " + e.getChunk().getZ());
		if (Config.getBoolean("properties.active-inspections")){
			inspectTask task = new inspectTask(e.getChunk());
			int taskID = Plugin.scheduleSyncRepeatingTask(task, Config.getInt("properties.inspection-frequency") * 20L);
			task.setId(taskID);

			chunkTasks.put(e.getChunk(), taskID);
		}

		if (Config.getBoolean("properties.check-chunk-load"))
			checkChunk(e.getChunk(), null);
	}

	@EventHandler
	public void onChunkUnloadEvent(final ChunkUnloadEvent e) {
		Logger.debug("ChunkUnloadEvent " + e.getChunk().getX() + " " + e.getChunk().getZ());
		
		if (chunkTasks.containsKey(e.getChunk())){
			Plugin.getInstance().getServer().getScheduler().cancelTask(chunkTasks.get(e.getChunk()));
			chunkTasks.remove(e.getChunk());
		}

		if (Config.getBoolean("properties.check-chunk-unload"))
			checkChunk(e.getChunk(), null);
	}
	
	

	public static boolean checkChunk(Chunk c, Entity entity) {
        // Stop processing quickly if this world is excluded from limits.
		if (Config.getStringList("excluded-worlds").contains(c.getWorld().getName())) {
			return false;
        }
		
		Entity[] ents = c.getEntities();

		HashMap<String, ArrayList<Entity>> types = new HashMap<>();

		for (int i = ents.length - 1; i >= 0; i--) {
			String eType = ents[i].getType().name();
			String eGroup = MobGroupCompare.getMobGroup(ents[i]);

			if (Config.contains("entities." + eType)) {
				if (!types.containsKey(eType))
					types.put(eType, new ArrayList<>());
				types.get(eType).add(ents[i]);
			}

			if (Config.contains("entities." + eGroup)) {
				if (!types.containsKey(eGroup))
					types.put(eGroup, new ArrayList<>());
				types.get(eGroup).add(ents[i]);
			}
		}

		if(entity != null) {
			String type = entity.getType().name();
			if (Config.contains("entities." + type)) {
				int typeCount;
				if (types.containsKey(type)) {
					typeCount = types.get(type).size() + 1;
				} else {
					typeCount = 1;
				}

				if (typeCount > Config.getInt("entities" + type)) {
					return true;
				}
			}

			String eGroup = MobGroupCompare.getMobGroup(entity);

			if (Config.contains("entities." + eGroup)) {
				int typeCount;
				if (types.containsKey(eGroup)) {
					typeCount = types.get(eGroup).size() + 1;
				} else {
					typeCount = 1;
				}
				return typeCount > Config.getInt("entities." + eGroup);
			}
		}

			for (Entry<String, ArrayList<Entity>> entry : types.entrySet()) {
				String eType = entry.getKey();
				int limit = Config.getInt("entities." + eType);

				if (entry.getValue().size() < limit) {
					continue;
				}

				if (entry.getValue().size() > limit) {
					Logger.debug("Removing " + (entry.getValue().size() - limit) + " " + eType + " @ " + c.getX() + " " + c.getZ());

					if (Config.getBoolean("properties.notify-players-remove")) {
						for (int i = ents.length - 1; i >= 0; i--) {
							if (ents[i] instanceof Player) {
								Player p = (Player)ents[i];
								ChatUtils.send(p, Config.getString("messages.removed-entites").replace("%amount%", String.valueOf(entry.getValue().size() - limit)).replace("%type%", eType));
							}
						}
					}

					// Add preserve-named-entities config variable - from PR https://github.com/Cyprias/ChunkSpawnerLimiter/pull/5/commits/9ef4c7d47a22ef4747017bb9d9ed33e15329a2c4
					boolean skipNamed = Config.getBoolean("properties.preserve-named-entities");
					int toRemove = entry.getValue().size() - limit;
					int index = entry.getValue().size() - 1;
					while (toRemove > 0 && index >= 0) {
						Entity e = entry.getValue().get(index);
						if (!skipNamed || e.getCustomName() != null) {
							e.remove();
							--toRemove;
						}
						--index;
					}
					if (toRemove == 0) {
						continue;
					}

					index = entry.getValue().size() - toRemove - 1;
					for (; index < entry.getValue().size(); index++) {
						// Don't remove players - from PR https://github.com/Cyprias/ChunkSpawnerLimiter/pull/5/commits/27a81d754ddad0562ba4d71e9a1d74d8395d1e31
						if (entry.getValue().get(index) instanceof HumanEntity) {
							continue;
						}
						entry.getValue().get(index).remove();
					}
				}
			}
		return false;
	}
}
