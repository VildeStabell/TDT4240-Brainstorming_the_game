package com.mygdx.game.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * The Session model keeps track of players, selected brains, total brains and
 * rounds.
 *
 * This class implements the MVC pattern.
 * The parameters given in the constructor makes it easier to implement the quality requirement M1.
 */

public class Session {
    public static final int MAX = 9999;
    public static final int MIN = 1000;

    private final int maxHitPoints;
    private final int brainDamage;
    private final int maxSelectedBrains;
    private final int maxRounds;
    private final ArrayList<Player> players;
    private ArrayList<Brain> selectedBrains;
    private ArrayList<Brain> allBrains;
    private ArrayList<Round> rounds;
    private boolean activeRound;
    private final int sessionCode;

    /**
     * The Constructor for a singleplayer session
     * @param maxHitPoints: The max number of hit points a wall has
     * @param brainDamage: How much damage each brain does
     * @param maxSelectedBrains: The mac number of brains that can be selected in each
     *                         elimination phase
     * @param maxRounds: The max number of rounds that can be played
     * @param player: Which player is playing the session
     */
    public Session(int maxHitPoints, int brainDamage, int maxSelectedBrains, int maxRounds,
                   Player player) {
        this(maxHitPoints, brainDamage, maxSelectedBrains, maxRounds,
                new ArrayList<>(Collections.singletonList(player)), generateSessionCode());
    }

    /**
     * The Constructor for a multiplayer session
     * @param maxHitPoints: The max number of hit points a wall has
     * @param brainDamage: How much damage each brain does
     * @param maxSelectedBrains: The mac number of brains that can be selected in each
     *                         elimination phase
     * @param maxRounds: The max number of rounds that can be played
     * @param players: The list of players that
     */
    public Session(int maxHitPoints, int brainDamage, int maxSelectedBrains, int maxRounds,
                   ArrayList<Player> players, int sessionCode) {
        validateStartingValues(maxHitPoints, brainDamage, maxSelectedBrains, maxRounds, players,
                sessionCode);
        this.selectedBrains = new ArrayList<>();
        this.allBrains = new ArrayList<>();
        this.rounds = new ArrayList<>();
        this.maxHitPoints = maxHitPoints;
        this.brainDamage = brainDamage;
        this.maxSelectedBrains = maxSelectedBrains;
        this.maxRounds = maxRounds;
        this.activeRound = false;
        this.players = new ArrayList<>(players);
        this.sessionCode = sessionCode;
    }

    /**
     * Validates that the parameters given to the constructor are valid.
     * This assumes that maxSelectedBrains = 0 means that there is no limit on selected brains.
     */
    private void validateStartingValues(int maxHitPoints, int brainDamage, int maxSelectedBrains,
                                        int maxRounds, ArrayList<Player> players, int sessionCode) {
        if(players.size() < 1)
            throw new IllegalArgumentException("Must be at least one player in a session");
        if(maxHitPoints <= 0)
            throw new IllegalArgumentException("maxHitPoints must be higher than 0");
        if(brainDamage <= 0)
            throw new IllegalArgumentException("brainDamage must be higher than 0");
        if(maxSelectedBrains < 0)
            throw new IllegalArgumentException("maxSelectedBrains cannot be negative");
        if(maxRounds < 1)
            throw new IllegalArgumentException("maxRounds must be at least 1");
        for (Player player : players) {
            if(player == null)
                throw new IllegalArgumentException("No players can be null");
        }
        if(sessionCode < MIN || sessionCode > MAX)
            throw new IllegalArgumentException("The sessionCode must be between " + MIN +
                    " and " + MAX);
    }

    /**
     * Generates a random session code. The MAX and MIN constants can be changed if more unique
     * codes are needed, yet 4 digits is probably more than enough for our current scope.
     * @return a random number between MIN and MAX (Currently any number with 4 digits)
     */
    public static int generateSessionCode() {
        Random random = new Random();
        return random.nextInt(MAX - MIN) + MIN;
    }


    /**
     * Starts a new round and adds it to the rounds list.
     */
    public void startNewRound() {
        if (getNumOfRounds() >= maxRounds)
            throw new IllegalStateException("Cannot start another round since the max number of " +
                    "rounds has been reached.");

        if (activeRound)
            throw new IllegalStateException("Cannot start a new round while another is in progress");

        int brainsNeeded = (int) Math.ceil((double) maxHitPoints / brainDamage);
        ArrayList<Brain> brains = new ArrayList<>(selectedBrains);
        while(brains.size() < brainsNeeded) {
            Brain newBrain = new Brain();
            brains.add(newBrain);
            allBrains.add(newBrain);
        }

        rounds.add(new Round(players, brains, maxHitPoints, brainDamage, maxSelectedBrains));
        activeRound = true;
    }

    /**
     * Ends the current round, and adds the selected and total brains to their respective lists.
     * @return true if this means the session is over, false otherwise.
     */
    public boolean endRound() {
        if (!activeRound)
            throw new IllegalStateException("Cannot end a round, because no rounds are " +
                    "currently active");

        if (!getCurrentRound().isInEliminationPhase())
            throw new IllegalStateException("The round is in the brainstormingPhase, " +
                    "and can therefore not be ended");

        for (Brain brain : getCurrentRound().getSelectedBrains()) {
            if (!selectedBrains.contains(brain)) {
                this.selectedBrains.add(brain);
            }
        }

        activeRound = false;
        return getNumOfRounds() >= maxRounds;
    }

    public ArrayList<Brain> getSelectedBrains() {
        return selectedBrains;
    }

    public ArrayList<Brain> getAllBrains() {
        return allBrains;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public int getNumOfRounds() {
        return rounds.size();
    }

    public Round getCurrentRound() {
        return rounds.get(rounds.size()-1);
    }

    public int getSessionCode() {
        return sessionCode;
    }
}
