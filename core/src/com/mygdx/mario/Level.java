package com.mygdx.mario;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mario.entities.Bloque2;
import com.mygdx.mario.entities.Bloque3;
import com.mygdx.mario.entities.Bola;
import com.mygdx.mario.entities.Enemigo;
import com.mygdx.mario.entities.Fondo;
import com.mygdx.mario.entities.Llave;
import com.mygdx.mario.entities.Mario;
import com.mygdx.mario.entities.Pinchos;
import com.mygdx.mario.entities.Plataforma;
import com.mygdx.mario.entities.Pocion;
import com.mygdx.mario.entities.Vida;

public class Level {
    public static final String TAG = Level.class.getName();

    private Viewport viewport;

    public Mario mario;
    Array<Plataforma> plataformas;
    private DelayedRemovalArray<Enemigo> enemigos;
    private DelayedRemovalArray<Vida> vidas;
    private DelayedRemovalArray<Llave> llaves;
    private DelayedRemovalArray<Bola> bolas;
    private Array<Fondo> fondos;
    private Array<Pinchos> pinchos;
    private Array<Pocion> pociones;
    private Array<Bloque2> bloques2;
    private Array<Bloque3> bloques3;


    public Level(Viewport viewport) {
        this.viewport = viewport;
        plataformas = new Array<Plataforma>();
        fondos = new Array<Fondo>();
        pinchos = new Array<Pinchos>();
        pociones = new Array<Pocion>();
        bloques2 = new Array<>();
        bloques3 = new Array<>();
        initializeDebugLevel();
    }

    public void update(float delta) {
        mario.update(delta, plataformas, bloques2, bloques3);

        for (int i = 0; i < enemigos.size; i++) {
            Enemigo enemigo = enemigos.get(i);
            enemigo.update(delta);
        }

        for (int i = 0; i < bolas.size; i++) {
            Bola bola= bolas.get(i);
            bola.update(delta);
        }
    }

    public void render(SpriteBatch batch) {
        for (Fondo fondo : fondos) {
            fondo.render(batch);
        }

        for (Plataforma plataforma : plataformas) {
            plataforma.render(batch);
        }

        for (Vida vida : vidas) {
            vida.render(batch);
        }

        for (Llave llave : llaves) {
            llave.render(batch);
        }

        for (Pinchos pinchos : pinchos) {
            pinchos.render(batch);
        }

        for (Pocion pocion : pociones) {
            pocion.render(batch);
        }

        for (Enemigo enemigo : enemigos) {
            enemigo.render(batch);
        }

        for (Bloque2 bloque2 : bloques2) {
            bloque2.render(batch);
        }

        for (Bloque3 bloque3 : bloques3) {
            bloque3.render(batch);
        }

        for (Bola bola : bolas) {
            bola.render(batch);
        }

        mario.render(batch);
    }

    private void initializeDebugLevel() {
        mario = new Mario(new Vector2(-4450,0),this);
        enemigos = new DelayedRemovalArray<Enemigo>();
        vidas = new DelayedRemovalArray<Vida>();
        llaves = new DelayedRemovalArray<Llave>();
        bolas = new DelayedRemovalArray<Bola>();
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Mario getMario() {
        return mario;
    }

    public void setMario(Mario mario) {
        this.mario = mario;
    }

    public DelayedRemovalArray<Enemigo> getEnemigos() {
        return enemigos;
    }

    public void setEnemigos(DelayedRemovalArray<Enemigo> enemigos) {
        this.enemigos = enemigos;
    }

    public Array<Plataforma> getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(Array<Plataforma> plataformas) {
        this.plataformas = plataformas;
    }

    public void setVidas(DelayedRemovalArray<Vida> vidas) {
        this.vidas = vidas;
    }

    public DelayedRemovalArray<Vida> getVidas() {
        return vidas;
    }

    public DelayedRemovalArray<Llave> getLlaves() {
        return llaves;
    }

    public void setLlaves(DelayedRemovalArray<Llave> llaves) {
        this.llaves = llaves;
    }

    public Array<Fondo> getFondos() {
        return fondos;
    }

    public void setFondos(Array<Fondo> fondos) {
        this.fondos = fondos;
    }

    public Array<Pinchos> getPinchos() {
        return pinchos;
    }

    public void setPinchos(Array<Pinchos> pinchos) {
        this.pinchos = pinchos;
    }

    public Array<Pocion> getPociones() {
        return pociones;
    }

    public void setPociones(Array<Pocion> pociones) {
        this.pociones = pociones;
    }

    public Array<Bloque2> getBloques2() {
        return bloques2;
    }

    public void setBloques2(Array<Bloque2> bloques2) {
        this.bloques2 = bloques2;
    }

    public Array<Bloque3> getBloques3() {
        return bloques3;
    }

    public void setBloques3(Array<Bloque3> bloques3) {
        this.bloques3 = bloques3;
    }

    public DelayedRemovalArray<Bola> getBolas() {
        return bolas;
    }

    public void setBolas(DelayedRemovalArray<Bola> bolas) {
        this.bolas = bolas;
    }

    public void siguienteNivel(){

    }
}
