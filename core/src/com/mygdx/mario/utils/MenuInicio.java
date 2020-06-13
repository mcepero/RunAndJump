package com.mygdx.mario.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mario.GamePlayScreen;
import com.mygdx.mario.Main;
import com.mygdx.mario.entities.Fondo;

public class MenuInicio implements Screen {
    SpriteBatch batch;
    public final Viewport viewport;
    BitmapFont font;
    long startTime;
    private Texture reiniciar;
    private Texture salir;
    Main main;
    //GamePlayScreen gamePlayScreen;
    Sound fin;
    public MenuInicio(Main main) {
        this.viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        reiniciar = new Texture(Constants.REANUDAR);
        batch = new SpriteBatch();
        this.main=main;
        font = new BitmapFont(Gdx.files.internal(Constants.FONT_FILE));
        font.getData().setScale(1);
    }

    public void init() {
        startTime = TimeUtils.nanoTime();
    }

    public void render(SpriteBatch batch) {

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        float timeElapsed = Utils.secondsSince(startTime);
        Texture texture = new Texture(Constants.FONDO_INICIO);
        batch.draw(texture, 0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        /*Gdx.gl.glClearColor(
                0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);*/

        font.setColor(Color.BLACK);
        font.draw(batch, "RUN AND JUMP!", viewport.getWorldWidth() / 2, viewport.getWorldHeight() - 50, 2, Align.center, false);

        batch.draw(reiniciar, viewport.getWorldWidth()/2.5f,viewport.getWorldHeight()/4.5f,reiniciar.getWidth()/2,reiniciar.getHeight()/2);
        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.getCamera().unproject(tmp);
            Rectangle restartBounds = new Rectangle(viewport.getWorldWidth()/2.5f,viewport.getWorldHeight()/4.5f,reiniciar.getWidth()/2,reiniciar.getHeight()/2);

            if (restartBounds.contains(tmp.x, tmp.y)) {
                main.juego();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
