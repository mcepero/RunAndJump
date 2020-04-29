package com.mygdx.mario.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.mario.Assets;
import com.mygdx.mario.utils.Constants;
import com.mygdx.mario.utils.Utils;

public class Nube {
    float y;
    float x;
    float width;
    float height;

    public Nube(float x, float y, float width, float height) {
        this.y=y;
        this.x=x;
        this.width=width;
        this.height=height;
    }

    public void render(SpriteBatch batch) {
        Assets.instance.nubeAssets.nubeNinePatch.draw(batch, x+5, y+20, width+15, height+15);
    }
}
