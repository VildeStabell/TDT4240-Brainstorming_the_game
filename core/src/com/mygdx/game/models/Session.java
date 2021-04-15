package com.mygdx.game.models;

import java.util.ArrayList;

/**
 * The abstract Session model defines methods and variables that is needed regardless of
 * whether the session is singleplayer or multiplayer.
 */

public abstract class Session {
    protected ArrayList<Brain> brains;
    protected ArrayList<Round> rounds;
    protected int maxHitPoints;
    protected int brainDamage;
    protected int maxSelectedBrains;

    public Session(int maxHitPoints, int brainDamage, int maxSelectedBrains) {
        this.brains = new ArrayList<>();
        this.rounds = new ArrayList<>();
        this.maxHitPoints = maxHitPoints;
        this.brainDamage = brainDamage;
        this.maxSelectedBrains = maxSelectedBrains;
    }

    public ArrayList<Brain> getBrains() {
        return brains;
    }
    public ArrayList<Round> getRounds() {
        return rounds;
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
