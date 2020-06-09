package com.mygdx.mario.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.mario.Assets;
import com.mygdx.mario.Level;
import com.mygdx.mario.utils.Constants;
import com.mygdx.mario.utils.Utils;

public class Bola {
    public Vector2 position;
   // public Vector2 lastFramePosition;
    public Vector2 velocity;
    public Plataforma plataforma;
    private long walkStartTime;
    Level level;
    public float width;
    public float height;

    public Bola(Vector2 position, float width, float height, Level level) {
        this.plataforma=plataforma;
        this.position = position;

        //this.position.x=position.x+12;
        //this.position.y=position.y+-7;

        velocity = new Vector2();
        this.level=level;
        this.width=width;
        this.height=height;
    }

    public void update(float delta) {
        position.mulAdd(velocity, delta);
        moveLeft(delta);

        if (position.x < -4700) {
            level.getBolas().removeValue(this,true);
        }

    }

    public void render(SpriteBatch batch) {
        TextureRegion region;
        float walkTimeSeconds = Utils.secondsSince(walkStartTime);
        region = (TextureRegion) Assets.instance.bolaAssets.moveAnimation.getKeyFrame(walkTimeSeconds);
        Utils.drawTextureRegion(batch, region,
                position.x,
                position.y
        );
    }

    private void moveLeft(float delta) {
        walkStartTime = TimeUtils.nanoTime();
        position.x -= delta * Constants.BOLA_MOVE_SPEED;
    }

    public long getWalkStartTime() {
        return walkStartTime;
    }
}
