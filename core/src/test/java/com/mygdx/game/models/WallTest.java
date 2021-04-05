package com.mygdx.game.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WallTest {
    Wall w1;

    @Before
    public void setUp() throws Exception {
        try {
            w1 = new Wall(20);
        }
        catch (Exception e) {
            throw new Exception("Failed to set up WallTest: ", e);
        }
    }

    @Test
    public void constructor() {
        assertThrows("Should not be able to initialize hitpoints to 0",
                IllegalArgumentException.class, () -> new Wall(0));
        assertThrows("Should not be able to initialize hitpoints to a negative number",
                IllegalArgumentException.class, () -> new Wall(-5));
    }

    @Test
    public void getMaxHitPoints() {
        assertEquals("Expected 20, but was: " + w1.getMaxHitPoints(),
                20, w1.getMaxHitPoints());
        w1.takeDmg(5);
        assertEquals("Expected 20, but was: " + w1.getMaxHitPoints(),
                20, w1.getMaxHitPoints());
    }

    @Test
    public void getHitPoints() {
        assertEquals("Expected 20, but was: " + w1.getHitPoints(),
                20, w1.getHitPoints());
        w1.takeDmg(5);
        assertEquals("Expected 15, but was: " + w1.getHitPoints(),
                15, w1.getHitPoints());
    }

    @Test
    public void takeDmg() {
        assertThrows("Should not be able to take a negative amount of damage",
                IllegalArgumentException.class, () -> w1.takeDmg(-1));
        assertThrows("Should not be able to take 0 damage",
                IllegalArgumentException.class, () -> w1.takeDmg(0));
        assertEquals("Expected 20, but was: " + w1.getHitPoints(),
                20, w1.getHitPoints());
        assertFalse("Should return false if wall is not destroyed", w1.takeDmg(5));
        assertEquals("Expected 15, but was: " + w1.getHitPoints(),
                15, w1.getHitPoints());
        assertTrue("Should return true when wall is destroyed", w1.takeDmg(20));
        assertEquals("Expected 0, but was: " + w1.getHitPoints(),
                0, w1.getHitPoints());
        assertThrows("Should not be able to take damage after being destroyed",
                IllegalStateException.class, () -> w1.takeDmg(5));
    }
}
