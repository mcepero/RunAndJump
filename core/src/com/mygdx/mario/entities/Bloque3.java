package com.mygdx.mario.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.mario.Assets;

public class Bloque3 {
    public float top;
    public float bottom;
    public float left;
    public float right;
     public float width;
     public float height;
    public Vector2 position;

    public Bloque3(float x, float y, float width, float height) {
        this.top = y+height;
        this.bottom = y-height;
        this.left = x;
        this.right = x + width;
        this.width=width;
        this.height=height;
        position = new Vector2(x,y);
    }

    public void render(SpriteBatch batch) {
        //float width = right - left;
        //float height = top - bottom;
        Assets.instance.bloque3Assets.bloque3NinePatch.draw(batch, position.x, position.y, width, height);
    }


}
