package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.models.Brain;
import com.mygdx.game.models.Player;
import com.mygdx.game.models.Round;
import java.util.ArrayList;

public class BrainstormingScreen extends BaseScreen {

    private Round round;
    private String ideaText;

    // Skin
    Skin brainstormingSkin;
    Skin plainSkin;

    // Ui widgets using customized skin (almost)
    TextField ideaInput;
    Button ideaCheck;
    ImageButton brainButton;
    CheckBox checkBrain;

    // Texture
    Texture ideaBrainTexture;
    Image ideaBrainImg;

    BitmapFont font;

    // Ratios according to screen size, temporary variables
    private static final float wallWidth = Gdx.graphics.getHeight()/2.5f; // Find ratio of grass
    private static final float wallHeight = Gdx.graphics.getHeight()/3f;
    private static final float wallWidthOffset = Gdx.graphics.getWidth()- (8f/7f)*(wallWidth); // Offset from wall from the screen
    private static final float wallHeightOffset = (Gdx.graphics.getHeight()/3.5f)/5f;

    private static final float brainButtonSize = wallWidth/2f;
    private static final float brainPosOffset = Gdx.graphics.getWidth()/14f;

    private static final float ideaBrainWidth = Gdx.graphics.getWidth()/2f;
    private static final float ideaBrainHeight = Gdx.graphics.getHeight()/2f;
    private static final float widthCenter = Gdx.graphics.getWidth()/2f - ideaBrainWidth/2;
    private static final float heightCenter = ideaBrainHeight/2f;

    // ideaBrain checkbox
    private static final float cbWidth = ideaBrainWidth/6f;
    private static final float cbHeight = ideaBrainHeight/6f;
    private static final float cbX = widthCenter+2*(ideaBrainWidth/3f);
    private static final float cbY = heightCenter+5*(cbHeight/4f);

    private static final float ideaTextWidth = Gdx.graphics.getWidth() / 3f;
    private static final float ideaInputHeight = Gdx.graphics.getHeight() / 10f;
    private static final float ideaInputPosX = Gdx.graphics.getWidth()/2f - ideaTextWidth/2f;
    private static final float ideaInputPosY = Gdx.graphics.getHeight()/2f;

    private int maxHitPoints;

    private boolean toggleMiniScreen;

    public BrainstormingScreen(GameScreenManager gsm, String imagePath) {
        super(gsm, imagePath);
        // dummy values
        ideaText = "";
        ArrayList<Brain> brains = new ArrayList<>();
        maxHitPoints = 10;
        int brainDamage = 1;
        int maxSelectedBrains = 3;
        Player player = new Player("Mai");
        round = new Round(player, brains, maxHitPoints, brainDamage, maxSelectedBrains);
    }

    @Override
    public void show() {
        super.show();
        // Skin
        brainstormingSkin = new Skin(Gdx.files.internal("skin/brainstormingSkin.json"));
        plainSkin = new Skin(Gdx.files.internal("ui/plain_james.json"));

        // Ui widgets
        ideaInput = new TextField("Write an idea", plainSkin);
        ideaCheck = new Button(brainstormingSkin, "ideaCheck");
        brainButton = new ImageButton(brainstormingSkin);
        checkBrain = new CheckBox("", brainstormingSkin);

        // Texture
        ideaBrainTexture = new Texture("textures/brains/ideaBrain.png");
        ideaBrainImg = new Image(ideaBrainTexture);

        // Defining position of brain and make it clickable
        brainButton.setBounds(brainPosOffset,brainPosOffset, brainButtonSize, brainButtonSize);
        brainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                toggleMiniScreen();
            }
       });

        // Init the brain to the stage
        stage.addActor(brainButton);

        table.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        table.debug();      // Turn on all debug lines (table, cell, and widget).
        table.debugTable(); // Turn on only table lines.
    }

    private void toggleMiniScreen(){
        toggleMiniScreen = !toggleMiniScreen;
    }

    public void setIdeaText(String ideaText){
        this.ideaText = ideaText;
    }

    public String getIdeaText(){
        return this.ideaText;
    }

    @Override
    public void render(float delta){
        super.render(delta);
        stage.draw();
        stage.act(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(round.getWall().getWallTexture(), wallWidthOffset, wallHeightOffset, wallWidth, wallHeight);
        stage.getBatch().end();

        if(toggleMiniScreen){
            pause();
        }else{
            ideaInput.setDisabled(false);
            hide();
        }

        if(round.isInEliminationPhase()){
            resume();
        }
    }

    @Override
    public void pause() {
        // Mini screen to type the idea
        stage.addActor(ideaBrainImg);
        stage.addActor(ideaCheck);
        stage.addActor(ideaInput);

        ideaBrainImg.setBounds(
                widthCenter,
                heightCenter,
                ideaBrainWidth,
                ideaBrainHeight);
        ideaCheck.setBounds(
                cbX,
                cbY,
                cbWidth,
                cbHeight);
        ideaInput.setBounds(
                ideaInputPosX,
                ideaInputPosY,
                ideaTextWidth,
                ideaInputHeight);

        ideaInput.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setIdeaText(ideaInput.getText());
                ideaCheck.setDisabled(true);
            }
        });

        ideaCheck.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("DEBUG ideaCHECK: " + ideaCheck.isChecked());
                if(ideaCheck.isDisabled()){
                    // Submit idea to brain
                    System.out.println(String.format("Submit idea to brain: %s", getIdeaText()));
                    toggleMiniScreen();
                    hide();
                    setIdeaText("");
                    ideaInput.setText("Write an idea");
                    ideaCheck.setDisabled(false);
                }
            }
        });
    }

    @Override
    public void resume() {
        gsm.setScreen(GameScreenManager.ScreenEnum.MENU);
    }

    @Override
    public void hide() {
        ideaBrainImg.remove();
        ideaInput.remove();
        ideaCheck.remove();
    }

    @Override
    public void dispose(){
        brainstormingSkin.dispose();
        plainSkin.dispose();
        ideaBrainTexture.dispose();
        font.dispose();
        round.dispose();
        super.dispose();
    }

}
