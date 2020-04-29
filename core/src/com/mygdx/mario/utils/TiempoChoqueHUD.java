package com.mygdx.mario.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TiempoChoqueHUD {
    public final Viewport viewport;
    final BitmapFont font;

    public TiempoChoqueHUD() {
        this.viewport = new ExtendViewport(Constants.HUD_VIEWPORT_SIZE, Constants.HUD_VIEWPORT_SIZE);
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(1);
    }

    public void render(SpriteBatch batch, int tiempo) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        //batch.begin();

        final String hudString =
                "Tiempo de choque: " + tiempo;

        font.draw(batch, hudString, Constants.HUD_MARGIN, viewport.getWorldHeight() - Constants.HUD_MARGIN);

       // batch.end();
    }
}
