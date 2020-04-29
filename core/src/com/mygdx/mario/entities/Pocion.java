package com.mygdx.mario.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.mario.Assets;

public class Pocion {
    float y;
    float x;
    float width;
    float height;
    public Vector2 position;

    public Pocion(Vector2 position, float width, float height) {
        this.y=position.y;
        this.x=position.x;
        this.width=width;
        this.height=height;
        this.position=position;
    }

    public void render(SpriteBatch batch) {
        Assets.instance.pocionAssets.pocionNinePatch.draw(batch, x, y, width, height);
    }
}
