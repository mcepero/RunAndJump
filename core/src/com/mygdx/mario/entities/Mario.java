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
import com.mygdx.mario.NuevoNivel;
import com.mygdx.mario.utils.Constants;
import com.mygdx.mario.utils.CuentaAtras;
import com.mygdx.mario.utils.Enums;
import com.mygdx.mario.Level;
import com.mygdx.mario.utils.LevelLoader;
import com.mygdx.mario.utils.Utils;

import java.util.Locale;
import java.util.Timer;

public class Mario{

    public final static String TAG = Mario.class.getName();

    public Vector2 spawnLocation;
    public Vector2 position;
    public Vector2 lastFramePosition;
    public Vector2 velocity;
    public int vidasPersonaje;
    Enums.Direction facing;
    Enums.JumpState jumpState;
    Enums.WalkState walkState;
    Enums.Choque choque;

    long walkStartTime;
    long jumpStartTime;

    Level level;
    CuentaAtras cuentaAtras;

    public Mario(Vector2 spawnLocation, Level level) {
        this.spawnLocation = spawnLocation;
        this.level = level;
        position = new Vector2();
        lastFramePosition = new Vector2();
        velocity = new Vector2();
        vidasPersonaje=3;
        init();
    }


    public void init() {
        position.set(spawnLocation);
        lastFramePosition.set(position);
        velocity.setZero();
        jumpState = Enums.JumpState.FALLING;
        facing = Enums.Direction.RIGHT;
        walkState = Enums.WalkState.NOT_WALKING;
        choque = Enums.Choque.NO;
    }

