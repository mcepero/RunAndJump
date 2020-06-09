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
import com.mygdx.mario.utils.Constants;
import com.mygdx.mario.utils.CuentaAtras;
import com.mygdx.mario.utils.Enums;
import com.mygdx.mario.Level;
import com.mygdx.mario.utils.Utils;

import java.util.Timer;

public class Mario {

    public final static String TAG = Mario.class.getName();

    public boolean botonSaltar;
    public boolean botonIzquierda;
    public boolean botonDerecha;
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

    public void update(float delta, Array<Plataforma> plataformas, Array<Bloque2> bloques2, Array<Bloque3> bloques3) {
        lastFramePosition.set(position);
        velocity.y -= Constants.GRAVITY;
        position.mulAdd(velocity, delta);

        if (jumpState != Enums.JumpState.JUMPING) {
            jumpState = Enums.JumpState.FALLING;

            if (position.y - Constants.PERSONAJE_EYE_HEIGHT < 0) {
                jumpState = Enums.JumpState.GROUNDED;
                position.y = Constants.PERSONAJE_EYE_HEIGHT;
                velocity.y = 0;
            }

            for (Plataforma plataforma : plataformas) {
                if (SobrePlataforma(plataforma)) {
                    jumpState = Enums.JumpState.GROUNDED;
                    velocity.y = 0;
                    position.y = plataforma.top + Constants.PERSONAJE_EYE_HEIGHT;
                }
            }

            for (Bloque2 bloque2 : bloques2) {
                if (SobreBloque(bloque2)) {
                    jumpState = Enums.JumpState.GROUNDED;
                    velocity.y = 0;
                    position.y = bloque2.top + Constants.PERSONAJE_EYE_HEIGHT;
                    level.getBloques2().removeValue(bloque2,true);
                }
            }

            for (Bloque3 bloque3 : bloques3) {
                if (SobreBloque3(bloque3)) {
                    jumpState = Enums.JumpState.GROUNDED;
                    velocity.y = 0;
                    position.y = bloque3.top + Constants.PERSONAJE_EYE_HEIGHT;
                }
            }

        }

        /*if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveLeft(delta);
            botonIzquierda=true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveRight(delta);
            botonDerecha=true;
        } else {
            walkState = Enums.WalkState.NOT_WALKING;
        }*/

        if (jumpState != Enums.JumpState.RECOILING) {

            boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT) || botonIzquierda;
            boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT) || botonDerecha;

