package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.GameScreenManager;
//import com.mygdx.game.screens.MenuScreen;

public class Brainstorming extends Game {
	GameScreenManager gsm;
	public final static int WIDTH = 640;
	public final static int HEIGHT = 360;
	private final FirebaseInterface _FBIC;

	public Brainstorming(FirebaseInterface fbic){
		_FBIC = fbic;
	}
	
	@Override
	public void create () {
		gsm = new GameScreenManager(this);
	}

	@Override
	public void dispose () {
		gsm.dispose();
		super.dispose();
	}
}
