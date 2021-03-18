package com.mygdx.game.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BrainTest {
    Brain b1;
    Player p1;
    Idea i1;
    Idea i2;

    @Before
    public void setUp() throws Exception {
        try {
            b1 = new Brain();
            p1 = new Player("Player1");
            i1 = new Idea("Idea 1", p1);
            i2 = new Idea("Idea 2", p1);
        }
        catch (Exception e) {
            throw new Exception("Failed to set up BrainTest: ", e);
        }
    }

    @Test
    public void getIdeas() {
        assertEquals("Expected array length to be 0, but was: " +
                b1.getIdeas().size(),0, b1.getIdeas().size());
        b1.addIdea(i1);
        assertEquals("Expected array length to be 1, but was: " +
                b1.getIdeas().size(),1, b1.getIdeas().size());
        b1.addIdea(i2);
        assertEquals("Expected array length to be 2, but was: " +
                b1.getIdeas().size(),2, b1.getIdeas().size());
    }

    @Test
    public void getIdea() {
        b1.addIdea(i1);
        assertEquals("Expected Idea 1, but was: " + b1.getIdea(0),
                i1, b1.getIdea(0));
        b1.addIdea(i2);
        assertEquals("Expected Idea 2, but was: " + b1.getIdea(1),
                i2, b1.getIdea(1));
        assertThrows("Should not be able to access an out of bound index",
                IndexOutOfBoundsException.class, () -> b1.getIdea(-1));
        assertThrows("Should not be able to access an out of bound index",
                IndexOutOfBoundsException.class, () -> b1.getIdea(3));
    }

    @Test
    public void getSize() {
        assertEquals("Expected size 0, but was: " + b1.getSize(),
                0, b1.getSize());
        b1.addIdea(i1);
        assertEquals("Expected size 1, but was: " + b1.getSize(),
                1, b1.getSize());
    }

    @Test
    public void addIdea() {
        assertThrows("Should not be able to add null-idea",
                IllegalArgumentException.class, () -> b1.addIdea(null));
        b1.addIdea(i1);
        assertThrows("Should not be able to add idea multiple times",
                IllegalArgumentException.class, () -> b1.addIdea(i1));
    }
}