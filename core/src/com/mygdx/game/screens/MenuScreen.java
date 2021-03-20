package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Brainstorming;

public class MenuScreen extends BaseScreen {
    // Display, play, settings, exit

    private Texture background;

    private Skin skin;
    private TextButton playButton;
    private TextButton exitButton;
    private TextButton settingsButton;

    private static int MENU_OFFSET = 10;
    private static int OPTIONS = 3;

    public MenuScreen(GameScreenManager gsm){
        super(gsm);
    }

    // Called when this screen becomes the current screen for a Brainstorming game.
    @Override
    public void show() {
        super.show();
        background = new Texture("menuScreen.jpg");
        initButtons();
    }

    private void initButtons(){
        skin = new Skin(Gdx.files.internal("ui/plain_james.json"));
        playButton = new TextButton("Play", skin);
        exitButton = new TextButton("Exit", skin);
        settingsButton = new TextButton("Settings", skin);


        // TODO: Replace with a method for centering and write unit tests
        float menuHeigth = playButton.getHeight()+exitButton.getHeight()+settingsButton.getHeight()+(OPTIONS-1)*MENU_OFFSET;
        float margin = (Brainstorming.HEIGHT - menuHeigth)/2;
        // Top button
        playButton.setPosition(
                (Brainstorming.WIDTH/2f)-(playButton.getWidth()/2),
                Brainstorming.HEIGHT - margin - (playButton.getHeight()/2)
        );
        settingsButton.setPosition(
                (Brainstorming.WIDTH/2f) - (settingsButton.getWidth()/2),
                playButton.getY() - playButton.getHeight() - MENU_OFFSET
        );
        exitButton.setPosition(
                (Brainstorming.WIDTH/2f)-(exitButton.getWidth()/2),
                settingsButton.getY() - settingsButton.getHeight() - MENU_OFFSET
        );

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("I was clicked");
                gsm.setScreen(GameScreenManager.ScreenEnum.GAME);
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


    @Override
    public void render(float delta){
        super.render(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(background,0,0);
        stage.getBatch().end();
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
