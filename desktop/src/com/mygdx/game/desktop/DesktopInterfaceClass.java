package com.mygdx.game.desktop;

import com.mygdx.game.FirebaseInterface;

public class DesktopInterfaceClass implements FirebaseInterface {

    @Override
    public void justSomeFunction() {
        System.out.println("AndroidInterface");
    }

    @Override
    public void setOnValueChangedListener() {

    }

    @Override
    public void setValueInDb(String target, String value) {

    }

}
