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
    private HashMap<ScreenEnum, BaseScreen> gameScreens = new HashMap<>();

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
        this.gameScreens.put(ScreenEnum.MENU, new MenuScreen());
        this.gameScreens.put(ScreenEnum.GAME_PHASE, new BrainstormingScreen());
        this.gameScreens.put(ScreenEnum.ELIMINATION_PHASE, new EliminationScreen());
        this.gameScreens.put(ScreenEnum.LOBBY, new LobbyScreen());
        this.gameScreens.put(ScreenEnum.JOINING, new JoiningScreen());
        this.gameScreens.put(ScreenEnum.FINISH, new FinishingScreen());
    }

    public void setScreen(ScreenEnum nextScreen) {
        switch (nextScreen){
            case MENU:
                MenuScreen menu = new MenuScreen();
                gameScreens.put(ScreenEnum.MENU, menu);
                game.setScreen(menu);
                break;
            case GAME_PHASE:
                BrainstormingScreen gameScreen = new BrainstormingScreen();
                gameScreens.put(ScreenEnum.GAME_PHASE, gameScreen);
                game.setScreen(gameScreen);
                break;
            case ELIMINATION_PHASE:
                EliminationScreen eliminationScreen = new EliminationScreen();
                gameScreens.put(ScreenEnum.ELIMINATION_PHASE, eliminationScreen);
                game.setScreen(eliminationScreen);
                break;
            case LOBBY:
                LobbyScreen lobbyScreen = new LobbyScreen();
                gameScreens.put(ScreenEnum.LOBBY, lobbyScreen);
                game.setScreen(lobbyScreen);
                break;
            case JOINING:
                JoiningScreen joiningScreen = new JoiningScreen();
                gameScreens.put(ScreenEnum.JOINING, joiningScreen);
                game.setScreen(joiningScreen);
                break;
            case FINISH:
                FinishingScreen finishingScreen = new FinishingScreen();
                gameScreens.put(ScreenEnum.FINISH, finishingScreen);
                game.setScreen(finishingScreen);
        }
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
