package com.appmunki.survival;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import aurelienribon.tweenengine.TweenAccessor;

public class TutorMessageAccessor implements TweenAccessor<Actor> {
    public static final int POS_XY = 1;

    @Override
    public int getValues(Actor target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case POS_XY:
                returnValues[0] = target.getX(Align.center);
                returnValues[1] = target.getY(Align.center);
                return 2;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(Actor target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case POS_XY:
                target.setPosition(newValues[0], newValues[1],Align.center);
                break;
            default:
                assert false;
        }
    }
}