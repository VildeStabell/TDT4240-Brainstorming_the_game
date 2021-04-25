package com.mygdx.game.models;

import java.util.UUID;

/**
 * The Player model represents a player
 * playerId: The players unique id. Cannot be changed UPDATE: Because of our firebase setup this value is no longer in use
 * username: The username that will be displayed. Can be changed later.
 * This class implements the MVC pattern.
 */

public class Player {
    //private UUID playerId; NO LONGER IN USE BECAUSE OF FIREBASE
    private String username;

    /**
     * Empty constructor needed for Firebase
     * */
    public Player(){

    }
    public Player(String username) {
        if(username == null || username.equals(""))
            throw new IllegalArgumentException("The username cannot be null or an empty string");

        //this.playerId = UUID.randomUUID();
        this.username = username;
    }

    //NO LONGER IN USE BECAUSE OF FIREBASE
    /*public UUID getPlayerId() {
        return playerId;
    }*/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username == null || username.equals(""))
            throw new IllegalArgumentException("The username cannot be null or an empty string");

        this.username = username;
    }

    @Override
    public String toString() {
        return String.format("Player{username=%s}", username);
    }
}
