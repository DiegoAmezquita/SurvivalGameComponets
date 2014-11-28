package com.appmunki.survival;

import com.appmunki.survival.Game.SpriteBasic;
import com.appmunki.survival.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class MainMenuScreen implements BaseScreen {

    MyGdxGame gdxGame;

    private OrthographicCamera camera;
    Viewport viewport;
    private Stage stage;

    Label labelConsole;
    ScrollPane scrollConsole;


    Skin defaultSkin;

    private TextButtonStyle textButtonStyle;
    private Label.LabelStyle labelStyle;
    private Label.LabelStyle labelConsoleStyle;

    private TextField.TextFieldStyle textFieldStyle;

    private BitmapFont font;

    TweenManager tweenManager;

    FPSLogger fpsLogger;
    SpriteBasic spriteMenuBackground;
    SpriteBatch batch;

    ImageButton buttonSound;
    ImageButton buttonMusic;


    public MainMenuScreen(final MyGdxGame gdxGame) {
        this.gdxGame = gdxGame;
        camera = new OrthographicCamera(Util.SCREEN_WIDTH, Util.SCREEN_HEIGHT);
        viewport = new FitViewport(Util.SCREEN_WIDTH, Util.SCREEN_HEIGHT, camera);
        stage = new Stage(viewport);
        batch = new SpriteBatch();
        tweenManager = new TweenManager();


        defaultSkin = gdxGame.assetManagerMenu.get(Util.getPath() + "uiskin.json",Skin.class);
        Gdx.input.setInputProcessor(stage);

        FileHandle fontFile = Gdx.files.internal(Util.getPath() + "font.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);

        int fontSize = (int) (40*(Util.SCREEN_WIDTH/960f));

        font = generator.generateFont(fontSize);


        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;


        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelConsoleStyle = new Label.LabelStyle();
        labelConsoleStyle.font = generator.generateFont(20);

        textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = generator.generateFont(23);


//        Texture textureMenuBackground = gdxGame.assetManagerMenu.get(Util.getPath() + "menu_background.png",Texture.class);
//        spriteMenuBackground = new SpriteBasic(textureMenuBackground,false);
//        spriteMenuBackground.setScale(0.4f*(Util.SCREEN_WIDTH/960f));
//        spriteMenuBackground.setCenterPosition(Util.SCREEN_WIDTH/2, Util.SCREEN_HEIGHT/2);


        createSinglePlayerMenu();


        createOptions();

        fpsLogger = new FPSLogger();
    }

    public void createSinglePlayerMenu(){
        Label labelTitle = new Label("Civil War", labelStyle);
        labelTitle.setPosition(Util.SCREEN_WIDTH / 2, Util.SCREEN_HEIGHT - 60, Align.center);
        stage.addActor(labelTitle);

        TextButton buttonStartGame = new TextButton("Start Game", textButtonStyle);
        buttonStartGame.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                log("Load Game");
                gdxGame.loadGameComponentsScreen();
            }
        });


        Table rootTable = new Table();
        rootTable.setFillParent(true);

        Table table = new Table();

        table.add(buttonStartGame).padTop(10);
        table.row();
        rootTable.add(table);

        stage.addActor(rootTable);

    }

    public void createOptions(){


        Tween.setCombinedAttributesLimit(4);

        Tween.registerAccessor(Actor.class, new TutorMessageAccessor());

        Texture textureSound = gdxGame.assetManagerMenu.get(Util.getPath() + "punch.png",Texture.class);

        buttonSound = new ImageButton(new TextureRegionDrawable(new TextureRegion(textureSound)));
        buttonSound.getCells().get(0).size(buttonSound.getWidth()*(Util.SCREEN_WIDTH/960f), buttonSound.getHeight()*(Util.SCREEN_WIDTH/960f));
        buttonSound.setPosition(35*(Util.SCREEN_WIDTH/960f), 35*(Util.SCREEN_WIDTH/960f), Align.center);
        stage.addActor(buttonSound);


        Texture textureMusic = gdxGame.assetManagerMenu.get(Util.getPath() + "rock.png",Texture.class);
        buttonMusic = new ImageButton(new TextureRegionDrawable(new TextureRegion(textureMusic)));
        buttonMusic.getCells().get(0).size(buttonMusic.getWidth()*(Util.SCREEN_WIDTH/960f), buttonMusic.getHeight()*(Util.SCREEN_WIDTH/960f));
        buttonMusic.setPosition(35*(Util.SCREEN_WIDTH/960f), 35*(Util.SCREEN_WIDTH/960f), Align.center);
        stage.addActor(buttonMusic);

        Texture textureOptions = gdxGame.assetManagerMenu.get(Util.getPath() + "background.png",Texture.class);
        ImageButton buttonShowOptions = new ImageButton(new TextureRegionDrawable(new TextureRegion(textureOptions)));
        buttonShowOptions.getCells().get(0).size(60*(Util.SCREEN_WIDTH/960f), 60*(Util.SCREEN_WIDTH/960f));
        buttonShowOptions.setPosition(35*(Util.SCREEN_WIDTH/960f), 35*(Util.SCREEN_WIDTH/960f), Align.center);
        buttonShowOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                tweenManager.killTarget(buttonSound);
                tweenManager.killTarget(buttonMusic);

                if (buttonSound.getX(Align.center) == 35*(Util.SCREEN_WIDTH/960f)) {
                    Tween.to(buttonSound, TutorMessageAccessor.POS_XY, 0.4f).target(35*(Util.SCREEN_WIDTH/960f), 100*(Util.SCREEN_WIDTH/960f)).ease(TweenEquations.easeOutCirc).start(tweenManager);
                    Tween.to(buttonMusic, TutorMessageAccessor.POS_XY, 0.4f).target(35*(Util.SCREEN_WIDTH/960f), 175*(Util.SCREEN_WIDTH/960f)).ease(TweenEquations.easeOutCirc).start(tweenManager);
                } else {
                    Tween.to(buttonSound, TutorMessageAccessor.POS_XY, 0.4f).target(35*(Util.SCREEN_WIDTH/960f), 35*(Util.SCREEN_WIDTH/960f)).ease(TweenEquations.easeOutCirc).start(tweenManager);
                    Tween.to(buttonMusic, TutorMessageAccessor.POS_XY, 0.4f).target(35*(Util.SCREEN_WIDTH/960f), 35*(Util.SCREEN_WIDTH/960f)).ease(TweenEquations.easeOutCirc).start(tweenManager);
                }
            }
        });
        stage.addActor(buttonShowOptions);
    }

    public void createPlayServiceMenu() {

        Label labelTitle = new Label("Play Services", labelStyle);
        labelTitle.setPosition(400 - labelTitle.getWidth() / 2, 460 - labelTitle.getHeight());
        stage.addActor(labelTitle);

//        login = Util.getString(gdxGame.getGameHelper(), "login");
//        logout = Util.getString(gdxGame.getGameHelper(), "logout");
//        String createRoom = Util.getString(gdxGame.getGameHelper(), "create_room");
//        String inviteFriends = Util.getString(gdxGame.getGameHelper(), "invite_friends");
//
//        if (gdxGame.getGameHelper() != null) {
//            gdxGame.getGameHelper().signOut();
//        }
//
//        buttonLogin = new TextButton(login, textButtonStyle);
//        buttonLogin.addListener(new ChangeListener() {
//            public void changed(ChangeEvent event, Actor actor) {
//                if (!gdxGame.getGameHelper().isSignedIn()) {
//                    gdxGame.getGameHelper().signIn();
//                } else {
//                    gdxGame.getGameHelper().signOut();
//                    validateStateSign();
//                }
//            }
//        });
//
//
//        TextButton buttonCreateRoom = new TextButton(createRoom, textButtonStyle);
//        buttonCreateRoom.addListener(new ChangeListener() {
//            public void changed(ChangeEvent event, Actor actor) {
//                log("Creating room");
//                gdxGame.getGameHelper().createRoom();
//            }
//        });
//
//        TextButton buttonInviteFriends = new TextButton(inviteFriends, textButtonStyle);
//        buttonInviteFriends.addListener(new ChangeListener() {
//            public void changed(ChangeEvent event, Actor actor) {
//                log("Inviting friends In development");
//                gdxGame.getGameHelper().inviteFriends();
//            }
//        });
//
//
//        labelConsole = new Label("Console", labelConsoleStyle);
//
//        TextButton buttonChatScreen = new TextButton("Open Chat", textButtonStyle);
//        buttonChatScreen.addListener(new ChangeListener() {
//            public void changed(ChangeEvent event, Actor actor) {
//                log("Open Chat");
//                gdxGame.loadChatScreen();
////                    gdxGame.getGameHelper().sendMessage("MENSAJE DE PRUEBA BIANCA");
//            }
//        });

        TextButton buttonLoadGame = new TextButton("Load Game", textButtonStyle);
        buttonLoadGame.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                log("Load Game");
                gdxGame.loadGameScreen();

            }
        });


        Table rootTable = new Table();
        rootTable.setFillParent(true);
