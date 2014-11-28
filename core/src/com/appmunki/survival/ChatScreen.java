package com.appmunki.survival;

import com.appmunki.survival.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by diegoamezquita on 7/31/14.
 */
public class ChatScreen implements BaseScreen {

    MyGdxGame gdxGame;

    private OrthographicCamera camera;
    Viewport viewport;
    private Stage stage;



    Label labelConsole;
    TextField fieldChat;

    Skin defaultSkin;

    private TextButtonStyle textButtonStyle;
    private Label.LabelStyle labelStyle;
    private Label.LabelStyle labelConsoleStyle;

    private TextField.TextFieldStyle textFieldStyle;

    private BitmapFont font;


    FPSLogger fpsLogger;


    public ChatScreen(final MyGdxGame gdxGame) {
        this.gdxGame = gdxGame;
        camera = new OrthographicCamera(800, 480);
        viewport = new FitViewport(800, 480, camera);
        stage = new Stage(viewport);


        defaultSkin = new Skin(Gdx.files.internal(Util.getPath() + "uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        FileHandle fontFile = Gdx.files.internal(Util.getPath() + "font.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        font = generator.generateFont(40);


        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;


        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelConsoleStyle = new Label.LabelStyle();
        labelConsoleStyle.font = generator.generateFont(20);

        textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = generator.generateFont(23);


        createChatElements();


        fpsLogger = new FPSLogger();
    }

    public void createChatElements() {

        TextButton buttonChat = new TextButton("Send", textButtonStyle);
        buttonChat.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                sendMessage();
            }
        });

        labelConsole = new Label("Console", labelConsoleStyle);
        labelConsole.setAlignment(Align.left);

        fieldChat = new TextField("", defaultSkin);

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.debug();

        Table table = new Table();
        table.debug();

        table.add(fieldChat).width(600).height(50);
        table.add(buttonChat).padLeft(20);
        table.row();
        table.add(new ScrollPane(labelConsole)).height(400).width(600);

        rootTable.add(table);

        stage.addActor(rootTable);


    }

    public void sendMessage(){
        String message = fieldChat.getText().trim();
        if(!message.isEmpty()){
            log("Me: "+message);
            gdxGame.getGameHelper().sendUnreliableMessage(message);
            fieldChat.setText("");
        }
    }




    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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

    }

    @Override
    public void dispose() {

    }


    @Override
    public void onPlayServiceEvent(Util.EVENT event, String data) {

        switch (event) {
            case MESSAGE_RECEIVED:
                log("Playera: "+data);
                break;
            default:
                log("Event: "+event);
                break;
        }
    }

    public void log(String textLog) {
        labelConsole.setText(labelConsole.getText() + "\n" + textLog);


    }
}
