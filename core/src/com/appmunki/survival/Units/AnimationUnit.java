package com.appmunki.survival.Units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by diegoamezquita on 8/27/14.
 */
public class AnimationUnit extends Animation {


    float animationElapsed;
    AnimationListener animationListener;

    TextureRegion actualSprite;

    public AnimationUnit(float frameDuration, Array<? extends TextureRegion> keyFrames) {
        super(frameDuration, keyFrames);
        animationElapsed = 0;
        actualSprite = super.getKeyFrame(animationElapsed);
    }

    public void setAnimationListener(AnimationListener animationListener){
        this.animationListener = animationListener;
    }

    public TextureRegion animate(float delta){
        TextureRegion tempoSprite = super.getKeyFrame(animationElapsed);
        if(isAnimationFinished(animationElapsed)){
            animationElapsed = 0;
            if(animationListener!=null) {
                animationListener.onAnimationFinish();
            }

        }else if(animationElapsed==0){
            if(animationListener!=null) {
                animationListener.onAnimationStart();
            }
        }
        animationElapsed +=delta;

        if(actualSprite!=tempoSprite) {
            actualSprite = tempoSprite;
            if(animationListener!=null) {
                animationListener.onFrameChanged();
            }
        }

        return actualSprite;
    }

}
