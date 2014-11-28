package com.appmunki.survival.Processors;

import com.appmunki.survival.Components.RenderComponent;
import com.appmunki.survival.Framework.EntityFactory;
import com.appmunki.survival.Framework.EntityManager;
import com.appmunki.survival.Game.SpriteBasic;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Set;

public class RenderProcessor extends Processor {

    private static final String TAG = "RenderProcessor ";

    SpriteBatch spriteBatch;
    Camera camera;

    public RenderProcessor(EntityManager entityManager, EntityFactory entityFactory, Camera camera) {
        super(entityManager, entityFactory);
        spriteBatch = new SpriteBatch();
        this.camera = camera;
    }


    @Override
    public void processOneGameTick(float delta) {

        Set<Integer> entities = entityManager.getAllEntitiesPossessingComponent(RenderComponent.class);

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        for (Integer entity : entities) {
            RenderComponent renderComponent = entityManager.getComponent(entity, RenderComponent.class);
            SpriteBasic spriteBasic = renderComponent.spriteBasic;

            spriteBasic.render(spriteBatch,delta);
        }
        spriteBatch.end();
    }


}
