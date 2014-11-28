package com.appmunki.survival.Framework;

import com.appmunki.survival.Components.BagComponent;
import com.appmunki.survival.Components.CameraFollowComponent;
import com.appmunki.survival.Components.KeyboardInputComponent;
import com.appmunki.survival.Components.LightComponent;
import com.appmunki.survival.Components.MoveComponent;
import com.appmunki.survival.Components.PhysicsComponent;
import com.appmunki.survival.Components.RenderComponent;
import com.appmunki.survival.Game.AnimatedSprite;
import com.appmunki.survival.Game.SpriteBasic;
import com.appmunki.survival.Managers.LightManager;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.Light;
import box2dLight.PointLight;

public class EntityFactory {

    EntityManager entityManager;
    World world;
    LightManager lightManager;

    public EntityFactory(EntityManager entityManager, World world, LightManager lightManager) {
        this.entityManager = entityManager;
        this.lightManager = lightManager;
        this.world = world;

    }

    public Entity createHumanPlayer() {
        SpriteBasic sprite = new SpriteBasic("background.png");
        Entity entity = new Entity(entityManager.createEntity(), entityManager);

        entityManager.addComponent(entity.id, new RenderComponent(sprite));
        entityManager.addComponent(entity.id, new MoveComponent(0, 0, 2, 3));
        return entity;
    }

    public Entity createAnimateEntity(String file, Vector2 position, short categoryBit, short maskBit) {
        AnimatedSprite animatedSprite = new AnimatedSprite(file, "left", "right", "up", "down");
        Entity entity = new Entity(entityManager.createEntity(), entityManager);

        Body body = createBody(BodyType.DynamicBody, position, new Vector2(animatedSprite.getWidth(), animatedSprite.getHeight()), categoryBit, maskBit);

        Light light = createLight(Color.BLUE);

        entityManager.addComponent(entity.id, new RenderComponent(animatedSprite));
        entityManager.addComponent(entity.id, new PhysicsComponent(body));
        entityManager.addComponent(entity.id, new MoveComponent(position.x, position.y, 5, 15));
        entityManager.addComponent(entity.id, new KeyboardInputComponent(Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.R));
        entityManager.addComponent(entity.id, new CameraFollowComponent());
        entityManager.addComponent(entity.id, new LightComponent(light));
        entityManager.addComponent(entity.id, new BagComponent());
        return entity;
    }




    //The position is in the bottom left corner
    public Body createBody(BodyType bodyType, Vector2 position, Vector2 size, short categoryBit, short maskBit) {
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

        System.out.println("WIDTH " + size);


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

    public void createSensor(Body body,float radius, short categoryBit, short maskBit) {
        CircleShape shape = new CircleShape();
        float padding = 300;
        shape.setRadius(radius);
        shape.setPosition(body.getPosition());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = categoryBit;
        fixtureDef.filter.maskBits = maskBit;
        body.createFixture(fixtureDef);
    }


    public Light createLight(Color color){
        Light light = new PointLight(lightManager, 360, Color.WHITE, 8, 0, 0);
        light.setStaticLight(false);
        light.setColor(color);
        return light;
    }

}
