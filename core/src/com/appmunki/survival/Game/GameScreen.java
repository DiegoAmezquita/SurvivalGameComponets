package com.appmunki.survival.Game;

import com.appmunki.survival.BaseScreen;
import com.appmunki.survival.Game.Hud.GameHUD;
import com.appmunki.survival.Managers.LightManager;
import com.appmunki.survival.MyGdxGame;
import com.appmunki.survival.Actions.SpriteAction;
import com.appmunki.survival.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import box2dLight.Light;
import box2dLight.PointLight;


public class GameScreen implements BaseScreen {

    MyGdxGame gdxGame;

    private Viewport viewport;
    public Stage stage;

    public boolean gameOver;

    public GameHUD gameHUD;


    private OrthogonalTiledMapRenderer tiledMapRender;
    MapProperties mapProperties;
    int mapWidth;
    int mapHeight;
    float minCameraX;
    float maxCameraX;
    float minCameraY;
    float maxCameraY;

    LightManager lightManager;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    SpriteBatch batch;

    TweenManager tweenManager;

    private FPSLogger fpsLogger;
    OrthographicCamera camera;

    Player player;
    Light lightPlayer;



    public GameScreen(MyGdxGame gdxGame) {

        fpsLogger = new FPSLogger();

        this.gdxGame = gdxGame;
        camera = new OrthographicCamera(Util.SCREEN_WIDTH, Util.SCREEN_HEIGHT);
        camera.zoom = 1.5f;
        float divisor = Util.SCREEN_HEIGHT / 12;
        viewport = new FitViewport(Util.SCREEN_WIDTH / divisor, 12, camera);
        stage = new Stage(viewport);
        batch = new SpriteBatch();

        gameHUD = new GameHUD(this);
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();

        tweenManager = new TweenManager();
        Tween.setCombinedAttributesLimit(4);
        Tween.registerAccessor(Sprite.class, new SpriteAction());

        loadMap();


        player = new Player(true, "player.txt", world);
        player.setNewPosition(21, 49);
        player.setGameManager(this);
        player.shouldRescale = true;

        gameHUD.setPlayer(player);


        lightManager = new LightManager(world);
        lightManager.setAmbientLightColor(new Color(0.4f, 0.4f, 0.4f, 0.5f));

        lightPlayer = new PointLight(lightManager, 3600, Color.WHITE, 8, 0, 0);
        lightPlayer.setStaticLight(false);
        lightPlayer.setColor(0.3f, 0.3f, 0.3f, 1.0f);


        InputMultiplexer inputMultiplexer = new InputMultiplexer(gameHUD);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void toggleLantern(){
        lightPlayer.setActive(!lightPlayer.isActive());
    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getCamera().update();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        tweenManager.update(delta);

        tiledMapRender.setView(camera);
        tiledMapRender.render();

        player.move(gameHUD.getTouchPadPercentajes(), delta);

        player.render(stage.getCamera().combined, delta);

        camera.position.set(
                Math.min(maxCameraX, Math.max(player.getBody().getPosition().x, minCameraX)),
                Math.min(maxCameraY, Math.max(player.getBody().getPosition().y, minCameraY)),
                0);
        camera.update();

        lightPlayer.setPosition(player.getBody().getPosition().x + 0.5f, player.getBody().getPosition().y + 0.5f);


//        if (Util.debugEnable) {
        debugRenderer.render(world, stage.getCamera().combined);
//        }


        lightManager.updateLights(stage.getCamera().combined);

        updateBodiesPosition();


        gameHUD.act(Gdx.graphics.getDeltaTime());
        gameHUD.draw();

        world.step(1 / 300f, 6, 2);

    }

    public void updateBodiesPosition() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (int i = 0; i < bodies.size; i++) {
            Body b = bodies.get(i);
            if (b.getType() == BodyDef.BodyType.DynamicBody) {
                Sprite e = (Sprite) b.getUserData();

                if (e != null) {
                    e.setPosition(b.getPosition().x, b.getPosition().y);
                    e.setRotation(MathUtils.radiansToDegrees * b.getAngle());
                }
            }
        }

        // output the current FPS
        fpsLogger.log();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
        gameHUD.getViewport().update(width, height, false);
    }

    @Override
    public void show() {
        System.out.println("SHOW GAME");
    }

    @Override
    public void hide() {
        System.out.println("HIDE GAME");
        restartGame();
    }

    @Override
    public void pause() {
        System.out.println("PAUSE GAME");
    }

    @Override
    public void resume() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer(gameHUD);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void onPlayServiceEvent(Util.EVENT event, String data) {
        switch (event) {
            case MESSAGE_RECEIVED:
                String[] values = data.split("/");
                String typeData = values[1];
                String participantID = values[0];
                break;
            default:
                break;
        }
    }

    public void loadMap() {
        System.out.println("loadMap");
        TiledMap map = new TmxMapLoader().load(Util.getPath() + "beach.tmx");
        tiledMapRender = new OrthogonalTiledMapRenderer(map, 1f / 16f);
        Array<Body> bodies = MapBodyBuilder.buildShapes(map, 16f, world);


        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Spawns");


        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Object property = cell.getTile().getProperties().get("PlayerSpawn");
                    if (property != null) {
                        System.out.println("AGREGADA UNA CELDA X: " + x + "  Y: " + y);
                    }
                }
            }
        }

        mapProperties = tiledMapRender.getMap().getProperties();
        mapWidth = mapProperties.get("width", Integer.class); //how many tiles in map
        mapHeight = mapProperties.get("height", Integer.class);

        minCameraX = camera.zoom * (camera.viewportWidth / 2);
        maxCameraX = mapWidth - minCameraX;
        minCameraY = camera.zoom * (camera.viewportHeight / 2);
        maxCameraY = mapHeight - minCameraY;

    }


    public void restartGame() {
        gameHUD.resetButtons();
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2, stage.getCamera().viewportHeight / 2, 0);

    }

    public void exitToMenu() {
        gdxGame.loadMenuScreen();
    }

}
