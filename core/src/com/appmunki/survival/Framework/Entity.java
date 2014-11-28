package com.appmunki.survival.Framework;


import com.appmunki.survival.Components.MoveComponent;
import com.appmunki.survival.Components.RenderComponent;

public class Entity {

    public int id;
    public EntityManager entityManager;

    public Entity(int id, EntityManager entityManager){
        this.id=id;
        this.entityManager = entityManager;
    }


    public RenderComponent getRenderComponent(){
        return entityManager.getComponent(id,RenderComponent.class);
    }



}
