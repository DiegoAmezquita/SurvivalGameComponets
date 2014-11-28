package com.appmunki.survival.Game;

import com.appmunki.survival.BaseScreen;
import com.appmunki.survival.Framework.Entity;
import com.appmunki.survival.Framework.EntityFactory;
import com.appmunki.survival.Framework.EntityManager;
import com.appmunki.survival.Game.Hud.GameHUDComponents;
import com.appmunki.survival.Managers.LightManager;
import com.appmunki.survival.MyGdxGame;
import com.appmunki.survival.Processors.CameraFollowProcessor;
import com.appmunki.survival.Processors.KeyboardInputProcessor;
import com.appmunki.survival.Processors.LightProcessor;
import com.appmunki.survival.Processors.MoveProcessor;
import com.appmunki.survival.Processors.RenderProcessor;
import com.appmunki.survival.util.EntityType;
import com.appmunki.survival.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;


public class GameScreenComponents implements BaseScreen {

    MyGdxGame gdxGame;

    private Viewport viewport;
    public Stage stage;

    private OrthogonalTiledMapRenderer tiledMapRender;
    MapProperties mapProperties;
    int mapWidth;
    int mapHeight;
    float minCameraX;
    float maxCameraX;
    float minCameraY;
    float maxCameraY;

    SpriteBatch batch;

    private FPSLogger fpsLogger;
    OrthographicCamera camera;

    GameHUDComponents gameHUD;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    RenderProcessor renderProcessor;
    MoveProcessor moveProcessor;
    KeyboardInputProcessor keyboardInputProcessor;
    CameraFollowProcessor cameraFollowProcessor;
    LightProcessor lightProcessor;

    EntityManager entityManager;
    EntityFactory entityFactory;

    LightManager lightManager;


    Entity player;


    ArrayList<SpriteBasic> arraySprites;


    public GameScreenComponents(MyGdxGame gdxGame) {


        fpsLogger = new FPSLogger();

        this.gdxGame = gdxGame;
        camera = new OrthographicCamera(Util.SCREEN_WIDTH, Util.SCREEN_HEIGHT);
        camera.zoom = 1.5f;
        float divisor = Util.SCREEN_HEIGHT / 12;
        viewport = new FitViewport(Util.SCREEN_WIDTH / divisor, 12, camera);
        stage = new Stage(viewport);
        batch = new SpriteBatch();


        arraySprites = new ArrayList<SpriteBasic>();

        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();


        loadMap();

        lightManager = new LightManager(world);
        lightManager.setAmbientLightColor(new Color(0.4f, 0.4f, 0.4f, 0.1f));


        entityManager = new EntityManager();
        entityFactory = new EntityFactory(entityManager, world,lightManager);

//        entityFactory.createHumanPlayer();


        renderProcessor = new RenderProcessor(entityManager, entityFactory, camera);
        moveProcessor = new MoveProcessor(entityManager, entityFactory);
        keyboardInputProcessor = new KeyboardInputProcessor(entityManager, entityFactory);
        cameraFollowProcessor = new CameraFollowProcessor(entityManager, entityFactory, camera, new float[]{minCameraX, minCameraY, maxCameraX, maxCameraY});
        lightProcessor = new LightProcessor(entityManager,entityFactory);

        player = entityFactory.createAnimateEntity("player.txt", new Vector2(2, 2), EntityType.CATEGORY_PLAYER, EntityType.MASK_PLAYER);


        gameHUD = new GameHUDComponents(this,entityManager,player);

        InputMultiplexer inputMultiplexer = new InputMultiplexer(gameHUD);
        Gdx.input.setInputProcessor(inputMultiplexer);




    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getCamera().update();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        tiledMapRender.setView(camera);
        tiledMapRender.render();


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(int i=0;i<arraySprites.size();i++){
            SpriteBasic spriteBasic = arraySprites.get(i);
            spriteBasic.render(batch,delta);
        }
        batch.end();


        keyboardInputProcessor.processOneGameTick(delta);
        moveProcessor.processOneGameTick(delta);
        renderProcessor.processOneGameTick(delta);
        cameraFollowProcessor.processOneGameTick(delta);
        lightProcessor.processOneGameTick(delta);

        camera.update();

        fpsLogger.log();

        debugRenderer.render(world, camera.combined);

        lightManager.updateLights(camera.combined);

        gameHUD.act(Gdx.graphics.getDeltaTime());
        gameHUD.draw();

        world.step(1 / 300f, 6, 2);

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

    }

    @Override
    public void pause() {
        System.out.println("PAUSE GAME");
    }

    @Override
    public void resume() {
        System.out.println("Resume");
        InputMultiplexer inputMultiplexer = new InputMultiplexer(gameHUD);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void onPlayServiceEvent(Util.EVENT event, String data) {
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

        TiledMapTileLayer layerItems = (TiledMapTileLayer) map.getLayers().get("ItemsToPick");
        for (int x = 0; x < layerItems.getWidth(); x++) {
            for (int y = 0; y < layerItems.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layerItems.getCell(x, y);
                if (cell != null) {
                    SpriteBasic spriteItem = new SpriteBasic("background.png");
                    spriteItem.setPosition(x,y);

                    spriteItem.setSize(layer.getTileWidth()/16,layer.getTileWidth()/16);

                    createBody(BodyDef.BodyType.StaticBody,new Vector2(x,y),new Vector2(layer.getTileWidth()/16,layer.getTileWidth()/16),EntityType.CATEGORY_DROID,EntityType.MASK_DROID);


                    arraySprites.add(spriteItem);
                    System.out.println("LayerItems "+x+" "+y);
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

    public Body createBody(BodyDef.BodyType bodyType, Vector2 position, Vector2 size, short categoryBit, short maskBit) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(position);
        bodyDef.fixedRotation = true;
        bodyDef.gravityScale = 0.0f;
        Body body = world.createBody(bodyDef);

        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(0, 0);
        vertices[1] = new Vector2(0, size.y);
        vertices[2] = new Vector2(size.x, size.y);
        vertices[3] = new Vector2(size.x, 0);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;

        fixtureDef.filter.categoryBits = categoryBit;
        fixtureDef.filter.maskBits = maskBit;
        body.setUserData(this);
        body.createFixture(fixtureDef);
        polygonShape.dispose();

        return body;
    }



}
