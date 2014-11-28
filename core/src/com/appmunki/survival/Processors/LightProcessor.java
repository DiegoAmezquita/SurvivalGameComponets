package com.appmunki.survival.Processors;

import com.appmunki.survival.Components.LightComponent;
import com.appmunki.survival.Components.MoveComponent;
import com.appmunki.survival.Components.RenderComponent;
import com.appmunki.survival.Framework.EntityFactory;
import com.appmunki.survival.Framework.EntityManager;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import java.util.Set;


public class LightProcessor extends Processor {

    private static final String TAG = "InputProcessor ";


    public LightProcessor(EntityManager entityManager, EntityFactory entityFactory) {
        super(entityManager, entityFactory);
    }


    @Override
    public void processOneGameTick(float delta) {

        Set<Integer> entities = entityManager.getAllEntitiesPossessingComponent(LightComponent.class);

        for (Integer entity : entities) {
            MoveComponent moveComponent = entityManager.getComponent(entity, MoveComponent.class);
            RenderComponent renderComponent = entityManager.getComponent(entity, RenderComponent.class);
            LightComponent lightComponent = entityManager.getComponent(entity, LightComponent.class);

            lightComponent.light.setPosition(moveComponent.x + renderComponent.spriteBasic.getWidth() / 2,
                    moveComponent.y + renderComponent.spriteBasic.getHeight() / 2);
        }
    }


}
