package com.mygdx.game.models;

import java.util.ArrayList;
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
    private final Player player;
    private ArrayList<Brain> selectedBrains;
    private ArrayList<Brain> allBrains;
    private ArrayList<Round> rounds;
    private boolean activeRound;
    private final int sessionCode;


    /**
     * The Constructor for a session. Works for both singleplayer and multiplayer
     * @param maxHitPoints: The max number of hit points a wall has
     * @param brainDamage: How much damage each brain does
     * @param maxSelectedBrains: The mac number of brains that can be selected in each
     *                         elimination phase
     * @param maxRounds: The max number of rounds that can be played
     * @param player: Which player is playing the session
     */
    public Session(int maxHitPoints, int brainDamage, int maxSelectedBrains, int maxRounds,
                   Player player, int sessionCode) {
        validateStartingValues(maxHitPoints, brainDamage, maxSelectedBrains, maxRounds, player,
                sessionCode);
        this.selectedBrains = new ArrayList<>();
        this.allBrains = new ArrayList<>();
        this.rounds = new ArrayList<>();
        this.maxHitPoints = maxHitPoints;
        this.brainDamage = brainDamage;
        this.maxSelectedBrains = maxSelectedBrains;
        this.maxRounds = maxRounds;
        this.activeRound = false;
        this.player = player;
        this.sessionCode = sessionCode;
    }

    /**
     * Validates that the parameters given to the constructor are valid.
     * This assumes that maxSelectedBrains = 0 means that there is no limit on selected brains.
     */
    private void validateStartingValues(int maxHitPoints, int brainDamage, int maxSelectedBrains,
                                        int maxRounds, Player player, int sessionCode) {
        if(player == null)
            throw new IllegalArgumentException("Player cannot be null");
        if(maxHitPoints <= 0)
            throw new IllegalArgumentException("maxHitPoints must be higher than 0");
        if(brainDamage <= 0)
            throw new IllegalArgumentException("brainDamage must be higher than 0");
        if(maxSelectedBrains < 0)
            throw new IllegalArgumentException("maxSelectedBrains cannot be negative");
        if(maxRounds < 1)
            throw new IllegalArgumentException("maxRounds must be at least 1");
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
    public void startNewRound(ArrayList<Brain> selectedBrains) {
        if (getNumOfRounds() >= maxRounds)
            throw new IllegalStateException("Cannot start another round since the max number of " +
                    "rounds has been reached.");

        if (activeRound)
            throw new IllegalStateException("Cannot start a new round while another is in progress");

        this.selectedBrains = new ArrayList<>();
        for (Brain brain : selectedBrains) {
            boolean inSelectedBrains = false;
            for(Brain oldBrain : this.selectedBrains) {
                if (brain.toString().equals(oldBrain.toString())) {
                    inSelectedBrains = true;
                }
            }
            if(!inSelectedBrains)
                this.selectedBrains.add(brain);
        }

        int brainsNeeded = (int) Math.ceil((double) maxHitPoints / brainDamage);
        ArrayList<Brain> brains = new ArrayList<>(this.selectedBrains);
        while(brains.size() < brainsNeeded) {
            Brain newBrain = new Brain();
            brains.add(newBrain);
        }

        Round newRound = new Round(player, brains, maxHitPoints, brainDamage, maxSelectedBrains);
        rounds.add(newRound);

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

        Round currentRound = getCurrentRound();
        if (!currentRound.isInEliminationPhase())
            throw new IllegalStateException("The round is in the brainstormingPhase, " +
                    "and can therefore not be ended");

        for(Brain newBrain : currentRound.getBrainstormingBrains()) {
            boolean inAllBrains = false;
            for(Brain brain : allBrains) {
                if (newBrain.toString().equals(brain.toString())) {
                    inAllBrains = true;
                }
            }
            if(!inAllBrains)
                allBrains.add(newBrain);
        }

        activeRound = false;
        return getNumOfRounds() >= maxRounds;
    }

    public ArrayList<Brain> getSelectedBrains() {
        return selectedBrains;
    }

    /**
     * A getter for all the brains that has been, or is, in play.
     * @return The brains that have been played in previous rounds, and the brains from the
     * current round.
     */
    public ArrayList<Brain> getAllBrains() {
        ArrayList<Brain> tempAllBrains = new ArrayList<>(allBrains);
        if(activeRound)
            tempAllBrains.addAll(getCurrentRound().getBrainstormingBrains());
        return tempAllBrains;
    }

    /**
     * @return a list of rounds
     * */
    public ArrayList<Round> getRounds() {
        return rounds;
    }

    /**
     * @return number of rounds
     * */
    public int getNumOfRounds() {
        return rounds.size();
    }

    /**
     * @return the current round
     * */
    public Round getCurrentRound() {
        return rounds.get(rounds.size() - 1);
    }

    /**
     * @return the sessionCode
     * */
    public int getSessionCode() {
        return sessionCode;
    }
}
