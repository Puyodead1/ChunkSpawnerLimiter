package com.cyprias.ChunkSpawnerLimiterLegacy.listeners;

import com.cyprias.ChunkSpawnerLimiterLegacy.ChatUtils;
import com.cyprias.ChunkSpawnerLimiterLegacy.Config;
import com.cyprias.ChunkSpawnerLimiterLegacy.Logger;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EntityListener implements Listener {

	@EventHandler
	public void onCreatureSpawnEvent(CreatureSpawnEvent e) {
		if (e.isCancelled())
			return;

		if (!Config.getBoolean("properties.watch-creature-spawns"))
			return;

		String reason = e.getSpawnReason().toString();
		
		if (!Config.getBoolean("spawn-reasons."+reason) || !Config.getBoolean("spawn-reasons." + reason)){
			Logger.debug("Igonring " + e.getEntity().getType() + " due to spawnreason " + reason);
			return;
		}

		Chunk c = e.getLocation().getChunk();

		Entity entity = Config.getBoolean("properties.prevent-creature-spawns") ? e.getEntity() : null;

		if(WorldListener.checkChunk(c, entity)) {
			assert entity != null;
			Logger.debug("Cancelling spawn of "  + entity.getType().name() + " in chunk at " + c.getX() + "," + c.getZ());
			e.setCancelled(true);
			for(Entity chunkEntity : c.getEntities()) {
				if (chunkEntity instanceof Player && Config.getBoolean("properties.notify-players-spawn-cancelled")) {
					// Notify any players in the chunk
					ChatUtils.send(chunkEntity, Config.getString("messages.spawn-cancelled").replace("%type%", entity.getType().name()));
				}
			}
		}

		if(entity != null) {
			// If we are preventing new spawns instead of culling, don't cull surrounding chunks.
			return;
		}

		int surrounding = Config.getInt("properties.check-surrounding-chunks");

		if (surrounding > 0) {
			World w = e.getLocation().getWorld();
			for (int x = c.getX() + surrounding; x >= (c.getX() - surrounding); x--) {
				for (int z = c.getZ() + surrounding; z >= (c.getZ() - surrounding); z--) {
					Logger.debug("Checking chunk " + x + " " +z);
					assert w != null;
					WorldListener.checkChunk(w.getChunkAt(x, z), null);
				}
			}

		}
	}
}
