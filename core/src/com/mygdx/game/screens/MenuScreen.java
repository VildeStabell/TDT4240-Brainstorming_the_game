package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Controller;

/**
 * Menu screen containing different options for the game
 */

public class MenuScreen extends BaseScreen {

    public MenuScreen(GameScreenManager gsm, String imagePath){
        super(gsm, imagePath);
    }

    /**
     * The method is called whenever the gsm sets MenuScreen as its current screen.
     * Will draw the background and initialize and display the buttons.
     */
    @Override
    public void show() {
        super.show();
        initButtons();
    }

    /**
     * Centering and initialize the buttons using a Skin (Brainstorming Skin) and a table.
     *
     * startGameButton: Currently navigate to a placeholder GameScreen view
     * joinGameButton: Currently exits the app when clicked (change this to an input for joining game)
     *
     * Implementing listeners to each button by using addListener and instantiate a ChangeListener.
     * Then override the change method to fit the purpose of each event (a click event in this case).
     * Lastly add the each button which is considered as an actor to the table {@link BaseScreen}
     *
     */
    private void initButtons(){
        TextButton newGameButton = new TextButton("START NEW GAME", skin);
        TextButton joinGameButton = new TextButton("JOIN EXISTING GAME", skin);

        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Controller.getInstance().startSingleplayerSession();
                //resume();
            }
        });

        joinGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Redirect to a screen for sign in other games with a digit code
                Gdx.app.exit();
            }
        });
        table.add(newGameButton);
        table.row();
        table.add(joinGameButton);
        table.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
    }

    /**
     * Rendering the background image first.
     * Drawing the stage with the buttons afterwards.
     * @param delta: time difference since last frame
     */
    @Override
    public void render(float delta){
        super.render(delta);
        stage.draw();
    }

    @Override
    public void pause() {}


    @Override
    public void resume() {
        // TODO: Maybe: temporary, need to redirecet to a lobby while waiting for others to join?
        // TODO: Only accepting players that have typed username in text field
        gsm.setScreen(GameScreenManager.ScreenEnum.GAME_PHASE);
    }

    @Override
    public void hide() {
        super.dispose();
    }
}
