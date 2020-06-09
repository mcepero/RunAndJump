package com.mygdx.mario.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.mario.Assets;
import com.mygdx.mario.Level;
import com.mygdx.mario.utils.Constants;
import com.mygdx.mario.utils.Enums;
import com.mygdx.mario.utils.Utils;

public class Enemigo {

    public Vector2 position;
    //public Vector2 lastFramePosition;
    public Vector2 velocity;
    public Plataforma plataforma;
    private long walkStartTime;
    Enums.WalkState walkState;
    Enums.Direction direction;
    Enums.EstadoEnemigo estadoEnemigo;
    Level level;
    float width;
    float height;

    public Enemigo(float x, float y, float width, float height, Level level) {
        this.plataforma=plataforma;
        this.level=level;
        walkState= Enums.WalkState.WALKING;
        position = position = new Vector2(x,y);
        //lastFramePosition = new Vector2();
        //lastFramePosition.set(position);
        velocity = new Vector2();
        direction= Enums.Direction.LEFT;
        estadoEnemigo = Enums.EstadoEnemigo.VIVO;
        this.width=width;
        this.height=height;
    }

    public boolean acabar=false;
    public boolean eliminar=false;
    public void update(float delta) {
        if (acabar!=true) {
            //lastFramePosition.set(position);
            position.mulAdd(velocity, delta);
            if (direction == Enums.Direction.RIGHT)
                moveRight(delta);
            else if (direction == Enums.Direction.LEFT)
                moveLeft(delta);

            if (position.x < -4400) {
                position.x = -4400;
                direction = Enums.Direction.RIGHT;
            } else if (position.x > 2420) {
                position.x = 2420;
                direction = Enums.Direction.LEFT;
            }

            Rectangle rectanguloEnemigo = new Rectangle(
                    position.x,
                    position.y,
                    width,
                    height
            );

            for (Bloque3 bloque3: level.getBloques3()) {
                Rectangle rectanguloBloque = new Rectangle(
                        bloque3.position.x/* - Constants.VIDA_COLLISION_RADIUS*/,
                        bloque3.position.y/* - Constants.VIDA_COLLISION_RADIUS*/,
                        bloque3.width,
                        bloque3.height);

                if(rectanguloEnemigo.overlaps(rectanguloBloque)) {
                    if (direction== Enums.Direction.RIGHT)
                        direction= Enums.Direction.LEFT;
                    else
                        direction= Enums.Direction.RIGHT;
                }/*else if(rectanguloEnemigo.x==bloque3.right){
                    System.out.println("EL ENEMIGO HA CHOCADO!!");
                    if (direction== Enums.Direction.RIGHT)
                        direction= Enums.Direction.LEFT;
                    else
                        direction= Enums.Direction.RIGHT;
                }*/
            }
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
            float walkTimeSeconds = Utils.secondsSince(walkStartTime);
            region = (TextureRegion) Assets.instance.enemimyAssets.deadRightAnimation.getKeyFrame(walkTimeSeconds);
            if (Assets.instance.enemimyAssets.deadRightAnimation.isAnimationFinished(walkTimeSeconds) == true) {
                acabar=true;
            }
        }else if (walkState== Enums.WalkState.WALKING && direction== Enums.Direction.LEFT && estadoEnemigo==Enums.EstadoEnemigo.MUERTO) {
            float walkTimeSeconds = Utils.secondsSince(walkStartTime);
            region = (TextureRegion) Assets.instance.enemimyAssets.deadLeftAnimation.getKeyFrame(walkTimeSeconds);
            if (Assets.instance.enemimyAssets.deadLeftAnimation.isAnimationFinished(walkTimeSeconds) == true) {
                acabar=true;
            }
        }

        if(eliminar==false) {
            Utils.drawTextureRegion(batch, region,
                    position.x,
                    position.y
            );
        }
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

    public long getWalkStartTime() {
        return walkStartTime;
    }
}
