package com.mygdx.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Basic abstract screen view implementing the Screen interface
 *
 * gsm: game screen manager to control the different screens
 * stage: processing inputs and managing actors (2D Node Graph object)
 * background: optionally to set a background image in the constructor
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
    protected Texture background;

    /**
     * Default constructor
     * @param gsm: game screen manager to control the screens
     */

    public BaseScreen(GameScreenManager gsm){
        this.gsm = gsm;
        this.stage = new Stage();
    }

    /**
     * Alternatively providing a background image to seamlessly scale the image to fit the screen size.
     * Especially important to call the super method of render() when providing a background image.
     * @param gsm: game screen manager to control the screens
     * @param imagePath: a path to the background image as a string
     */

    public BaseScreen(GameScreenManager gsm, String imagePath){
        this.gsm = gsm;
        this.stage = new Stage();
        this.background = new Texture(imagePath);
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Clearing the buffer and checking if a background has been set.
     * If so, it will render the background to fit the screen size.
     * Call this method if using the alternatively constructor.
     * @param delta: time difference since last frame
     */
    @Override
    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        if(background != null){
            stage.getBatch().begin();
            stage.getBatch().draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.getBatch().end();
        }
    }

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }

    /**
     * Disposing the resources.
     * Also disposing the background resource if it has been set in the constructor.
     */
    @Override
    public void dispose(){
        if(background != null){
            background.dispose();
        }
        stage.dispose();
    }

}
