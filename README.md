# ChunkSpawnLimiter

[![forthebadge](https://forthebadge.com/images/badges/built-with-love.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/designed-in-ms-paint.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/approved-by-george-costanza.svg)](https://forthebadge.com)
![GitHub forks](https://img.shields.io/github/forks/Puyodead1/ChunkSpawnerLimiter?style=for-the-badge)
![GitHub Repo stars](https://img.shields.io/github/stars/Puyodead1/ChunkSpawnerLimiter?style=for-the-badge)
![GitHub](https://img.shields.io/github/license/Puyodead1/ChunkSpawnerLimiter?style=for-the-badge)

[![Discord Banner 2](https://discordapp.com/api/guilds/589200717277954093/widget.png?style=banner2)](https://discord.gg/tMzrSxQ)

## Credit Where Credit is Due

This is a fork of the original plugin by Cyprias which can be found on [dev.bukkit.org](https://dev.bukkit.org/projects/chunkspawnerlimiter), which is aimed to be compatible with newer versions of Minecraft and to have some updated features. I have gotten Cyprias's permission to maintain my fork and upload to SpigotMC.

## Synopsis

ChunkSpawnerLimiter limits the number of mobs a chunk can spawn. It can prevent spawn events if the chunk is at capacity, cull at certain intervals, and check when chunks are loaded/unloaded.

Functionality:

- Limit the number of mobs in chunks.
- Limit specific mobs (zombie, spider, etc) or groups (monster, animal).
- Cull on chunk load.
- Cull on chunk unload.
- Periodic chunk culling.
- Check Surrounding chunks for mobs.

## Permissions and Commands

None

## Configuration

- properties
  - debug-messages: Show debug messages in console.
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
- entities: List of mob groups and mobs to cull and their max limits.
- excluded-worlds: Worlds to exclude mob limits in.
- messages:
  - removed-entities: The message sent to players in the chunk when its culled.
  - spawn-cancelled: The message sent to players in the chunk when an entity spawn is cancelled.

<details>
    <summary>Click to expand default config</summary>

```yaml
config-version: 1.2
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
  BEEHIVE: true # When a bee is released from a beehive/bee nest
  BREEDING: true # When an animal breeds to create a child
  BUILD_IRONGOLEM: true # When an iron golem is spawned by being built
  BUILD_SNOWMAN: true # When a snowman is spawned by being built
  BUILD_WITHER: true # When a wither boss is spawned by being built
  COMMAND: true # When a creature is spawned by the "/summon" command
  CURED: true # When a villager is cured from infection
  CUSTOM: true # When a creature is spawned by plugins
  DEFAULT: true # When an entity is missing a SpawnReason
  DISPENSE_EGG: true # When a creature is spawned by a dispenser dispensing an egg
  DROWNED: true # When a creature is spawned by another entity drowning
  EGG: true # When a creature spawns from an egg
  ENDER_PEARL: true # When an entity is spawned as a result of ender pearl usage
  EXPLOSION: true # When eg an effect cloud is spawned as a result of a creeper exploding
  INFECTION: true # When a zombie infects a villager
  JOCKEY: true # When an entity spawns as a jockey of another entity (mostly spider jockeys)
  LIGHTNING: true # When a creature spawns because of a lightning strike
  MOUNT: true # When an entity spawns as a mount of another entity (mostly chicken jockeys)
  NATURAL: true # When something spawns from natural means
  NETHER_PORTAL: true # When a creature is spawned by nether portal
  OCELOT_BABY: true # When an ocelot has a baby spawned along with them
  PATROL: true # When an entity is spawned as part of a patrol
  PIGLIN_ZOMBIFIED: true # When a piglin is converted to a zombified piglib.
  RAID: true # When an entity is spawned as part of a raid
  REINFORCEMENTS: true # When an entity calls for reinforcements
  SHEARED: true # When an cow is spawned by shearing a mushroom cow
  SHOULDER_ENTITY: true # When an entity is spawned as a result of the entity it is being perched on jumping or being damaged
  SILVERFISH_BLOCK: true # When a silverfish spawns from a block
  SLIME_SPLIT: true # When a slime splits
  SPAWNER: true # When a creature spawns from a spawner
  SPAWNER_EGG: true # When a creature spawns from a Spawner Egg
  TRAP: true # When an entity spawns as a trap for players approaching
  VILLAGE_DEFENSE: true # When an iron golem is spawned to defend a village
  VILLAGE_INVASION: true # When a zombie is spawned to invade a village

entities:
  # NOTE: Remember that these numbers are per chunk

  # Mob Groups
  AMBIENT: 5 # Bat
  ANIMAL: 50 # Axolotl, Bee, Chicken, Cow, Mushroom Cow, Fox, Goat, Hoglin, Ocelot, Panda, Polar Bear, Rabbit, Sheep, Pig, Strider, Donkey, Llama, Mule, Horse, Skeleton Horse, Zombie Horse, Cat, Parrot, Wolf, Turtle
  MONSTER: 50 # Skeleton, Stray, Wither Skeleton, Blaze, Creeper, Enderman, Endermite, Giant, Guardian, Elder Guardian, Piglin, Piglin Brute, Pillager, Evoker, Illusioner, Vindicator, Ravager, Witch, Silverfish, Spider, Cave Spider, Vex, Wither, Zoglin, Zombie, Drowned, Husk, Pig Zombie, Zombie Villager, Slime, Magma Cube
  NPC: 50 # Villager, Wandering Trader
  OTHER: 200 # Anything else not listed in other categories
  WATER_MOB: 50 # Squid, Dolphin, Cod, Puffer Fish, Salmon, Tropical Fish, Glow Squid

  # Individual entities
  AXOLOTL: 10
  BEE: 10
  CHICKEN: 10
  COW: 10
  MUSHROOM_COW: 10
  FOX: 10
  GOAT: 10
  HOGLIN: 10
  OCELOT: 10
  PANDA: 10
  POLAR_BEAR: 10
  RABBIT: 10
  SHEEP: 10
  PIG: 10
  STRIDER: 10
  DONKEY: 10
  LLAMA: 10
  MULE: 10
  HORSE: 10
  SKELETON_HORSE: 10
  ZOMBIE_HORSE: 10
  CAT: 10
  PARROT: 10
  WOLF: 10
  TURTLE: 10
  SKELETON: 10
  STRAY: 10
  WITHER_SKELETON: 10
  BLAZE: 10
  CREEPER: 10
  ENDERMAN: 10
  ENDERMITE: 10
  GIANT: 10
  GUARDIAN: 10
  ELDER_GUARDIAN: 10
  PIGLIN: 10
  PIGLIN_BRUTE: 10
  PILLAGER: 10
  EVOKER: 10
  ILLUSIONER: 10
  VINDICATOR: 10
  RAVAGER: 10
  WITCH: 10
  SILVERFISH: 10
  SPIDER: 10
  CAVE_SPIDER: 10
  VEX: 10
  WITHER: 10
  ZOGLIN: 10
  ZOMBIE: 10
  DROWNED: 10
  HUSK: 10
  PIG_ZOMBIE: 10
  ZOMBIE_VILLAGER: 10
  SLIME: 10
  MAGMA_CUBE: 10
  BAT: 10
  SQUID: 10
  DOLPHIN: 10
  COD: 10
  PUFFER_FISH: 10
  SALMON: 10
  TROPICAL_FISH: 10
  GLOW_SQUID: 10
  VILLAGER: 10
  WANDERING_TRADER: 10
  GHAST: 10
  PHANTOM: 10
  IRON_GOLEM: 10
  SHULKER: 10
  SNOWMAN: 10

# Exclude these worlds from limits.
excluded-worlds: []

messages:
  removed-entites: "&7Removed %amount% %type% in your chunk."
  spawn-cancelled: "&7Cancelled spawn event for %type% in your chunk."
```
</details>

The server will need to be restarted or /reloaded for changes to take effect.

## Issues
Issues can be reported in the [GitHub](https://github.com/Puyodead1/ChunkSpawnerLimiter/issues/new).

If you find a bug, please check the [Issues](https://github.com/Puyodead1/ChunkSpawnerLimiter/issues) to make sure it isn't already reported.

## Other Info
CSL's original source code is available [here](https://github.com/Cyprias/ChunkSpawnerLimiter). The plugin was made due to NoLagg and CraftBukkitPlusPlus not being able to limit mob spawners per chunk. (NoLagg limits globally and CB++ only limits wild mobs).

The SpigotMC resource can be found [here](https://www.spigotmc.org/resources/chunkspawnerlimiter.93337/).
