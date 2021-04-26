package com.mygdx.game.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The round keeps track of current players and their
 * BrainstormingPhase, EliminationPhase, Brains,
 * and which brain the player is currently on.
 *
 * player: Current player
 * brainstormingPhases: The brainstorming phase
 * eliminationPhases: The elimination phase
 * brains: a list of the brain being used this round
 * currentBrainNumber: the current brain
 * maxSelectedBrains: the max nr of selected brains in the eliminationphase
 * inEliminationPhase: A boolean to tell which round is going
 *
 * This class implements the MVC pattern.
 * */

public class Round {

    private Player player;
    private BrainstormingPhase brainstormingPhase;
    private EliminationPhase eliminationPhase;
    private ArrayList<Brain> brains = new ArrayList<>();
    private int currentBrainNumber;
    private int maxSelectedBrains;
    private boolean inEliminationPhase = false;


    /**
     * A constructor that initializes the brainstormingphase
     * @param player: player
     * @param brains: List of brains used this round, if round one these brains should contain no ideas
     * @param maxHitPoints: The max HP for the wall
     * @param BRAIN_DAMAGE: The amount of damage the brain does
     * @param maxSelectedBrains: Max selected brains for the eliminationphase
     * */
    public Round(Player player, ArrayList<Brain> brains, int maxHitPoints, int BRAIN_DAMAGE, int maxSelectedBrains) {
        this.player = player;
        brainstormingPhase = new BrainstormingPhase(player, maxHitPoints, BRAIN_DAMAGE);
        for (Brain originalBrain : brains){
            this.brains.add(new Brain(originalBrain.getIdeas()));
        }
        currentBrainNumber = 0;
        this.maxSelectedBrains = maxSelectedBrains;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isInEliminationPhase() {
        return inEliminationPhase;
    }

    /**
     * Checks if the wall is still standing
     * @return True if the wall is standing, false if not
     * */
    public Boolean isWallStanding(){
        return brainstormingPhase.getWall().isStanding();
    }

    /**
     * Gets the current brain, and adds the idea on the current brain in the
     * brainstormingphase connected to the user.
     * @param idea: Idea thats going on the brain
     * @return true if the wall has fallen down, false if not.
     * */
    public boolean addBrainInBrainstormingPhase(String idea){
        currentBrainNumber++;
        return brainstormingPhase.putIdeaOnBrainAndFire(brains.get(currentBrainNumber - 1), idea);
    }


    /**
     * Gets the brains used in the brainstormingphase
     * @return list of brains collected from the brainstormingphases
     * */
    public ArrayList<Brain> getBrainstormingBrains(){
        return brainstormingPhase.getBrains();
    }

    /**
     * @return the wall of the brainstormingphase
     * */
    public Wall getWall() {
        return brainstormingPhase.getWall();
    }


    /**
     * Creates new eliminationPhases for each player
     * */
    public void startEliminationPhase(ArrayList<Brain> brains){
        System.out.println("Started EliminationPhase");
        if(isWallStanding()){
            throw new IllegalStateException("Can't start eliminationPhase when wall is still standing");
        }
        inEliminationPhase = true;
        eliminationPhase = new EliminationPhase(brains, maxSelectedBrains);
    }

    /**
     * Return the selected brains from all the eliminationPhases.
     * */
    public ArrayList<Brain> getSelectedBrains(){
        return eliminationPhase.getSelectedBrains();
    }


    /**
     * Calls on the toggleBrain function of the eliminationPhase for a player
     * @param brain: the brain that's being toggled
     * */
    public void toggleBrain(Brain brain){
        if(!inEliminationPhase){
            throw new IllegalStateException("Can't toggle a brain when not in eliminationPhase");
        }
        eliminationPhase.toggleBrain(brain);
    }

    public int brainsLeft(){
        return brains.size() - brainstormingPhase.getBrains().size();
    }
}
