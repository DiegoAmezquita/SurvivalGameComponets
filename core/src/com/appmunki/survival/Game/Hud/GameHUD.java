package com.appmunki.survival.Game.Hud;

import com.appmunki.survival.Game.GameScreen;
import com.appmunki.survival.Game.Player;
import com.appmunki.survival.TutorMessageAccessor;
import com.appmunki.survival.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;


public class GameHUD extends Stage {


    GameScreen gameManager;

    Label labelMatchTime;

    float secondsElapsed;
    int secondsToShow;
    int minutesToShow;
    String timeToShow;

    TweenManager tweenManager;
    Label.LabelStyle labelStyle;
    TextButton.TextButtonStyle textButtonStyle;

    Label labelGameOver;


    PauseMenu pauseMenu;


    SpriteBatch batch;

    static final float factor = (Util.SCREEN_WIDTH/960f);

    private Touchpad touchpad;
    private Player player;
    private Skin defaultSkin;

    public GameHUD(final GameScreen gameManager) {
        this.gameManager = gameManager;

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
        defaultSkin.add("touchBackground", new Texture(Util.getPath() + "onscreen_control_base.png"));
        defaultSkin.add("touchKnob", new Texture(Util.getPath() + "onscreen_control_knob.png"));
        Touchpad.TouchpadStyle dPadStyle = new Touchpad.TouchpadStyle();
        Drawable dPadBackground = defaultSkin.getDrawable("touchBackground");
        Drawable dPadKnob = defaultSkin.getDrawable("touchKnob");
        dPadStyle.background = dPadBackground;
        dPadStyle.knob = dPadKnob;

        dPadStyle.knob.setMinWidth(dPadStyle.knob.getMinWidth()*factor);
        dPadStyle.knob.setMinHeight(dPadStyle.knob.getMinHeight()*factor);

        touchpad = new Touchpad(10, dPadStyle);
        touchpad.setSize(touchpad.getWidth()*factor,touchpad.getHeight()*factor);

        touchpad.setPosition(50*factor, 50*factor);
        addActor(touchpad);

        TextButton buttonShoot = new TextButton("BAG", defaultSkin);
        buttonShoot.setSize(100*factor, 50*factor);
        buttonShoot.setPosition(Util.SCREEN_WIDTH-100*factor, 120*factor, Align.center);
        buttonShoot.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showPauseMenu();
            }
        });
        addActor(buttonShoot);

        TextButton buttonLantern = new TextButton("Lantern", defaultSkin);
        buttonLantern.setSize(100*factor, 50*factor);
        buttonLantern.setPosition(Util.SCREEN_WIDTH-100*factor, 50*factor, Align.center);
        buttonLantern.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameManager.toggleLantern();
            }
        });
        addActor(buttonLantern);


        Tween.setCombinedAttributesLimit(4);

        Tween.registerAccessor(Actor.class, new TutorMessageAccessor());

//        pauseMenu = new PauseMenu(this);
//        pauseMenu.setVisible(false);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Vector2 getTouchPadPercentajes() {
        return new Vector2(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
    }

    public void showPauseMenu() {
        pauseMenu.setVisible(true);
    }

    public void createGameOverScreen() {

        if (labelGameOver == null) {
            labelGameOver = new Label("GAME OVER", labelStyle);
            labelGameOver.setPosition(Util.SCREEN_WIDTH/2, Util.SCREEN_HEIGHT/2, Align.center);
            addActor(labelGameOver);
        }
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