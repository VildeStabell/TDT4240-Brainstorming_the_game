package com.mygdx.game.models;

import com.badlogic.gdx.graphics.Texture;

/**
 * The Wall model represents a wall, and stores how damaged it currently is.
 * maxHitpoints: The amount of hp the wall starts out with.
 * hitPoints: The amount of hp the wall currently has.
 * This class implements the MVC pattern.
 */

public class Wall {
    private final int maxHitPoints;
    private int hitPoints;

    public Wall(int hitPoints) {
        if(hitPoints <= 0)
            throw new IllegalArgumentException("Hitpoints has to be initialized to a value above 0");

        this.maxHitPoints = hitPoints;
        this.hitPoints = hitPoints;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Checks if wall is still standing
     * */
    public boolean isStanding() {
        return hitPoints > 0;
    }

    /**
     * Reduces the wall's current hitpoints by the specified amount
     * @param dmg: How much to reduce the hitpoints by
     * @return true if the wall is broken by the damage, otherwise returns false
      */
    public boolean takeDmg(int dmg) {
        if(dmg <= 0)
            throw new IllegalArgumentException("Damage taken has to be above 0");
        if(hitPoints <= 0)
            throw new IllegalStateException("Cannot take damage if wall is already destroyed");

        if(dmg >= hitPoints) {
            hitPoints = 0;
            return true;
        }
        hitPoints -= dmg;
        return false;
    }
}
