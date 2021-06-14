package com.cyprias.ChunkSpawnerLimiter.compare;

import org.bukkit.entity.*;


public record MobGroupCompare(String mobGroup) implements EntityCompare {

        @Override
        public boolean isSimilar(Entity entity) {
                return (getMobGroup(entity).equals(this.mobGroup));
        }

        public static String getMobGroup(Entity entity) {
                // Determine the general group this mob belongs to.
                if (entity instanceof Animals) {
                        // Chicken, Cow, MushroomCow, Ocelot, Pig, Sheep, Wolf
                        return "ANIMAL";
                }

                if (entity instanceof Monster) {
                        // Blaze, CaveSpider, Creeper, Enderman, Giant, PigZombie, Silverfish, Skeleton, Spider, Witch, Wither, Zombie
                        return "MONSTER";
                }

                if (entity instanceof Ambient) {
                        // Bat
                        return "AMBIENT";
                }

                if (entity instanceof WaterMob) {
                        // Squid
                        return "WATER_MOB";
                }

                if (entity instanceof NPC) {
                        // Villager
                        return "NPC";
                }

                // Anything else.
                return "OTHER";
        }
}
