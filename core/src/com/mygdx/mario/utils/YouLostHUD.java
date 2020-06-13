package com.mygdx.mario.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mario.GamePlayScreen;
import com.mygdx.mario.entities.Fondo;

public class YouLostHUD {
    public final Viewport viewport;
    BitmapFont font;
    long startTime;
    private Texture reiniciar;
    private Texture salir;
    GamePlayScreen gamePlayScreen;
    Sound fin;

    public YouLostHUD(GamePlayScreen gamePlayScreen) {
        this.viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        font = new BitmapFont(Gdx.files.internal(Constants.FONT_FILE));
        font.getData().setScale(1);
        reiniciar = new Texture(Constants.NUEVA_PARTIDA);
        this.gamePlayScreen = gamePlayScreen;
    }

    public void init() {
        startTime = TimeUtils.nanoTime();
    }

    public void render(SpriteBatch batch) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        float timeElapsed = Utils.secondsSince(startTime);
        Fondo fondo = new Fondo(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(),viewport.getScreenHeight());
        fondo.render(batch);
        Gdx.gl.glClearColor(
                0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        font.setColor(Color.BLACK);
        if (gamePlayScreen.getNumeroNivel().equals("Nivel3") && gamePlayScreen.getLevel().getLlaves().size==0){
            font.draw(batch, Constants.VICTORIA_MENSAJE, viewport.getWorldWidth() / 2, viewport.getWorldHeight() - 50, 2, Align.center, false);
        }else{
            font.draw(batch, Constants.GAME_OVER_MESSAGE, viewport.getWorldWidth() / 2, viewport.getWorldHeight() - 50, 2, Align.center, false);
        }

        batch.draw(reiniciar, viewport.getWorldWidth()/2.5f,viewport.getWorldHeight()/4.5f,reiniciar.getWidth()/2,reiniciar.getHeight()/2);
        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.getCamera().unproject(tmp);
            Rectangle restartBounds = new Rectangle(viewport.getWorldWidth()/2.5f,viewport.getWorldHeight()/4.5f,reiniciar.getWidth()/2,reiniciar.getHeight()/2);

            if (restartBounds.contains(tmp.x, tmp.y)) {
                    gamePlayScreen.nivel="Nivel1";
                    gamePlayScreen.getLevel().getMario().setVidasPersonaje(3);
                    gamePlayScreen.nuevaPartida(batch);
            }
        }
    }
}
