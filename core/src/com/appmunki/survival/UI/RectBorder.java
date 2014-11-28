package com.appmunki.survival.UI;

import com.appmunki.survival.util.Util;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class RectBorder extends Image {

    Image rectInside;
    int borderSize;


    public RectBorder(){
        this(5,new Vector2(),new Vector2());


    }

    public RectBorder(int borderSize, Vector2 size, Vector2 position) {
        super(new TextureRegionDrawable(new TextureRegion(new Texture(Util.getPath() + "background.png"))));
        this.borderSize = borderSize;
        setColor(Color.WHITE);
        setSize(size.x, size.y);
        setPosition(position.x, position.y, Align.center);




        rectInside = new Image(getDrawable());
        rectInside.setColor(Color.BLACK);
        rectInside.setSize(size.x - borderSize * Util.factor, size.y - borderSize * Util.factor);
        rectInside.setPosition(position.x, position.y, Align.center);



    }

    @Override
    public void validate() {
        super.validate();

        rectInside.setSize(getWidth() - borderSize * Util.factor, getHeight() - borderSize * Util.factor);
        rectInside.setPosition(getX(Align.center), getY(Align.center), Align.center);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        rectInside.draw(batch, parentAlpha);
    }
}
