package com.mygdx.game.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class SessionTest {
    private Session spSession;
    private Session mpSession;
    private ArrayList<Player> players;
    private ArrayList<Brain> brains;
    private Player p1;
    private Player p2;
    private Player p3;
    private Brain b1;
    private Brain b2;
    private Brain b3;

    @Before
    public void setUp() {
        players = new ArrayList<>();
        p1 = new Player("player1");
        players.add(p1);
        p2 = new Player("player2");
        players.add(p2);
        p3 = new Player("player3");
        players.add(p2);
        brains = new ArrayList<>();
        b1 = new Brain();
        brains.add(b1);
        b2 = new Brain();
        brains.add(b2);
        b3 = new Brain();
        brains.add(b3);
        spSession = new Session(15, 5, 2,
                3, p1);
        mpSession = new Session(15, 5, 2,
                3, players, 1234);
    }

    @Test
    public void singlePlayerConstructor() {
        assertThrows("MaxHitPoints should not be able to be null or lower.",
                IllegalArgumentException.class, () -> new Session(0,
                        5, 2, 3, p1));
        assertThrows("MaxHitPoints should not be able to be null or lower.",
                IllegalArgumentException.class, () -> new Session(-1,
                        5, 2, 3, p1));
        assertThrows("BrainDamage should not be able to be null or lower.",
                IllegalArgumentException.class, () -> new Session(15,
                        0, 2, 3, p1));
        assertThrows("BrainDamage should not be able to be null or lower.",
                IllegalArgumentException.class, () -> new Session(15,
                        -1, 2, 3, p1));
        assertThrows("MaxSelectedBrains should not be able to be lower than null.",
                IllegalArgumentException.class, () -> new Session(15,
                        5, -1, 3, p1));
        assertThrows("MaxRounds should not be able to be at least one.",
                IllegalArgumentException.class, () -> new Session(15,
                        5, 2, 0, p1));
    }

    @Test
    public void multiPlayerConstructor() {
        assertThrows("MaxHitPoints should not be able to be null or lower.",
                IllegalArgumentException.class, () -> new Session(0,5,
                        2, 3, players, 1234));
        assertThrows("MaxHitPoints should not be able to be null or lower.",
                IllegalArgumentException.class, () -> new Session(-1,5,
                        2, 3, players, 1234));
        assertThrows("BrainDamage should not be able to be null or lower.",
                IllegalArgumentException.class, () -> new Session(15,0,
                        2, 3, players, 1234));
        assertThrows("BrainDamage should not be able to be null or lower.",
                IllegalArgumentException.class, () -> new Session(15,-1,
                        2, 3, players, 1234));
        assertThrows("MaxSelectedBrains should not be able to be lower than null.",
                IllegalArgumentException.class, () -> new Session(15,5,
                        -1, 3, players, 1234));
        assertThrows("MaxRounds should not be able to be at least one.",
                IllegalArgumentException.class, () -> new Session(15,5,
                        2, 0, players, 1234));
        assertThrows("There must be at least one player.",
                IllegalArgumentException.class, () -> new Session(0,5,
                        2, 3, new ArrayList<>(), 1234));
        assertThrows("SessionCode should be above the MIN parameter",
                IllegalArgumentException.class, () -> new Session(15,5,
                        2, 0, players,Session.MIN - 1));
        assertThrows("SessionCode should be below the MAX parameter",
                IllegalArgumentException.class, () -> new Session(15,5,
                        2, 0, players,Session.MAX + 1));
        players.add(null);
        assertThrows("No players can be null.",
                IllegalArgumentException.class, () -> new Session(0, 5,
                        2, 3, players, 1234));
    }

    @Test
    public void startNewRound() {
        assertEquals("Expected 0 rounds, but was " + spSession.getNumOfRounds(),
                0, spSession.getNumOfRounds());
        spSession.startNewRound();
        assertEquals("Expected 1 round, but was " + spSession.getNumOfRounds(),
                1, spSession.getNumOfRounds());
        ArrayList<Round> rounds = spSession.getRounds();
        assertEquals("Didn't receive the correct rounds", rounds, spSession.getCurrentRounds());
        assertThrows("Shouldn't be able to start a new round before the previous is finished",
                IllegalStateException.class, () -> spSession.startNewRound());
        moveToEliminationPhase(spSession.getCurrentRounds());
        spSession.endRound();
        spSession.startNewRound();
        assertEquals("Expected 2 rounds but was " + spSession.getNumOfRounds(),
                2, spSession.getNumOfRounds());

        moveToEliminationPhase(spSession.getCurrentRounds());
        spSession.endRound();
        spSession.startNewRound();
        assertEquals("Expected 3 rounds but was " + spSession.getNumOfRounds(),
                3, spSession.getNumOfRounds());

        moveToEliminationPhase(spSession.getCurrentRounds());
        spSession.endRound();
        assertThrows("Should not be able to start more rounds then maxRounds",
                IllegalStateException.class, () -> spSession.startNewRound());

        assertEquals("Expected 0 rounds, but was " + mpSession.getNumOfRounds(),
                0, mpSession.getNumOfRounds());
        mpSession.startNewRound();
        assertEquals("Expected 1 round, but was " + mpSession.getNumOfRounds(),
                1, mpSession.getNumOfRounds());
        ArrayList<Round> mpRounds = mpSession.getRounds();
        assertEquals("Didn't receive the correct rounds", mpRounds,
                mpSession.getCurrentRounds());
    }

    @Test
    public void endSPRound() {
        assertThrows("Should not be able to end a nonexistent round",
                IllegalStateException.class, () -> spSession.endRound());
        spSession.startNewRound();
        assertThrows("Should not be able to end a round in its brainstorming phase",
                IllegalStateException.class, () -> spSession.endRound());

        ArrayList<Round> currentRounds = spSession.getCurrentRounds();
        moveToEliminationPhase(currentRounds);
        Round round = currentRounds.get(0);
        ArrayList<Brain> bBrains = round.getBrainstormingBrains();
        round.toggleBrain(bBrains.get(0));
        round.toggleBrain(bBrains.get(1));


        assertFalse("The session should not be over yet", spSession.endRound());
        ArrayList<Brain> sBrains = spSession.getSelectedBrains();
        ArrayList<Brain> aBrains = spSession.getAllBrains();

        assertTrue("Selected brains should contain b1 and b2, but was " + sBrains,
                sBrains.size() == 2 && sBrains.contains(bBrains.get(0)) &&
                        sBrains.contains(bBrains.get(1)));
        assertTrue("The list of all brains should be " + bBrains + ",\n but was " +
                aBrains, aBrains.size() == 3 && aBrains.contains(bBrains.get(0)) &&
                aBrains.contains(bBrains.get(1)) && aBrains.contains(bBrains.get(2)));
    }

    @Test
    public void endMPRound() {
        mpSession.startNewRound();
        assertThrows("Should not be able to end a round in its brainstorming phase",
                IllegalStateException.class, () -> mpSession.endRound());
        ArrayList<Round> currentRounds = mpSession.getCurrentRounds();
        moveToEliminationPhase(new ArrayList<>(Collections.singletonList(currentRounds.get(0))));
        assertThrows("Should not be able to end a round while at least one player is in" +
                        "the brainstorming phase", IllegalStateException.class,
                () -> mpSession.endRound());
        moveToEliminationPhase(new ArrayList<>(Arrays.asList(currentRounds.get(1),
                currentRounds.get(2))));

        ArrayList<Brain> bBrains = new ArrayList<>();
        ArrayList<Brain> actualSBrains = new ArrayList<>();
        for(Round round : currentRounds) {
            ArrayList<Brain> roundBrains = round.getBrainstormingBrains();
            bBrains.addAll(roundBrains);
            round.toggleBrain(roundBrains.get(0));
            actualSBrains.add(roundBrains.get(0));
            round.toggleBrain(roundBrains.get(1));
            actualSBrains.add(roundBrains.get(1));
        }

        assertFalse("The session should not be over yet", mpSession.endRound());
        ArrayList<Brain> sBrains = mpSession.getSelectedBrains();
        ArrayList<Brain> aBrains = mpSession.getAllBrains();

        assertTrue("Selected brains should be " + actualSBrains + ",\n but was " + sBrains,
                sBrains.size() == actualSBrains.size() && sBrains.containsAll(actualSBrains));
        assertTrue("The list of all brains should be " + bBrains + ",\n but was " +
                aBrains, aBrains.size() == bBrains.size() && aBrains.containsAll(bBrains));
    }

    @Test
    public void getNumOfRounds() {
        assertEquals("Expected 0 rounds, but was " + spSession.getNumOfRounds(), 0,
        spSession.getNumOfRounds());
        spSession.startNewRound();
        assertEquals("Expected 1 round, but was " + spSession.getNumOfRounds(), 1,
                spSession.getNumOfRounds());
        moveToEliminationPhase(spSession.getCurrentRounds());
        spSession.endRound();
        spSession.startNewRound();
        assertEquals("Expected 2 round2, but was " + spSession.getNumOfRounds(),
                2, spSession.getNumOfRounds());

        assertEquals("Expected 0 rounds, but was " + mpSession.getNumOfRounds(), 0,
                mpSession.getNumOfRounds());
        mpSession.startNewRound();
        assertEquals("Expected 1 round, but was " + mpSession.getNumOfRounds(), 1,
                mpSession.getNumOfRounds());
        moveToEliminationPhase(mpSession.getCurrentRounds());
        mpSession.endRound();
        mpSession.startNewRound();
        assertEquals("Expected 2 rounds, but was " + mpSession.getNumOfRounds(),
                2, mpSession.getNumOfRounds());
    }

    private void moveToEliminationPhase(ArrayList<Round> rounds) {
        ArrayList<Brain> brains = new ArrayList<>();
        for(Round round : rounds) {
            round.addBrainInBrainstormingPhase("Test idea 1");
            round.addBrainInBrainstormingPhase("Test idea 2");
            round.addBrainInBrainstormingPhase("Test idea 3");
            for(Brain brain : round.getBrainstormingBrains())
                if(!brains.contains(brain))
                    brains.add(brain);
        }
        for(Round round : rounds) {
            round.startEliminationPhase(brains);
        }
    }
}