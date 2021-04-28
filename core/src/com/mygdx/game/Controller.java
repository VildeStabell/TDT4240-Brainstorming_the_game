package com.mygdx.game;

import com.mygdx.game.models.Brain;
import com.mygdx.game.models.Player;
import com.mygdx.game.models.Session;
import com.mygdx.game.screens.BrainstormingScreen;
import com.mygdx.game.screens.EliminationScreen;
import com.mygdx.game.screens.GameScreenManager;
import com.mygdx.game.screens.LobbyScreen;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * This controller connects the models, views and firebase interface.
 *
 * INSTANCE: the instance of the controller
 * fb: The firebase interface used to connect to the firebase database
 * gsm: the GameScreenManager used to update the views
 * session: the current session
 * player: this game's Player
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
    private Random random = new Random();

    private ArrayList<Brain> firebaseBrains = new ArrayList<>();
    private ArrayList<String> players = new ArrayList<>();

    /**
     * Checks if there is an instance of the controller, if not it creates a new instance.
     * This ensures that the Controller is a singleton.
     * */
    public static Controller getInstance() {
        if (INSTANCE == null){
            INSTANCE = new Controller();
        }
        return INSTANCE;
    }

    public void setFb(FirebaseInterface fb){
        this.fb = fb;
    }

    public void setGSM(GameScreenManager gsm){
        this.gsm = gsm;
    }

    /**
     * Gets the username from the user interface or generateUsername and creates a new player
     * */
    public void setUsername(String username){
        if(player == null)
            this.player = new Player(username);
        else
            this.player.setUsername(username);
    }

    /**
     * Generates a random username, and updates the player with said username
     * @return the generated username
     */
    public String generateUsername() {
        int MIN = 1000;
        int MAX = 9999;
        String username = "User" + (random.nextInt(MAX - MIN) + MIN);
        setUsername(username);
        return username;
    }

    public String getUsername() {
        if(player == null)
            generateUsername();
        return player.getUsername();
    }

    public void setFirebaseBrains(ArrayList<Brain> firebaseBrains){
        this.firebaseBrains = firebaseBrains;
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
    }

    /**
     * Updates the database with the correct gameCode reference, sets the needed listeners,
     * and adds the player to the database.
     * @param gameCode: GameCode from the user interface
     * */
    public void joinMultiplayerGameRoom(String gameCode){
        this.gameCode = Integer.parseInt(gameCode);
        fb.setGameCodeRef(gameCode);
        fb.setNrPlayersChangedListener();
        fb.setAllDoneBrainstormingChangedListener();
        fb.setAllDoneEliminatingChangedListener();
        fb.setAllBrainsChangedListener();
        fb.setStartGameChangedListener();
        fb.setUserAddedChanged();
        fb.writeNewPlayer(player);

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
    public void startGameChangedToTrue() {
        session = new Session(maxHitPoints, brainDamage, maxSelectedBrains, maxRound, player, gameCode);
        session.startNewRound(new ArrayList<>());
        LobbyScreen lobby = (LobbyScreen) gsm.getGameScreens().get(GameScreenManager.ScreenEnum.LOBBY);
        lobby.setGameStarted();

    }


    /**
     * Fires a brain, and checks if the wall has fallen.
     * If the wall has fallen, the firebase interface updates the DoneBrainstorming field for
     * the player to true, and updates the player's list of brains in the database,
     * then sets the gsm to a waiting screen.
     * */
    public void pressFireBrain(String idea) {
        boolean wallFallen = session.getCurrentRound().addBrainInBrainstormingPhase(idea);
        if (wallFallen){
            fb.setPlayerBrainList(player, session.getCurrentRound().getBrainstormingBrains());
            fb.setPlayerDoneBrainstorming(player, true);
            BrainstormingScreen brainstormingScreen = (BrainstormingScreen) gsm.getGameScreens()
                    .get(GameScreenManager.ScreenEnum.GAME_PHASE);
            brainstormingScreen.setWallFallen();
        }
    }

    /**
     * The firebase interface updates the DoneEliminating field for the player to true,
     * and updates the player's list of brains in the database.
     * Then sets the gsm to a waiting screen.
     * */
    public void playerDoneEliminating(){
        ArrayList<Brain> selectedBrains = session.getCurrentRound().getSelectedBrains();
        fb.setPlayerBrainList(player, selectedBrains);
        fb.setPlayerDoneEliminating(player, true);
    }

    /**
     * Toggles a brain in the eliminating phase
     * @param currentBrain: the brain that's being toggled
     * */
    public void toggleBrain(int currentBrain) {
        session.getCurrentRound().toggleBrain(currentBrain);
    }

    /**
     * Gets all the brains from every player in the firebase, and starts the elimination round.
     * Resets the "DoneBrainstorming" field in the database.
     * The sleep function is added because of delays when retrieving data from firebase.
     * */
    public void allPlayersDoneBrainstorming(){
        sleep(1);
        fb.setPlayerDoneBrainstorming(player, false);
        sleep(5);
        session.getCurrentRound().startEliminationPhase(firebaseBrains);
        EliminationScreen eliminationScreen = (EliminationScreen) gsm.getGameScreens()
                .get(GameScreenManager.ScreenEnum.ELIMINATION_PHASE);
        eliminationScreen.resetEliminating();
        BrainstormingScreen brainstormingScreen = (BrainstormingScreen) gsm.getGameScreens()
                .get(GameScreenManager.ScreenEnum.GAME_PHASE);
        brainstormingScreen.setAllPlayersCompleted();

    }


    /**
     * Gets all the brains from every player in the firebase,
     * and either starts a new Round or ends the game.
     * Resets the "DoneEliminating" field in the database.
     * The sleep function is added because of delays when retrieving data from firebase.
     * */
    public void allPlayersDoneEliminating(){
        sleep(1);
        fb.setPlayerDoneEliminating(player, false);
        if (session.endRound()){
            EliminationScreen eliminationScreen = (EliminationScreen) GameScreenManager.getInstance()
                    .getGameScreens().get(GameScreenManager.ScreenEnum.ELIMINATION_PHASE);
            eliminationScreen.setGameDone();
            eliminationScreen.setAllPlayersDone();
            return;
        }
        BrainstormingScreen brainstormingScreen = (BrainstormingScreen) gsm.getGameScreens()
                .get(GameScreenManager.ScreenEnum.GAME_PHASE);
        brainstormingScreen.resetBrainstorming();
        EliminationScreen eliminationScreen = (EliminationScreen) GameScreenManager.getInstance()
                .getGameScreens().get(GameScreenManager.ScreenEnum.ELIMINATION_PHASE);
        eliminationScreen.setAllPlayersDone();
        session.startNewRound(firebaseBrains);
    }

    /**
     * A helper function to delay the code with a specified number of seconds.
     * Used to stop the program from running too fast for firebase.
     * */
    private void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the current wall's hitpoints
     * */
    public int getHitPoints() {
        return session.getCurrentRound().getWall().getHitPoints();
    }

    /**
     * Returns the current wall's max hitpoints
     * */
    public int getMaxHitPoints() {
        return session.getCurrentRound().getWall().getMaxHitPoints();
    }

    /**
     * Returns the brains left in the current round
     * */
    public int getBrainsLeft() {
        return session.getCurrentRound().brainsLeft();
    }

    /**
     * Returns the current round's eliminationpPhase brains
     * */
    public ArrayList<Brain> getEliminatingBrains() {
        return session.getCurrentRound().getEliminationBrains();
    }

    /**
     * Returns the current round's selected brains in eliminationPhase
     * */
    public ArrayList<Brain> getSelectedBrains() {
        return session.getCurrentRound().getSelectedBrains();
    }

    /**
     * Returns true if the brain at a given index is selected in elimination phase.
     * @param brainNumber: The index of the brain in question.
     * */
    public boolean checkBrainSelected(int brainNumber){
        return session.getCurrentRound().checkBrainSelected(brainNumber);
    }

    /**
     * Sets the arraylist of usernames for the current players
     * */
    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    /**
     * Returns the arraylist of usernames for the current players
     * */
    public ArrayList<String> getPlayers(){
        return players;
    }

    public ArrayList<String> getFinalBrains() {
        ArrayList<String> finalBrains = new ArrayList<>();
        for (Brain brain : firebaseBrains){
            finalBrains.add(brain.toString());
        }
        return finalBrains;
    }

    public ArrayList<String> getPlayersBrains() {
        ArrayList<String> playerBrains = new ArrayList<>();
        for (Brain brain : session.getAllBrains()){
            playerBrains.add(brain.toString());
        }
        return playerBrains;
    }

    public int getMaxNrBrains() {
        return Math.min(maxSelectedBrains, session.getCurrentRound().getEliminationBrains().size());
    }

    public int getNrEliminationBrains(){
        return session.getCurrentRound().getEliminationBrains().size();
    }

    public String getCurrentBrainIdeas() {
        return session.getCurrentRound().getBrains().get(session.getCurrentRound().getCurrentBrain()).toString();
    }

    public String getGameCode(){
        return String.valueOf(gameCode);
    }
}
