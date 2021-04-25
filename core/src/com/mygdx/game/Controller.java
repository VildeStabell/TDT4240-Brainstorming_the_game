package com.mygdx.game;

import com.mygdx.game.models.Brain;
import com.mygdx.game.models.Player;
import com.mygdx.game.models.Session;
import com.mygdx.game.screens.GameScreenManager;

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

    private int maxHitPoints = 25;
    private int brainDamage = 5;
    private int maxSelectedBrains = 5;
    private int maxRound = 3;


    private FirebaseInterface fb;
    private GameScreenManager gsm;
    private Session session;
    private Player player;
    private int gameCode;

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
        fb.writeNewPlayer(player);
    }

    /**
     * Updates the database with the correct gameCode reference, sets the needed listeners,
     * and adds the player to the database.
     * @param gameCode: GameCode from the user interface
     * */
    public void joinMultiplayerGameRoom(String gameCode){
        fb.setGameCodeRef(gameCode);
        fb.setNrPlayersChangedListener();
        fb.setAllDoneBrainstormingChangedListener();
        fb.setAllDoneEliminatingChangedListener();
        fb.setStartGameChangedListener();
        fb.writeNewPlayer(player);
    }

    public void startSingleplayerSession(){
        int gameCode = Session.generateSessionCode();
        fb.setGameCodeRef(String.valueOf(gameCode));
        fb.initializeGameRoom();
        fb.setNrPlayersChangedListener();
        fb.setAllDoneBrainstormingChangedListener();
        fb.setAllDoneEliminatingChangedListener();
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
        //gsm.fireBrainAnimation();
        //gsm.updateHitPoints(session.getCurrentRound().getWall()
        if (wallFallen){
            fb.setPlayerBrainList(player, session.getCurrentRound().getBrainstormingBrains());
            fb.setPlayerDoneBrainstorming(player, true);
            //gsm.fallenWall();
            //gsm.waitingForOtherPlayers();
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
     * @param brain: the brain thats being toggled TODO: Change this to not-brain (Int)
     * */
    public void toggleBrain(Brain brain) {
        session.getCurrentRound().toggleBrain(brain);
        //gsm.toggleBrain(brain);
    }

    /**
     * Gets all the brains from every player in the firebase, and
     * starts the elimination round. Resets the "DoneBrainstorming" field in the database.
     * The sleep function is added because of delays when dealing with retrieving data from firebase
     * */
    public void allPlayersDoneBrainstorming(){
        Dataholder dataholder = new Dataholder();
        fb.getAllBrains(dataholder);
        sleep(1);
        ArrayList<Brain> brains = dataholder.getBrains();
        fb.setPlayerDoneBrainstorming(player, false);
        session.getCurrentRound().startEliminationPhase(brains);
        //gsm.setScreen(elimination);
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
            //gsm.endGame()
            return;
        }
        session.startNewRound(brains);
        //gsm.setScreen(brainstorming);
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
}
