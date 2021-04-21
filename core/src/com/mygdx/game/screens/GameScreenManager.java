package com.mygdx.game.screens;


import com.mygdx.game.Brainstorming;
import java.util.HashMap;

/**
 * Game screen manager to control and initialize the screens
 *
 * game: the Brainstorming game instance
 * gameScreens: representing the game screens as a Hash Map with ScreenEnum-BaseScreen pair.
 * screenEnum: storing the available screens as enums.
 *
 */

public class GameScreenManager {

    private final Brainstorming game;
    private HashMap<ScreenEnum, BaseScreen> gameScreens;

    // Add to ScreenEnum as we extend more screen options
    public enum ScreenEnum {
        MENU,
        GAME, // We probably want the game phases to extend from Game so this will need to apply to these cases
        SETTINGS,
        GAME_PHASE,
        ELIMINATION_PHASE,
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
        this.gameScreens.put(ScreenEnum.MENU, new MenuScreen(this, "menuScreen.jpg"));
        this.gameScreens.put(ScreenEnum.GAME, new GameScreen(this,"gameScreen.jpg"));
        this.gameScreens.put(ScreenEnum.GAME_PHASE, new BrainstormingScreen(this, "textures/backgrounds/standardBackground.png"));
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
