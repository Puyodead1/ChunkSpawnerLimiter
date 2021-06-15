package com.cyprias.ChunkSpawnerLimiterLegacy.compare;

import org.bukkit.entity.*;


public class MobGroupCompare implements EntityCompare {

        private String mobGroup;

        public MobGroupCompare(String mobGroup)
        {
                this.mobGroup = mobGroup;
        }

        @Override
        public boolean isSimilar(Entity entity) {
                return (getMobGroup(entity) == this.mobGroup);
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