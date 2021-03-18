package com.mygdx.game.models;
import java.util.ArrayList;

/**
 * The BrainstormingPhase keeps track of the brains that have been fired and the wall.
 *
 * BRAIN_DAMAGE: How much damage 1 brain does to the wall
 * Brains: The list of brains that has been sendt to the wall
 * Wall: The wall
 *
 * This class implements the MVC pattern as a model.
 *
 * */

public class BrainstormingPhase {

    private final int BRAIN_DAMAGE;
    private final Wall wall;
    private ArrayList<Brain> brains = new ArrayList<>();

    /**
     * The constructor that takes the hitpointmax of the wall, and
     * how much damage a brain will do
     * @param wallHitpoints: Walls max HP.
     * @param BRAIN_DAMAGE: The amount of damage one brain does.
     */
    public BrainstormingPhase(int wallHitpoints, int BRAIN_DAMAGE){
        this.wall = new Wall(wallHitpoints);
        this.BRAIN_DAMAGE = BRAIN_DAMAGE;
    }

    public ArrayList<Brain> getBrains() {
        return brains;
    }

    public Wall getWall() {
        return wall;
    }

    /**
     * Updates a given brain with an Idea
     * @param brain: The brain in question.
     * @param idea: The idea thats going on the brain.
     * */
    public void putIdeaOnBrain(Brain brain, Idea idea){
        brain.addIdea(idea);
    }

    /***
     * Fires a brain towards the wall, adds the brain to the brains-list, and updates
     * the wall's HP.
     * @param brain: The brain that is being fired.
     * @return True if the wall has fallen, othervice return false.
     */
    public boolean fireBrain(Brain brain){
        if (brain == null){
            throw new IllegalArgumentException("Brain can't be nothing");
        }
        brains.add(brain);
        return wall.takeDmg(BRAIN_DAMAGE);
    }

}
