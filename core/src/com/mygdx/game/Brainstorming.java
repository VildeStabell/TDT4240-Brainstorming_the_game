package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.models.Brain;
import com.mygdx.game.models.Idea;
import com.mygdx.game.models.Player;
import com.mygdx.game.screens.GameScreenManager;

import org.omg.DynamicAny._DynFixedStub;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import sun.security.util.AuthResources_fr;
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
		Player player = new Player("Sigrid");
		Player player2 = new Player("Vilde");
		Dataholder data = new Dataholder();
		/*_FBIC.getGameCodeFromDB(data);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		_FBIC.setGameCodeRef(data.getGameCode());
		_FBIC.writeNewPlayer(player);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		_FBIC.writeNewPlayer(player2);
		_FBIC.setPlayerDoneBrainstorming(player, true);
		_FBIC.setPlayerDoneBrainstorming(player2, false);
		//_FBIC.setValueInDb("Score", "305");
		//_FBIC.setOnValueChangedListener();
		//System.out.println("Data gamecode:" +data.gameCode);
		//System.out.println("FBIC gamecoderef: "+_FBIC.getGameCodeRef());*/
		_FBIC.setGameCodeRef("5");
		Brain b1 = new Brain(new ArrayList<>(Arrays.asList(new Idea("I1", player), new Idea("I2", player))));
		Brain b2 = new Brain(new ArrayList<>(Arrays.asList(new Idea("I3", player), new Idea("I4", player))));
		ArrayList<Brain> brains = new ArrayList<>(Arrays.asList(b1,b2));
		//_FBIC.setPlayerBrainList(player,brains);
		System.out.println("TestBrains: "+brains);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		_FBIC.getAllBrains(data, player);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Databrains:" + data.getBrains());

	}

	@Override
	public void dispose () {
		gsm.dispose();
		super.dispose();
	}
}
