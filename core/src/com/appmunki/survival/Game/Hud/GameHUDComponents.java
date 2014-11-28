package com.appmunki.survival.Game.Hud;

import com.appmunki.survival.Components.BagComponent;
import com.appmunki.survival.Components.LightComponent;
import com.appmunki.survival.Framework.Entity;
import com.appmunki.survival.Framework.EntityManager;
import com.appmunki.survival.Game.GameScreenComponents;
import com.appmunki.survival.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import aurelienribon.tweenengine.TweenManager;


public class GameHUDComponents extends Stage {


    GameScreenComponents gameManager;

    Label labelMatchTime;

    float secondsElapsed;
    int secondsToShow;
    int minutesToShow;
    String timeToShow;

    TweenManager tweenManager;
    Label.LabelStyle labelStyle;
    TextButton.TextButtonStyle textButtonStyle;

    PauseMenu pauseMenu;

    BagHUD bagHUD;

    SpriteBatch batch;

    static final float factor = (Util.SCREEN_WIDTH/960f);


    private Skin defaultSkin;

    Table tableButtons;

    EntityManager entityManager;
    Entity player;

    public GameHUDComponents(GameScreenComponents gameManager, EntityManager entityManager,Entity player) {
        this.gameManager = gameManager;
        this.entityManager = entityManager;
        this.player = player;

        tweenManager = new TweenManager();

        batch = new SpriteBatch();

        OrthographicCamera camera = new OrthographicCamera(Util.SCREEN_WIDTH, Util.SCREEN_HEIGHT);
        camera.position.set(Util.SCREEN_WIDTH/2, Util.SCREEN_HEIGHT/2, camera.position.z);
        FitViewport viewport = new FitViewport(Util.SCREEN_WIDTH, Util.SCREEN_HEIGHT, camera);

        setViewport(viewport);
        FileHandle fontFile = Gdx.files.internal(Util.getPath() + "font.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        BitmapFont font = generator.generateFont((int)(30*factor));

        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;

        labelMatchTime = new Label("Time: 0:00", labelStyle);
        labelMatchTime.setPosition(Util.SCREEN_WIDTH/2, 510*factor, Align.center);
        addActor(labelMatchTime);


        defaultSkin = new Skin(Gdx.files.internal(Util.getPath() + "uiskin.json"));


        tableButtons = new Table();
        tableButtons.setSize(100*factor,Util.SCREEN_HEIGHT/2);
        tableButtons.setPosition(Util.SCREEN_WIDTH-100*factor, Util.SCREEN_HEIGHT/4, Align.center);

        addActor(tableButtons);


        createButtonsByItems();

        pauseMenu = new PauseMenu(this);
        pauseMenu.setVisible(false);

        bagHUD = new BagHUD(this);
        bagHUD.setVisible(false);
    }

    public void createButtonsByItems(){

        final LightComponent lightComponent = entityManager.getComponent(player.id, LightComponent.class);
        if(lightComponent!=null){
            TextButton buttonLantern = new TextButton("Lantern", defaultSkin);
            buttonLantern.setPosition(Util.SCREEN_WIDTH-100*factor, 50*factor, Align.center);
            buttonLantern.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                  lightComponent.toggle();
                }
            });
            tableButtons.add(buttonLantern).padTop(10).minSize(100 * factor, 50 * factor).padBottom(10);
            tableButtons.row();
        }

        final BagComponent bagComponent = entityManager.getComponent(player.id, BagComponent.class);

        if(bagComponent!=null) {

            TextButton buttonBag = new TextButton("BAG", defaultSkin);
            buttonBag.setPosition(Util.SCREEN_WIDTH - 100 * factor, 120 * factor, Align.center);
            buttonBag.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    showBagHUD();
                }
            });


            tableButtons.add(buttonBag).minSize(100 * factor, 50 * factor);

        }


    }





    public void showPauseMenu() {
        pauseMenu.setVisible(true);
    }

    public void showBagHUD() {
        bagHUD.setVisible(true);
    }


    @Override
    public void draw() {

        super.draw();

        tweenManager.update(Gdx.graphics.getDeltaTime());

    }

    private void updateTimeMatch() {
        secondsElapsed += Gdx.graphics.getDeltaTime();
        secondsToShow = (int) secondsElapsed;
        minutesToShow = secondsToShow / 60;
        secondsToShow = secondsToShow - (minutesToShow * 60);

        if (secondsToShow < 10) {
            timeToShow = minutesToShow + ":0" + secondsToShow;
        } else {
            timeToShow = minutesToShow + ":" + secondsToShow;
        }

        labelMatchTime.setText("Time: "+timeToShow);

        labelMatchTime.setPosition(Util.SCREEN_WIDTH / 2, 510*factor, Align.center);
    }



    public void resetButtons() {
        secondsElapsed = 0;
        pauseMenu.setVisible(false);
    }
}