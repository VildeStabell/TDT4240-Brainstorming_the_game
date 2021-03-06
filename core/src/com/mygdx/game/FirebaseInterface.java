package com.mygdx.game;

import com.mygdx.game.models.Brain;
import com.mygdx.game.models.Player;

import java.util.ArrayList;

public interface FirebaseInterface {

    void setValueInDb(String target, String value);
    void writeNewPlayer(Player player);
    void setNrPlayers(int value);
    int getNrPlayers();
    void setNrPlayersChangedListener();
    void setGameCodeRef(String gameCodeRef);
    String getGameCodeRef();
    void setPlayerDoneBrainstorming(Player player, boolean value);
    void setPlayerDoneEliminating(Player player, boolean value);
    void setAllDoneBrainstormingChangedListener();
    void setAllDoneEliminatingChangedListener();
    void setPlayerBrainList(Player player, ArrayList<Brain> brains);
    void getAllBrains(Dataholder dataholder);
    void setStartGame();
    void setStartGameChangedListener();
    void initializeGameRoom();
    void setAllBrainsChangedListener();
    void setUserAddedChanged();
}
