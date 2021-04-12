package com.mygdx.game.models;

import java.util.ArrayList;

public class MultiplayerSession extends Session {
    private ArrayList<Player> players;

    public MultiplayerSession(ArrayList<Player> players, int maxHitPoints, int brainDamage, int maxSelectedBrains) {
        super(maxHitPoints, brainDamage, maxSelectedBrains);
        this.players = players;
    }

    @Override
    public void startNewRound() {
        //Round newRound = new Round(players, BRAINS, maxHitPoints, brainDamage, maxSelectedBrains);
    }

    @Override
    public boolean endRound() {
        return false;
    }
}
