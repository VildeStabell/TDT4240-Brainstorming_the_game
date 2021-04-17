package com.mygdx.game.controllers;

import com.mygdx.game.models.Brain;
import com.mygdx.game.models.MultiplayerSession;
import com.mygdx.game.models.Player;
import com.mygdx.game.screens.GameScreenManager;

import java.util.ArrayList;

public class MultiplayerController extends Controller {
    //Firebaseapp = fb;


    public MultiplayerController(GameScreenManager gsm, String username) {
        super(gsm, username);
        //fb = new fb;
        //fb.addPlayer(Player);
    }

    @Override
    public void pressStartSession(int maxHitPoints, int brainDamage, int maxSelectedBrains, int maxRounds) {
        ArrayList<Player> players = new ArrayList<>();
        //players = fb.getPlayers();
        session = new MultiplayerSession(players, maxHitPoints, brainDamage, maxSelectedBrains, maxRounds);
    }

    @Override
    public void pressFireBrain(String idea) {
        //fb.addBrain();
        Boolean wallFallen = session.getCurrentRound().addBrainInBrainstormingPhase(idea);
        if (wallFallen){
            //fb.setPlayerDoneFlag;
            //gsm.fallenWall();
        }
    }

    @Override
    public void toggleBrain(Brain brain) {
        session.getCurrentRound().toggleBrain(brain);
    }

    public void allPlayersDoneBrainstorming(){
        ArrayList<Brain> brains = new ArrayList<>();
        //brains = fb.getBrains()
        //fb.setBrainstormingFlagToFalse()
        session.getCurrentRound().startEliminationPhase(brains);
    }

    public void allPlayersDoneEliminating(){
        ArrayList<Brain> brains = new ArrayList<>();
        //brains = fb.getSelectedBrains;
        //fb.setEliminationFlagToFalse()
        session.startNewRound();
    }

    public void playerDoneEliminating(){
        ArrayList<Brain> selectedBrains = session.getSelectedBrains();
        //fb.updateSelectedBrains();
    }
}
