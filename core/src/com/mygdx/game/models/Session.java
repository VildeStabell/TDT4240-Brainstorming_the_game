package com.mygdx.game.models;

import java.util.ArrayList;

/**
 * The abstract Session model defines methods and variables that is needed regardless of
 * whether the session is singleplayer or multiplayer.
 *
 * This class implements the MVC pattern.
 * The parameters given in the constructor makes it easier to implement the quality requirement M1.
 */

public abstract class Session {
    protected ArrayList<Brain> selectedBrains;
    protected ArrayList<Brain> allBrains;
    protected ArrayList<Round> rounds;
    protected final int maxHitPoints;
    protected final int brainDamage;
    protected final int maxSelectedBrains;
    protected final int maxRounds;
    protected boolean activeRound;

    public Session(int maxHitPoints, int brainDamage, int maxSelectedBrains, int maxRounds) {
        this.selectedBrains = new ArrayList<>();
        this.allBrains = new ArrayList<>();
        this.rounds = new ArrayList<>();
        this.maxHitPoints = maxHitPoints;
        this.brainDamage = brainDamage;
        this.maxSelectedBrains = maxSelectedBrains;
        this.maxRounds = maxRounds;
        this.activeRound = false;
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

    /**
     * Starts a new round and adds it to the rounds list.
     */
    public abstract void startNewRound();

    /**
     * Ends the current round.
     * @return true if this means the session is over, false otherwise.
     */
    public abstract boolean endRound();
}
