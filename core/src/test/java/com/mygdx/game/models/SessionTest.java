package com.mygdx.game.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;


public class SessionTest {
    private Session singleplayerSession;
    private Session multiplayerSession;
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
        players.add(p1);
        p3 = new Player("player3");
        players.add(p1);
        brains = new ArrayList<>();
        b1 = new Brain();
        brains.add(b1);
        b2 = new Brain();
        brains.add(b2);
        b3 = new Brain();
        brains.add(b3);
        singleplayerSession = new Session(15, 5, 2,
                3, p1);
        multiplayerSession = new Session(15, 5, 2,
                3, players);
    }

    @Test
    public void singlePlayerConstructor() {
        assertThrows("MaxHitPoints should not be able to be null or lower.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(0,
                        5, 2, 3, p1);});
        assertThrows("MaxHitPoints should not be able to be null or lower.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(-1,
                        5, 2, 3, p1);});
        assertThrows("BrainDamage should not be able to be null or lower.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(15,
                        0, 2, 3, p1);});
        assertThrows("BrainDamage should not be able to be null or lower.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(15,
                        -1, 2, 3, p1);});
        assertThrows("MaxSelectedBrains should not be able to be lower than null.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(15,
                        5, -1, 3, p1);});
        assertThrows("MaxRounds should not be able to be at least one.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(15,
                        5, 2, 0, p1);});
    }

    @Test
    public void multiPlayerConstructor() {
        assertThrows("MaxHitPoints should not be able to be null or lower.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(0,
                        5, 2, 3, players);});
        assertThrows("MaxHitPoints should not be able to be null or lower.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(-1,
                        5, 2, 3, players);});
        assertThrows("BrainDamage should not be able to be null or lower.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(15,
                        0, 2, 3, players);});
        assertThrows("BrainDamage should not be able to be null or lower.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(15,
                        -1, 2, 3, players);});
        assertThrows("MaxSelectedBrains should not be able to be lower than null.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(15,
                        5, -1, 3, players);});
        assertThrows("MaxRounds should not be able to be at least one.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(15,
                        5, 2, 0, players);});
        players.add(null);
        assertThrows("No players can be null.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(0,
                        5, 2, 3, players);});
        ArrayList<Player> emptyPlayers = new ArrayList<>();
        assertThrows("There must be at least one player.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(0,
                        5, 2, 3, emptyPlayers);});
    }

    @Test
    public void startNewRound() {
        singleplayerSession.startNewRound();
        assertEquals("Expected 1 round, but was " + singleplayerSession.getNumOfRounds(),
                1, singleplayerSession.getNumOfRounds());
        ArrayList<Round> rounds = singleplayerSession.getRounds();
        assertEquals("Didn't receive the correct round", rounds.get(0),
                singleplayerSession.getCurrentRound());
        assertThrows("Shouldn't be able to start a new round before the previous is finished",
                IllegalStateException.class, () -> singleplayerSession.startNewRound());
        moveSingleToEliminationPhase(singleplayerSession.getCurrentRound());
        singleplayerSession.endRound();
        singleplayerSession.startNewRound();
        assertEquals("Expected 2 rounds but was " + singleplayerSession.getNumOfRounds(),
                2, singleplayerSession.getNumOfRounds());

        moveSingleToEliminationPhase(singleplayerSession.getCurrentRound());
        singleplayerSession.endRound();
        singleplayerSession.startNewRound();
        assertEquals("Expected 3 rounds but was " + singleplayerSession.getNumOfRounds(),
                3, singleplayerSession.getNumOfRounds());

        moveSingleToEliminationPhase(singleplayerSession.getCurrentRound());
        singleplayerSession.endRound();
        assertThrows("Should not be able to start more rounds then maxRounds",
                IllegalStateException.class, () -> singleplayerSession.startNewRound());
    }

    @Test
    public void endRound() {
        assertThrows("Should not be able to end a nonexistent round",
                IllegalStateException.class, () -> singleplayerSession.endRound());
        singleplayerSession.startNewRound();
        assertThrows("Should not be able to end a round in its brainstorming phase",
                IllegalStateException.class, () -> singleplayerSession.endRound());

        Round currentRound = singleplayerSession.getCurrentRound();
        moveSingleToEliminationPhase(currentRound);
        ArrayList<Brain> bBrains = currentRound.getBrainstormingBrains();
        currentRound.toggleBrain(p1, bBrains.get(0));
        currentRound.toggleBrain(p1, bBrains.get(1));
        assertFalse("The session should not be over yet", singleplayerSession.endRound());
        ArrayList<Brain> sBrains = singleplayerSession.getSelectedBrains();
        ArrayList<Brain> aBrains = singleplayerSession.getAllBrains();

        assertTrue("Selected brains should contain b1 and b2, but was " + sBrains,
                sBrains.size() == 2 && sBrains.contains(bBrains.get(0)) &&
                        sBrains.contains(bBrains.get(1)));
        assertTrue("Test", aBrains.contains(bBrains.get(2)));
        assertTrue("The list of all brains should contain b1, b2 and b3, but was " +
                aBrains, aBrains.size() == 3 && aBrains.contains(bBrains.get(0)) &&
                aBrains.contains(bBrains.get(1)) && aBrains.contains(bBrains.get(2)));
    }

    private void moveSingleToEliminationPhase(Round round) {
        round.addBrainInBrainstormingPhase(p1, "Test idea 1");
        round.addBrainInBrainstormingPhase(p1, "Test idea 2");
        round.addBrainInBrainstormingPhase(p1, "Test idea 3");
        round.startEliminationPhase(round.getBrainstormingBrains());
    }
}