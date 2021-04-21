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
     * Centering and initialize the buttons using a Skin (Plain James resource).
     *
     * skin: Consist of styling of the UI widgets, texture pack image and file
     * playButton: Currently navigate to a placeholder GameScreen view
     * exitButton: Exits the app when clicked
     * settingsButton: Not implemented yet
     *
     * Implementing listeners to each button by using addListener and instantiate a ChangeListener.
     * Then override the change method to fit the purpose of each event (a click event in this case).
     * Lastly add the each button which is considered as an actor to the stage {@link BaseScreen}
     *
     * A generalized centering method will replace line 57-74 when it can be applied on multiple use cases.
     */
    private void initButtons(){
        Skin skin = new Skin(Gdx.files.internal("ui/plain_james.json"));
        TextButton playButton = new TextButton("Play", skin);
        TextButton exitButton = new TextButton("Exit", skin);
        TextButton settingsButton = new TextButton("Settings", skin);


        // TODO: Replace with a method for centering and write unit tests
        int MENU_OFFSET = 10;
        int OPTIONS = 3;
        float menuHeight = playButton.getHeight()+exitButton.getHeight()+settingsButton.getHeight()+(OPTIONS-1)*MENU_OFFSET;
        float margin = (Gdx.graphics.getHeight() - menuHeight)/2f;
        // Top button
        playButton.setPosition(
                (Gdx.graphics.getWidth()/2f)-(playButton.getWidth()/2f),
                Gdx.graphics.getHeight() - margin - (playButton.getHeight()/2)
        );
        settingsButton.setPosition(
                (Gdx.graphics.getWidth()/2f)-(settingsButton.getWidth()/2f),
                playButton.getY() - playButton.getHeight() - MENU_OFFSET
        );
        exitButton.setPosition(
                (Gdx.graphics.getWidth()/2f)-(exitButton.getWidth()/2f),
                settingsButton.getY() - settingsButton.getHeight() - MENU_OFFSET
        );

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.setScreen(GameScreenManager.ScreenEnum.GAME_PHASE);
            }
        });

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        stage.addActor(settingsButton);
        stage.addActor(exitButton);
        stage.addActor(playButton);
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
