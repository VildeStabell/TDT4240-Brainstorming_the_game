package com.mygdx.game;

import com.mygdx.game.models.Brain;
import com.mygdx.game.models.EliminationPhase;
import com.mygdx.game.models.Player;
import com.mygdx.game.models.Session;
import com.mygdx.game.screens.BrainstormingScreen;
import com.mygdx.game.screens.EliminationScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.GameScreenManager;
import com.mygdx.game.screens.LobbyScreen;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * Controller
 * This controller connects the models, views and firebase interface.
 *
 *  INSTANCE: the instance of the controller
 *  fb: The firebase interface used to connect to the firebase database
 *  gsm: the GameScreenManager used to update the views
 *  session: the current session
 *  player: this games Player
 *
 * This class implements the Singleton pattern, Observer pattern and the MVC pattern.
 * */
public class Controller {

    private static Controller INSTANCE = null;

    private int maxHitPoints = 15;
    private int brainDamage = 5;
    private int maxSelectedBrains = 5;
    private int maxRound = 3;


    private FirebaseInterface fb;
    private GameScreenManager gsm;
    private Session session;
    private Player player;
    private int gameCode;

    private ArrayList<Brain> brains = new ArrayList<>();
    private ArrayList<String> players = new ArrayList<>();

    /**
     * Checks if there is an instance of the controller, if not it creates an instance
     * */
    public static Controller getInstance() {
        if (INSTANCE == null){
            INSTANCE = new Controller();
        }
        return INSTANCE;
    }

    /**
     * Setter for the firebase interface
     * */
    public void setFb(FirebaseInterface fb){
        this.fb = fb;
    }

    /**
     * Setter for the GameScreenManager
     * */
    public void setGSM(GameScreenManager gsm){
        this.gsm = gsm;
    }

    /**
     * Gets the username from the userinterface and creates a new mainPlayer
     * */
    public void setUsername(String username){
        this.player = new Player(username);
        //gsm next screen
    }

    public void setBrains(ArrayList<Brain> brains){
        this.brains = brains;
    }


    /**
     * Starts a new Multiplayer GameRoom, generates a session code, updates the firebase interface
     * with the correct gameCode reference and initializes the gameRoom.
     * Also sets the needed listeners, and adds the player to the database.
     * */
    public void startMultiplayerGameRoom(){
        this.gameCode = Session.generateSessionCode();
        fb.setGameCodeRef(String.valueOf(gameCode));
        fb.initializeGameRoom();
        fb.setNrPlayersChangedListener();
        fb.setAllDoneBrainstormingChangedListener();
        fb.setAllDoneEliminatingChangedListener();
        fb.setStartGameChangedListener();
        fb.setAllBrainsChangedListener();
        fb.setUserAddedChanged();
        fb.writeNewPlayer(player);
        LobbyScreen lobby = (LobbyScreen) gsm.getGameScreens().get(GameScreenManager.ScreenEnum.LOBBY);
        lobby.setGameCode(String.valueOf(gameCode));
    }

    /**
     * Updates the database with the correct gameCode reference, sets the needed listeners,
     * and adds the player to the database.
     * @param gameCode: GameCode from the user interface
     * */
    public void joinMultiplayerGameRoom(String gameCode){
        LobbyScreen lobby = (LobbyScreen) gsm.getGameScreens().get(GameScreenManager.ScreenEnum.LOBBY);
        lobby.setGameCode(gameCode);
        fb.setGameCodeRef(gameCode);
        fb.setNrPlayersChangedListener();
        fb.setAllDoneBrainstormingChangedListener();
        fb.setAllDoneEliminatingChangedListener();
        fb.setAllBrainsChangedListener();
        fb.setStartGameChangedListener();
        fb.setUserAddedChanged();
        fb.writeNewPlayer(player);

    }

    public void startSingleplayerSession(){
        gameCode = Session.generateSessionCode();
        fb.setGameCodeRef(String.valueOf(gameCode));
        fb.initializeGameRoom();
        fb.setNrPlayersChangedListener();
        fb.setAllDoneBrainstormingChangedListener();
        fb.setAllDoneEliminatingChangedListener();
        fb.setAllBrainsChangedListener();
        fb.setStartGameChangedListener();
        fb.writeNewPlayer(player);
        fb.setStartGame();
    }

    /**
     * Calls on the firebase interface to update the StartGame variable
     * */
    public void pressStartFirstRound() {
        fb.setStartGame();
    }

