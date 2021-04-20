package com.mygdx.game.screens;


import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Brainstorming;
import java.util.HashMap;

/**
 * Game screen manager to control and initialize the screens
 *
 * game: the Brainstorming game instance
 * gameScreens: representing the game screens as a HasH Map with ScreenEnum-BaseScreen pair.
 */

public class GameScreenManager {

    private final Brainstorming game;
    private HashMap<ScreenEnum, BaseScreen> gameScreens;

    // Add to ScreenEnum as we extend more screen options
    public enum ScreenEnum {
        MENU,
        GAME, // We probably want the game phases to extend from Game so this will need to apply to these cases
        // SETTINGS,
      }

    /**
     * Initializing the screens in constructor
     * Setting the menu screen as start screen
     *
     * @param game: Brainstorming game instance
     */

    public GameScreenManager(Brainstorming game){
        this.game = game;
        initGameScreens();
        setScreen(ScreenEnum.MENU);
    }


    private void initGameScreens(){
        this.gameScreens = new HashMap<>();
        //this.gameScreens.put(ScreenEnum.MENU, new MenuScreen(this));
        //this.gameScreens.put(ScreenEnum.GAME, new GameScreen(this, new Texture("gameScreen.jpg")));
    }

    public void setScreen(ScreenEnum nextScreen){
        game.setScreen(gameScreens.get(nextScreen));
    }

    public void dispose(){
        for(BaseScreen screen : gameScreens.values()){
            if(screen != null ){
                screen.dispose();
          }
      }
    }


}
