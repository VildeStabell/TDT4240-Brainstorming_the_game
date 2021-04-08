package com.mygdx.game.models;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BrainstormingPhaseTest {
    int BRAIN_DAMAGE = 10;
    int maxHitPoints = 100;

    BrainstormingPhase brainstormingPhase;
    Player player1;
    Brain brain1;
    Brain brain2;
    String idea1;
    String idea2;



    @Before
    public void setUp() throws Exception {
        try {
            player1 = new Player("Player1");
            brainstormingPhase = new BrainstormingPhase(player1, maxHitPoints, BRAIN_DAMAGE);
            brain1 = new Brain();
            brain2 = new Brain();
            idea1 = "Idea 1";
            idea2 = "Idea 2";
        }
        catch (Exception e){
            throw new Exception("Failed to set up BrainstormingPhaseTest: ", e);
        }
    }

    @Test
    public void getBrainsWithCorrectParameters(){
        ArrayList<Brain> expectedBrainArrayList = new ArrayList<>();
        expectedBrainArrayList.add(brain1);
        expectedBrainArrayList.add(brain2);
        brainstormingPhase.putIdeaOnBrainAndFire(brain1, idea1);
        brainstormingPhase.putIdeaOnBrainAndFire(brain2, idea2);
        assertEquals("Expected list with b1 and b2, but was" + brainstormingPhase.getBrains(),
                expectedBrainArrayList.toString(), brainstormingPhase.getBrains().toString());
    }

    @Test
    public void getPlayer() {
        assertEquals("Expected Player1 but was" + brainstormingPhase.getPlayer(),
                player1.getPlayerId(), brainstormingPhase.getPlayer().getPlayerId());
    }

    @Test
    public void putIdeaOnBrainAndFireTestBrainIdeaCheckIdea() {
        assertFalse("Expected wall to be standing, but the wall has fallen",
                brainstormingPhase.putIdeaOnBrainAndFire(brain1, idea1));
        assertEquals(String.format("Expected %s, but was %s", idea1,
                brainstormingPhase.getBrains().get(0).getIdea(0).getIdea()),
                idea1,
                brainstormingPhase.getBrains().get(0).getIdea(0).getIdea());
        assertEquals(String.format("Expected %s, but was %s", idea1, brain1.getIdea(0).getIdea()),
                idea1,
                brain1.getIdea(0).getIdea());
    }

    @Test
    public void putIdeaOnBrainAndFireTestBrainIdeaAndCheckWallIsFallen(){
        int roundsNeededForFallenWall = maxHitPoints/BRAIN_DAMAGE;
        for (int round = 1; round < roundsNeededForFallenWall; round++){
            assertFalse("Expected wall to be standing, but wall has fallen",
                    brainstormingPhase.putIdeaOnBrainAndFire(new Brain(), idea1));
        }
        assertTrue("Expected wall to fall, but it's standing",
                brainstormingPhase.putIdeaOnBrainAndFire(new Brain(), idea1));
    }

    @Test
    public void putIdeaOnBrainAndFireTestDuplicateBrain(){
        brainstormingPhase.putIdeaOnBrainAndFire(brain1, idea1);
        assertThrows("Should not be possible to fire same brain twice",
                IllegalArgumentException.class, () -> brainstormingPhase.putIdeaOnBrainAndFire(brain1, idea1));
    }

}
