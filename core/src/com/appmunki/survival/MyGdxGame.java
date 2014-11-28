package com.appmunki.survival;

import com.appmunki.survival.Game.GameScreen;
import com.appmunki.survival.Game.GameScreenComponents;
import com.appmunki.survival.util.Util;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MyGdxGame extends Game implements ApplicationListener {

    private GameHelper mGameHelper;
    BaseScreen actualScreen;

    MainMenuScreen mainMenu;
    GameScreen gameScreen;

    GameScreenComponents gameScreenComponents;

    public AssetManager assetManagerMenu;

    boolean menuLoaded = false;

//    String path = "C:/Users/Seidan/Dropbox/GameWar/SurvivalGame/android/assets/";
    String path = "/Users/diegoamezquita/Dropbox/GameWar/SurvivalGame/android/assets/";
//    String path = "";

    public MyGdxGame(GameHelper gameHelper) {
        this.mGameHelper = gameHelper;
        loadMenuAssets();
    }

    public MyGdxGame() {
        loadMenuAssets();
    }

    public void loadSplashScreen(){

    }

    public void loadMenuAssets(){
        assetManagerMenu = new AssetManager();

        System.out.println("Init asset manager");

        assetManagerMenu.load(path+"uiskin.json", Skin.class);

        assetManagerMenu.load(path+"menu_background.png", Texture.class);
        assetManagerMenu.load(path+"background.png", Texture.class);
        assetManagerMenu.load(path+"punch.png", Texture.class);
        assetManagerMenu.load(path+"rock.png", Texture.class);

        assetManagerMenu.load(path+"map/map.png", Texture.class);
        assetManagerMenu.load(path+"map/town.png", Texture.class);

        System.out.println("end asset manager");

    }



    @Override
    public void render() {
        super.render();

        if(assetManagerMenu.update()) {
            if(!menuLoaded){
                initialize();
                loadMenuScreen();
                menuLoaded = true;
            }
//            System.out.println("TERMINO DE CARGAR LOS ASSETS");
        }else{
            float progress = assetManagerMenu.getProgress();
            System.out.println("RENDERING "+progress);
        }


    }

    public void initialize(){
        mainMenu = new MainMenuScreen(this);
    }

    @Override
    public void create() {

    }

    public void loadMenuScreen(){
        mainMenu.resume();
        actualScreen = mainMenu;
        setScreen(mainMenu);
    }

    public void loadGameScreen() {
        if (gameScreen == null) {
            gameScreen = new GameScreen(this);
        }
        gameScreen.resume();
        setScreen(gameScreen);
        actualScreen = gameScreen;
    }

    public void loadGameComponentsScreen() {

        if (gameScreenComponents == null) {
            gameScreenComponents = new GameScreenComponents(this);
        }
        gameScreenComponents.resume();
        setScreen(gameScreenComponents);
        actualScreen = gameScreenComponents;
    }


    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public GameHelper getGameHelper() {
        return mGameHelper;
    }


    public void onRoomEvent(Util.EVENT event, String data) {


//        if (event == Util.EVENT.ROOM_JOINED) {
//            float delay = 5; // seconds
//            System.out.println("DEBE CARGAR LA PANTALLA DE CHAT 1");
//            Timer.schedule(new Timer.Task() {
//                @Override
//                public void run() {
//                    System.out.println("DEBE CARGAR LA PANTALLA DE CHAT 2");
//                    loadChatScreen();
//                }
//            }, delay);
//
//
//
//        } else
        if (actualScreen != null)
            actualScreen.onPlayServiceEvent(event,data);
    }
}