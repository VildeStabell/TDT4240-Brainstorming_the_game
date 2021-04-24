package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

public class EliminationScreen extends GameScreen {
    private Controller controller;
    private ArrayList<ImageButton> brains;

    private final Texture brainTexture = new Texture("textures/brains/eliminationBrain.png");
    //private final Texture checkboxTexture = new Texture("textures/buttons/checkbox.png");
    private final Texture checkSignTexture = new Texture("textures/buttons/checkSign.png");
    private final Texture backArrowTexture = new Texture("textures/buttons/back.png");
    private final Texture nextArrowTexture = new Texture("textures/buttons/next.png");
    private final Texture backInactiveTexture = new Texture("textures/buttons/backInactive.png");
    private final Texture nextInactiveTexture = new Texture("textures/buttons/nextInactive.png");


    public EliminationScreen(GameScreenManager gsm, Controller controller) {
        super(gsm, "textures/backgrounds/eliminationBackground.png");
        brains = new ArrayList<>();
        this.controller = controller;
        render(0); //Consider removing
    }

    @Override
    public void show() {
        super.show();
        brains = //ArrayList of a list of ideas
        for (ImageButton brain : brains) {
            brain.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.toggleBrain(EliminationScreen.this.brains.indexOf(brain));
                }
            });
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //Remember to use scaleImage() here
    }

    @Override
    public void dispose() {
        super.dispose();
        brainTexture.dispose();
        //checkboxTexture.dispose();
        checkSignTexture.dispose();
        backArrowTexture.dispose();
        nextArrowTexture.dispose();
        backInactiveTexture.dispose();
        nextInactiveTexture.dispose();
    }
}
