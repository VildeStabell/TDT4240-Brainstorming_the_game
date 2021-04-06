package com.mygdx.game;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static android.content.ContentValues.TAG;


public class AndroidInterfaceClass implements FirebaseInterface {

    private final FirebaseDatabase database;
    private DatabaseReference myRef;

    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://brainstorming-1fa06-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("message");
    }

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
