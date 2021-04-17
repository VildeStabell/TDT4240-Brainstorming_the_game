package com.mygdx.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Brainstorming;

/**
 * Basic abstract screen view implementing the Screen interface
 *
 * gsm: game screen manager to control the different screens
 * stage: processing inputs and managing actors (2D Node Graph object)
 * @see com.badlogic.gdx.scenes.scene2d.Actor
 *
 * Note to call the super method of
 * show: for the stage to process inputs,
 * dispose: to avoid rendering it when not in use.
 * render: call this first to clear the screen and avoid keeping data from last frame for optimalization
 *
 * Implementing the MVC pattern with Screens being part of the View component.
 */

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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
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
