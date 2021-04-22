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
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
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
    private float stateTime;

    private boolean toggleMiniScreen;

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
        stateTime = 0f;


        // Skin
        brainstormingSkin = new Skin(Gdx.files.internal("skin/brainstormingSkin.json"));
        plainSkin = new Skin(Gdx.files.internal("ui/plain_james.json"));

        // Font
        font = brainstormingSkin.getFont("myriad-pro-font");
        font.setColor(Color.RED);

        // Ui widgets
        ideaInput = new TextField("Write an idea", plainSkin);
        ideaCheck = new Button(brainstormingSkin, "ideaCheck");
        brainButton = new ImageButton(brainstormingSkin);
        checkBrain = new CheckBox("", brainstormingSkin);

        castle = new Image(round.getWall().getWallTexture());

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
        
        healthLabel = new Label(getCurrentHealth(), plainSkin);
        table.add(healthLabel).top();
        table.row();
        table.add(castle).size(wallWidth, wallHeight);
        table.setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 5f, wallHeight);
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
        font.draw(
                stage.getBatch(),
                String.format("BRAINS LEFT: %s", round.getMaxSelectedBrains()-round.getCurrentBrainNumber()),
                Gdx.graphics.getWidth()/2f,
                Gdx.graphics.getHeight() - font.getCapHeight(),
                10,
                Align.center,
                true);
        stage.getBatch().end();

        if(toggleMiniScreen){
            pause();
        }else{
            ideaInput.setDisabled(false);
            hide();
        }

        if(!round.isWallStanding()){
            wallFallingAnimation(delta);
            //round.startEliminationPhase(round.getBrainstormingBrains());
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
                if(ideaCheck.isDisabled()){
                    // Submit idea to brain
                    System.out.println(String.format("Submit idea to brain: %s", getIdeaText()));
                    round.addBrainInBrainstormingPhase(ideaInput.getText());
                    toggleMiniScreen();
                    hide();
                    setIdeaText("");
                    ideaInput.setText("Write an idea");
                    ideaCheck.setDisabled(false);
                    healthLabel.setText(getCurrentHealth());

                }
            }
        });
    }

    @Override
    public void resume() {
        // Temporary
//        gsm.setScreen(GameScreenManager.ScreenEnum.MENU);
    }

    @Override
    public void hide() {
        ideaBrainImg.remove();
        ideaInput.remove();
        ideaCheck.remove();
    }

    @Override
    public void dispose(){
        atlas.dispose();
        brainstormingSkin.dispose();
        plainSkin.dispose();
        ideaBrainTexture.dispose();
        font.dispose();
        round.dispose();
        super.dispose();
    }

    private void wallFallingAnimation(float delta){
        castle.remove();
        stateTime += delta;
        atlas = new TextureAtlas(Gdx.files.internal("textures/walls/castle.atlas"));
        Animation<TextureRegion> castleAnimation = new Animation<TextureRegion>(1/2f, atlas.findRegions("castle"), Animation.PlayMode.NORMAL);
        stage.draw();
        TextureRegion currentFrame = castleAnimation.getKeyFrame(stateTime, false);
        stage.getBatch().begin();
        stage.getBatch().draw(currentFrame, table.getX()-castle.getWidth()/2f, table.getY()-castle.getHeight()/2f-healthLabel.getHeight()/2f, wallWidth, wallHeight);

        stage.getBatch().end();
    }

    public String getCurrentHealth(){
        return String.format("HEALTH: %s/%s", round.getWall().getHitPoints(), round.getWall().getMaxHitPoints());
    }

}
