package com.appmunki.survival.Actions;

import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

public class SpriteAction implements TweenAccessor<Sprite> {
    public static final int POS_XY = 1;
    public static final int ALPHA = 2;
    public static final int SIZE_Y = 3;

    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case POS_XY:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 2;
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 2;
            case SIZE_Y:
                returnValues[0] = target.getHeight();
                return 3;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case POS_XY:
                target.setCenter(newValues[0], newValues[1]);
                break;
            case ALPHA:
                target.setAlpha(newValues[0]);
                break;
            case SIZE_Y:
                target.setSize(target.getWidth(),newValues[0]);
                break;
            default:
                assert false;
        }
    }
}