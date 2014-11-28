package com.appmunki.survival.Game.Hud;

import com.appmunki.survival.UI.RectBorder;
import com.appmunki.survival.UI.ScrollPaneBorder;
import com.appmunki.survival.UI.TableBorder;
import com.appmunki.survival.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

public class BagHUD {

    ArrayList<Actor> arrayActors;
    boolean visible;
    GameHUDComponents gameHUD;

    static final float factor = (Util.SCREEN_WIDTH / 960f);

    public BagHUD(GameHUDComponents gameHud) {
        this.gameHUD = gameHud;
        arrayActors = new ArrayList<Actor>();
        visible = true;

        FileHandle fontFile = Gdx.files.internal(Util.getPath() + "font.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        BitmapFont font = generator.generateFont((int) (30 * factor));

        Label.LabelStyle textButtonStyle = new Label.LabelStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;


        Image backgroundPauseMenu = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Util.getPath() + "background.png"))));
        backgroundPauseMenu.setColor(0,0,0,0.4f);

        backgroundPauseMenu.setFillParent(true);
        backgroundPauseMenu.setSize(Util.SCREEN_WIDTH, Util.SCREEN_HEIGHT);
        backgroundPauseMenu.setPosition(Util.SCREEN_WIDTH/2, Util.SCREEN_HEIGHT/2,Align.center);
        backgroundPauseMenu.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setVisible(false);
                return false;
            }
        });
        gameHUD.addActor(backgroundPauseMenu);
        arrayActors.add(backgroundPauseMenu);


        Table scrollTable = new Table();

        for (int i = 1; i <= 10; i++) {
            RectBorder rectBorderItem = new RectBorder(5, new Vector2(), new Vector2());
            scrollTable.add(rectBorderItem).minWidth(100 * factor).minHeight(100 * factor).pad(5);

            if (i % 3 == 0 && i != 0) {
                scrollTable.row();
            }
        }

        ScrollPaneBorder scrollPane = new ScrollPaneBorder(scrollTable);

        TableBorder tableBag = new TableBorder();
        tableBag.setSize(Util.SCREEN_WIDTH * 0.8f, Util.SCREEN_HEIGHT * 0.8f);
        tableBag.setPosition(Util.SCREEN_WIDTH / 2, Util.SCREEN_HEIGHT / 2, Align.center);
        tableBag.debug();
        gameHUD.addActor(tableBag);
        arrayActors.add(tableBag);

        Image imageTest = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Util.getPath() + "background.png"))));
        imageTest.setColor(Color.YELLOW);

        tableBag.add(scrollPane).width((tableBag.getWidth() / 2) - 10).height(tableBag.getHeight() - 20).padLeft(10);
        tableBag.add(imageTest).width((tableBag.getWidth() / 2) - 10).expandY().padRight(10);

    }



    public void setVisible(boolean visible) {
        this.visible = visible;
        for (Actor actor : arrayActors) {
            actor.setVisible(visible);
        }
    }

    public boolean isVisible() {
        return visible;
    }


}
