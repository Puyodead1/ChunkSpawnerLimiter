# ChunkSpawnLimiter

## Credit Where Credit is Due

This is a fork of the original plugin by Cyprias, which is aimed to be compatable with newer versions of Minecraft and to have some updated features, with the permission of Cyprias.

## Synopsis

ChunkSpawnerLimiter limits the number of mobs a chunk can spawn.

Functionality:

- Limit the number of mobs in chunks.
- Limit specific mobs (zombie, spider, etc) or groups (monster, animal).
- Cull on chunk load.
- Cull on chunk unload.
- Periodic chunk culling.
- Check Surrounding chunks for mobs.

## Changelog

- 06/14/2021 - 3.0.4
  - Maven
    - Updated to Java 16 for Minecraft 1.17+
    - Switched from Bukkit to Spigot
  - Removed ChatUtils class as 90% was unused
  - Removed Metrics
  - Added `prevent-creature-spawns` config property (From [PR 5 in the original repo](https://github.com/Cyprias/ChunkSpawnerLimiter/pull/5/commits/9ef4c7d47a22ef4747017bb9d9ed33e15329a2c4))
  - Added `preserve-named-entities` config property (From [PR 5 in the original repo](https://github.com/Cyprias/ChunkSpawnerLimiter/pull/5/commits/9ef4c7d47a22ef4747017bb9d9ed33e15329a2c4))
  - Prevent removing players (From [PR 5 in the original repo](https://github.com/Cyprias/ChunkSpawnerLimiter/pull/5/commits/27a81d754ddad0562ba4d71e9a1d74d8395d1e31))
  - Added `BEE_HIVE` to spawn reasons

## Permissions and Commands

None

## Configuration

- properties
  - debug-messages: Show debug messages in console.
  - use-metrics: Let the developer know how popular the plugin is.
  - check-chunk-load: Check chunk upon load.
  - check-chunk-unload: Check chunk upon unload.
  - watch-creature-spawns: Check chunk upon mob spawn.
  - prevent-creature-spawns: Prevent new spawns rather than cull older entities until cap is hit.
  - check-surrounding-chunks: Check surrounding chunks.
  - active-inspections: Periodically recheck loaded chunks.
  - inspection-frequency: How often to inspect a chunk.
  - notify-players-remove: Notify players within the chunk when it's culled.
  - notify-players-spawn-cancelled: Notify players within the chunk when an entity spawn has been cancelled.
  - preserve-named-entities: Prioritize entities without names over older entities.
- spawn-reasons: List of spawn reasons to cull on.
- entities :List of mobs to cull and their max limits.
- excluded-worlds: Worlds to exclude mob limits in.
- messages:
  - removed-entities: The message sent to players in the chunk when its culled.
  - spawn-cancelled: The message sent to players in the chunk when an entity spawn is cancelled.

<details>
    <summary>Click to expand default config</summary>

```yaml
config-version: 1.1
properties:
  # Show debug messages.
  debug-messages: false

  # Check a chunk upon load (ChunkLoadEvent).
  check-chunk-load: false

  # Check a chunk upon unload (ChunkUnloadEvent).
  check-chunk-unload: false

  # Check a chunk when a mob spawns (CreatureSpawnEvent).
  watch-creature-spawns: true

  # Prevent new spawns rather than cull older entities until cap is hit.
  prevent-creature-spawns: false

  # Radius of surrounding chunks to check.
  check-surrounding-chunks: 1

  # When a chunk is loaded, recheck it periodically.
  active-inspections: true

  # How often, in seconds, to check the chunk.
  inspection-frequency: 300

  # Notify players in that chunk if stuff has been culled.
  notify-players-remove: false

  # Notify players in that chunk if a spawn event has been cancelled
  notify-players-spawn-cancelled: false

  # Prioritize entities without names over older entities.
  preserve-named-entities: true

# Spawn reasons to cull on.
spawn-reasons:
  NATURAL: true
  JOCKEY: true
  CHUNK_GEN: true
  SPAWNER: true
  EGG: true
  SPAWNER_EGG: true
  LIGHTNING: true
  BEEHIVE: true
  # These are deprecated, might not work in the future.
  BED: true
  BUILD_SNOWMAN: true
  BUILD_IRONGOLEM: true
  BUILD_WITHER: true
  VILLAGE_DEFENSE: true
  VILLAGE_INVASION: true
  BREEDING: true
  SLIME_SPLIT: true
  REINFORCEMENTS: true
  CUSTOM: true
  DEFAULT: true

entities:
  ANIMAL: 50
  MONSTER: 50
  NPC: 50
  OTHER: 500
#  WATER_MOB: 5
#  AMBIENT: 5
#  CREEPER: 10
#  SHEEP: 10
#  OCELOT: 10
#  GIANT: 10
#  SLIME: 10
#  GHAST: 10
#  PIG_ZOMBIE: 10
#  ENDERMAN: 10
#  SILVERFISH: 10
#  MAGMA_CUBE: 10
#  CHICKEN: 10
#  SQUID: 10
#  WOLF: 10
#  MUSHROOM_COW: 10
#  SNOWMAN: 10
#  VILLAGER: 10
#  SPIDER: 10
#  SKELETON: 10
#  ZOMBIE: 10
#  BLAZE: 10
#  CAVE_SPIDER: 10
#  IRON_GOLEM: 10
#  WITHER: 10
#  PIG: 10
#  HORSE: 10
#  WITCH: 10

# Exclude these worlds from limits.
excluded-worlds: []

messages:
  removed-entites: "&7Removed %amount% %type% in your chunk."
  spawn-cancelled: "&7Cancelled spawn event for %type% in your chunk."
```

</details>

The server will need to be restarted or /reloaded for changes to take effect.
