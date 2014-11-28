package com.appmunki.survival.Units;

import com.appmunki.survival.Game.SpriteBasic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

/**
 * Created by diegoamezquita on 8/23/14.
 */
public class HealthBar {


    ShapeRenderer shapeRenderer;

    float pX;
    float pY;
    float width;
    float height;
    Color color;
    float health;

    boolean visible;
    SpriteBasic parent;

    float elapsedFromLastChange;

    public HealthBar(SpriteBasic parent) {
        this.parent = parent;
        this.pX = 0;
        this.pY = 0;
        this.width = 1f;
        this.health = width;
        this.height = 0.1f;
        this.color = Color.GREEN;
        this.visible = false;
        elapsedFromLastChange = 0;
        shapeRenderer = new ShapeRenderer();
    }


    public void setColor(Color color){
        this.color = color;
    }

    public void setHealth(float health){
        elapsedFromLastChange = 0;
            setVisible(true);

        this.health = health>=0?health:0;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    public void setPosition(float pX, float pY){
        this.pX = pX-width/2;
        this.pY = pY+parent.getHeight()/1.8f;
    }

    public void render(Matrix4 matrix,float dt) {
        if(visible) {
            if(elapsedFromLastChange>2){
                if(health>=1) {
                    setVisible(false);
                }
                elapsedFromLastChange = 0;
            }
            elapsedFromLastChange+=dt;
            setPosition(parent.getCenterX(),parent.getCenterY());
            shapeRenderer.setProjectionMatrix(matrix);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(color.r, color.g, color.b, 1);
            shapeRenderer.rect(pX, pY, health, height);
            shapeRenderer.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            Gdx.gl.glLineWidth(2);
            shapeRenderer.setColor(0, 0, 0, 1);
            shapeRenderer.rect(pX, pY, width, height);
            shapeRenderer.end();
            Gdx.gl.glLineWidth(1);
        }
    }
}
