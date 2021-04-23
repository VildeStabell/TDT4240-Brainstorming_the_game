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
		Player player3 = new Player("Mai");
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
		/*_FBIC.setGameCodeRef("7");
		sleep();
		_FBIC.writeNewPlayer(player);
		sleep();
		_FBIC.writeNewPlayer(player2);
		sleep();
		_FBIC.writeNewPlayer(player3);
		sleep();
		sleep();
		sleep();*/
		_FBIC.setGameCodeRef("7");
		sleep();
		/*_FBIC.writeNewPlayer(player);
		sleep();
		_FBIC.writeNewPlayer(player2);
		sleep();
		_FBIC.writeNewPlayer(player3);
		sleep();*/
		/*_FBIC.setAllDoneBrainstormingChangedListener();
		_FBIC.setAllDoneEliminatingChangedListener();
		sleep();
		sleep();
		_FBIC.setPlayerDoneBrainstorming(player, true);
		sleep();
		_FBIC.setPlayerDoneBrainstorming(player2, true);
		sleep();
		_FBIC.setPlayerDoneBrainstorming(player3, true);*/
		/*_FBIC.setPlayerDoneEliminating(player, true);
		sleep();
		_FBIC.setPlayerDoneEliminating(player2, true);
		sleep();
		_FBIC.setPlayerDoneEliminating(player3, true);
		sleep();*/
		Brain b1 = new Brain(new ArrayList<>(Arrays.asList(new Idea("I1", player), new Idea("I2", player))));
		Brain b2 = new Brain(new ArrayList<>(Arrays.asList(new Idea("I3", player), new Idea("I4", player))));
		Brain b3 = new Brain(new ArrayList<>(Arrays.asList(new Idea("I5", player2))));
		ArrayList<Brain> brains = new ArrayList<>(Arrays.asList(b1));
		_FBIC.setPlayerBrainList(player, brains);
		sleep();
		_FBIC.setPlayerBrainList(player2, new ArrayList<>(Arrays.asList(b3)));
		sleep();
		_FBIC.setPlayerBrainList(player3, new ArrayList<>(Arrays.asList(b2)));
		System.out.println("TestBrains: "+brains);
		sleep();
		_FBIC.getAllBrains(data);
		sleep();
		System.out.println("Databrains:" + data.getBrains());

	}

	@Override
	public void dispose () {
		gsm.dispose();
		super.dispose();
	}

	private void sleep(){
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
