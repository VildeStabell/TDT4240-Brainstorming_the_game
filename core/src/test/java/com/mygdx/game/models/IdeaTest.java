package com.mygdx.game.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IdeaTest {
    Player p1;
    Idea idea;

    @Before
    public void setUp() {
        p1 = new Player("Player 1");
        idea = new Idea("This is an idea", p1);
    }

    @Test
    public void getIdea() {
        assertEquals("This is an idea", idea.getIdea());
    }

    @Test
    public void getPlayer() {
        assertEquals(p1, idea.getPlayer());
    }
}