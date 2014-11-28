package com.appmunki.survival.Processors;

import com.appmunki.survival.Components.KeyboardInputComponent;
import com.appmunki.survival.Components.MoveComponent;
import com.appmunki.survival.Components.PhysicsComponent;
import com.appmunki.survival.Components.RenderComponent;
import com.appmunki.survival.Framework.EntityFactory;
import com.appmunki.survival.Framework.EntityManager;
import com.appmunki.survival.Game.AnimatedSprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import java.util.Set;


public class KeyboardInputProcessor extends Processor {

    private static final String TAG = "InputProcessor ";

    Touchpad touchpad;


    public KeyboardInputProcessor(EntityManager entityManager, EntityFactory entityFactory) {
        super(entityManager, entityFactory);
    }


    @Override
    public void processOneGameTick(float delta) {

        Set<Integer> entities = entityManager.getAllEntitiesPossessingComponent(KeyboardInputComponent.class);

        for (Integer entity : entities) {
            KeyboardInputComponent keyboardInputComponent = entityManager.getComponent(entity, KeyboardInputComponent.class);
            RenderComponent renderComponent = entityManager.getComponent(entity, RenderComponent.class);
            MoveComponent moveComponent = entityManager.getComponent(entity, MoveComponent.class);
            PhysicsComponent physicsComponent = entityManager.getComponent(entity, PhysicsComponent.class);

            if(physicsComponent!=null) {
                physicsComponent.body.setLinearVelocity(0,0);
            }

            boolean anyKeyPressed = false;

            float velocity = moveComponent.normalVelocity;

            if (Gdx.input.isKeyPressed(keyboardInputComponent.keySpeed)) {
                velocity = moveComponent.maxVelocity;
            }

            if (Gdx.input.isKeyPressed(keyboardInputComponent.keyLeft)) {

                anyKeyPressed = true;

                if(physicsComponent!=null) {
                    physicsComponent.body.setLinearVelocity(-velocity,physicsComponent.body.getLinearVelocity().y);
                }else{
                    moveComponent.x -= velocity * delta;
                }

                if(renderComponent.spriteBasic instanceof AnimatedSprite){
                    ((AnimatedSprite)renderComponent.spriteBasic).changeToLeft();
                }

            }
            if (Gdx.input.isKeyPressed(keyboardInputComponent.keyRight)) {
                anyKeyPressed = true;
                if(physicsComponent!=null) {
                    physicsComponent.body.setLinearVelocity(velocity,physicsComponent.body.getLinearVelocity().y);
                }else{
                    moveComponent.x += velocity * delta;
                }
                if(renderComponent.spriteBasic instanceof AnimatedSprite){
                    ((AnimatedSprite)renderComponent.spriteBasic).changeToRight();
                }
            }
            if (Gdx.input.isKeyPressed(keyboardInputComponent.keyUp)) {
                anyKeyPressed = true;
                if(physicsComponent!=null) {
                    physicsComponent.body.setLinearVelocity(physicsComponent.body.getLinearVelocity().x,velocity);
                }else{
                    moveComponent.y += velocity * delta;
                }


                if(renderComponent.spriteBasic instanceof AnimatedSprite){
                    ((AnimatedSprite)renderComponent.spriteBasic).changeToUp();
                }
            }
            if (Gdx.input.isKeyPressed(keyboardInputComponent.keyDown)) {
                anyKeyPressed = true;
                if(physicsComponent!=null) {
                    physicsComponent.body.setLinearVelocity(physicsComponent.body.getLinearVelocity().x,-velocity);
                }else{
                    moveComponent.y -= velocity * delta;
                }
                if(renderComponent.spriteBasic instanceof AnimatedSprite){
                    ((AnimatedSprite)renderComponent.spriteBasic).changeToDown();
                }
            }

            if(!anyKeyPressed){
                if(renderComponent.spriteBasic instanceof AnimatedSprite){
                    ((AnimatedSprite)renderComponent.spriteBasic).stopAnimation();
                }
            }else{
                if(renderComponent.spriteBasic instanceof AnimatedSprite){
                    ((AnimatedSprite)renderComponent.spriteBasic).startAnimation();
                }
            }

            renderComponent.spriteBasic.setPosition(moveComponent.x, moveComponent.y);

        }
    }


}
