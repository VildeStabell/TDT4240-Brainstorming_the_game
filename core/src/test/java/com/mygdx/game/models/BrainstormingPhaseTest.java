package com.mygdx.game.models;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BrainstormingPhaseTest {
    int BRAIN_DAMAGE = 10;
    int maxHitPoints = 100;

    BrainstormingPhase bp;
    Player p1;
    Brain b1;
    Brain b2;
    String i1;
    String i2;



    @Before
    public void setUp() throws Exception {
        try {
            p1 = new Player("Player1");
            bp = new BrainstormingPhase(p1, maxHitPoints, BRAIN_DAMAGE);
            b1 = new Brain();
            b2 = new Brain();
            i1 = "Idea 1";
            i2 = "Idea 2";
        }
        catch (Exception e){
            throw new Exception("Failed to set up BrainstormingPhaseTest: ", e);
        }
    }

    @Test
    public void getBrainsWithCorrectParameters(){
        ArrayList<Brain> brainArrayList = new ArrayList<>();
        brainArrayList.add(b1);
        brainArrayList.add(b2);
        bp.putIdeaOnBrainAndFire(b1, i1);
        bp.putIdeaOnBrainAndFire(b2, i2);
        assertEquals("Expected list with b1 and b2, but was" + bp.getBrains(), brainArrayList, bp.getBrains());
    }

    @Test
    public void getPlayer() {
        assertEquals("Expected Player1 but was" + bp.getPlayer(), p1, bp.getPlayer());
    }

    @Test
    public void putIdeaOnBrainAndFireTestBrainIdeaCheckIdea() {
        assertFalse("Expected false but was true",bp.putIdeaOnBrainAndFire(b1, i1));
        assertEquals("Expected Idea 1, but was" + bp.getBrains().get(0).getIdea(0).getIdea(), i1,
                bp.getBrains().get(0).getIdea(0).getIdea());
        assertEquals("Expected Idea 1, but was" + b1.getIdea(0).getIdea(), i1,
                b1.getIdea(0).getIdea());
    }

    @Test
    public void putIdeaOnBrainAndFireTestBrainIdeaAndCheckWallIsFallen(){
        for (int i = 0; i < 9; i++){
            assertFalse("Expected false but was true",bp.putIdeaOnBrainAndFire(new Brain(), i1));
        }
        assertTrue("Expected true but was false",bp.putIdeaOnBrainAndFire(new Brain(), i1));
    }

    @Test
    public void putIdeaOnBrainAndFireTestDuplicateBrain(){
        bp.putIdeaOnBrainAndFire(b1, i1);
        assertThrows("Should not be possible to fire same brain twice",
                IllegalArgumentException.class, () -> bp.putIdeaOnBrainAndFire(b1,i1));
    }

}
