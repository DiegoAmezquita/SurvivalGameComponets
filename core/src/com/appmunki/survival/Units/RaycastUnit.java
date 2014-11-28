package com.appmunki.survival.Units;

import com.appmunki.survival.util.Util;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Seidan on 8/25/2014.
 */
public class RaycastUnit {

    ShapeRenderer shapeRenderer;
    World world;
    RayCastCallback callback;

    public RaycastUnit(World world,RayCastCallback callback){
        this.world = world;
        shapeRenderer = new ShapeRenderer();
        this.callback = callback;
    }

    public void render(Matrix4 matrix,Vector2 vectorOrigin, Vector2 vectorEnd){
        if(Util.debugEnable) {
            shapeRenderer.setProjectionMatrix(matrix);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.line(vectorOrigin.x, vectorOrigin.y, vectorEnd.x, vectorEnd.y);
            shapeRenderer.end();
        }
        if(world!=null) {
            world.rayCast(callback, vectorOrigin, vectorEnd);
        }
    }
}
