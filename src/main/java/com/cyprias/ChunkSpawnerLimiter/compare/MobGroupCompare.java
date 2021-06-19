package com.cyprias.ChunkSpawnerLimiter.compare;

import org.bukkit.entity.*;


public class MobGroupCompare implements EntityCompare {

    private final String mobGroup;

    public MobGroupCompare(String mobGroup) {
        this.mobGroup = mobGroup;
    }

    @Override
    public boolean isSimilar(Entity entity) {
        return (getMobGroup(entity).equals(this.mobGroup));
    }

    public static String getMobGroup(Entity entity) {
        // Determine the general group this mob belongs to.
        if (entity instanceof Animals) {
            // Axolotl, Bee, Chicken, Cow, Mushroom Cow, Fox, Goat, Hoglin, Ocelot, Panda, Polar Bear, Rabbit, Sheep, Pig, Strider, Donkey, Llama, Mule, Horse, Skeleton Horse, Zombie Horse, Cat, Parrot, Wolf, Turtle
            return "ANIMAL";
        } else if (entity instanceof Monster) {
            // Skeleton, Stray, Wither Skeleton, Blaze, Creeper, Enderman, Endermite, Giant, Guardian, Elder Guardian, Piglin, Piglin Brute, Pillager, Evoker, Illusioner, Vindicator, Ravager, Witch, Silverfish, Spider, Cave Spider, Vex, Wither, Zoglin, Zombie, Drowned, Husk, Pig Zombie, Zombie Villager, Slime, Magma Cube
            return "MONSTER";
        } else if (entity instanceof Ambient) {
            // Bat
            return "AMBIENT";
        } else if (entity instanceof WaterMob) {
            // Squid, Dolphin, Cod, Puffer Fish, Salmon, Tropical Fish, Glow Squid
            return "WATER_MOB";
        } else if (entity instanceof NPC) {
            // Villager, Wandering Trader
            return "NPC";
        } else if (entity instanceof Flying) {
            // Ghast, Phantom
            return "FLYING";
        } else if (entity instanceof Golem) {
            // Iron Golem, Shulker, Snowman
            return "GOLEM";
        }
        // Anything else.
        return "OTHER";
    }
}
