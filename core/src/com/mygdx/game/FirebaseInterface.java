package com.mygdx.game;

public interface FirebaseInterface {

    void setOnValueChangedListener();
    void setValueInDb(String target, String value);
}
