package com.appmunki.survival.Processors;

import com.appmunki.survival.Framework.EntityFactory;
import com.appmunki.survival.Framework.EntityManager;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;


public class DPadInputProcessor extends Processor {

    private static final String TAG = "InputProcessor ";

    Touchpad touchpad;


    public DPadInputProcessor(EntityManager entityManager, EntityFactory entityFactory, Touchpad touchpad) {
        super(entityManager, entityFactory);
    }


    @Override
    public void processOneGameTick(float delta) {

//        Set<Integer> entities = entityManager.getAllEntitiesPossessingComponent(MoveComponent.class);
//
//        for (Integer entity : entities) {
//            MoveComponent moveComponent = entityManager.getComponent(entity, MoveComponent.class);
//            moveComponent.x = moveComponent.x+moveComponent.MAX_VELOCITY*delta;
//
//            RenderComponent renderComponent = entityManager.getComponent(entity, RenderComponent.class);
//            renderComponent.spriteBasic.setPosition(moveComponent.x,moveComponent.y);
//
//        }
    }


}
