package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Brainstorming;

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
