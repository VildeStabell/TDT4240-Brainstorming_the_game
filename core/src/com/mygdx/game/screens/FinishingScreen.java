package com.mygdx.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Arrays;

public class FinishingScreen extends BaseScreen {

    private Label totalSelectedBrains, eliminatedBrains;
    // TODO: change to type brains/ideas?
    private List<String> selectedBrainsList, eliminatedBrainsList;

    // TODO: controller
    public FinishingScreen(GameScreenManager gsm){
        super(gsm, "textures/backgrounds/standardBackground.png");
    }

    @Override
    public void show(){
        super.show();
        totalSelectedBrains = new Label("Selected brains", skin);
        eliminatedBrains = new Label("Eliminated brains", skin);
        selectedBrainsList = new List<String>(skin);
        eliminatedBrainsList = new List<String>(skin);
        selectedBrainsList.setAlignment(Align.center);
        eliminatedBrainsList.setAlignment(Align.center);

        // TODO: get selected brains as list
        //selectedBrainsList.setItems(getIdeasAsString(selectedBrainIdeas));
        //eliminatedBrainsList.setItems(getIdeasAsString(eliminatedBrainsIdeas));

        table.setBounds(
                Gdx.graphics.getWidth()/4f,
                Gdx.graphics.getHeight()/10f,
                Gdx.graphics.getWidth()/2f,
                Gdx.graphics.getHeight()/1.2f
        );
        table.add(totalSelectedBrains).expandX().top();
        table.add(eliminatedBrains).expandX().top();
        table.row();
        table.add(selectedBrainsList).expandX();
        table.add(eliminatedBrainsList).expandX();
        table.debugTable();

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
