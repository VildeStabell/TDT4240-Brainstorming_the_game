package com.mygdx.game.desktop;

import com.mygdx.game.Dataholder;
import com.mygdx.game.FirebaseInterface;
import com.mygdx.game.models.Brain;
import com.mygdx.game.models.Player;

import java.util.ArrayList;

public class DesktopInterfaceClass implements FirebaseInterface {


    @Override
    public void setValueInDb(String target, String value) {}

    @Override
    public void writeNewPlayer(Player player) { }
    @Override
    public void getGameCodeFromDB(Dataholder dataholder) { }

    @Override
    public void setGameCodeRef(String gameCodeRef) { }

    @Override
    public void setPlayerDoneBrainstorming(Player player, boolean value) { }

    @Override
    public void setPlayerDoneEliminating(Player player, boolean value) { }

    @Override
    public void setPlayerBrainList(Player player, ArrayList<Brain> brains) { }

    @Override
    public void getAllBrains(Dataholder dataholder) { }

    @Override
    public String getGameCodeRef() { return null; }

    @Override
    public void setNrPlayers(int i) { }

    @Override
    public void setNrPlayersChangedListener() {

    }

    @Override
    public void setAllDoneBrainstormingChangedListener() { }

    @Override
    public void setAllDoneEliminatingChangedListener() { }
}
