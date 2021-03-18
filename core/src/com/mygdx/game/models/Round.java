package com.mygdx.game.models;

import java.util.ArrayList;

/**
 * The round keeps track of the brainstorming and eliminationphase.
 *
 * brainstorminPhase: the BrainstormingPhase
 * eliminationPhase: the EliminaionPhase
 * brains: A list of brains, that start out empty
 * maxSelectedBrains: the max nr of selected brains in the eliminationphase
 * currentBrain: A way to keep track of the current brain
 * inEliminationPhase: A boolean to tell which round is going
 *
 * */

public class Round {

    private BrainstormingPhase brainstormingPhase;
    private EliminationPhase eliminationPhase;
    private ArrayList<Brain> brains = new ArrayList<>();
    private int maxSelectedBrains;
    private int currentBrain=0;
    private boolean inEliminationPhase = false;


    /**
     * A constructor that initializes the brainstormingphase
     * @param nrBrains: The max nr of brains for the round
     * @param wallHitpoints: The max HP for the wall
     * @param BRAIN_DAMAGE: The amound of damage the brain does
     * @param maxSelectedBrains: Max selected brains for the eliminationphase
     * */
    public Round(int nrBrains, int wallHitpoints, int BRAIN_DAMAGE, int maxSelectedBrains){
        for(int i=0; i<nrBrains; i++){
            brains.add(new Brain());
        }
        brainstormingPhase = new BrainstormingPhase(wallHitpoints, BRAIN_DAMAGE);
        this.maxSelectedBrains = maxSelectedBrains;
    }


    /**
     * Adds an idea on the current brain, then firers the brain on the wall.
     * If the wall has fallen, it starts the eliminationphase, and returns true.
     * If the wall is still standing, return false.
     * */
    public boolean addBrainInBrainstormingPhase(Idea idea){
        Brain brain = brains.get(currentBrain);
        currentBrain++;
        brainstormingPhase.putIdeaOnBrain(brain, idea);
        if (brainstormingPhase.fireBrain(brain)){
            inEliminationPhase = true;
            startEliminationPhase(brainstormingPhase.getBrains());
            return true;
        }
        return false;
    }


    /**
     * create a new emilinationphase object
     * */
    public void startEliminationPhase(ArrayList<Brain> brains){
        eliminationPhase = new EliminationPhase(brains, maxSelectedBrains);
    }

    /**
     * Return the selected brains from the eliminationphase
     * */
    public ArrayList<Brain> getSelectedBrains(){
        return eliminationPhase.getSelectedBrains();
    }

}
