package com.mygdx.mario.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.mario.Assets;
import com.mygdx.mario.utils.Constants;
import com.mygdx.mario.utils.Utils;

public class Bloque2 {
    public float top;
    public float bottom;
    public float left;
    public float right;
    public Vector2 position;

    public Bloque2(float left, float top, float width, float height) {
        this.top = top;
        this.bottom = top - height;
        this.left = left;
        this.right = left + width;
        position = new Vector2(left,top);
    }

    public void render(SpriteBatch batch) {
        float width = right - left;
        float height = top - bottom;

        Assets.instance.bloque2Assets.bloqueNinePatch.draw(batch, left, bottom, width, height);
    }
}
