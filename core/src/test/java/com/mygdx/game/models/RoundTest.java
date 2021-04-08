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
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Brain brain1;
    Brain brain2;
    Brain brain3;


    @Before
    public void setUp() throws Exception {
        try{
            player1 = new Player("Player1");
            player2 = new Player("Player2");
            player3 = new Player("Player3");
            player4 = new Player("Player4");
            brain1 = new Brain();
            brain2 = new Brain();
            brain3 = new Brain();
            players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));
            brains = new ArrayList<>(Arrays.asList(brain1, brain2, brain3));
            round = new Round(players, brains, maxHitPoints, BRAIN_DAMAGE, maxSelectedBrains);
        }
        catch (Exception e){
            throw new Exception("Failed to set up RoundTest: ", e);
        }
    }

    @Test
    public void getPlayers() {
        assertEquals(String.format("Expected %s but was %s",
                Arrays.asList(player1, player2, player3, player4), round.getPlayers()),
                players.toString(),
                round.getPlayers().toString());
    }

    @Test
    public void isInEliminationPhaseFalse() {
        assertFalse(round.isInEliminationPhase());
    }

    @Test
    public void isInEliminationPhaseTrue(){
        for (Player player : players){
            round.addBrainInBrainstormingPhase(player, "Idea");
        }
        round.startEliminationPhase(brains);
        assertTrue("Expected to be in the EliminationPhase, but is not",
                round.isInEliminationPhase());
    }

    @Test
    public void playersLeft() {
        round.addBrainInBrainstormingPhase(player1, "Idea");
        ArrayList<Player> playersLeft = new ArrayList<>(Arrays.asList(player2, player3, player4));
        assertEquals(String.format("Expected %s but was %s", playersLeft, round.playersLeft()),
                playersLeft.toString(), round.playersLeft().toString());
    }

    @Test
    public void  getBrainstorimingBrains(){
        for (Player player : players){
            round.addBrainInBrainstormingPhase(player, "Idea");
        }
        assertEquals(String.format("Expected length of %d, but was %d",
                players.size(), round.getBrainstorimingBrains().size()),
                4,
                round.getBrainstorimingBrains().size());

    }


    @Test
    public void startEliminationPhaseWhenPlayersLeft() {
        assertThrows("Should not be able to start eliminationphase when players are left",
                IllegalStateException.class, () -> round.startEliminationPhase(brains));
    }

    @Test
    public void getSelectedBrains() {
        for (Player player : players){
            round.addBrainInBrainstormingPhase(player, "Idea");
        }
        ArrayList<Brain> brainstormingBrains = round.getBrainstorimingBrains();
        round.startEliminationPhase(brainstormingBrains);
        Brain brainstormingBrain1 = brainstormingBrains.get(0);
        Brain brainstormingBrain2 = brainstormingBrains.get(1);
        round.toggleBrain(player1, brainstormingBrain1);
        round.toggleBrain(player2, brainstormingBrain2);
        assertTrue(String.format("Expected list with (%s,%s) but was %s",
                brainstormingBrain1, brainstormingBrain2, round.getSelectedBrains()),
                round.getSelectedBrains().size()==2 &&
                round.getSelectedBrains().contains(brainstormingBrain1) &&
                round.getSelectedBrains().contains(brainstormingBrain2));
    }

}
