package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;
import com.mygdx.game.Controller;

import java.util.ArrayList;

/**
 * Displaying the lobby view before starting the game.
 * The host will receive a digit code appearing at top center of screen.
 * Incoming players will be displayed on the white list.
 *
 * startGame: start the game when at least another player has joined
 * digitCodeLabel: presents the digit code
 * activePlayers: List to display the players that have joined the session
 * (NOTE: this is not from the standard java.util package but LibGdx Scene2D ui)
 * waitingPlayers: ArrayList of the players waiting to join. Currently a dummy list
 * numberOfPlayers: Keeping track of player that are added to the waiting list
 * Implementing MVC pattern.
 */

public class LobbyScreen extends BaseScreen {

    private TextButton startGame;
    private Label digitCodeLabel;
    private List<String> activePlayers;
    private String gameCode;


    // TODO: DUMMY values, remove
    //ArrayList<String> waitingPlayers;
    private int numberOfPlayers;

    public LobbyScreen(){
        super("textures/backgrounds/standardBackground.png");
    }

    @Override
    public void show(){
        super.show();
        digitCodeLabel = new Label("", skin);
        activePlayers = new List<String>(skin);
        startGame = new TextButton("START GAME", skin);
        // centering the table layout
        table.setBounds(
                Gdx.graphics.getWidth()/4f,
                Gdx.graphics.getHeight()/4f,
                Gdx.graphics.getWidth()/2f,
                Gdx.graphics.getHeight()/2f
        );

        startGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Controller.getInstance().pressStartFirstRound();
                gsm.setScreen(GameScreenManager.ScreenEnum.GAME_PHASE);
            }
        });

        activePlayers.setAlignment(Align.center);
        //activePlayers.setItems(getPlayerListAsString(waitingPlayers));
        table.row();
        table.add(digitCodeLabel);
        table.row();
        table.add(activePlayers);
        table.row();
        table.add(startGame);
        // TODO: remove
        // table.setDebug(true);
    }

    @Override
    public void render(float delta){
        super.render(delta);
        stage.draw();
        // TODO: check if a waiting player has been added to active list of players
        /*if(waitingPlayers.size() > numberOfPlayers){
            numberOfPlayers = waitingPlayers.size();
            activePlayers.setItems(getPlayerListAsString(waitingPlayers));
        }*/

        // TODO: show only digit code for host
        digitCodeLabel.setText(getGameCodeLabel());

    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        super.dispose();
    }

    private String getGameCodeLabel(){
        return String.format("Digit code: %s", gameCode);
    }

    public void setGameCode(String gameCode){
        this.gameCode = gameCode;
    }


    /**
     * Passing a list of players to setItems(list) will also include symbols
     * like [],
     * @param playerList: List of players
     * @return concatenated player's name followed by a newline
     */
    private String getPlayerListAsString(ArrayList<String> playerList){
        StringBuilder playersAsString = new StringBuilder();
        for(String player : playerList){
            playersAsString.append(player);
            playersAsString.append("\n");
        }
        return playersAsString.toString();
    }


}
