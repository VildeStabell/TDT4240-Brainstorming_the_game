package com.mygdx.game.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    Player p1;

    @Before
    public void setUp() throws Exception {
        try {
            p1 = new Player("Player1");
        }
        catch (Exception e) {
            throw new Exception("Failed to setup PlayerTest: ", e);
        }
    }

    @Test
    public void constructor() {
        assertThrows("Should not be able to initialize username to null",
                IllegalArgumentException.class, () -> p1.setUsername(null));

        assertThrows("Should not be able to initialize username to an empty string",
                IllegalArgumentException.class, () -> p1.setUsername(""));
    }

   /** @Test NO LONGER IN USE BECAUSE OF FIREBASE
    public void getPlayerId() {
        assertNotNull("The PlayerId cannot be null", p1.getPlayerId());
        assertTrue("The PlayerId does not match the expected pattern",
                p1.getPlayerId().toString().matches(
                "([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})"));
        Player p2 = new Player("Player2");
        assertNotEquals("The PlayerId has to be unique", p1.getPlayerId(),
                p2.getPlayerId());
    }*/

    @Test
    public void getUsername() {
        assertEquals("Expected 'Player1', but got: " + p1.getUsername(),
                "Player1", p1.getUsername());
    }

    @Test
    public void setUsername() {
        p1.setUsername("Player2");
        assertEquals("Expected 'Player2', but got: " + p1.getUsername(),
                "Player2", p1.getUsername());

        assertThrows("Should not be able to set username to null",
                IllegalArgumentException.class, () -> p1.setUsername(null));

        assertThrows("Should not be able to set username to an empty string",
                IllegalArgumentException.class, () -> p1.setUsername(""));
    }
}
