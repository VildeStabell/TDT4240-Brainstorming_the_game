package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Texture;

/**
 * The basic foundation for the game screen view such as
 * Elimination, brainstorming and finishing screen
 *
 * Modifications and changes are needed
 *
 * background: JPG image
 * gsm: game screen manager to control the different screens
 *
 */

public class GameScreen extends BaseScreen {
    private Texture background;

    public GameScreen(GameScreenManager gsm, Texture background){
        super(gsm);
        this.background = background;
    }

    @Override
    public void show() {
        super.show();
    }

    /**
     * Drawing the background with the inherited stage from {@link BaseScreen}
     * @param delta: time difference since last frame
     */
    @Override
    public void render(float delta){
        super.render(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(background,0,0);
        stage.getBatch().end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        background.dispose();
        super.dispose();
    }
}
