package com.mygdx.game;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mygdx.game.models.Brain;
import com.mygdx.game.models.Player;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * A class to access the Firebase package through an interface
 * The database is a NoSQL database which means the data is stored as a key-value pair
 *
 * database: the realtime database in Firebase
 * gameCodeRef: the gamecode, used for referencing to the place where the games data is stored
 * nrPlayers: the current number of players
 *
 */
public class AndroidInterfaceClass implements FirebaseInterface {

    private final FirebaseDatabase database;
    private String gameCodeRef;
    private int nrPlayers;


    /**
     * Initialize the database by passing an URL to the database
     * Pointing to a "key" addressed by the path to store and write data whenever myRef is used.
     */
    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://brainstorming-1fa06-default-rtdb.europe-west1.firebasedatabase.app/");
    }

    /**
     * Sets a value in the database on a given target to a given value.
     * @param target: target of the update
     * @param value: value target should be set to
     * */
    @Override
    public void setValueInDb(String target, String value) {
        DatabaseReference currentRef = database.getReference(target);
        currentRef.setValue(value);
    }

    /**
     * Increases a value in the database with one, in the field specified by target
     * @param target: Target for update
     * @param value: the current number stored in the database
     * */
    private void increaseDBValue(String target, int value){
        database.getReference(gameCodeRef).child(target).setValue(value+1);
    }

    /**
     * Initialized a databasefield with the number 1.
     * @param target: Target for initialization.
     * */
    private void startDBValue(String target){
        database.getReference(gameCodeRef).child(target).setValue(1);
    }

    /**
     * Initializes the values in the database
     * @param gameCode: the current gameCodeReference
     * */
    public void initializeGameRoom(String gameCode) {
        DatabaseReference game = database.getReference(gameCode);
        game.child("NumberOfPlayers").setValue(0);
        game.child("PlayersDoneBrainstorming").setValue(0);
        game.child("PlayersDoneEliminating").setValue(0);
        game.child("AllDoneBrainstorming").setValue(false);
        game.child("AllDoneEliminating").setValue(false);
        game.child("StartGame").setValue(false);
    }

    /**
     * Sets the gameCodeRef to the current game
     * @param gameCodeRef: the gameCode
     * */
    @Override
    public void setGameCodeRef(String gameCodeRef){
        this.gameCodeRef = gameCodeRef;
    }

    /**
     * Getter for gameCodeRef
     * @return gameCodeRef
     * */
    @Override
    public String getGameCodeRef() {
        return this.gameCodeRef;
    }

    /**
     * Sets the nrPlayers to the given value
     * @param value: value nrPlayers have been set to
     * */
    @Override
    public void setNrPlayers(int value) {
        this.nrPlayers = value;
    }

    /**
     * @return the number of players
     * */
    @Override
    public int getNrPlayers(){
        return nrPlayers;
    }

    /**
     * Creates a listener that updates the nrPlayers value each time the NumberOfPlayers value in the DB
     * is updated.
     * */
    @Override
    public void setNrPlayersChangedListener() {
        database.getReference(gameCodeRef).child("NumberOfPlayers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = String.valueOf(snapshot.getValue());
                if (!value.equals(null) && !value.equals("null")) {
                    setNrPlayers(Integer.parseInt(value));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    /**
     * Adds a new player to the database, and increases the number of players
     * @param player: The player being added
     * */
    @Override
    public void writeNewPlayer(Player player){
        if (gameCodeRef == null){
            throw new IllegalStateException("GameRoom is not set");
        }
        DatabaseReference currentRef = database.getReference(gameCodeRef);
        currentRef.child("Players").child(player.getUsername()).setValue(player.getUsername());
        updateNrPlayerValues("NumberOfPlayers");
    }


    /**
     * Updates the number of different player values:
     * - Number of players
     * - Players done brainstorming
     * - Players done eliminating
     * Calls on internal methods depending on whats being changed.
     * @param target: The value being updated
     * */
    private void updateNrPlayerValues(String target) {
        database.getReference(gameCodeRef).child(target).addListenerForSingleValueEvent(new ValueEventListener()
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            String value = String.valueOf(snapshot.getValue());
            if (value.isEmpty() || value == null || value.equals(null) || value.contains("null")){
                startDBValue(target);
                value = "1";
            }
            else {
                increaseDBValue(target, Integer.parseInt(value));
            }
            switch (target){
                case "NumberOfPlayers":
                    break;
                case "PlayersDoneBrainstorming":
                    checkPlayersDoneBrainstorming(Integer.parseInt(value)+1);
                    break;
                case "PlayersDoneEliminating":
                    checkPlayersDoneEliminating(Integer.parseInt(value)+1);
                    break;
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w(TAG,"loadGamecode:onCancelled", error.toException());
        }

    });
    }

    /**
     * Checks if all players are done brainstorming by comparing the number of players with
     * the number of players done brainstorming.
     * Sets the field AllDoneBrainstorming to true,
     * PlayersDoneEliminating to 0, and AllDoneEliminating to false
     * @param nrDoneBrainstorming: The number of players done brainstorming.
     * */
    private void checkPlayersDoneBrainstorming(int nrDoneBrainstorming) {
        if (nrDoneBrainstorming == nrPlayers){
            database.getReference(gameCodeRef).child("AllDoneBrainstorming").setValue(true);
            database.getReference(gameCodeRef).child("PlayersDoneEliminating").setValue(0);
            database.getReference(gameCodeRef).child("AllDoneEliminating").setValue(false);
        }
    }

    /**
     * Checks if all players are done eliminating by comparing the number of players with
     * the number of players done eliminating.
     * Sets the field AllDoneEliminating to true,
     * PlayersDoneBrainstorming to 0, and AllDoneBrainstorming to false
     * @param nrDoneEliminating: The number of players done eliminating.
     * */
    private void checkPlayersDoneEliminating(int nrDoneEliminating) {
        if (nrDoneEliminating == nrPlayers){
            database.getReference(gameCodeRef).child("AllDoneEliminating").setValue(true);
            database.getReference(gameCodeRef).child("PlayersDoneBrainstorming").setValue(0);
            database.getReference(gameCodeRef).child("AllDoneBrainstorming").setValue(false);
        }
    }

    /**
     * Sets the player field "DoneBrainstorming" to given value for a selected player.
     * If the value is true it updates the PlayersDoneBrainstorming field in the database
     * @param player: the player which is being updated
     * @param value: a boolean value
     * */
    @Override
    public void setPlayerDoneBrainstorming(Player player, boolean value){
        DatabaseReference currentRef = database.getReference(gameCodeRef).child("Players").child(player.getUsername());
        currentRef.child("DoneBrainstorming").setValue(value);
        if(value){
            updateNrPlayerValues("PlayersDoneBrainstorming");
        }
    }


    /**
     * Sets the player field "DoneEliminating" to given value for a selected player.
     * If the value is true it updates the PlayersDoneEliminating field in the database
     * @param player: the player which is being updated
     * @param value: a boolean value
     * */
    @Override
    public void setPlayerDoneEliminating(Player player, boolean value) {
        DatabaseReference currentRef = database.getReference(gameCodeRef).child("Players").child(player.getUsername());
        currentRef.child("DoneEliminating").setValue(value);
        if(value){
            updateNrPlayerValues("PlayersDoneEliminating");
        }

    }

    /**
     * Listener for allDoneBrainstorming
     * TODO: Should call on the controller to start elimination round
     * */
    @Override
    public void setAllDoneBrainstormingChangedListener() {
        database.getReference(gameCodeRef).child("AllDoneBrainstorming").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String stringValue = String.valueOf(snapshot.getValue());
                if (!stringValue.equals(null) && !stringValue.equals("null")){
                    Boolean value = (Boolean) snapshot.getValue();
                    if (value){
                        //TODO: Should call on the controller to start elimination round
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    /**
     * Listener for AllDoneEliminating
     * TODO: Should call on the controller to start a new round or end the game
     * */
    @Override
    public void setAllDoneEliminatingChangedListener() {
        database.getReference(gameCodeRef).child("AllDoneEliminating").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String stringValue = String.valueOf(snapshot.getValue());
                if (!stringValue.equals(null) && !stringValue.equals("null")){
                    Boolean value = (Boolean) snapshot.getValue();
                    if (value){
                        //TODO: Should call on the controller to start new round or end the game
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * Updates the player's list of brains with a given ArrayList of brains
     * @param player: The player who is being updated
     * @param brains: the list of brains that are being saved to the database
     * */
    @Override
    public void setPlayerBrainList(Player player, ArrayList<Brain> brains) {
        DatabaseReference currentRef = database.getReference(gameCodeRef).child("Players").child(player.getUsername());
        currentRef.child("BrainList").setValue(brains);
    }

    /**
     * Gets all the brains saved in the database for the game, and saves them in a dataholder class.
     * @param dataholder: a dataholder to save the values into
     * */
    @Override
    public void getAllBrains(Dataholder dataholder) {
        database.getReference(gameCodeRef).child("Players").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Brain> brains = new ArrayList<>();
                for (DataSnapshot playersSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot brainSnapshot : playersSnapshot.getChildren()) {
                        for (DataSnapshot brain2Snapshot : brainSnapshot.getChildren()){
                            if (!(brain2Snapshot.getValue() instanceof String) && !(brain2Snapshot.getValue() instanceof Boolean)){
                                Brain brain = brain2Snapshot.getValue(Brain.class);
                                brains.add(brain);
                            }
                        }
                    }
                }
                dataholder.setBrains(brains);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG,"loadGamecode:onCancelled", error.toException());
            }

        });
    }

    /**
     *Sets the variable "StartGame" to true
     * */
    @Override
    public void setStartGame() {
        database.getReference(gameCodeRef).child("StartGame").setValue(true);
    }

    /**
     * Sets a listener for the variable "StartGame" and notifies the controller when its set to true.
     * TODO: Should call on the controller to start the game
     */
    @Override
    public void setStartGameChangedListener() {
        database.getReference(gameCodeRef).child("StartGame").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String stringValue = String.valueOf(snapshot.getValue());
                if (!stringValue.equals(null) && !stringValue.equals("null")){
                    Boolean value = (Boolean) snapshot.getValue();
                    if (value){
                        //TODO: Should call on the controller to start the game
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}
