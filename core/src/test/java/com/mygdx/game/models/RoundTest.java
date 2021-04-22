package com.mygdx.game.models;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class RoundTest {
    int BRAIN_DAMAGE = 10;
    int maxHitPoints = 10;
    int maxSelectedBrains = 10;

    Round round;
    ArrayList<Brain> brains;
    Player player;
    Brain brain1;
    Brain brain2;
    Brain brain3;


    @Before
    public void setUp() throws Exception {
        try{
            player = new Player("Player1");
            brain1 = new Brain();
            brain2 = new Brain();
            brain3 = new Brain();
            brains = new ArrayList<>(Arrays.asList(brain1, brain2, brain3));
            round = new Round(player, brains, maxHitPoints, BRAIN_DAMAGE, maxSelectedBrains);
        }
        catch (Exception e){
            throw new Exception("Failed to set up RoundTest: ", e);
        }
    }

    @Test
    public void getPlayer() {
        assertEquals(String.format("Expected %s but was %s", player, round.getPlayer()),
                player.toString(),
                round.getPlayer().toString());
    }

    @Test
    public void isInEliminationPhaseFalse() {
        assertFalse(round.isInEliminationPhase());
    }

    @Test
    public void isInEliminationPhaseTrue(){
        round.addBrainInBrainstormingPhase("Idea");
        round.startEliminationPhase(brains);
        assertTrue("Expected to be in the EliminationPhase, but is not",
                round.isInEliminationPhase());
    }


    @Test
    public void isWallStandingFalse() {
        round.addBrainInBrainstormingPhase("Idea");
        assertFalse(String.format("Expected %s but was %s", false, round.isWallStanding()), round.isWallStanding());
    }

    @Test
    public void isWallStandingTrue(){
        int nrNeededForFallenWall = maxHitPoints/BRAIN_DAMAGE;
        for (int nr = 1; nr < nrNeededForFallenWall; nr++){
            round.addBrainInBrainstormingPhase("Idea");
        }
        assertTrue(String.format("Expected %s but was %s", true, round.isWallStanding()), round.isWallStanding());
    }

    @Test
    public void  getBrainstorimingBrains(){
        round.addBrainInBrainstormingPhase("Idea");
        assertEquals(String.format("Expected length of %d, but was %d",
                1, round.getBrainstormingBrains().size()),
                1,
                round.getBrainstormingBrains().size());
    }


    @Test
    public void startEliminationPhaseWhenWallIsStanding() {
        assertThrows("Should not be able to start eliminationphase when wall is standing",
                IllegalStateException.class, () -> round.startEliminationPhase(brains));
    }

    @Test
    public void getSelectedBrains() {
        round.addBrainInBrainstormingPhase("Idea");
        ArrayList<Brain> brainstormingBrains = round.getBrainstormingBrains();
        round.startEliminationPhase(brainstormingBrains);
        Brain brainstormingBrain1 = brainstormingBrains.get(0);
        round.toggleBrain(brainstormingBrain1);
        assertTrue(String.format("Expected list with %s but was %s",
                brainstormingBrain1, round.getSelectedBrains()),
                round.getSelectedBrains().size()==1 &&
                        round.getSelectedBrains().contains(brainstormingBrain1));
    }
}
