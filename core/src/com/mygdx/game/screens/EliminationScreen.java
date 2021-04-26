package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Controller;
import com.mygdx.game.models.Brain;

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
    private Label title, totalBrainsLabel, selectedBrainsLabel, currentBrainLabel, brainCounterLabel, waitingForPlayers;
    private Button nextArrow;
    private Button prevArrow;
    private TextButton continueButton;
    private Image brainImg;

    private CheckBox checkBox;
    private int currentBrain = 0;
    private boolean playerDone = false;
    private boolean allPlayersDone = false;
    private boolean gameDone = false;

    // TODO: temporary values
    //private int getTotalBrains = eliminationBrains.size();



    // TODO: init controller
    public EliminationScreen() {
        super("textures/backgrounds/eliminationBackground.png");
        brains = new ArrayList<>();
        // this.controller = controller;
        // render(0); //Consider removing
    }


    @Override
    public void show() {
        super.show();
        brainTexture = new Texture("textures/brains/ideaBrain.png");
        title = new Label("CHOOSE YOUR FAVORITES", skin);
        totalBrainsLabel = new Label(String.format("TOTAL BRAINS: %s", getTotalBrains()), skin);
        nextArrow = new Button(skin, "next");
        prevArrow = new Button(skin, "back");
        checkBox = new CheckBox("", skin, "elimnationCheck");
        selectedBrainsLabel = new Label(getSelectedBrainsText(), skin);
        waitingForPlayers = new Label("You have to wait for other players ...", skin);
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

        table.setBounds(
                Gdx.graphics.getWidth()/4f,
                Gdx.graphics.getHeight()/4f,
                Gdx.graphics.getWidth()/2f,
                Gdx.graphics.getHeight()/2f
        );
        table.add(title).top().align(Align.center);
        table.row();
        table.add(totalBrainsLabel).top().center().align(Align.center);
        table.row();
        table.add(overlay).size(brainSize, brainSize).align(Align.center);
        table.row();
        table.add(checkBox).align(Align.center);
        table.row();
        table.add(selectedBrainsLabel);
        table.row();
        table.add(brainCounterLabel);
        table.row();
        table.add(continueButton).height(Gdx.graphics.getHeight()/10f);
        table.row();
        table.add(waitingForPlayers);
        setActorOnTable(table, waitingForPlayers, false);

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
               if(currentBrain < getTotalBrains()){
                   currentBrain++;
               }
           }
        });

        continueButton.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               // TODO: not sure where to place this
               Controller.getInstance().playerDoneEliminating();
               playerDone = true;
           }
        });

        checkBox.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               Controller.getInstance().toggleBrain(currentBrain);
               /*if(!getSelectedBrains().contains(getEliminationBrains().get(currentBrain))){
                   Controller.getInstance().toggleBrain(currentBrain);
               }
               if(!checkBox.isChecked()){
                   Controller.getInstance().toggleBrain(currentBrain);
               }*/
           }
        });
        stage.addActor(nextArrow);
        stage.addActor(prevArrow);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (playerDone){
            // Show "Waiting for players" text
            setActorOnTable(table, continueButton, false);
            setActorOnTable(table,waitingForPlayers, true);
            if (allPlayersDone){
                // Only show the continue button
                setActorOnTable(table, continueButton, true);
                setActorOnTable(table, waitingForPlayers, false);
                if (gameDone){
                    gsm.setScreen(GameScreenManager.ScreenEnum.FINISH);
                }
                else {
                    gsm.setScreen(GameScreenManager.ScreenEnum.GAME_PHASE);
                }
            }
        }
        stage.draw();
        currentBrainLabel.setText(getEliminationBrains().get(currentBrain).toString());
        brainCounterLabel.setText(getBrainCounterLabel());
        selectedBrainsLabel.setText(getSelectedBrainsText());

        if(currentBrain == 0){
            prevArrow.setDisabled(true);
            stage.cancelTouchFocus(prevArrow);
        }else if(currentBrain == getTotalBrains() -1){
            nextArrow.setDisabled(true);
            stage.cancelTouchFocus(nextArrow);

        }else{
            nextArrow.setDisabled(false);
            prevArrow.setDisabled(false);
        }

        if(Controller.getInstance().checkBrainSelected(currentBrain)){
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

    private void setActorOnTable(Table table, Actor actor, boolean visibility){
        table.getCell(actor).getActor().setVisible(visibility);
    }

    private int getTotalBrains(){
        return Controller.getInstance().getEliminatingBrains().size();
    }

    private ArrayList<Brain> getSelectedBrains(){
        return Controller.getInstance().getSelectedBrains();
    }

    private ArrayList<Brain> getEliminationBrains(){
        return Controller.getInstance().getEliminatingBrains();
    }

    private String getSelectedBrainsText(){
        return String.format("Selected brains: %s/%s", getSelectedBrains().size(), getTotalBrains());
    }

    private String getBrainCounterLabel(){
        return String.format("Brain number %s", currentBrain+1);
    }

    public void setAllPlayersDone(){
        allPlayersDone = true;
    }

    public void setGameDone(){
        gameDone = true;
    }

    public void resetEliminating(){
        playerDone = false;
        allPlayersDone = false;
        gameDone = false;
    }

}
