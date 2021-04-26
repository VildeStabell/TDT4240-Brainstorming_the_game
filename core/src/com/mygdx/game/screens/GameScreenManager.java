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
        LOBBY,
        JOINING,
        FINISH,
      }

    /**
     * Initializing the screens in constructor
     * Setting the menu screen as start screen
     *
     */
    private GameScreenManager(){ }

    public static GameScreenManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new GameScreenManager();
        }
        return INSTANCE;
    }

    public void setGame(Brainstorming newGame){
        game = newGame;
    }

    public void initGameScreens(){
        this.gameScreens = new HashMap<>();
        this.gameScreens.put(ScreenEnum.MENU, new MenuScreen("textures/backgrounds/standardBackground.png"));
        this.gameScreens.put(ScreenEnum.GAME, new GameScreen("textures/backgrounds/standardBackground.png"));
        this.gameScreens.put(ScreenEnum.GAME_PHASE, new BrainstormingScreen("textures/backgrounds/standardBackground.png"));
        this.gameScreens.put(ScreenEnum.ELIMINATION_PHASE, new EliminationScreen());
        this.gameScreens.put(ScreenEnum.LOBBY, new LobbyScreen());
        this.gameScreens.put(ScreenEnum.JOINING, new JoiningScreen());
        this.gameScreens.put(ScreenEnum.FINISH, new FinishingScreen());
    }

    public void setScreen(ScreenEnum nextScreen) {
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
