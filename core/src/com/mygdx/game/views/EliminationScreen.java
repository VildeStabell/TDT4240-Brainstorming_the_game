package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Brainstorming;
import com.mygdx.game.models.Brain;
import com.mygdx.game.models.Player;

public class EliminationScreen implements Screen {
    private Player player;
    private Brain brain;
    public Brainstorming game;
    private OrthographicCamera gameCam;
    private Viewport viewPort;
    private Stage stage;

    //Tittle and background
    private Texture title;
    private Texture background;

    // Navigation buttons
    // These are to come at the end of the elimination phase.
    private Texture newRound;
    private Texture quit;

    // Brain texture
    private Texture brainSheet;

    // Buttons
    private Button newRoundButton;
    private Button quitButton;


    public EliminationScreen(Brainstorming game) {
        this.game = game;

        // Todo: Add background, brains, tittle, buttons, ect.
        // background =

    }


    @Override
    // Make stage
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Number of rounds
        //int numRound = ...

        // Todo: Make tittle image, labels and buttons

        //Label round = makeLabel("ROUND" + numRound + "OUT OF " + maxNumOfRounds, 50f, 60f, Color.BLACK );

        // Todo: add actor to stage
        //stage.addActor(title);
        //...


    }

    @Override
    // Draw everything
    public void render(float delta) {


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    private Label makeLabel(String text, float xPos, float yPos, Color c) {
        Label l = new Label(text, new Label.LabelStyle(new BitmapFont(), c));
        l.setFontScale(1f,1f);
        l.setPosition(Gdx.graphics.getWidth() /100f*xPos - l.getWidth()/ 2, Gdx.graphics.getHeight() /100f*yPos);
        return l;
    }

    @Override
    public void dispose() {

    }
}
