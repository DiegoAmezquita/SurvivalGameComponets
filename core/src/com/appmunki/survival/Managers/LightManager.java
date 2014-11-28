package com.appmunki.survival.Managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.RayHandler;

/**
 * Created by diegoamezquita on 8/7/14.
 */
public class LightManager extends RayHandler {

    Color ambientLight;


    public LightManager(World world) {
        super(world);
        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);
        ambientLight = new Color(0.0f, 0.0f, 0.0f, 1.0f);
        setCulling(true);
        setBlurNum(1);
    }

    public void setAmbientLightColor(Color color){
        this.ambientLight = color;
    }

    public void updateLights(Matrix4 matrix) {
        setAmbientLight(ambientLight);
        setCombinedMatrix(matrix);
        updateAndRender();
    }
}
