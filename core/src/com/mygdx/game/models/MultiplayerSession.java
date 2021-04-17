package com.mygdx.game.models;

import java.util.ArrayList;

/**
 * The MultiplayerSession model extends Session, and keeps track of players, selected brains and
 * rounds.
 *
 * This class implements the MVC pattern.
 * The parameters given in the constructor makes it easier to implement the quality requirement M1.
 */

public class MultiplayerSession extends Session {
    private final ArrayList<Player> players;

    public MultiplayerSession(ArrayList<Player> players, int maxHitPoints, int brainDamage,
                              int maxSelectedBrains, int maxRounds) {
        super(maxHitPoints, brainDamage, maxSelectedBrains, maxRounds);
        this.players = new ArrayList<>(players);
    }

    /**
     * Starts a new round and adds it to the rounds list.
     */
    @Override
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
    @Override
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
}
