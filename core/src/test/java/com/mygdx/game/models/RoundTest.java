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
    ArrayList<Player> players;
    ArrayList<Brain> brains;
    Player p1;
    Player p2;
    Player p3;
    Player p4;
    Brain b1;
    Brain b2;
    Brain b3;


    @Before
    public void setUp() throws Exception {
        try{
            p1 = new Player("Player1");
            p2 = new Player("Player2");
            p3 = new Player("Player3");
            p4 = new Player("Player4");
            b1 = new Brain();
            b2 = new Brain();
            b3 = new Brain();
            players = new ArrayList<>(Arrays.asList(p1,p2,p3,p4));
            brains = new ArrayList<>(Arrays.asList(b1,b2,b3));
            round = new Round(players, brains, maxHitPoints, BRAIN_DAMAGE, maxSelectedBrains);
        }
        catch (Exception e){
            throw new Exception("Failed to set up RoundTest: ", e);
        }
    }

    @Test
    public void getPlayers() {
        assertEquals("Expected (p1,p2,p3,p4) but was" + round.getPlayers(), players, round.getPlayers());
    }

    @Test
    public void isInEliminationPhaseFalse() {
        assertFalse(round.isInEliminationPhase());
    }

    @Test
    public void isInEliminationPhaseTrue(){
        for (Player p : players){
            round.addBrainInBrainstormingPhase(p, "Idea");
        }
        round.startEliminationPhase(brains);
        assertTrue("Expected true, but was false", round.isInEliminationPhase());
    }

    @Test
    public void playersLeft() {
        round.addBrainInBrainstormingPhase(p1, "Idea");
        ArrayList<Player> playersLeft = new ArrayList<>(Arrays.asList(p2,p3,p4));
        assertEquals("Expected (p2,p3,p4) but was"+ round.playersLeft(), playersLeft, round.playersLeft());
    }

    @Test
    public void  getBrainstorimingBrains(){
        for (Player p : players){
            round.addBrainInBrainstormingPhase(p, "Idea");
        }
        assertEquals("Expected length of 4, but was" + round.getBrainstorimingBrains().size(),
                4, round.getBrainstorimingBrains().size());

    }


    @Test
    public void startEliminationPhaseWhenPlayersLeft() {
        assertThrows("Should not be able to start eliminationphase when players are left",
                IllegalStateException.class, () -> round.startEliminationPhase(brains));
    }

    @Test
    public void getSelectedBrains() {
        for (Player p : players){
            round.addBrainInBrainstormingPhase(p, "Idea");
        }
        ArrayList<Brain> brainstormingBrains = round.getBrainstorimingBrains();
        round.startEliminationPhase(brainstormingBrains);
        Brain b1 = brainstormingBrains.get(0);
        Brain b2 = brainstormingBrains.get(1);
        round.toggleBrain(p1, b1);
        round.toggleBrain(p2, b2);
        assertTrue("Expected list with (" + b1+ "," +
                b2+"but was"+round.getSelectedBrains(),
                round.getSelectedBrains().size()==2 &&
                round.getSelectedBrains().contains(b1) &&
                round.getSelectedBrains().contains(b2));
    }

}
