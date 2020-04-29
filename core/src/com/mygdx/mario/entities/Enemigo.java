package com.mygdx.mario.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.mario.Assets;
import com.mygdx.mario.Level;
import com.mygdx.mario.utils.Constants;
import com.mygdx.mario.utils.Enums;
import com.mygdx.mario.utils.Utils;

import javax.xml.soap.Text;

public class Enemigo {

    public Vector2 position;
    public Vector2 lastFramePosition;
    public Vector2 velocity;
    public Plataforma plataforma;
    long walkStartTime;
    Enums.WalkState walkState;
    Enums.Direction direction;
    Enums.EstadoEnemigo estadoEnemigo;

    public Enemigo(Plataforma plataforma) {
        this.plataforma=plataforma;
        walkState= Enums.WalkState.WALKING;
        position = position = new Vector2(plataforma.left, 0 + Constants.ENEMY_CENTER.y);
        lastFramePosition = new Vector2();
        lastFramePosition.set(position);
        velocity = new Vector2();
        direction= Enums.Direction.LEFT;
        estadoEnemigo = Enums.EstadoEnemigo.VIVO;
    }

    public void update(float delta) {
        lastFramePosition.set(position);
        position.mulAdd(velocity, delta);
        if (direction==Enums.Direction.RIGHT)
            moveRight(delta);
        else if(direction== Enums.Direction.LEFT)
            moveLeft(delta);

        if (position.x < 30) {
            position.x = 30;
            direction = Enums.Direction.RIGHT;
        } else if (position.x > 1300) {
            position.x = 1300;
            direction = Enums.Direction.LEFT;
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion  region = Assets.instance.enemimyAssets.standingRight;
        if (walkState== Enums.WalkState.WALKING && direction== Enums.Direction.RIGHT && estadoEnemigo==Enums.EstadoEnemigo.VIVO) {
            float walkTimeSeconds = Utils.secondsSince(walkStartTime);
            region = (TextureRegion) Assets.instance.enemimyAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
        }else if (walkState== Enums.WalkState.WALKING && direction== Enums.Direction.LEFT && estadoEnemigo==Enums.EstadoEnemigo.VIVO) {
            float walkTimeSeconds = Utils.secondsSince(walkStartTime);
            region = (TextureRegion) Assets.instance.enemimyAssets.walkingLeftAnimation.getKeyFrame(walkTimeSeconds);
        }else if (walkState== Enums.WalkState.WALKING && direction== Enums.Direction.RIGHT && estadoEnemigo==Enums.EstadoEnemigo.MUERTO) {
            /*float walkTimeSeconds = Utils.secondsSince(walkStartTime);
            region = (TextureRegion) Assets.instance.enemimyAssets.deadRightAnimation.getKeyFrame(walkTimeSeconds);*/
            region = Assets.instance.enemimyAssets.dead;
        }else if (walkState== Enums.WalkState.WALKING && direction== Enums.Direction.LEFT && estadoEnemigo==Enums.EstadoEnemigo.MUERTO) {
            /*float walkTimeSeconds = Utils.secondsSince(walkStartTime);
            region = (TextureRegion) Assets.instance.enemimyAssets.deadLeftAnimation.getKeyFrame(walkTimeSeconds);*/
            region = Assets.instance.enemimyAssets.dead;
        }



        Utils.drawTextureRegion(batch, region,
                position.x - Constants.ENEMY_EYE_POSITION.x,
                position.y - Constants.ENEMY_EYE_POSITION.y
        );

    }

    private void moveRight(float delta) {
        if (walkState != Enums.WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }
        position.x += delta * Constants.ENEMY_MOVE_SPEED;
    }

    private void moveLeft(float delta) {
        if (walkState != Enums.WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }
        position.x -= delta * Constants.ENEMY_MOVE_SPEED;
    }
}
