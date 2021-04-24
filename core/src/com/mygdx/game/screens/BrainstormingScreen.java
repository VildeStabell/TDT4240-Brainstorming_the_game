package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.models.Brain;
import com.mygdx.game.models.Player;
import com.mygdx.game.models.Round;
import java.util.ArrayList;

/**
 *
 * statetime:
 * Implementing the MVC pattern
 */

public class BrainstormingScreen extends BaseScreen {

    private Round round;
    private String ideaText;

    // Ui widgets using customized skin (almost)
    TextField ideaInputField;
    Button ideaCheck;
    ImageButton brainButton;
    Image castle;
    Label healthLabel;

    // Texture
    Texture ideaBrainTexture;
    Image ideaBrainImg;
    TextureAtlas atlas;

    // Font
    BitmapFont font;

    // Ratios according to screen size, temporary variables
    private static final float wallWidth = Gdx.graphics.getHeight()/2.5f; // Find ratio of grass
    private static final float wallHeight = Gdx.graphics.getHeight()/3f;

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
    private float stateTime;

    private boolean toggleSubmitIdeaField;

    public BrainstormingScreen(GameScreenManager gsm, String imagePath) {
        super(gsm, imagePath);
        // dummy values
        ideaText = "";
        ArrayList<Brain> brains = new ArrayList<>();
        maxHitPoints = 1;
        int brainDamage = 1;
        int maxSelectedBrains = 3;
        Player player = new Player("Mai");
        round = new Round(player, brains, maxHitPoints, brainDamage, maxSelectedBrains);
    }

    @Override
    public void show() {
        super.show();
        initWidgets();

        // Defining position of brain and make it clickable
        brainButton.setBounds(brainPosOffset,brainPosOffset, brainButtonSize, brainButtonSize);
        brainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                toggleSubmitIdea();
            }
       });

        // Init the brain to the stage
        stage.addActor(brainButton);

        // Table, adjusting to screen and add actors
        table.add(healthLabel).top();
        table.row();
        table.add(castle).size(wallWidth, wallHeight);
        table.setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 5f, wallHeight);
        table.debug();      // Turn on all debug lines (table, cell, and widget).
        table.debugTable(); // Turn on only table lines.
    }

    private void initWidgets(){
        stateTime = 0f;


        // Font
        font = skin.getFont("myriad-pro-font");
        font.setColor(Color.RED);

        // Clickable brain
        brainButton = new ImageButton(skin);

        // Castle
        castle = new Image(round.getWall().getWallTexture());
        healthLabel = new Label(getCurrentHealth(), skin);
        atlas = new TextureAtlas(Gdx.files.internal("textures/walls/castle.atlas"));

        // Idea brain field
        ideaBrainTexture = new Texture("textures/brains/ideaBrain.png");
        ideaBrainImg = new Image(ideaBrainTexture);
        ideaCheck = new Button(skin, "ideaCheck");
        ideaInputField = new TextField("Write an idea", skin);
        ideaInputField.setAlignment(Align.center);

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
        ideaInputField.setBounds(
                ideaInputPosX,
                ideaInputPosY,
                ideaTextWidth,
                ideaInputHeight);
    }

    @Override
    public void render(float delta){
        super.render(delta);
        stage.draw();
        stage.act(delta);

        stage.getBatch().begin();
        font.draw(
                stage.getBatch(),
                String.format("BRAINS LEFT: %s", getBrainsLeft()),
                Gdx.graphics.getWidth()/2f,
                Gdx.graphics.getHeight() - font.getCapHeight(),
                10,
                Align.center,
                true);
        stage.getBatch().end();

        if(toggleSubmitIdeaField){
            showSubmitIdeaField();
        }else{
            ideaInputField.setDisabled(false);
            hideSubmitIdeaField();
        }

        if(!round.isWallStanding()){
            wallFallingAnimation(delta);
            //round.startEliminationPhase(round.getBrainstormingBrains());
            resume();
        }
    }

    // Should display the menu option for continue game, exit and possibly turn on/off volume
    @Override
    public void pause() {
        System.out.println("Show menu options");
    }

    @Override
    public void resume() {
        // Temporary
        gsm.setScreen(GameScreenManager.ScreenEnum.MENU);
    }

    @Override
    public void hide() {
        atlas.dispose();
        ideaBrainTexture.dispose();
        font.dispose();
        round.dispose();
        super.dispose();
    }

    private void showSubmitIdeaField(){
        // Mini screen to type the idea
        stage.addActor(ideaBrainImg);
        stage.addActor(ideaCheck);
        stage.addActor(ideaInputField);


        ideaInputField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setIdeaText(ideaInputField.getText());
                ideaCheck.setDisabled(true);
            }
        });

        ideaCheck.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(ideaCheck.isDisabled()){
                    // Submit idea to brain
                    System.out.println(String.format("Submit idea to brain: %s", getIdeaText()));
//                    round.addBrainInBrainstormingPhase(ideaInput.getText());
                    submitIdea();
                }
            }
        });
    }

    private void hideSubmitIdeaField(){
        ideaBrainImg.remove();
        ideaInputField.remove();
        ideaCheck.remove();
    }

    private void submitIdea(){
        toggleSubmitIdea();
        hideSubmitIdeaField();
        setIdeaText("");
        ideaCheck.setDisabled(false);
        healthLabel.setText(getCurrentHealth());
        ideaInputField.setText("Write an idea");

    }

    private void toggleSubmitIdea(){
        toggleSubmitIdeaField = !toggleSubmitIdeaField;
    }

    private void setIdeaText(String ideaText){
        this.ideaText = ideaText;
    }

    private String getIdeaText(){
        return this.ideaText;
    }

    private void wallFallingAnimation(float delta){
        float castleWidthCenter = table.getX()-castle.getWidth()/2f;
        float castleHeightCenter = table.getY()-castle.getHeight()/2f-healthLabel.getHeight()/2f;
        castle.remove();
        stateTime += delta;
        Animation<TextureRegion> castleAnimation = new Animation<>(1/2f, atlas.findRegions("castle"), Animation.PlayMode.NORMAL);
        stage.draw();
        TextureRegion currentFrame = castleAnimation.getKeyFrame(stateTime, false);
        stage.getBatch().begin();
        stage.getBatch().draw(currentFrame, castleWidthCenter, castleHeightCenter, wallWidth, wallHeight);

        stage.getBatch().end();
    }

    public String getCurrentHealth(){
        return String.format("HEALTH: %s/%s", round.getWall().getHitPoints(), round.getWall().getMaxHitPoints());
    }

    private int getBrainsLeft(){
        return round.getMaxSelectedBrains()-round.getCurrentBrainNumber();
    }
}
