package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.GameScreenManager;


public class Brainstorming extends Game {
	private GameScreenManager gsm;
	private final FirebaseInterface _FBIC;
	private Controller controller;

	public Brainstorming(FirebaseInterface fbic){
		_FBIC = fbic;
	}
	
	@Override
	public void create () {
		gsm = GameScreenManager.getInstance();
		gsm.initGameScreens();
		gsm.setGame(this);
		controller = Controller.getInstance();
		controller.setFb(_FBIC);
		controller.setGSM(gsm);
		gsm.setScreen(GameScreenManager.ScreenEnum.MENU);

	}

	@Override
	public void dispose () {
		gsm.dispose();
		super.dispose();
	}
}
