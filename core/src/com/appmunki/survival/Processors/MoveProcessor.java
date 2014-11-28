package com.appmunki.survival.Processors;

import com.appmunki.survival.Components.LightComponent;
import com.appmunki.survival.Components.MoveComponent;
import com.appmunki.survival.Components.PhysicsComponent;
import com.appmunki.survival.Components.RenderComponent;
import com.appmunki.survival.Framework.EntityFactory;
import com.appmunki.survival.Framework.EntityManager;
import com.appmunki.survival.Game.AnimatedSprite;

import java.util.Set;

public class MoveProcessor extends Processor {

    private static final String TAG = "MoveProcessor ";




    public MoveProcessor(EntityManager entityManager, EntityFactory entityFactory) {
        super(entityManager, entityFactory);
    }


    @Override
    public void processOneGameTick(float delta) {

        Set<Integer> entities = entityManager.getAllEntitiesPossessingComponent(MoveComponent.class);

        for (Integer entity : entities) {
            MoveComponent moveComponent = entityManager.getComponent(entity, MoveComponent.class);
            PhysicsComponent physicsComponent = entityManager.getComponent(entity,PhysicsComponent.class);

            if(physicsComponent!=null) {
                moveComponent.x = physicsComponent.body.getPosition().x;
                moveComponent.y = physicsComponent.body.getPosition().y;
            }

            RenderComponent renderComponent = entityManager.getComponent(entity, RenderComponent.class);
            renderComponent.spriteBasic.setPosition(moveComponent.x,moveComponent.y);

        }
    }


}
