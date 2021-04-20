package com.mygdx.game.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;


public class SessionTest {
    private Session session;
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
        session = new Session(15, 5, 2,
                3, p1);
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
                        5, 2, 3, players, 1234);});
        assertThrows("MaxHitPoints should not be able to be null or lower.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(-1,
                        5, 2, 3, players, 1234);});
        assertThrows("BrainDamage should not be able to be null or lower.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(15,
                        0, 2, 3, players, 1234);});
        assertThrows("BrainDamage should not be able to be null or lower.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(15,
                        -1, 2, 3, players, 1234);});
        assertThrows("MaxSelectedBrains should not be able to be lower than null.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(15,
                        5, -1, 3, players, 1234);});
        assertThrows("MaxRounds should not be able to be at least one.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(15,
                        5, 2, 0, players, 1234);});
        players.add(null);
        assertThrows("No players can be null.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(0,
                        5, 2, 3, players, 1234);});
        ArrayList<Player> emptyPlayers = new ArrayList<>();
        assertThrows("There must be at least one player.",
                IllegalArgumentException.class, () -> {Session s1 = new Session(0,
                        5, 2, 3, emptyPlayers, 1234);});
        assertThrows("SessionCode should be above the MIN parameter",
                IllegalArgumentException.class, () -> {Session s1 = new Session(15,
                        5, 2, 0, players,
                        Session.MIN - 1);});
        assertThrows("SessionCode should be below the MAX parameter",
                IllegalArgumentException.class, () -> {Session s1 = new Session(15,
                        5, 2, 0, players,
                        Session.MAX + 1);});
    }

    @Test
    public void startNewRound() {
        session.startNewRound();
        assertEquals("Expected 1 round, but was " + session.getNumOfRounds(),
                1, session.getNumOfRounds());
        ArrayList<Round> rounds = session.getRounds();
        assertEquals("Didn't receive the correct round", rounds.get(0),
                session.getCurrentRound());
        assertThrows("Shouldn't be able to start a new round before the previous is finished",
                IllegalStateException.class, () -> session.startNewRound());
        moveSingleToEliminationPhase(session.getCurrentRound());
        session.endRound();
        session.startNewRound();
        assertEquals("Expected 2 rounds but was " + session.getNumOfRounds(),
                2, session.getNumOfRounds());

        moveSingleToEliminationPhase(session.getCurrentRound());
        session.endRound();
        session.startNewRound();
        assertEquals("Expected 3 rounds but was " + session.getNumOfRounds(),
                3, session.getNumOfRounds());

        moveSingleToEliminationPhase(session.getCurrentRound());
        session.endRound();
        assertThrows("Should not be able to start more rounds then maxRounds",
                IllegalStateException.class, () -> session.startNewRound());
    }

    @Test
    public void endRound() {
        assertThrows("Should not be able to end a nonexistent round",
                IllegalStateException.class, () -> session.endRound());
        session.startNewRound();
        assertThrows("Should not be able to end a round in its brainstorming phase",
                IllegalStateException.class, () -> session.endRound());

        Round currentRound = session.getCurrentRound();
        moveSingleToEliminationPhase(currentRound);
        ArrayList<Brain> bBrains = currentRound.getBrainstormingBrains();
        currentRound.toggleBrain(p1, bBrains.get(0));
        currentRound.toggleBrain(p1, bBrains.get(1));
        assertFalse("The session should not be over yet", session.endRound());
        ArrayList<Brain> sBrains = session.getSelectedBrains();
        ArrayList<Brain> aBrains = session.getAllBrains();

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