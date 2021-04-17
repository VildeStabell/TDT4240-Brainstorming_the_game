package com.mygdx.game.controllers;

import com.mygdx.game.models.Brain;
import com.mygdx.game.models.Player;
import com.mygdx.game.models.Session;
import com.mygdx.game.screens.GameScreenManager;

import java.util.ArrayList;

public abstract class Controller {
    protected final GameScreenManager gsm;
    protected Session session;
    protected Player mainPlayer;

    public Controller(GameScreenManager gsm, String username){
        this.gsm = gsm;
        mainPlayer = new Player(username);
    }

    public abstract void pressStartSession(int maxHitPoints, int brainDamage, int maxSelectedBrains, int maxRounds);

    public void pressStartNewRound(){
        session.startNewRound();
        gsm.setScreen(GameScreenManager.ScreenEnum.GAME);
    }

    public abstract void pressFireBrain(String idea);


    public abstract void toggleBrain(Brain brain);


}
