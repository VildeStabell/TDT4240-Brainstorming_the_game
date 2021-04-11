package com.mygdx.game.models;

import java.util.ArrayList;

public abstract class Session {
    private ArrayList<Brain> brains = new ArrayList<>();
    private ArrayList<Round> rounds = new ArrayList<>();

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
