package com.appmunki.survival.Components;


import box2dLight.Light;

public class LightComponent implements Component {

    public Light light;


    public LightComponent(Light light) {
        this.light = light;
    }


    @Override
    public void toggle() {
        light.setActive(!light.isActive());
    }
}
