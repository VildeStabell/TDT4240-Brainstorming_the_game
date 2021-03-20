package com.mygdx.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Brainstorming;

public abstract class BaseScreen implements Screen {

    protected final GameScreenManager gsm;
    protected Stage stage;

    public BaseScreen(GameScreenManager gsm){
        this.gsm = gsm;
        this.stage = new Stage();
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage);
        resize(Brainstorming.WIDTH, Brainstorming.HEIGHT);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose(){
        stage.dispose();
    }
}