    /**
     * Starts a new session
     * */
    public void startGameChangedToTrue(){
        session = new Session(maxHitPoints, brainDamage, maxSelectedBrains, maxRound, player, gameCode);
        session.startNewRound(new ArrayList<>());
    }


    /**
     * Fires a brain, and checks if the wall has fallen.
     * If the wall has fallen, the firebase interface updates the DoneBrainstorming field for
     * the player to true, and updates the list of brains for the player in the database.
     * Then sets the gsm to a waiting screen.
     * */
    public void pressFireBrain(String idea) {
        boolean wallFallen = session.getCurrentRound().addBrainInBrainstormingPhase(idea);
        if (wallFallen){
            fb.setPlayerBrainList(player, session.getCurrentRound().getBrainstormingBrains());
            fb.setPlayerDoneBrainstorming(player, true);
            BrainstormingScreen brainstormingScreen = (BrainstormingScreen) gsm.getGameScreens().get(GameScreenManager.ScreenEnum.GAME_PHASE);
            brainstormingScreen.setWallFallen();
        }
    }

    /**
     * The firebase interface updates the DoneEliminating field for
     * the player to true, and updates the list of brains for the player in the database.
     * Then sets the gsm to a waiting screen.
     * */
    public void playerDoneEliminating(){
        ArrayList<Brain> selectedBrains = session.getCurrentRound().getSelectedBrains();
        fb.setPlayerBrainList(player, selectedBrains);
        fb.setPlayerDoneEliminating(player, true);
        //gsm.waitingForOtherPlayers();
    }

    /**
     * Toggles a brain in the eliminating phase
     * @param currentBrain: the brain thats being toggled
     * */
    public void toggleBrain(int currentBrain) {
        session.getCurrentRound().toggleBrain(currentBrain);
    }

    /**
     * Gets all the brains from every player in the firebase, and
     * starts the elimination round. Resets the "DoneBrainstorming" field in the database.
     * The sleep function is added because of delays when dealing with retrieving data from firebase
     * */
    public void allPlayersDoneBrainstorming(){
        sleep(1);
        fb.setPlayerDoneBrainstorming(player, false);
        session.getCurrentRound().startEliminationPhase(brains);
        EliminationScreen eliminationScreen = (EliminationScreen) gsm.getGameScreens().get(GameScreenManager.ScreenEnum.ELIMINATION_PHASE);
        eliminationScreen.resetEliminating();
        BrainstormingScreen brainstormingScreen = (BrainstormingScreen) gsm.getGameScreens().get(GameScreenManager.ScreenEnum.GAME_PHASE);
        brainstormingScreen.setAllPlayersCompleted();

    }


    /**
     * Gets all the brains from every player in the firebase, and
     * starts either a new Round or ends the game.
     * Resets the "DoneEliminating" field in the database.
     * The sleep function is added because of delays when dealing with retrieving data from firebase
     * */
    public void allPlayersDoneEliminating(){
        Dataholder dataholder = new Dataholder();
        fb.getAllBrains(dataholder);
        sleep(1);
        ArrayList<Brain> brains = dataholder.getBrains();
        fb.setPlayerDoneEliminating(player, false);
        if (session.endRound()){
            GameScreenManager.getInstance().getGameScreens().get(GameScreenManager.ScreenEnum.ELIMINATION_PHASE); //ADD SET GAMEDONE = TRUE
            return;
        }
        BrainstormingScreen brainstormingScreen = (BrainstormingScreen) gsm.getGameScreens().get(GameScreenManager.ScreenEnum.GAME_PHASE);
        brainstormingScreen.resetBrainstorming();
        session.startNewRound(brains);
    }

    /**
     * A helper function to delay the code with a specified number of seconds.
     * */
    private void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getHitPoints() {
        return session.getCurrentRound().getWall().getHitPoints();
    }

    public int getMaxHitPoints() {
        return session.getCurrentRound().getWall().getMaxHitPoints();
    }

    public int getBrainsLeft() {
        return session.getCurrentRound().brainsLeft();
    }

    public ArrayList<Brain> getEliminatingBrains() {
        return session.getCurrentRound().getBrainstormingBrains();
    }

    public ArrayList<Brain> getSelectedBrains() {
        return session.getCurrentRound().getSelectedBrains();
    }

    public boolean checkBrainSelected(int brainNumber){
        return session.getCurrentRound().checkBrainSelected(brainNumber);
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public ArrayList<String> getPlayers(){
        return players;
    }
}
