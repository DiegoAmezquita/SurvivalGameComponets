package com.appmunki.survival.Components;


public class MoveComponent implements Component{

    public float x;
    public float y;
    public float normalVelocity;
    public float maxVelocity;

    public MoveComponent(float x,float y,float normalVelocity,float maxVelocity){
        this.x = x;
        this.y = y;
        this.normalVelocity = normalVelocity;
        this.maxVelocity = maxVelocity;
    }

    @Override
    public void toggle() {

    }
}
