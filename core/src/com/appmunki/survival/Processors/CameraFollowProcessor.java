package com.appmunki.survival.Processors;

import com.appmunki.survival.Components.CameraFollowComponent;
import com.appmunki.survival.Components.MoveComponent;
import com.appmunki.survival.Framework.EntityFactory;
import com.appmunki.survival.Framework.EntityManager;
import com.badlogic.gdx.graphics.Camera;

import java.util.Set;

public class CameraFollowProcessor extends Processor {

    private static final String TAG = "CameraFollowProcessor ";

    private Camera camera;

    private float minX;
    private float maxX;
    private float minY;
    private float maxY;


    public CameraFollowProcessor(EntityManager entityManager, EntityFactory entityFactory, Camera camera, float[] limits) {
        super(entityManager, entityFactory);
        this.camera = camera;
        this.minX = limits[0];
        this.minY = limits[1];
        this.maxX = limits[2];
        this.maxY = limits[3];
    }


    @Override
    public void processOneGameTick(float delta) {

        Set<Integer> entities = entityManager.getAllEntitiesPossessingComponent(CameraFollowComponent.class);

        for (Integer entity : entities) {
            CameraFollowComponent cameraFollowComponent = entityManager.getComponent(entity, CameraFollowComponent.class);
            MoveComponent moveComponent = entityManager.getComponent(entity, MoveComponent.class);

            camera.position.set(
                    Math.min(maxX, Math.max(moveComponent.x, minX)),
                    Math.min(maxY, Math.max(moveComponent.y, minY)),
                    0);

            break;
        }
    }


}
