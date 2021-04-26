package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Controller;

/**
 * Showing the screen for typing in the code to join a hosted game
 * title: descriptive text
 * digitCodeField: Textfield to type the code
 * submitCode: button to submit the code
 *
 * Implemting the MVC pattern
 */
public class JoiningScreen extends BaseScreen {

    private Label title;
    private TextField digitCodeField;
    private TextButton submitCode;


    public JoiningScreen(){
        super("textures/backgrounds/standardBackground.png");
    }

    @Override
    public void show(){
        super.show();
        digitCodeField = new TextField("", skin);
        submitCode = new TextButton("Submit code", skin);
        title = new Label("Please type the received code below", skin);

        table.setPosition(
                Gdx.graphics.getWidth()/2f,
                Gdx.graphics.getHeight()/2f
        );

        digitCodeField.setAlignment(Align.center);
        table.add(title).space(title.getHeight());
        table.row();
        table.add(digitCodeField);
        table.row();
        table.add(submitCode);

        table.setDebug(true);
        submitCode.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               Controller.getInstance().joinMultiplayerGameRoom(digitCodeField.getText());
               gsm.setScreen(GameScreenManager.ScreenEnum.LOBBY);
           }
        });

    }

    @Override
    public void render(float delta){
        super.render(delta);
        stage.draw();
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
