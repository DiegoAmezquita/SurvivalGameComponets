package com.appmunki.survival.Actions;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

public class CameraAction implements TweenAccessor<Camera> {
    public static final int POS_XY = 1;

    @Override
    public int getValues(Camera target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case POS_XY:
                returnValues[0] = target.position.x;
                returnValues[1] = target.position.y;
                return 2;

            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(Camera target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case POS_XY:
                target.position.set(newValues[0], newValues[1],target.position.z);
                break;

            default:
                assert false;
        }
    }
}