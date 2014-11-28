package com.appmunki.survival.Processors;

import com.appmunki.survival.Framework.EntityFactory;
import com.appmunki.survival.Framework.EntityManager;
import com.badlogic.gdx.math.Matrix4;

/**
 * Created by diegoamezquita on 11/20/14.
 */
public class Processor {

    EntityManager entityManager;
    EntityFactory entityFactory;

    public Processor(EntityManager entityManager, EntityFactory entityFactory) {
        this.entityManager = entityManager;
        this.entityFactory = entityFactory;
    }

    public void processOneGameTick(float delta) {
    }
}