    public void update(float delta, Array<Plataforma> plataformas) {
        lastFramePosition.set(position);
        velocity.y -= Constants.GRAVITY;
        position.mulAdd(velocity, delta);

        if (jumpState != Enums.JumpState.JUMPING) {
            jumpState = Enums.JumpState.FALLING;

            if (position.y - Constants.MARIO_EYE_HEIGHT < 0) {
                jumpState = Enums.JumpState.GROUNDED;
                position.y = Constants.MARIO_EYE_HEIGHT;
                velocity.y = 0;
            }

            for (Plataforma plataforma : plataformas) {
                if (SobrePlataforma(plataforma)) {
                    jumpState = Enums.JumpState.GROUNDED;
                    velocity.y = 0;
                    position.y = plataforma.top + Constants.MARIO_EYE_HEIGHT;
                }
            }

        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveLeft(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveRight(delta);
        } else {
            walkState = Enums.WalkState.NOT_WALKING;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            switch (jumpState) {
                case GROUNDED:
                    startJump();
                    break;
                case JUMPING:
                    continueJump();
            }
        } else {
            endJump();
        }

        if (position.x<=0)
            position.x=0;
        else if(position.x>=1350)
            position.x=1350;

        Rectangle rectanguloMario = new Rectangle(
                position.x - Constants.MARIO_STANCE_WIDTH / 2,
                position.y - Constants.MARIO_EYE_HEIGHT,
                Constants.MARIO_STANCE_WIDTH,
                Constants.MARIO_HEIGHT);

        for (Enemigo enemigo : level.getEnemigos()) {
            Rectangle rectanguloEnemigo = new Rectangle(
                    enemigo.position.x - Constants.ENEMIGO_COLLISION_RADIUS,
                    enemigo.position.y - Constants.ENEMIGO_COLLISION_RADIUS,
                    2 * Constants.ENEMIGO_COLLISION_RADIUS,
                    2 * Constants.ENEMIGO_COLLISION_RADIUS
            );

            Timer timer = new Timer();
            CuentaAtras cuenta = new CuentaAtras(this);
            if (rectanguloMario.overlaps(rectanguloEnemigo)) {
                if (choque == Enums.Choque.NO){
                    position=new Vector2(0, 30);
                    vidasPersonaje--;
                }else if(choque == Enums.Choque.SI){
                    enemigo.estadoEnemigo=Enums.EstadoEnemigo.MUERTO;
                    //if (enemigo.estadoEnemigo==Enums.EstadoEnemigo.VIVO)
                    timer.schedule(cuenta, 0, 100);

                    System.out.println("EEEE"+cuenta.getCountdown());
                    if (cuenta.getCountdown()==0)
                        level.getEnemigos().removeValue(enemigo,true);
                }
            }
        }

        for (Vida vida : level.getVidas()) {
            Rectangle rectanguloVida = new Rectangle(
                    vida.position.x - Constants.VIDA_COLLISION_RADIUS,
                    vida.position.y - Constants.VIDA_COLLISION_RADIUS,
                    2 * Constants.VIDA_COLLISION_RADIUS,
                    2 * Constants.VIDA_COLLISION_RADIUS
            );

            if (rectanguloMario.overlaps(rectanguloVida)) {
                level.getVidas().removeValue(vida,true);
                vidasPersonaje++;
            }
        }

        for (Llave llave : level.getLlaves()) {
            Rectangle rectanguloLlave = new Rectangle(
                    llave.position.x - Constants.VIDA_COLLISION_RADIUS,
                    llave.position.y - Constants.VIDA_COLLISION_RADIUS,
                    2 * Constants.VIDA_COLLISION_RADIUS,
                    2 * Constants.VIDA_COLLISION_RADIUS
            );

            if (rectanguloMario.overlaps(rectanguloLlave)) {
                level.getLlaves().removeValue(llave,true);
                System.out.println("HAS GANADO!!!!");
                NuevoNivel nuevoNivel = new NuevoNivel();
            }
        }

        for (Pinchos pinchos : level.getPinchos()) {
            Rectangle rectanguloPinchos = new Rectangle(
                    pinchos.position.x - Constants.PINCHOS_COLLISION_RADIUS,
                    pinchos.position.y - Constants.PINCHOS_COLLISION_RADIUS,
                    2 * Constants.PINCHOS_COLLISION_RADIUS,
                    2 * Constants.PINCHOS_COLLISION_RADIUS
            );

            if (rectanguloMario.overlaps(rectanguloPinchos)) {
                vidasPersonaje--;
                position = new Vector2(0, 30);
            }
        }

        for (Pocion pocion : level.getPociones()) {
            Rectangle rectanguloPociones = new Rectangle(
                    pocion.position.x - Constants.POCION_COLLISION_RADIUS,
                    pocion.position.y - Constants.POCION_COLLISION_RADIUS,
                    2 * Constants.POCION_COLLISION_RADIUS,
                    2 * Constants.POCION_COLLISION_RADIUS
            );

            if (rectanguloMario.overlaps(rectanguloPociones)) {
                choque=Enums.Choque.SI;
                Timer timer = new Timer();
                cuentaAtras = new CuentaAtras(this);
                timer.schedule(cuentaAtras, 0, 1000);
                //cuentaAtras.setCountdown(5);
                level.getPociones().removeValue(pocion,true);
            }
        }
    }

    boolean SobrePlataforma(Plataforma plataforma) {
        boolean pieIzqEn = false;
        boolean pieDerEn = false;
        boolean encima = false;

        if (lastFramePosition.y - Constants.MARIO_EYE_HEIGHT >= plataforma.top &&
                position.y - Constants.MARIO_EYE_HEIGHT < plataforma.top) {

            float pieIzq = position.x - Constants.MARIO_STANCE_WIDTH / 2;
            float pieDer = position.x + Constants.MARIO_STANCE_WIDTH / 2;

            pieIzqEn = (plataforma.left < pieIzq && plataforma.right > pieIzq);
            pieDerEn = (plataforma.left < pieDer && plataforma.right > pieDer);

            encima = (plataforma.left > pieIzq && plataforma.right < pieDer);
        }
        return pieIzqEn || pieDerEn || encima;
    }

    private void moveLeft(float delta) {
        if (jumpState == Enums.JumpState.GROUNDED && walkState != Enums.WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }
        walkState = Enums.WalkState.WALKING;
        facing = Enums.Direction.LEFT;
        position.x -= delta * Constants.MARIO_MOVE_SPEED;
    }

    private void moveRight(float delta) {
        if (jumpState == Enums.JumpState.GROUNDED && walkState != Enums.WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }
        walkState = Enums.WalkState.WALKING;
        facing = Enums.Direction.RIGHT;
        position.x += delta * Constants.MARIO_MOVE_SPEED;
    }

    private void startJump() {
        jumpState = Enums.JumpState.JUMPING;
        jumpStartTime = TimeUtils.nanoTime();
        continueJump();
    }

    private void continueJump() {
        if (jumpState == Enums.JumpState.JUMPING) {
            float jumpDuration = MathUtils.nanoToSec * (TimeUtils.nanoTime() - jumpStartTime);
            if (jumpDuration < Constants.MAX_JUMP_DURATION) {
                velocity.y = Constants.JUMP_SPEED;
            } else {
                endJump();
            }
        }
    }

    private void endJump() {
        if (jumpState == Enums.JumpState.JUMPING) {
            jumpState = Enums.JumpState.FALLING;
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion region = Assets.instance.marioAssets.standing;

        if (facing == Enums.Direction.RIGHT && jumpState != Enums.JumpState.GROUNDED) {
            region = Assets.instance.marioAssets.jumping;
        } else if (facing == Enums.Direction.RIGHT && walkState == Enums.WalkState.NOT_WALKING) {
            region = Assets.instance.marioAssets.standing;
        } else if (facing == Enums.Direction.RIGHT && walkState == Enums.WalkState.WALKING) {
            float walkTimeSeconds = Utils.secondsSince(walkStartTime);
            region = (TextureRegion) Assets.instance.marioAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
        }else if (facing == Enums.Direction.LEFT && jumpState != Enums.JumpState.GROUNDED) {
            region = Assets.instance.marioAssets.jumpingLeft;
        } else if (facing == Enums.Direction.LEFT && walkState == Enums.WalkState.NOT_WALKING) {
            region = Assets.instance.marioAssets.standingLeft;
        } else if (facing == Enums.Direction.LEFT && walkState == Enums.WalkState.WALKING) {
            float walkTimeSeconds = Utils.secondsSince(walkStartTime);
            region = (TextureRegion) Assets.instance.marioAssets.walkingLeftAnimation.getKeyFrame(walkTimeSeconds);
        }

        Utils.drawTextureRegion(batch, region,
                position.x - Constants.MARIO_EYE_POSITION.x,
                position.y - Constants.MARIO_EYE_POSITION.y
        );
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public int getVidasPersonaje() {
        return vidasPersonaje;
    }

    public void setVidasPersonaje(int vidasPersonaje) {
        this.vidasPersonaje = vidasPersonaje;
    }

    public Enums.Choque getChoque() {
        return choque;
    }

    public void setChoque(Enums.Choque choque) {
        this.choque = choque;
    }

    public CuentaAtras getCuentaAtras() {
        return cuentaAtras;
    }

    public void setCuentaAtras(CuentaAtras cuentaAtras) {
        this.cuentaAtras = cuentaAtras;
    }
}