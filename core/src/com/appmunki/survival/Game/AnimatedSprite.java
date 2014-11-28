package com.appmunki.survival.Game;

import com.appmunki.survival.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedSprite extends SpriteBasic {

    protected AnimationUnit animationLeft;
    protected AnimationUnit animationRight;
    protected AnimationUnit animationUp;
    protected AnimationUnit animationDown;

    protected AnimationUnit actualAnimation;


    public AnimatedSprite(String file, String left, String right, String up, String down) {
        super(new Texture(Gdx.files.internal(Util.getPath() + "background.png")));
        TextureAtlas spriteSheet = new TextureAtlas(Util.getPath() + file);

        animationLeft = new AnimationUnit(0.25f, spriteSheet.createSprites(left));
        animationRight = new AnimationUnit(0.25f, spriteSheet.createSprites(right));
        animationUp = new AnimationUnit(0.25f, spriteSheet.createSprites(up));
        animationDown = new AnimationUnit(0.25f, spriteSheet.createSprites(down));


        TextureRegion textureRegionInit = animationDown.getKeyFrame(0, true);

        setRegion(textureRegionInit);

        actualAnimation = animationDown;
    }

    @Override
    public void setRegion(TextureRegion region) {
        super.setRegion(region);
        setSize(region.getRegionWidth() / scale, region.getRegionHeight() / scale);

    }

    public void changeToLeft() {
        actualAnimation = animationLeft;
    }

    public void changeToRight() {
        actualAnimation = animationRight;
    }

    public void changeToUp() {
        actualAnimation = animationUp;
    }

    public void changeToDown() {
        actualAnimation = animationDown;
    }

    @Override
    public void render(SpriteBatch batch, float dt) {
        setRegion(actualAnimation.animate(dt));
        super.render(batch, dt);
    }

    public void startAnimation(){
        actualAnimation.start();
    }

    public void stopAnimation() {
        actualAnimation.stop();
    }
}
