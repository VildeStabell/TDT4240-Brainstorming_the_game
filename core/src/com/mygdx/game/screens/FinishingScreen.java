package com.mygdx.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Controller;

import java.util.ArrayList;
import java.util.Arrays;

public class FinishingScreen extends BaseScreen {

    private Label totalSelectedBrains, eliminatedBrains;
    private List<String> selectedBrainsList, eliminatedBrainsList;
    private TextButton returnButton;

    // TODO: controller
    public FinishingScreen(){
        super("textures/backgrounds/standardBackground.png");
    }

    @Override
    public void show(){
        super.show();
        totalSelectedBrains = new Label("Final brains", skin); //Change to final brains
        eliminatedBrains = new Label("Your brains", skin); //Change to your brains
        selectedBrainsList = new List<String>(skin);
        eliminatedBrainsList = new List<String>(skin);
        returnButton = new TextButton("Return to menu", skin);
        selectedBrainsList.setAlignment(Align.center);
        eliminatedBrainsList.setAlignment(Align.center);

        selectedBrainsList.setItems(getIdeasAsString(Controller.getInstance().getFinalBrains()));
        eliminatedBrainsList.setItems(getIdeasAsString(Controller.getInstance().getPlayersBrains()));

        returnButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gsm.setScreen(GameScreenManager.ScreenEnum.MENU);
            }
        });
        returnButton.setPosition(
                Gdx.graphics.getWidth()/2f-returnButton.getWidth()/2f,
                0
        );
        stage.addActor(returnButton);
        table.setBounds(
                Gdx.graphics.getWidth()/4f,
                Gdx.graphics.getHeight()/10f,
                Gdx.graphics.getWidth()/2f,
                Gdx.graphics.getHeight()/1.1f
        );
        table.add(totalSelectedBrains).expandX().top();
        table.add(eliminatedBrains).expandX().top();
        table.row();
        table.add(selectedBrainsList).expandX().height(Gdx.graphics.getHeight()/1.2f);
        table.add(eliminatedBrainsList).expandX().height(Gdx.graphics.getHeight()/1.2f);
        table.row();

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

    /**
     * Passing a list of players to setItems(list) will also include symbols
     * like [],
     * @param list: List of ideas
     * @return concatenated player's name followed by a newline
     */
    private String getIdeasAsString(ArrayList<String> list){
        StringBuilder playersAsString = new StringBuilder();
        for(String idea : list){
            playersAsString.append(idea);
            playersAsString.append("\n");
        }
        return playersAsString.toString();
    }
}
