package com.mygdx.game.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The EliminationPhase model keeps track of the brains the player can choose between,
 * and which of them is currently selected.
 *
 * brains: A HashMap of available brains, true means the brain is selected.
 * maxSelected: The maximum number of brains that is allowed to be selected.
 *      0 means that there is no limit.
 * numSelected: how many brains are currently selected.
 *
 * This class implements the MVC pattern as a model.
 */

public class EliminationPhase {
    private HashMap<Brain, Boolean> brains = new HashMap<>(); //True indicates a brain is selected
    private final int maxSelected; //maxSelected == 0 means that there is no limit
    private int numSelected = 0;

    /**
     * The constructor that takes in a list of brains to be chosen between,
     * and how many can be chosen at a time.
     * @param brains: A list of brains that the player can choose from.
     * @param maxSelected: How many brains can be selected at a time.
     */
    public EliminationPhase(ArrayList<Brain> brains, int maxSelected) {
        if(brains.size() <= 0)
            throw new IllegalArgumentException("List of brains cannot be empty");

        for (Brain brain : brains) {
            if(brain == null)
                throw new IllegalArgumentException("List of brains cannot contain null-objects");

            this.brains.put(brain, false);
        }
        this.maxSelected = maxSelected;
    }

    /**
     * A constructor that sets the maxSelected to a default 0, meaning that there is no limit.
     * @param brains: A list of brains that the player can choose from.
     */
    public EliminationPhase(ArrayList<Brain> brains) {
        this(brains, 0);
    }

    public HashMap<Brain, Boolean> getBrains() {
        return brains;
    }

    public ArrayList<Brain> getSelectedBrains() {
        ArrayList<Brain> selectedBrains = new ArrayList<>();
        for(Brain brain : brains.keySet()) {
            if(brains.get(brain))
                selectedBrains.add(brain);
        }
        return selectedBrains;
    }

    public int getNumSelected() {
        return numSelected;
    }

    /**
     * Updates the selection value of the provided brain,
     * and the number of currently selected brains.
     * @param brain: The brain that should be toggled.
     */
    public void toggleBrain(Brain brain) {
        if(!brains.containsKey(brain))
            throw new IllegalArgumentException("Must provide a brain that is already in the list");

        boolean previousValue = brains.get(brain);

        if(!previousValue && numSelected >= maxSelected && maxSelected != 0)
            throw new IllegalArgumentException("Max number of brains have already been selected");

        if(!previousValue)
            numSelected++;
        else
            numSelected--;
        brains.put(brain, !previousValue);
    }
}
