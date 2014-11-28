package com.appmunki.survival.UI;

import com.appmunki.survival.util.Rect;
import com.appmunki.survival.util.Util;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ScrollPaneBorder extends ScrollPane {

    RectBorder rectBorder;


    public ScrollPaneBorder(Actor actor) {
        super(actor);

        rectBorder = new RectBorder();
    }


    @Override
    public void validate() {
        super.validate();

        rectBorder.setSize(getWidth() - 5 * Util.factor, getHeight() - 5 * Util.factor+10);
        rectBorder.setPosition(getX(Align.center), getY(Align.center), Align.center);
        rectBorder.validate();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        rectBorder.draw(batch,parentAlpha);
        super.draw(batch, parentAlpha);



    }
}
