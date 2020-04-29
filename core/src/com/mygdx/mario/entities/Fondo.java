package com.mygdx.mario.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mario.Assets;

public class Fondo {
    float y;
    float x;
    float width;
    float height;

    public Fondo(float x, float y, float width, float height) {
        this.y=y;
        this.x=x;
        this.width=width;
        this.height=height;
    }

    public void render(SpriteBatch batch) {
        Assets.instance.fondoAssets.fondoNinePatch.draw(batch, x, y, width*2, height*2);
    }
}
