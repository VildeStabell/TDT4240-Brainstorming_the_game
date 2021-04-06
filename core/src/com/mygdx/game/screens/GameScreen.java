package com.mygdx.game.screens;

/**
 * The basic foundation for the game screen view such as
 * Elimination, brainstorming and finishing screen
 *
 * Modifications and changes are needed
 *
 * gsm: game screen manager to control the different screens
 *
 */

public class GameScreen extends BaseScreen {

    public GameScreen(GameScreenManager gsm, String imagePath){
        super(gsm, imagePath);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta){
        super.render(delta);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        super.dispose();
    }
}
