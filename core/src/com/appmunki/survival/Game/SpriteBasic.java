package com.appmunki.survival.Game;

import com.appmunki.survival.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class SpriteBasic extends Sprite {

    private float centerX;
    private float centerY;
    private Object userData;
    private boolean enabled = true;
    private boolean visible = true;

    protected float scale = 16.f;


    public SpriteBasic() {
    }

    public SpriteBasic(String nameFile) {
        this(new Texture(Gdx.files.internal(Util.getPath() + nameFile)));
    }

    public SpriteBasic(Texture texture) {
        super(texture);
        setSize(getRegionWidth() / scale, getRegionHeight() / scale);
    }


    @Override
    public void setX(float x) {
        super.setX(x);
        centerX = x + getWidth() / 2;
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        centerY = y + getHeight() / 2;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        centerX = x + getWidth() / 2;
        centerY = y + getHeight() / 2;
    }

    public void setCenterPosition(float x, float y) {
        setPosition(x - getWidth() / 2, y - getHeight() / 2);

    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }


    public void render(SpriteBatch batch, float dt) {
        draw(batch);
    }


    public void setUserData(Object userData) {
        this.userData = userData;
    }

    public Object getUserData() {
        return userData;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (!this.enabled) {
            setColor(Color.GRAY);
        } else {
            setColor(Color.WHITE);
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }
}


