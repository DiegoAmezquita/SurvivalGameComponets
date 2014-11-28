package com.appmunki.survival.Components;


public class HealthComponent implements Component{

    public float current;
    public float max;
    public boolean alive;

    public HealthComponent(float current, float max){
        this.current = current;
        this.max = max;
        this.alive = true;
    }

    @Override
    public void toggle() {

    }
}
