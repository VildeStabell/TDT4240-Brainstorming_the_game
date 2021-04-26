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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.models.Brain;
import com.mygdx.game.models.Player;
import com.mygdx.game.models.Round;
import java.util.ArrayList;

/**
 * statetime: accumulated delta time for each frame, used in animation
 * toggleSubmitField: boolean value for toggling the text field
 * ideaText: input text from the user
 *
 * ideaInputField: the text field to submit ideas
 * ideaCheck: validate by outputting a green checkmark if the user has typed anything to text field
 * brainButton: clickable brain to toggle the text field
 * castle: the castle to attack
 * healthLabel: displaying the remaining HP
 * ideaBrainTexture: background of the toggling text field
 * ideaBrainImg: the image of the ideaBrainTexture in order to add it as an actor to stage
 * atlas: generated drawable texture regions for animation purposes
 * castleAnimation: contain the texture regions for the animation
 * font: Myriad Pro font from customized skin
 * roundOverTable: table for centering the round over text and continue button
 * Implementing the MVC pattern
 */

public class BrainstormingScreen extends BaseScreen {

    private float stateTime;
    private boolean toggleSubmitIdeaField;
    private String ideaText;

    // TODO: replace with controller
    private Round round;

    private TextField ideaInputField;
    private Button ideaCheck;
    private ImageButton brainButton;
    private Image castle;
    private Label healthLabel, waitingForPlayers;

    private Texture ideaBrainTexture;
    private Image ideaBrainImg;
    private TextureAtlas atlas;

    private Animation<TextureRegion> castleAnimation;
    private BitmapFont font;
    private Table roundOverTable;
    private TextButton continueButton;

    // TODO: Ratios according to screen size, temporary variables
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

    /**
     * Init the game phase
     * Temporary using the round model, but should use the controller
     * @param gsm: GameScreenManager to switch screens
     * @param imagePath: path to background image
     */

    public BrainstormingScreen(GameScreenManager gsm, String imagePath) {
        super(gsm, imagePath);
        // TODO: replace with controller
        ideaText = "";
        ArrayList<Brain> brains = new ArrayList<>();
        int maxHitPoints = 1;
        int brainDamage = 1;
        int maxSelectedBrains = 3;
        Player player = new Player("Mai");
        round = new Round(player, brains, maxHitPoints, brainDamage, maxSelectedBrains);
    }

    /**
     * Init UI widget and layout (using table)
     * Also added listeners to some of the widgets
     */
    @Override
    public void show() {
        super.show();
        initWidgets();

        brainButton.setBounds(brainPosOffset,brainPosOffset, brainButtonSize, brainButtonSize);
        brainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                toggleSubmitIdea();
            }
       });

        stage.addActor(brainButton);

        // Table, adjusting to screen and add actors
        table.add(healthLabel).top();
        table.row();
        table.add(castle).size(wallWidth, wallHeight);
        table.setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 5f, wallHeight);
        // TODO: remove debug
//        table.debug();      // Turn on all debug lines (table, cell, and widget).
//        table.debugTable(); // Turn on only table lines.
    }

    /**
     * Init the UI widgets and set position of each widget according to screen size ratios
     */
    private void initWidgets(){
        stateTime = 0f;
        font = skin.getFont("myriad-pro-font");
        font.getData().setScale(1.2f);

        // Mid widgets  ("Round over" and continue button for elimination phase)
        roundOverTable = new Table();
        Label roundOver = new Label("ROUND OVER", skin);
        continueButton = new TextButton("Continue", skin);
        waitingForPlayers = new Label("Waiting for players ...", skin);
        roundOverTable.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
        roundOverTable.add(roundOver);
        roundOverTable.row();
        roundOverTable.add(continueButton);
        roundOverTable.row();
        roundOverTable.add(waitingForPlayers);

        // Clickable brain
        brainButton = new ImageButton(skin);

        // Castle
        castle = new Image(round.getWall().getWallTexture());
        healthLabel = new Label(getCurrentHealth(), skin);
        atlas = new TextureAtlas(Gdx.files.internal("textures/walls/castle.atlas"));
        castleAnimation = new Animation<>(1/2f, atlas.findRegions("castle"), Animation.PlayMode.NORMAL);

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

    /**
     * Drawing everything that has been put to the stage (i.e table and widgets)
     * Also drawing how many brains left in the phase
     * Will toggle the text field when clicking on the brain
     * Display the animation when castle's health decrease to zero and a continue button to elimination phase
     * @param delta: time difference since last frame
     */
    @Override
    public void render(float delta){
        super.render(delta);
        stage.draw();
        stage.act(delta);
        font.setColor(Color.RED);

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
            if (castleAnimation.isAnimationFinished(stateTime)){
                stage.addActor(roundOverTable);
                // TODO: check if all players have completed
                if(allPlayersCompleted){
                    // TODO: Start elimination round
                    setActorOnTable(roundOverTable, waitingForPlayers, false);
                    setActorOnTable(roundOverTable, continueButton, true);

                }else{
                    // Waiting for players
                    setActorOnTable(roundOverTable, continueButton, false);
                }
            }
        }
    }


    @Override
    public void pause() {
        System.out.println("Show menu options");
    }

    @Override
    public void resume() {
        // TODO: Temporary empty
        gsm.setScreen(GameScreenManager.ScreenEnum.ELIMINATION_PHASE);
    }

    @Override
    public void hide() {
        atlas.dispose();
        ideaBrainTexture.dispose();
        font.dispose();
        // TODO: Remove when replacing with controller
        round.dispose();
        super.dispose();
    }

    /**
     * Adding the UI widgets to stage to show the text field
     */
    private void showSubmitIdeaField(){
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
                    // TODO: Submit idea to brain
                    System.out.println(String.format("Submit idea to brain: %s", getIdeaText()));
//                    round.addBrainInBrainstormingPhase(ideaInput.getText());
                    submitIdea();
                }
            }
        });
    }

    /**
     * Turning on/off visiblity of an actor
     */
    private void setActorOnTable(Table table, Actor actor, boolean bool){
        table.getCell(actor).getActor().setVisible(bool);
    }

    /**
     * Remove the actors/ UI widget from the stage
     */
    private void hideSubmitIdeaField(){
        ideaBrainImg.remove();
        ideaInputField.remove();
        ideaCheck.remove();
    }

    /**
     * Submit idea to brain
     * TODO: controller
     */
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

    /**
     * Accumulating the time differences to render the animation frames
     * @param delta: time difference since last frame
     */
    private void wallFallingAnimation(float delta){
        float castleWidthCenter = table.getX()-castle.getWidth()/2f;
        float castleHeightCenter = table.getY()-castle.getHeight()/2f-healthLabel.getHeight()/2f;
        castle.remove();
        stateTime += delta;
        stage.draw();
        TextureRegion currentFrame = castleAnimation.getKeyFrame(stateTime, false);
        stage.getBatch().begin();
        stage.getBatch().draw(currentFrame, castleWidthCenter, castleHeightCenter, wallWidth, wallHeight);

        stage.getBatch().end();
    }

    // TODO: calculate the remaining health of castle
    public String getCurrentHealth(){
        return String.format("HEALTH: %s/%s", round.getWall().getHitPoints(), round.getWall().getMaxHitPoints());
    }

    // TODO: calculate the remaining brains
    private int getBrainsLeft(){
//        return round.getMaxSelectedBrains()-round.getCurrentBrainNumber();
        return 10;
    }
}
