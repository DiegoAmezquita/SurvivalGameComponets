package com.appmunki.survival.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;


public class Rect {


    ShapeRenderer shapeRenderer;

    float pX;
    float pY;
    float width;
    float height;
    Color color;

    boolean visible;

    public Rect(float pX, float pY, float width, float height, Color color) {
        this.pX = pX-1;
        this.pY = pY-1;
        this.width = width;
        this.height = height;
        this.color = color;
        this.visible = true;
        shapeRenderer = new ShapeRenderer();
    }

    public void setAlpha(float alpha){
        color.a = alpha;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    public void setPosition(float pX, float pY){
        this.pX = pX-1;
        this.pY = pY-1;
    }

    public void setSize(float width,float height){
        this.width = width;
        this.height = height;
    }

    public float getHeight(){
        return height;
    }

    public void setHeight(float height){
        this.height = height;
    }

    public void render(Matrix4 matrix) {
        if(visible) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.setProjectionMatrix(matrix);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(color.r, color.g, color.b, 0.5f);
            shapeRenderer.rect(pX, pY, width, height);
            shapeRenderer.end();
            Gdx.gl.glLineWidth(1);
        }
    }
}
