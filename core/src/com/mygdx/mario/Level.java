package com.mygdx.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mario.entities.Enemigo;
import com.mygdx.mario.entities.Fondo;
import com.mygdx.mario.entities.Llave;
import com.mygdx.mario.entities.Mario;
import com.mygdx.mario.entities.Nube;
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
    Array<Nube> nubes;
    private DelayedRemovalArray<Vida> vidas;
    private DelayedRemovalArray<Llave> llaves;
    private Array<Fondo> fondos;
    private Array<Pinchos> pinchos;
    private Array<Pocion> pociones;


    public Level(Viewport viewport) {
        this.viewport = viewport;
        plataformas = new Array<Plataforma>();
        nubes = new Array<Nube>();
        fondos = new Array<Fondo>();
        pinchos = new Array<Pinchos>();
        pociones = new Array<Pocion>();
        initializeDebugLevel();
    }

    public void update(float delta) {
        mario.update(delta, plataformas);
        for (int i = 0; i < enemigos.size; i++) {
            Enemigo enemigo = enemigos.get(i);
            enemigo.update(delta);
        }
    }

    public void render(SpriteBatch batch) {

        for (Fondo fondo : fondos) {
            fondo.render(batch);
        }

        for (Plataforma plataforma : plataformas) {
            plataforma.render(batch);
        }

        for (Nube nube : nubes) {
            nube.render(batch);
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

        mario.render(batch);
    }

    private void initializeDebugLevel() {
        mario = new Mario(new Vector2(0, 30),this);
        enemigos = new DelayedRemovalArray<Enemigo>();
        vidas = new DelayedRemovalArray<Vida>();
        llaves = new DelayedRemovalArray<Llave>();
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

    public Array<Nube> getNubes() {
        return nubes;
    }

    public void setNubes(Array<Nube> nubes) {
        this.nubes = nubes;
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

    public void siguienteNivel(){

    }
}
