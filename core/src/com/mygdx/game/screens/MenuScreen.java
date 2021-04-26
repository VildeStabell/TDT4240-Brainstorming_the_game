package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Controller;

/**
 * Menu screen containing different options for the game
 */

public class MenuScreen extends BaseScreen {
    private Label title;
    private TextField usernameField;
    private String username;

    public MenuScreen(String imagePath){
        super(imagePath);
        username = Controller.getInstance().getUsername();
    }

    /**
     * The method is called whenever the gsm sets MenuScreen as its current screen.
     * Will draw the background and initialize and display the buttons.
     */
    @Override
    public void show() {
        super.show();
        title = new Label("Hello " + username + "!", skin);
        title.setFontScale(5);
        table.add(title).space(title.getHeight() + 70);
        table.row();
        initButtons();
    }

    /**
     * Centering and initialize the buttons using a Skin (Brainstorming Skin) and a table.
     *
     * startGameButton: Currently navigate to a placeholder GameScreen view
     * joinGameButton: Currently exits the app when clicked (change this to an input for joining game)
     *
     * Implementing listeners to each button by using addListener and instantiate a ChangeListener.
     * Then override the change method to fit the purpose of each event (a click event in this case).
     * Lastly add the each button which is considered as an actor to the table {@link BaseScreen}
     *
     */
    private void initButtons(){
        usernameField = new TextField(username, skin);
        usernameField.setAlignment(Align.center);
        TextButton newGameButton = new TextButton("START NEW GAME", skin);
        TextButton joinGameButton = new TextButton("JOIN EXISTING GAME", skin);
        TextButton changeUsernameButton = new TextButton("SUBMIT NEW USERNAME", skin);

        changeUsernameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Controller.getInstance().setUsername(usernameField.getText());
                username = usernameField.getText();
                title.setText("Hello " + username + "!");
            }
        });

        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Controller.getInstance().startMultiplayerGameRoom();
                gsm.setScreen(GameScreenManager.ScreenEnum.LOBBY);
            }
        });

        joinGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.setScreen(GameScreenManager.ScreenEnum.JOINING);

            }
        });

        table.add(usernameField);
        table.row();
        table.add(changeUsernameButton).spaceBottom(100);
        table.row();
        table.add(newGameButton);
        table.row();
        table.add(joinGameButton);
        table.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
    }

    /**
     * Rendering the background image first.
     * Drawing the stage with the buttons afterwards.
     * @param delta: time difference since last frame
     */
    @Override
    public void render(float delta){
        super.render(delta);
        stage.draw();
    }

    @Override
    public void pause() {}


    @Override
    public void resume() {
        // TODO: Maybe: temporary, need to redirect to a lobby while waiting for others to join?
        // TODO: Only accepting players that have typed username in text field
        gsm.setScreen(GameScreenManager.ScreenEnum.GAME_PHASE);
    }

    @Override
    public void hide() {
        super.dispose();
    }
}