//        rootTable.debug();

        Table table = new Table();
//        table.debug();

//        table.add(buttonLogin);
//        table.row();
        table.add(buttonLoadGame).padTop(10);
        table.row();
        rootTable.add(table);

        scrollConsole = new ScrollPane(labelConsole);
        scrollConsole.setSize(800, 100);
        scrollConsole.setPosition(0, 0);

        stage.addActor(scrollConsole);

        stage.addActor(rootTable);


    }

    public void validateStateSign() {
        System.out.println("isSignedIn " + gdxGame.getGameHelper().isSignedIn());

        if (gdxGame.getGameHelper().isSignedIn()) {
//            buttonLogin.setText(logout);
            log("Connected");
        } else {
//            buttonLogin.setText(login);
            log("No Connected");
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.getCamera().update();

//        spriteMenuBackground.render(batch,stage.getCamera().combined,delta);

        tweenManager.update(Gdx.graphics.getDeltaTime());

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();



//        Table.drawDebug(stage);

//        fpsLogger.log();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("RESIZING");


//        stage.getViewport().setWorldSize(width,height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {

    }


    @Override
    public void onPlayServiceEvent(Util.EVENT event, String data) {
        log("EVENTO RECIBIDO " + event);
        switch (event) {
            case SIGNIN_SUCCESS:
            case SIGNIN_FAIL:
                validateStateSign();
                break;
            case ROOM_CREATED_SUCCESS:
                log("Room created");
                break;
            case MESSAGE_RECEIVED:
                log(data);
                break;
            default:
                break;
        }
    }

    public void log(String textLog) {
//        labelConsole.setText(labelConsole.getText() + "\n" + textLog);
//        scrollConsole.scrollTo(0, 0, 0, 100000);

    }
}
