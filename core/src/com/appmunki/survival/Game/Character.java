package com.appmunki.survival.Game;

import com.appmunki.survival.util.EntityType;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Character extends Sprite {


    final float JUMP_SCALE = 2f;
    float MAX_JUMP = 80 * JUMP_SCALE;
    protected SpriteBatch batch;
    protected AnimationUnit animationLeft;
    protected AnimationUnit animationRight;
    protected AnimationUnit animationUp;
    protected AnimationUnit animationDown;
    protected AnimationUnit actualAnimation;
    protected GameScreen gameManager;
    protected short mCategoryBit = EntityType.CATEGORY_PLAYER;
    protected short mMaskBit = EntityType.MASK_PLAYER;
    float MAX_VELOCITY = 15;
    float MAX_FORCE = 10;
    boolean flipped = false;
    World world;
    Body body;
    float centerX;
    float centerY;

    boolean shouldRescale = false;
    float inGameScale = 16.008f;
    int life;


    public float mDelta;
    protected Character mNearPLayer = null;

    public Character(World world) {
        this.world = world;
    }

    public Character() {
    }

    /**
     * Creates the sensor for fighting nearby players.
     * Must be called after createBody()
     */
    public void createSensor() {
        CircleShape shape = new CircleShape();
        float padding = 300;
        shape.setRadius(getWidth() + padding / 32f);
        shape.setPosition(new Vector2(getCenterX(), getCenterY()));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = mCategoryBit;
        fixtureDef.filter.maskBits = mMaskBit;
        body.createFixture(fixtureDef);
    }

    public void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(2, 3f);
        bodyDef.fixedRotation = true;
        bodyDef.gravityScale = 0.0f;
        body = world.createBody(bodyDef);

        Vector2[] vertices = new Vector2[4];

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        float size = getWidth() / 2f;

        vertices[0] = new Vector2(centerX - size, centerY - size);
        vertices[1] = new Vector2(centerX - size, centerY + size);
        vertices[2] = new Vector2(centerX + size, centerY + size);
        vertices[3] = new Vector2(centerX + size, centerY - size);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);

        System.out.println("WIDTH " + size);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;

        fixtureDef.filter.categoryBits = mCategoryBit;
        fixtureDef.filter.maskBits = mMaskBit;
        body.setUserData(this);
        body.createFixture(fixtureDef);
        polygonShape.dispose();

    }

    public void createBodyKinematic() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(400, 240);
        bodyDef.fixedRotation = true;

        body = world.createBody(bodyDef);

        Vector2[] vertices = new Vector2[4];

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        float size = getWidth() / 2;

        vertices[0] = new Vector2(centerX - size, centerY - size);
        vertices[1] = new Vector2(centerX - size, centerY + size);
        vertices[2] = new Vector2(centerX + size, centerY + size);
        vertices[3] = new Vector2(centerX + size, centerY - size);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;

        body.setUserData(this);
        body.createFixture(fixtureDef);
        polygonShape.dispose();

    }

    public void setNewPosition(float x, float y) {
        if (body != null) {
            body.setTransform(x, y, body.getAngle());
        } else {
            setPosition(x, y);
        }
    }

    public float getCenterX() {
        return getX() + centerX;
    }

    public float getCenterY() {
        return getY() + centerY;
    }

    public void receiveDamage(int damage) {
        life -= damage;
    }

    public void manageBeginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.isSensor() == false && fixtureB.isSensor() == false) {
            if (null != fixtureA.getBody().getUserData() && fixtureA.getBody().getUserData().equals(this) && fixtureB.getBody().getType().equals(BodyDef.BodyType.StaticBody) && fixtureB.getBody().getUserData().equals("map")) {
            }
            if (null != fixtureB.getBody().getUserData() && fixtureB.getBody().getUserData().equals(this) && fixtureA.getBody().getType().equals(BodyDef.BodyType.StaticBody) && fixtureA.getBody().getUserData().equals("map")) {
            }
        }
        if (fixtureA.getBody().getUserData().equals(this) || fixtureB.getBody().getUserData().equals(this)) {
            Fixture me = null;
            Fixture that = null;
            if (fixtureA.getBody().getUserData().equals(this)) {
                me = fixtureA;
                that = fixtureB;
            }
            if (fixtureB.getBody().getUserData().equals(this)) {
                me = fixtureB;
                that = fixtureA;
            }
            if (me.isSensor()) {
                if (null != that.getBody().getUserData() && that.getBody().getUserData() instanceof Player) {
                    mNearPLayer = (Character) that.getBody().getUserData();
                }
            }
        }

    }

    public void manageEndContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA.getBody().getUserData().equals(this) || fixtureB.getBody().getUserData().equals(this)) {
            Fixture me = null;
            Fixture that = null;
            if (fixtureA.getBody().getUserData().equals(this)) {
                me = fixtureA;
                that = fixtureB;
            }
            if (fixtureB.getBody().getUserData().equals(this)) {
                me = fixtureB;
                that = fixtureA;
            }
            if (me.isSensor()) {
                if (null != that.getBody().getUserData() && that.getBody().getUserData() instanceof Player) {
                    mNearPLayer = null;
                }
            }
        }
    }


    public void move(Vector2 target, float dt) {
        Vector2 myVelocity = body.getLinearVelocity();
        float direction = Math.signum(target.x);
        body.setLinearVelocity(direction * MAX_VELOCITY, myVelocity.y);
    }


    public void render(Matrix4 matrix, float dt) {
    }

    public void setGameManager(GameScreen gameManager) {
        this.gameManager = gameManager;
    }


    public void update(float delta) {
        this.mDelta = delta;
    }

    public boolean isPlayerNear() {
        return mNearPLayer != null;
    }

    public void attack() {

    }


}
