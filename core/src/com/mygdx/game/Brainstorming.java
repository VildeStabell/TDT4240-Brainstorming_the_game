package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
		gsm.setGame(this);
		controller = Controller.getInstance();
		controller.setFb(_FBIC);
		controller.setGSM(gsm);
		controller.setUsername("Sigrid");
		//controller.startSingleplayerSession();
		gsm.setScreen(GameScreenManager.ScreenEnum.MENU);

	}

	@Override
	public void dispose () {
		gsm.dispose();
		super.dispose();
	}
}
