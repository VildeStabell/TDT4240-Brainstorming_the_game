package com.mygdx.game.models;

/** The Idea model stores one idea from a player
 * @param idea: the string containing the idea.
 * @param player: the player that came up with the idea
 * This class implements the MVC pattern.
 * **/

public class Idea {
    private final String idea;
    private final Player player;

    public Idea(String idea, Player player) {
        this.idea = idea;
        this.player = player;
    }

    public String getIdea() {
        return idea;
    }

    public Player getPlayer() {
        return player;
    }
}
