package com.mygdx.game;

public class CoreInterfaceClass implements FirebaseInterface {

    @Override
    public void justSomeFunction() {
        System.out.println("CoreInterface");
    }

    @Override
    public void setOnValueChangedListener() {

    }

    @Override
    public void setValueInDb(String target, String value) {

    }
}
