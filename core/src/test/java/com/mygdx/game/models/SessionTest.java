package com.mygdx.game.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;


public class SessionTest {
    private Session session;
    private Player player;
    private final Random random = new Random();

    @Before
    public void setUp() {
        player = new Player("player1");
        session = new Session(15, 5, 2,
                3, player, 1234);
    }

    @Test
    public void constructor() {
        assertThrows("MaxHitPoints should not be able to be null or lower.",
                IllegalArgumentException.class, () -> new Session(0, 5,
                        2, 3, player, 1234));
        assertThrows("MaxHitPoints should not be able to be null or lower.",
                IllegalArgumentException.class, () -> new Session(-1, 5,
                        2, 3, player, 1234));
        assertThrows("BrainDamage should not be able to be null or lower.",
                IllegalArgumentException.class, () -> new Session(15, 0,
                        2, 3, player, 1234));
        assertThrows("BrainDamage should not be able to be null or lower.",
                IllegalArgumentException.class, () -> new Session(15, -1,
                        2, 3, player, 1234));
        assertThrows("MaxSelectedBrains should not be able to be lower than null.",
                IllegalArgumentException.class, () -> new Session(15, 5,
                        -1, 3, player, 1234));
        assertThrows("MaxRounds should be at least one.", IllegalArgumentException.class,
                () -> new Session(15, 5, 2, 0,
                        player, 1234));
        assertThrows("Player cannot be null.", IllegalArgumentException.class,
                () -> new Session(0,5, 2, 3,
                        null, 1234));
        assertThrows("SessionCode should be above the MIN parameter",
                IllegalArgumentException.class, () -> new Session(15,5,
                        2, 0, player,Session.MIN - 1));
        assertThrows("SessionCode should be below the MAX parameter",
                IllegalArgumentException.class, () -> new Session(15,5,
                        2, 0, player,Session.MAX + 1));
    }

    @Test
    public void startNewRound() {
        assertEquals("Expected 0 rounds, but was " + session.getNumOfRounds(),
                0, session.getNumOfRounds());
        session.startNewRound();
        assertEquals("Expected 1 round, but was " + session.getNumOfRounds(),
                1, session.getNumOfRounds());
        assertEquals("Didn't receive the correct round", session.getRounds().get(0),
                session.getCurrentRound());
        assertThrows("Shouldn't be able to start a new round before the previous is finished",
                IllegalStateException.class, () -> session.startNewRound());
        moveToEliminationPhase(session.getCurrentRound());
        session.endRound();
        session.startNewRound();
        assertEquals("Expected 2 rounds but was " + session.getNumOfRounds(),
                2, session.getNumOfRounds());
        assertEquals("Didn't receive the correct round", session.getRounds().get(1),
                session.getCurrentRound());

        moveToEliminationPhase(session.getCurrentRound());
        session.endRound();
        session.startNewRound();
        assertEquals("Expected 3 rounds but was " + session.getNumOfRounds(),
                3, session.getNumOfRounds());

        moveToEliminationPhase(session.getCurrentRound());
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

        ArrayList<Brain> bBrains = new ArrayList<>();
        ArrayList<Brain>[] brainLists = toggleBrains(session.getCurrentRound(), bBrains);
        bBrains = brainLists[0];
        ArrayList<Brain> actualSBrains = brainLists[1];
        assertFalse("The session should not be over yet", session.endRound());
        checkBrainContent(actualSBrains, bBrains);


        session.startNewRound();
        brainLists = toggleBrains(session.getCurrentRound(), bBrains);
        bBrains = brainLists[0];
        actualSBrains = brainLists[1];
        assertFalse("The session should not be over yet", session.endRound());
        checkBrainContent(actualSBrains, bBrains);


        session.startNewRound();
        brainLists = toggleBrains(session.getCurrentRound(), bBrains);
        bBrains = brainLists[0];
        actualSBrains = brainLists[1];
        assertTrue("The session should over", session.endRound());
        checkBrainContent(actualSBrains, bBrains);
    }

    private void moveToEliminationPhase(Round round) {
        round.addBrainInBrainstormingPhase("Test idea " + random.nextInt());
        round.addBrainInBrainstormingPhase("Test idea " + random.nextInt());
        round.addBrainInBrainstormingPhase("Test idea "+ random.nextInt());
        round.startEliminationPhase(round.getBrainstormingBrains());

    }

    private ArrayList<Brain>[] toggleBrains(Round currentRound, ArrayList<Brain> bBrains) {
        moveToEliminationPhase(currentRound);
        ArrayList<Brain> roundBrains = currentRound.getBrainstormingBrains();
        bBrains.addAll(roundBrains);
        ArrayList<Brain> actualSBrains = new ArrayList<>();
        currentRound.toggleBrain(roundBrains.get(0));
        actualSBrains.add(roundBrains.get(0));
        currentRound.toggleBrain(roundBrains.get(1));
        actualSBrains.add(roundBrains.get(1));
        return new ArrayList[]{bBrains, actualSBrains};
    }

    private void checkBrainContent(ArrayList<Brain> actualSBrains, ArrayList<Brain> bBrains) {
        ArrayList<Brain> sBrains = session.getSelectedBrains();
        ArrayList<Brain> aBrains = session.getAllBrains();

        assertTrue("Selected brains should be \n" + actualSBrains + ", \nbut was \n" + sBrains,
                sBrains.size() == actualSBrains.size() && sBrains.containsAll(actualSBrains));
        assertTrue("The list of all brains should be \n" + bBrains + ",\nbut was \n" + aBrains,
                aBrains.size() == bBrains.size() && aBrains.containsAll(bBrains));
    }
}
