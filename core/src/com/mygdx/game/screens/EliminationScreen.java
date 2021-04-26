package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * brainTexture: texture of the brain
 * title: Description of the brainstorming phase
 * totalBrainsLabel: label of total brains
 * selectedBrainsLabel: label of selected brains
 * currentBrainLabel: label of which brain from previous round the player is choosing
 * brainCounterLabel: Showing which brain number
 * nextArrow: shows next brain
 * prevArrow: show previous brain
 * eliminationBrains: containing brains from Brainstorming Phase
 * selecteedBrains: storing selected brains from player
 * checkBox: selecting a brain
 * currentBrain: keeping track of current brain in eliminiationBrains
 * totalBrains: total brains in eliminationBrains
 *
 * Implementing MVC pattern.
 */

public class EliminationScreen extends GameScreen {

    // TODO: controller
    // private Controller controller;
    private ArrayList<ImageButton> brains;

    private Texture brainTexture;
    private Label title, totalBrainsLabel, selectedBrainsLabel, currentBrainLabel, brainCounterLabel;
    private Button nextArrow;
    private Button prevArrow;
    private TextButton continueButton;
    private Image brainImg;


    // TODO: change to Brains, consider brains from previous round?
    private ArrayList<String> eliminationBrains = new ArrayList<>(Arrays.asList("software", "architecture", "mvc"));
    private ArrayList<String> selectedBrains = new ArrayList<>();
    private CheckBox checkBox;
    private int currentBrain = 0;

    // TODO: temporary values
    private int totalBrains = eliminationBrains.size();



    // TODO: init controller
    public EliminationScreen(GameScreenManager gsm) {
        super(gsm, "textures/backgrounds/eliminationBackground.png");
        brains = new ArrayList<>();
        // this.controller = controller;
        // render(0); //Consider removing
    }


    @Override
    public void show() {
        super.show();
        brainTexture = new Texture("textures/brains/ideaBrain.png");
        title = new Label("CHOOSE YOUR FAVORITES", skin);
        totalBrainsLabel = new Label(String.format("TOTAL BRAINS: %s", totalBrains), skin);
        nextArrow = new Button(skin, "next");
        prevArrow = new Button(skin, "back");
        checkBox = new CheckBox("", skin, "elimnationCheck");
        selectedBrainsLabel = new Label(getSelectedBrainsText(), skin);
        currentBrainLabel = new Label("", skin);
        brainCounterLabel = new Label(getBrainCounterLabel(), skin);
        continueButton = new TextButton("Continue", skin);
        brainImg = new Image(brainTexture);
        float brainSize = Gdx.graphics.getHeight() / 2f;
        float brainWrap = brainSize/2f;

        WidgetGroup overlay = new WidgetGroup();
        brainImg.setSize(brainSize, brainSize);
        overlay.addActor(brainImg);
        currentBrainLabel.setWidth(brainWrap);
        currentBrainLabel.setPosition((brainWrap/2f), (1f/1.7f)*brainSize);
        currentBrainLabel.setAlignment(Align.center);
        currentBrainLabel.setWrap(true);

        overlay.addActor(currentBrainLabel);

        table.setBounds(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/4f, Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/2f);
        table.add(title).top();
        table.row();
        table.add(totalBrainsLabel).top().center();
        table.row();
        table.add(overlay).size(brainSize, brainSize).expandY().colspan(2);
        table.row();
        table.add(checkBox);
        table.row();
        table.add(selectedBrainsLabel);
        table.row();
        table.add(brainCounterLabel);
        table.row();
        // TODO: remove
//        table.setDebug(true);

        float arrowSize = checkBox.getWidth();
        float arrowHeight = overlay.getParent().getY() + brainSize/2f - arrowSize/2f;
        prevArrow.setBounds(
                prevArrow.getWidth()/4f,
                arrowHeight,
                arrowSize,
                arrowSize
        );
        nextArrow.setBounds(
                Gdx.graphics.getWidth()-nextArrow.getWidth(),
                arrowHeight,
                arrowSize,
                arrowSize
        );
        prevArrow.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               if(currentBrain > 0){
                   currentBrain--;
               }
           }
        });
        nextArrow.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               if(currentBrain < totalBrains){
                   currentBrain++;
               }
           }
        });

        continueButton.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               // TODO: next stage/finish screen
               // gsm.setScreen(GameScreenManager.ScreenEnum.FINISH);
           }
        });

        checkBox.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               if(!selectedBrains.contains(eliminationBrains.get(currentBrain))){
                   selectedBrains.add(eliminationBrains.get(currentBrain));
               }
               if(!checkBox.isChecked()){
                   selectedBrains.remove(eliminationBrains.get(currentBrain));
               }
           }
        });
        table.add(continueButton);
        stage.addActor(nextArrow);
        stage.addActor(prevArrow);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.draw();
        currentBrainLabel.setText(eliminationBrains.get(currentBrain));
        brainCounterLabel.setText(getBrainCounterLabel());
        selectedBrainsLabel.setText(getSelectedBrainsText());

        if(currentBrain == 0){
            prevArrow.setDisabled(true);
            stage.cancelTouchFocus(prevArrow);
        }else if(currentBrain == totalBrains-1){
            nextArrow.setDisabled(true);
            stage.cancelTouchFocus(nextArrow);

        }else{
            nextArrow.setDisabled(false);
            prevArrow.setDisabled(false);
        }

        if(selectedBrains.contains(eliminationBrains.get(currentBrain))){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }


    }

    @Override
    public void hide(){
        brainTexture.dispose();
        super.dispose();
    }

    private String getSelectedBrainsText(){
        return String.format("Selected brains: %s/%s", selectedBrains.size(), totalBrains);
    }

    private String getBrainCounterLabel(){
        return String.format("Brain number %s", currentBrain+1);
    }

}