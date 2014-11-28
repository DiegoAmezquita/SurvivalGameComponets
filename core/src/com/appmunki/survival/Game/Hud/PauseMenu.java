package com.appmunki.survival.Game.Hud;

import com.appmunki.survival.Game.Hud.GameHUDComponents;
import com.appmunki.survival.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

/**
 * Created by diegoamezquita on 9/25/14.
 */
public class PauseMenu {

    ArrayList<Actor> arrayActors;
    boolean visible;
    GameHUDComponents gameHUD;

    static final float factor = (Util.SCREEN_WIDTH/960f);

    public PauseMenu(GameHUDComponents gameHud){
        this.gameHUD = gameHud;
        arrayActors = new ArrayList<Actor>();
        visible = true;

        FileHandle fontFile = Gdx.files.internal(Util.getPath() + "font.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        BitmapFont font = generator.generateFont((int)(30*factor));


        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;



        Image backgroundPauseMenu = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Util.getPath() + "background.png"))));
        backgroundPauseMenu.setColor(0, 0, 0, 0.3f);
        backgroundPauseMenu.setSize(Util.SCREEN_WIDTH, Util.SCREEN_HEIGHT);
        backgroundPauseMenu.setPosition(0,0);
        gameHUD.addActor(backgroundPauseMenu);
        arrayActors.add(backgroundPauseMenu);


        Image backgroundMenu = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Util.getPath() + "background.png"))));
        backgroundMenu.setColor(Color.BLACK);
        backgroundMenu.setSize(300*factor, 200*factor);
        backgroundMenu.setPosition(Util.SCREEN_WIDTH / 2, Util.SCREEN_HEIGHT / 2, Align.center);
        gameHUD.addActor(backgroundMenu);
        arrayActors.add(backgroundMenu);

        Table tableButtons = new Table();
        tableButtons.setFillParent(true);

        TextButton labelResume = new TextButton("Resume", textButtonStyle);
//        labelResume.setCenterPosition(Util.SCREEN_WIDTH/2, Util.SCREEN_HEIGHT/2);
        labelResume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setVisible(false);
            }
        });

        TextButton labelNewGame = new TextButton("New Game", textButtonStyle);
//        labelNewGame.setCenterPosition(Util.SCREEN_WIDTH/2, Util.SCREEN_HEIGHT/2);
        labelNewGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                gameHUD.gameManager.restartGame();
            }
        });

        TextButton labelExit = new TextButton("Exit", textButtonStyle);
//        labelExit.setCenterPosition(Util.SCREEN_WIDTH/2, Util.SCREEN_HEIGHT/2);
        labelExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                gameHUD.gameManager.exitToMenu();
            }
        });

        tableButtons.add(labelResume);
        tableButtons.row();
        tableButtons.add(labelNewGame);
        tableButtons.row();
        tableButtons.add(labelExit);
        tableButtons.row();
        arrayActors.add(tableButtons);
        gameHud.addActor(tableButtons);

    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        for (Actor actor:arrayActors){
            actor.setVisible(visible);
        }
    }

    public boolean isVisible() {
        return visible;
    }


}
