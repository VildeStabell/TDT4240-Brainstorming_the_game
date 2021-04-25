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
 * This class inplements the Singleton pattern
 */

public class GameScreenManager {

    private static GameScreenManager INSTANCE = null;

    private Brainstorming game;
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
     */
    private GameScreenManager(){
        initGameScreens();
    }

    public static GameScreenManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new GameScreenManager();
        }
        return INSTANCE;
    }

    public void setGame(Brainstorming game){
        this.game = game;
    }

    private void initGameScreens(){
        this.gameScreens = new HashMap<>();
        this.gameScreens.put(ScreenEnum.MENU, new MenuScreen(this, "textures/backgrounds/standardBackground.png"));
        this.gameScreens.put(ScreenEnum.GAME, new GameScreen(this,"textures/backgrounds/standardBackground.png"));
        this.gameScreens.put(ScreenEnum.GAME_PHASE, new BrainstormingScreen(this,"textures/backgrounds/standardBackground.png"));
    }

    public void setScreen(ScreenEnum nextScreen){
        game.setScreen(gameScreens.get(nextScreen));
    }

    public HashMap<ScreenEnum, BaseScreen> getGameScreens() {
        return gameScreens;
    }

    public void dispose(){
        for(BaseScreen screen : gameScreens.values()){
            if(screen != null ){
                screen.dispose();
          }
      }
    }

}
