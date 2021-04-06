package com.mygdx.game;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private DatabaseReference myRef;

    /**
     * Initialize the database by passing an URL to the database
     * Pointing to a "key" addressed by the path to store and write data whenever myRef is used.
     */
    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://brainstorming-1fa06-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("message");
    }

    /**
     * Output the value every time myRef is updated
     */
    @Override
    public void setOnValueChangedListener() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void setValueInDb(String target, String value) {
        myRef = database.getReference(target);
        myRef.setValue(value);
    }
}
