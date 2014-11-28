package com.appmunki.survival.Components;

import com.appmunki.survival.Game.SpriteBasic;

/**
 * Created by Seidan on 11/17/2014.
 */
public class RenderComponent implements Component{

    public SpriteBasic spriteBasic;

    public RenderComponent(SpriteBasic spriteBasic){
        this.spriteBasic = spriteBasic;
    }

    @Override
    public void toggle() {

    }
}