            if (left && !right) {
                moveLeft(delta);
            } else if (right && !left) {
                moveRight(delta);
            } else {
                walkState = Enums.WalkState.NOT_WALKING;
            }
        }

        /*if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            switch (jumpState) {
                case GROUNDED:
                    startJump();
                    botonSaltar=true;
                    break;
                case JUMPING:
                    continueJump();
            }
        } else {
            endJump();
        }*/

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || botonSaltar) {
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

        if (position.x <= -4550)
            position.x = -4550;
        else if (position.x >= 2420)
            position.x = 2420;

        level.getEnemigos().begin();
        Rectangle rectanguloPersonaje= new Rectangle(
                position.x -Constants.PERSONAJE_STANCE_WIDTH /2 ,
                position.y -  Constants.PERSONAJE_EYE_HEIGHT,
                Assets.instance.marioAssets.jumping.getRegionWidth(),
                Assets.instance.marioAssets.jumping.getRegionHeight());
       // System.out.println("RM-X " + position.x + " RM-Y " + position.y + " f " + lastFramePosition.x +" e" + lastFramePosition.y );
        for (Enemigo enemigo : level.getEnemigos()) {
            Rectangle rectanguloEnemigo = new Rectangle(
                    enemigo.position.x,
                    enemigo.position.y,
                    enemigo.width,
                    enemigo.height
            );

            if (rectanguloPersonaje.overlaps(rectanguloEnemigo)) {
                if (choque == Enums.Choque.NO) {
                    position = new Vector2(-4450, 0);
                    vidasPersonaje--;
                } else if (choque == Enums.Choque.SI) {
                    enemigo.estadoEnemigo = Enums.EstadoEnemigo.MUERTO;
                    float walkTimeSeconds = Utils.secondsSince(enemigo.getWalkStartTime());
                    if (enemigo.direction == Enums.Direction.LEFT) {
                        if (enemigo.acabar == true) {
                            level.getEnemigos().removeValue(enemigo, true);
                        }
                    } else if (enemigo.direction == Enums.Direction.RIGHT) {
                        if (enemigo.acabar == true) {
                            level.getEnemigos().removeValue(enemigo, true);
                        }
                    }
                }
            }
        }
        level.getEnemigos().end();
        level.getVidas().begin();
        for (Vida vida : level.getVidas()) {
            Rectangle rectanguloVida = new Rectangle(
                    vida.position.x/* - Constants.VIDA_COLLISION_RADIUS*/,
                    vida.position.y/* - Constants.VIDA_COLLISION_RADIUS*/,
                    vida.width, //2 * Constants.VIDA_COLLISION_RADIUS,
                    vida.height//2 * Constants.VIDA_COLLISION_RADIUS
            );

            if (rectanguloPersonaje.overlaps(rectanguloVida)) {
                level.getVidas().removeValue(vida, true);
                vidasPersonaje++;
            }
        }
        level.getVidas().end();

        for (Llave llave : level.getLlaves()) {
            Rectangle rectanguloLlave = new Rectangle(
                    llave.x,
                    llave.y+10,
                    Assets.instance.llaveAssets.llave.getRegionWidth(),
                    Assets.instance.llaveAssets.llave.getRegionHeight()
            );

            if (rectanguloPersonaje.overlaps(rectanguloLlave)) {
                level.getLlaves().removeValue(llave, true);
            }
        }

        for (Pinchos pinchos : level.getPinchos()) {
            Rectangle rectanguloPinchos = new Rectangle(
                    pinchos.position.x-Constants.PINCHOS_COLLISION_RADIUS,
                    pinchos.position.y-Constants.PINCHOS_COLLISION_RADIUS,
                    pinchos.width,
                    pinchos.height
            );

            if (rectanguloPersonaje.overlaps(rectanguloPinchos)) {
                vidasPersonaje--;
                position = new Vector2(-4450, 0);
            }
        }

        level.getBolas().begin();
        for (Bola bola : level.getBolas()) {
            Rectangle rectanguloBola = new Rectangle(
                    bola.position.x,
                    bola.position.y,
                    Assets.instance.bolaAssets.bola.getRegionWidth(),
                    Assets.instance.bolaAssets.bola.getRegionHeight()
            );

            if (rectanguloPersonaje.overlaps(rectanguloBola)) {
                System.out.println("CHOQUEEEEEE");
                System.out.println("Rx " + rectanguloBola.x + " Ry " + rectanguloBola.y + " width " + rectanguloBola.width + " height " + rectanguloBola.height);
                System.out.println("x " +bola.position.x + " y " + bola.position.y);
                System.out.println("MARIO: " + rectanguloPersonaje.x+ " " +rectanguloPersonaje.y + " BOla: " + rectanguloBola.x + " " + rectanguloBola.y);
                vidasPersonaje--;
                position = new Vector2(-4450, 0);
            }

            /*System.out.println(lastFramePosition.dst(bola.position));
            if (position.dst(bola.position) < 25.0) {

                System.out.println("CHOQUEEEEEE");
                System.out.println("MARIO: " + rectanguloMario.x+ " " +rectanguloMario.y + " BOla: " + rectanguloBola.x + " " + rectanguloBola.y);
                vidasPersonaje--;
                position = new Vector2(-4450, 30);
            }*/
        }
        level.getBolas().end();

        for (Pocion pocion : level.getPociones()) {
            Rectangle rectanguloPociones = new Rectangle(
                    pocion.position.x,
                    pocion.position.y,
                    pocion.width,
                    pocion.height
            );

            if (rectanguloPersonaje.overlaps(rectanguloPociones)) {
                choque = Enums.Choque.SI;
                Timer timer = new Timer();
                cuentaAtras = new CuentaAtras(this);
                timer.schedule(cuentaAtras, 0, 1000);
                cuentaAtras.setCountdown(5);
                level.getPociones().removeValue(pocion, true);
                if (choque==Enums.Choque.NO){
                    timer.cancel();
                    timer.purge();
                }

            }
        }

    }

    boolean SobrePlataforma(Plataforma plataforma) {
        boolean pieIzqEn = false;
        boolean pieDerEn = false;
        boolean encima = false;

        if (lastFramePosition.y - Constants.PERSONAJE_EYE_HEIGHT >= plataforma.top &&
                position.y - Constants.PERSONAJE_EYE_HEIGHT < plataforma.top) {

            float pieIzq = position.x - Constants.PERSONAJE_STANCE_WIDTH / 2;
            float pieDer = position.x + Constants.PERSONAJE_STANCE_WIDTH / 2;

            pieIzqEn = (plataforma.left < pieIzq && plataforma.right > pieIzq);
            pieDerEn = (plataforma.left < pieDer && plataforma.right > pieDer);

            encima = (plataforma.left > pieIzq && plataforma.right < pieDer);
        }
        return pieIzqEn || pieDerEn || encima;
    }

    boolean SobreBloque(Bloque2 bloque2) {
        boolean pieIzqEn = false;
        boolean pieDerEn = false;
        boolean encima = false;

        if (lastFramePosition.y - Constants.PERSONAJE_EYE_HEIGHT >= bloque2.top &&
                position.y - Constants.PERSONAJE_EYE_HEIGHT < bloque2.top) {

            float pieIzq = position.x - Constants.PERSONAJE_STANCE_WIDTH / 2;
            float pieDer = position.x + Constants.PERSONAJE_STANCE_WIDTH / 2;

            pieIzqEn = (bloque2.left < pieIzq && bloque2.right > pieIzq);
            pieDerEn = (bloque2.left < pieDer && bloque2.right > pieDer);

            encima = (bloque2.left > pieIzq && bloque2.right < pieDer);
        }
        return pieIzqEn || pieDerEn || encima;
    }

    boolean SobreBloque3(Bloque3 bloque3) {
        boolean pieIzqEn = false;
        boolean pieDerEn = false;
        boolean encima = false;

        if (lastFramePosition.y - Constants.PERSONAJE_EYE_HEIGHT >= bloque3.top &&
                position.y - Constants.PERSONAJE_EYE_HEIGHT < bloque3.top) {

            float pieIzq = position.x - Constants.PERSONAJE_STANCE_WIDTH / 2;
            float pieDer = position.x + Constants.PERSONAJE_STANCE_WIDTH / 2;

            pieIzqEn = (bloque3.left < pieIzq && bloque3.right > pieIzq);
            pieDerEn = (bloque3.left < pieDer && bloque3.right > pieDer);

            encima = (bloque3.left > pieIzq && bloque3.right < pieDer);
        }
        return pieIzqEn || pieDerEn || encima;
    }

    private void moveLeft(float delta) {
        if (jumpState == Enums.JumpState.GROUNDED && walkState != Enums.WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }
        walkState = Enums.WalkState.WALKING;
        facing = Enums.Direction.LEFT;
        position.x -= delta * Constants.PERSONAJE_MOVE_SPEED;
    }

    private void moveRight(float delta) {
        if (jumpState == Enums.JumpState.GROUNDED && walkState != Enums.WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }
        walkState = Enums.WalkState.WALKING;
        facing = Enums.Direction.RIGHT;
        position.x += delta * Constants.PERSONAJE_MOVE_SPEED;
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
                position.x - Constants.PERSONAJE_EYE_POSITION.x,
                position.y - Constants.PERSONAJE_EYE_POSITION.y
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
