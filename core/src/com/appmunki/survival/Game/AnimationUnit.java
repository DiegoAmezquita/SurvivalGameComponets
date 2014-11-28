package com.appmunki.survival.Game;

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

    private boolean isRunning;

    public AnimationUnit(float frameDuration, Array<? extends TextureRegion> keyFrames) {
        super(frameDuration, keyFrames);
        animationElapsed = 0;
        actualSprite = super.getKeyFrame(animationElapsed);
        isRunning = true;
    }

    public void setAnimationListener(AnimationListener animationListener) {
        this.animationListener = animationListener;
    }

    public int getIndexFrame(TextureRegion textureRegion) {
        for (int i = 0; i < getKeyFrames().length; i++) {
            TextureRegion textureRegionTempo = getKeyFrames()[i];
            if (textureRegion == textureRegionTempo) {
                return i;
            }
        }
        return -1;
    }

    public TextureRegion animate(float delta) {
        if (!isRunning) {
            animationElapsed = 0;
        }


        if (animationElapsed == 0) {
            actualSprite = super.getKeyFrame(animationElapsed);
        }

        animationElapsed += delta;

        TextureRegion tempoSprite = super.getKeyFrame(animationElapsed);


        if (actualSprite != tempoSprite) {
            actualSprite = tempoSprite;
            if (animationListener != null) {
                animationListener.onFrameChanged(getIndexFrame(actualSprite));
            }
        }


        if (isAnimationFinished(animationElapsed)) {
            animationElapsed = 0;
            if (animationListener != null) {
                animationListener.onAnimationFinish();
            }

        } else if (animationElapsed == 0) {
            if (animationListener != null) {
                animationListener.onAnimationStart();
            }
        }


        return actualSprite;
    }

    public void stop() {
        isRunning = false;
    }

    public void start() {
        isRunning = true;
    }

}
