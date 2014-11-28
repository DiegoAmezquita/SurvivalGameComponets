package com.appmunki.survival.Components;


public class KeyboardInputComponent implements Component {

    public int keyLeft;
    public int keyRight;
    public int keyUp;
    public int keyDown;

    public int keySpeed;

    public KeyboardInputComponent(int keyLeft, int keyRight, int keyUp, int keyDown, int keySpeed) {
        this.keyLeft = keyLeft;
        this.keyRight = keyRight;
        this.keyUp = keyUp;
        this.keyDown = keyDown;
        this.keySpeed = keySpeed;
    }

    @Override
    public void toggle() {

    }
}


