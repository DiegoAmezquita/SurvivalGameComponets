package com.appmunki.survival.Processors;

import com.appmunki.survival.Components.HealthComponent;
import com.appmunki.survival.Components.RenderComponent;
import com.appmunki.survival.Framework.EntityFactory;
import com.appmunki.survival.Framework.EntityManager;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import java.util.Set;


public class HealthProcessor extends Processor {

    private static final String TAG = "HealthProcessor ";


    public HealthProcessor(EntityManager entityManager, EntityFactory entityFactory) {
        super(entityManager, entityFactory);
    }


    @Override
    public void processOneGameTick(float delta) {

        Set<Integer> entities = entityManager.getAllEntitiesPossessingComponent(HealthComponent.class);

        for (Integer entity : entities) {
            HealthComponent healthComponent = entityManager.getComponent(entity, HealthComponent.class);
            RenderComponent renderComponent = entityManager.getComponent(entity, RenderComponent.class);

            if(!healthComponent.alive){
                entityManager.killEntity(entity);
            }else if(healthComponent.max==0||healthComponent.current<=0){
                //Animation kill entity
                healthComponent.alive = false;
            }

        }

    }


}
