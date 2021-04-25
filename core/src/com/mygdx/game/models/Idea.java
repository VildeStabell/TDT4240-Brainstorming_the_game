package com.mygdx.game.models;

/**
 * The Idea model stores one idea from a player
 * idea: the string containing the idea.
 * player: the player that came up with the idea
 * This class implements the MVC pattern.
 */

public class Idea {
    private String idea;
    private Player player;


    public Idea(String idea, Player player) {
        if(idea == null || idea.equals("")|| player == null)
            throw new IllegalArgumentException("Both an idea and a player must be provided");

        this.idea = idea;
        this.player = player;
    }

    /**
     * Empty constructor needed for Firebase
     * */
    public Idea (){
    }

    public String getIdea() {
        return idea;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return String.format("Idea{idea=%s, player=%s}", idea, player);
    }
}
