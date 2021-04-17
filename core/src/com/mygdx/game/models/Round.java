package com.mygdx.game.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The round keeps track of current players and their
 * BrainstormingPhase, EliminationPhase, Brains,
 * and which brain the player is currently on.
 *
 * players: List of current players
 * brainstorminPhases: a Map that keeps track of which BrainstormingPhase belongs to which player
 * eliminationPhases: a Map that keeps track of which EliminationPhase belongs to which player
 * brains: a map of which Brains are being used this round and which player it belongs to.
 * currentBrainNumbers: a map that keeps track of which brain each player is on
 * maxSelectedBrains: the max nr of selected brains in the eliminationphase
 * inEliminationPhase: A boolean to tell which round is going
 *
 * This class implements the MVC pattern.
 * */

public class Round {

    private ArrayList<Player> players;
    private HashMap<Player, BrainstormingPhase> brainstormingPhases = new HashMap<>();
    private HashMap<Player, EliminationPhase> eliminationPhases = new HashMap<>();
    private HashMap<Player, ArrayList<Brain>> playersBrains = new HashMap<>();
    private HashMap<Player, Integer> currentBrainNumbers = new HashMap<>();
    private int maxSelectedBrains;
    private boolean inEliminationPhase = false;


    /**
     * A constructor that initializes the brainstormingphases for each player.
     * @param players: List of current players
     * @param brains: List of brains used this round, if round one these brains should contain no ideas
     * @param maxHitPoints: The max HP for the wall
     * @param BRAIN_DAMAGE: The amount of damage the brain does
     * @param maxSelectedBrains: Max selected brains for the eliminationphase
     * */
    public Round(ArrayList<Player> players, ArrayList<Brain> brains, int maxHitPoints, int BRAIN_DAMAGE, int maxSelectedBrains) {
        this.players = players;
        for(Player player:players){
            brainstormingPhases.put(player, new BrainstormingPhase(player, maxHitPoints, BRAIN_DAMAGE));
            ArrayList<Brain> brainsCopy = new ArrayList<>();
            for (Brain originalBrain : brains){
                brainsCopy.add(new Brain(originalBrain.getIdeas()));
            }
            playersBrains.put(player, brainsCopy);
            currentBrainNumbers.put(player, 0);
        }

        this.maxSelectedBrains = maxSelectedBrains;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean isInEliminationPhase() {
        return inEliminationPhase;
    }

    /**
     * Checks which players still has their walls standing, and returns a list of them
     * @return A list of players who still have standing walls
     * */
    public ArrayList<Player> playersLeft(){
        ArrayList<Player> remainingPlayers = new ArrayList<>();
        for (Player player : players){
            if (brainstormingPhases.get(player).getWall().isStanding()){
                remainingPlayers.add(player);
            }
        }
        return remainingPlayers;
    }

    /**
     * Gets the current brain, and adds the idea on the current brain in the
     * brainstormingphase connected to the user.
     * @param player: current player
     * @param idea: Idea thats going on the brain
     * @return true if the wall has fallen down, false if not.
     * */
    public boolean addBrainInBrainstormingPhase(Player player, String idea){
        int currentBrainNr = currentBrainNumbers.get(player);
        currentBrainNumbers.put(player, currentBrainNr + 1);
        Brain brain = playersBrains.get(player).get(currentBrainNr);
        BrainstormingPhase brainstormingPhase = brainstormingPhases.get(player);
        return brainstormingPhase.putIdeaOnBrainAndFire(brain, idea);
    }


    /**
     * Gets the brains used in the brainstormingphase
     * @return list of brains collected from the brainstormingphases
     * */
    public ArrayList<Brain> getBrainstormingBrains(){
        ArrayList<Brain> brains = new ArrayList<>();
        for (BrainstormingPhase brainstormingPhase : brainstormingPhases.values()){
            brains.addAll(brainstormingPhase.getBrains());
        }
        return brains;
    }

    /**
     * Creates new eliminationPhases for each player
     * */
    public void startEliminationPhase(ArrayList<Brain> brains){
        if (playersLeft().size() > 0){
            throw new IllegalStateException("There are still players who have walls left standing");
        }
        for (Player player : players){
            eliminationPhases.put(player, new EliminationPhase(brains, maxSelectedBrains));
        }
        inEliminationPhase = true;
    }

    /**
     * Return the selected brains from all the eliminationPhases, and removes duplicates.
     * */
    public ArrayList<Brain> getSelectedBrains(){
        ArrayList<Brain> brains = new ArrayList<>();
        for (EliminationPhase eliminationPhase : eliminationPhases.values()){
            ArrayList<Brain> selectedBrains = eliminationPhase.getSelectedBrains();
            for (Brain brain: selectedBrains){
                if (!brains.contains(brain)){
                    brains.add(brain);
                }
            }
        }
        return brains;
    }


    /**
     * Calls on the toggleBrain function of the eliminationPhase for a player
     * @param player: the player thats toggling
     * @param brain: the brain that's being toggled
     * */
    public void toggleBrain(Player player, Brain brain){
        if(!inEliminationPhase){
            throw new IllegalStateException("Can't togge a brain when not in eliminationPhase");
        }
        EliminationPhase eliminationPhase = eliminationPhases.get(player);
        eliminationPhase.toggleBrain(brain);
    }
}
