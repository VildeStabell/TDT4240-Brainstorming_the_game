package com.mygdx.game;

import com.mygdx.game.models.Brain;

import java.util.ArrayList;

public class Dataholder {

    String gameCode;
    ArrayList<Brain> brains;

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
        System.out.println("Gamecode sat to: "+this.gameCode);
    }

    public void setBrains(ArrayList<Brain> brains){
        this.brains = brains;
    }

    public ArrayList<Brain> getBrains(){
        return brains;
    }

}
