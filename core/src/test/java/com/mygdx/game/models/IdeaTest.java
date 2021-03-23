package com.mygdx.game.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IdeaTest {
    Player p1;
    Player p2;
    Idea idea1;
    Idea idea2;

    @Before
    public void setUp() throws Exception {
        try {
            p1 = new Player("Player 1");
            p2 = new Player("Player 2");
            idea1 = new Idea("This is an idea", p1);
            idea2 = new Idea("This is another idea", p2);
        }
        catch (Exception e) {
            throw new Exception("Failed to setup IdeaTest: ", e);
        }
    }

    @Test
    public void constructor() {
        assertThrows("Shouldn't be able to create an Idea that is null",
                IllegalArgumentException.class, () -> new Idea(null, p1));
        assertThrows("Shouldn't be able to create an empty idea",
                IllegalArgumentException.class, () -> new Idea("", p1));
        assertThrows("Shouldn't be able to create an Idea without a player",
                IllegalArgumentException.class, () -> new Idea("Hello", null));
    }

    @Test
    public void getIdea() {
        assertEquals("Expected 'This is an idea', but got: " + idea1.getIdea(),
                "This is an idea", idea1.getIdea());
        assertEquals("Expected 'This is another idea', but got: " + idea2.getIdea(),
                "This is another idea", idea2.getIdea());
    }

    @Test
    public void getPlayer() {
        assertEquals("Expected Player 1, but got: " + idea1.getPlayer(),
                p1, idea1.getPlayer());
        assertEquals("Expected Player 2, but got: " + idea2.getPlayer(),
                p2, idea2.getPlayer());
    }
}
