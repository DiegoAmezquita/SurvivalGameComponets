package com.appmunki.survival.Game;

import com.appmunki.survival.util.Util;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Character {


    //Depends the size of the body
    final float JUMP_SCALE = 1f;
    float MAX_JUMP = 80 * JUMP_SCALE;
    String participantID;

    boolean mainPlayer;

    String characterFile;

    public Player(boolean mainPlayer, String characterFile, World world) {
        super(world);
        this.characterFile = characterFile;
        this.mainPlayer = mainPlayer;
        this.world = world;

        batch = new SpriteBatch();


        TextureAtlas spriteSheet = new TextureAtlas(Util.getPath() + characterFile);

        animationLeft = new AnimationUnit(0.25f, spriteSheet.createSprites("left"));
        animationRight = new AnimationUnit(0.25f, spriteSheet.createSprites("right"));
        animationUp = new AnimationUnit(0.25f, spriteSheet.createSprites("up"));
        animationDown = new AnimationUnit(0.25f, spriteSheet.createSprites("down"));


        TextureRegion textureRegionInit = animationDown.getKeyFrame(0, true);

        setRegion(textureRegionInit);
        setColor(1, 1, 1, 1);

        //This depends of the size of the asset, so I think for now we have to reescale  it
        float scale = 16.f;
        setSize(getRegionWidth()/scale,getRegionHeight()/scale);
        setOrigin(getWidth() / 2, getHeight() / 2);

//        setPosition(500 - getWidth() / 2, 240 - getHeight() / 2);

        life = 100;

        actualAnimation = animationDown;
        createBody();

    }


    @Override
    public void setRegion(TextureRegion region) {
        super.setRegion(region);
        if (shouldRescale) {
            setSize(region.getRegionWidth() / inGameScale, region.getRegionHeight() / inGameScale);
        }
    }


    @Override
    public void manageBeginContact(Contact contact) {
        super.manageBeginContact(contact);

        if ((contact.getFixtureA().getBody().getUserData() instanceof Sprite) && (contact.getFixtureB().getBody().getUserData() instanceof Sprite)) {
            Sprite spriteA = (Sprite) contact.getFixtureA().getBody().getUserData();
            Sprite spriteB = (Sprite) contact.getFixtureB().getBody().getUserData();
        }
    }


    @Override
    public void move(Vector2 mvm, float dt) {

        Vector2 velocity = body.getLinearVelocity();

        velocity.x = MAX_VELOCITY * mvm.x;

        velocity.y = MAX_VELOCITY * mvm.y;

        if (mvm.x == 0f && mvm.y == 0f) {
            body.setLinearVelocity(0, 0);
            actualAnimation.stop();
        } else {
            body.setLinearVelocity(velocity);
            actualAnimation.start();
        }


        if(mvm.x>0&&mvm.y>0){
            if(mvm.x>mvm.y) {
                actualAnimation = animationRight;
            }else{
                actualAnimation = animationUp;
            }
        }
        if(mvm.x<0&&mvm.y<0){
            if(mvm.x<mvm.y) {
                actualAnimation = animationLeft;
            }else{
                actualAnimation = animationDown;
            }
        }

        if(mvm.x>0&&mvm.y<0){
            if(mvm.x>-mvm.y) {
                actualAnimation = animationRight;
            }else{
                actualAnimation = animationDown;
            }
        }

        if(mvm.x<0&&mvm.y>0){
            if(-mvm.x>mvm.y) {
                actualAnimation = animationLeft;
            }else{
                actualAnimation = animationUp;
            }
        }

    }

    public Body getBody() {
        return body;
    }



    @Override
    public void receiveDamage(int damage) {
        super.receiveDamage(damage);
    }


    @Override
    public void render(Matrix4 matrix, float dt) {

        batch.setProjectionMatrix(matrix);
        batch.begin();

        setRegion(actualAnimation.animate(dt));

        if (flipped) {
            setFlip(true, false);
        }
        draw(batch);
        batch.end();

    }


    @Override
    public void update(float delta) {

    }


}