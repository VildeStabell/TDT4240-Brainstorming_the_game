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
 * The database is a NoSQL database which the data is stored as a key-value pair
 *
 * database: the realtime database in Firebase
 * myRef: Access location in database for read/write
 *
 * Need to implement relevant methods according to the Brainstorming game
 */
public class AndroidInterfaceClass implements FirebaseInterface {

    private final FirebaseDatabase database;
    private String gameCodeRef;


    /**
     * Initialize the database by passing an URL to the database
     * Pointing to a "key" addressed by the path to store and write data whenever myRef is used.
     */
    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://brainstorming-1fa06-default-rtdb.europe-west1.firebasedatabase.app/");
    }

    public void increaseGameCode(int i){
        database.getReference("gameCode").setValue(i+1);
    }

    @Override
    public void getGameCodeFromDB(final Dataholder dataholder){
        database.getReference("gameCode").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = String.valueOf(snapshot.getValue());
                dataholder.setGameCode(value);
                gameCodeRef = dataholder.getGameCode();
                increaseGameCode(Integer.parseInt(value));
                initializeGameRoom(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG,"loadGamecode:onCancelled", error.toException());
            }

        });
    }

    private void initializeGameRoom(String value) {
        DatabaseReference game = database.getReference(value);
        game.child("NumberOfPlayers").setValue(0);
        game.child("PlayersDoneBrainstorming").setValue(0);
        game.child("PlayersDoneEliminating").setValue(0);
    }


    @Override
    public void setGameCodeRef(String gameCodeRef){
        this.gameCodeRef = gameCodeRef;
        //mDatabase = database.getInstance().getReference();
        //mDatabase.child(gameCodeRef).push();
    }


    /**
     * Output the value every time myRef is updated
     */
    @Override
    public void setOnValueChangedListener() {
        /**gameCodeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/
    }

    @Override
    public void setValueInDb(String target, String value) {
        DatabaseReference currentRef = database.getReference(target);
        currentRef.setValue(value);
    }

    @Override
    public void writeNewPlayer(Player player){
        if (gameCodeRef == null){
            throw new IllegalStateException("GameRoom is not set");
        }
        DatabaseReference currentRef = database.getReference(gameCodeRef);
        currentRef.child(player.getUsername()).setValue(player.getUsername());
        updateNrPlayers("NumberOfPlayers");
        System.out.println("Called on updateNrPlayers");
    }

    private void updateNrPlayers(String target) {
        database.getReference(gameCodeRef).child(target).addListenerForSingleValueEvent(new ValueEventListener()
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            String value = String.valueOf(snapshot.getValue());
            increaseDBValue(target,Integer.parseInt(value));
            System.out.println("Updated "+target);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w(TAG,"loadGamecode:onCancelled", error.toException());
        }

    });
    }

    private void increaseDBValue(String target, int i){
        database.getReference(gameCodeRef).child(target).setValue(i+1);
    }

    @Override
    public void setPlayerDoneBrainstorming(Player player, boolean value){
        DatabaseReference currentRef = database.getReference(gameCodeRef).child(player.getUsername());
        currentRef.child("DoneBrainstorming").setValue(value);
        if(value){
            updateNrPlayers("PlayersDoneBrainstorming");
        }
    }


    @Override
    public void setPlayerDoneEliminating(Player player, boolean value) {
        DatabaseReference currentRef = database.getReference(gameCodeRef).child(player.getUsername());
        currentRef.child("DoneEliminating").setValue(value);
        if(value){
            updateNrPlayers("PlayersDoneEliminating");
        }

    }


    @Override
    public void setPlayerBrainList(Player player, ArrayList<Brain> brains) {
        DatabaseReference currentRef = database.getReference(gameCodeRef).child(player.getUsername());
        currentRef.child("BrainList").setValue(brains);
    }

    @Override
    public void getAllBrains(Dataholder dataholder, Player player) {
    //TODO: get all brains, rn this only get one
        database.getReference(gameCodeRef).child(player.getUsername()).child("BrainList").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Brain> brains = new ArrayList<>();
                for (DataSnapshot brainSnapshot : snapshot.getChildren()){
                    Brain brain = brainSnapshot.getValue(Brain.class);
                    brains.add(brain);
                }
                dataholder.setBrains(brains);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG,"loadGamecode:onCancelled", error.toException());
            }

        });
    }

    @Override
    public String getGameCodeRef() {
        return this.gameCodeRef;
    }


}
