package com.mygdx.game.models;

import java.util.UUID;

/**
 * The Player model represents a player
 * playerId: The players unique id. Cannot be changed
 * username: The username that will be displayed. Can be changed later.
 * This class implements the MVC pattern.
 */

public class Player {
    private final UUID playerId;
    private String username;

    public Player(String username) {
        if(username == null || username.equals(""))
            throw new IllegalArgumentException("The username cannot be null or an empty string");

        this.playerId = UUID.randomUUID();
        this.username = username;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username == null || username.equals(""))
            throw new IllegalArgumentException("The username cannot be null or an empty string");

        this.username = username;
    }
}
