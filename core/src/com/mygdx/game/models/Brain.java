package com.mygdx.game.models;

import java.util.ArrayList;

/**
 * The Brain model stores related ideas
 * ideas: an ArrayList containing different ideas
 * This class implements the MVC pattern.
 */

public class Brain {
    private ArrayList<Idea> ideas = new ArrayList<>();

    public Brain(){    }

    public Brain (ArrayList<Idea> ideas){
        this.ideas = new ArrayList<>(ideas);
    }

    public ArrayList<Idea> getIdeas() {
        return ideas;
    }

    public Idea getIdea(int n) {
        if(n < 0 || n >= getSize())
            throw new IndexOutOfBoundsException("Index n has to be between 0 and "
                    + getSize() + "but was " + n);

        return ideas.get(n);
    }

    public int getSize() {
        return ideas.size();
    }

    public void addIdea(Idea idea) {
        if(idea == null)
            throw new IllegalArgumentException("Idea cannot be null");
        if(ideas.contains(idea))
            throw new IllegalArgumentException("Cannot add the same idea twice");

        ideas.add(idea);
    }

    @Override
    public String toString() {
        String brainString = "";
        for (Idea idea : ideas){
            brainString += idea.getIdea() + " - " +idea.getPlayer().getUsername() +"\n";
        }
        return brainString;
    }
}
