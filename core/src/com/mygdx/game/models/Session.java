package com.mygdx.game.models;

import java.util.ArrayList;

/**
 * The Session model keeps track of players, selected brains, total brains and
 * rounds.
 *
 * This class implements the MVC pattern.
 * The parameters given in the constructor makes it easier to implement the quality requirement M1.
 */

public class Session {
    private final int maxHitPoints;
    private final int brainDamage;
    private final int maxSelectedBrains;
    private final int maxRounds;
    private final ArrayList<Player> players;
    private ArrayList<Brain> selectedBrains;
    private ArrayList<Brain> allBrains;
    private ArrayList<Round> rounds;
    private boolean activeRound;
    private String sessionCode; //TODO: Generate code, or receive if first. Maybe have a public
    // method to generate a code, and a getter(?). Call it in SingleplayerConstructor, but take it
    //As a parameter in MultiplayerConstructor

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
        ArrayList<Player> tempPlayerList = new ArrayList<>();
        tempPlayerList.add(player);
        validateStartingValues(maxHitPoints, brainDamage, maxSelectedBrains, maxRounds,
                tempPlayerList);
        this.selectedBrains = new ArrayList<>();
        this.allBrains = new ArrayList<>();
        this.rounds = new ArrayList<>();
        this.maxHitPoints = maxHitPoints;
        this.brainDamage = brainDamage;
        this.maxSelectedBrains = maxSelectedBrains;
        this.maxRounds = maxRounds;
        this.activeRound = false;
        this.players = new ArrayList<>();
        this.players.add(player);
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
                   ArrayList<Player> players) {
        validateStartingValues(maxHitPoints, brainDamage, maxSelectedBrains, maxRounds, players);
        this.selectedBrains = new ArrayList<>();
        this.allBrains = new ArrayList<>();
        this.rounds = new ArrayList<>();
        this.maxHitPoints = maxHitPoints;
        this.brainDamage = brainDamage;
        this.maxSelectedBrains = maxSelectedBrains;
        this.maxRounds = maxRounds;
        this.activeRound = false;
        this.players = new ArrayList<>(players);
    }

    /**
     * Validates that the parameters given to the constructor are valid.
     * This assumes that maxSelectedBrains = 0 means that there is no limit on selected brains.
     */
    private void validateStartingValues(int maxHitPoints, int brainDamage, int maxSelectedBrains,
                                        int maxRounds, ArrayList<Player> players) {
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

        rounds.add(new Round(players, selectedBrains, maxHitPoints, brainDamage, maxSelectedBrains));
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
        for (Brain brain : getCurrentRound().getBrainstormingBrains()) {
            if (!allBrains.contains(brain)) {
                this.allBrains.add(brain);
            }
        }

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
}
