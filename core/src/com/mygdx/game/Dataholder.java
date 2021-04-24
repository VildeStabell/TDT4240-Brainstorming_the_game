package com.mygdx.game;

import com.mygdx.game.models.Brain;

import java.util.ArrayList;


/**
 * A class for helping with retrieving data from Firebase
 * */
public class Dataholder {

    private String gameCode;
    private ArrayList<Brain> brains;

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public void setBrains(ArrayList<Brain> brains){
        this.brains = brains;
    }

    public ArrayList<Brain> getBrains(){
        return brains;
    }

}
