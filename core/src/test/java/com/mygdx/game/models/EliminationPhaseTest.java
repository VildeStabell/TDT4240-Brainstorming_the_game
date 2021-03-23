package com.mygdx.game.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class EliminationPhaseTest {
    EliminationPhase ep1;
    EliminationPhase ep2;
    Brain b1;
    Brain b2;
    Brain b3;
    int maxSelected;

    @Before
    public void setUp() throws Exception {
        try {
            b1 = new Brain();
            b2 = new Brain();
            b3 = new Brain();
            maxSelected = 2;
            ep1 = new EliminationPhase(new ArrayList<>(Arrays.asList(b1, b2, b3)), maxSelected);
            ep2 = new EliminationPhase(new ArrayList<>(Arrays.asList(b1, b2, b3)));
        }
        catch (Exception e) {
            throw new Exception("Failed to set up EliminationPhaseTest: ", e);
        }
    }

    @Test
    public void constructor() {
        assertThrows("Should not be able to initialize with no brains",
                IllegalArgumentException.class, () ->
                        new EliminationPhase(new ArrayList<>(), maxSelected));
        assertThrows("Should not allow null-brains",
                IllegalArgumentException.class, () ->
                        new EliminationPhase(new ArrayList<>(Arrays.asList(b1, null, b2)),
                                maxSelected));
    }


    @Test
    public void getBrains() {
        HashMap<Brain,Boolean> map = new HashMap<Brain, Boolean>();
        map.put(b1, false);
        map.put(b2, false);
        map.put(b3, false);
        assertEquals(map, ep1.getBrains());
    }


    @Test
    public void getSelectedBrains() {
        assertEquals("Expected an empty list but was: " + ep1.getSelectedBrains(),
                new ArrayList<Brain>(), ep1.getSelectedBrains());
        assertEquals("Expected an empty list but was: " + ep2.getSelectedBrains(),
                new ArrayList<Brain>(), ep2.getSelectedBrains());
        ep1.toggleBrain(b1);
        assertEquals("Expected list with b1 but was: " + ep1.getSelectedBrains(),
                new ArrayList<>(Collections.singletonList(b1)), ep1.getSelectedBrains());
    }

    @Test
    public void getNumSelected() {
        assertEquals("Expected 0 but was: " + ep1.getNumSelected(),
                0, ep1.getNumSelected());
        ep1.toggleBrain(b2);
        assertEquals("Expected 1 but was: " + ep1.getNumSelected(),
                1, ep1.getNumSelected());
    }

    @Test
    public void toggleBrain() {
        assertThrows("Should not toggle brain if not in list",
                IllegalArgumentException.class, () -> ep1.toggleBrain(new Brain()));
        ep1.toggleBrain(b1);
        assertEquals("Expected list with b1 but was: " + ep1.getSelectedBrains(),
                new ArrayList<>(Collections.singletonList(b1)), ep1.getSelectedBrains());
        ep1.toggleBrain(b2);
        assertTrue("Expected list with b1 and b2 but was: " + ep1.getSelectedBrains(),
                ep1.getSelectedBrains().size() == 2 &&
                        ep1.getSelectedBrains().contains(b1) &&
                        ep1.getSelectedBrains().contains(b2));
        assertThrows("Should not be able to select more than maxSelected",
                IllegalArgumentException.class, () -> ep1.toggleBrain(b3));
        ep1.toggleBrain(b2);
        assertEquals("Expected list with b1 but was: " + ep1.getSelectedBrains(),
                new ArrayList<>(Collections.singletonList(b1)), ep1.getSelectedBrains());
        ep1.toggleBrain(b3);
        assertTrue("Expected list with b1 and b3 but was: " + ep1.getSelectedBrains(),
                ep1.getSelectedBrains().size() == 2 &&
                        ep1.getSelectedBrains().contains(b1) &&
                        ep1.getSelectedBrains().contains(b3));

        ep2.toggleBrain(b1);
        ep2.toggleBrain(b2);
        ep2.toggleBrain(b3);
        assertTrue("Expected list with b1, b2 and b3 but was: " + ep2.getSelectedBrains(),
                ep2.getSelectedBrains().size() == 3 &&
                        ep2.getSelectedBrains().contains(b1) &&
                        ep2.getSelectedBrains().contains(b2) &&
                        ep2.getSelectedBrains().contains(b3));
    }
}
