package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Menu screen containing different options for the game
 *
 * background: JPG image
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
     * Centering and initialize the buttons using a Skin (Brainstorming Skin).
     *
     * skin: Consist of styling of the UI widgets, texture pack image and file
     * startGameButton: Currently navigate to a placeholder GameScreen view
     * joinGameButton: Currently exits the app when clicked (change this to an input for joining game)
     *
     * Implementing listeners to each button by using addListener and instantiate a ChangeListener.
     * Then override the change method to fit the purpose of each event (a click event in this case).
     * Lastly add the each button which is considered as an actor to the stage {@link BaseScreen}
     *
     * A generalized centering method will replace line 57-74 when it can be applied on multiple use cases.
     */
    private void initButtons(){
        Skin skin = new Skin(Gdx.files.internal("skin/brainstormingSkin.json"));
        TextButton newGameButton = new TextButton("START NEW GAME", skin);
        TextButton joinGameButton = new TextButton("JOIN EXISTING GAME", skin);


        // TODO: Replace with a method for centering and write unit tests
        int MENU_OFFSET = 10;
        int OPTIONS = 3;
        float menuHeight = newGameButton.getHeight()+joinGameButton.getHeight()+(OPTIONS-1)*MENU_OFFSET;
        float margin = (Gdx.graphics.getHeight() - menuHeight)/2f;
        // Top button
        newGameButton.setPosition(
                (Gdx.graphics.getWidth()/2f)-(newGameButton.getWidth()/2f),
                Gdx.graphics.getHeight() - margin - (newGameButton.getHeight()/2)
        );
        joinGameButton.setPosition(
                (Gdx.graphics.getWidth()/2f)-(joinGameButton.getWidth()/2f),
                newGameButton.getY() - newGameButton.getHeight() - MENU_OFFSET
        );

        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.setScreen(GameScreenManager.ScreenEnum.GAME_PHASE);
            }
        });

        joinGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        stage.addActor(joinGameButton);
        stage.addActor(newGameButton);
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
    public void resume() {}

    @Override
    public void hide() {
        background.dispose();
        super.dispose();
    }
}
