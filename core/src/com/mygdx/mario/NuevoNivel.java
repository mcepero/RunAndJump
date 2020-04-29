package com.mygdx.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mario.utils.Constants;
import com.mygdx.mario.utils.Utils;

public class NuevoNivel {
    public final Viewport viewport;
    final BitmapFont font;
    long startTime;

    public NuevoNivel() {
        this.viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        font = new BitmapFont(Gdx.files.internal(Constants.FONT_FILE));
        font.getData().setScale(1);
    }

    public void init() {
        startTime = TimeUtils.nanoTime();
    }

    public void render(SpriteBatch batch) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        //batch.begin();
        float timeElapsed = Utils.secondsSince(startTime);
        Gdx.gl.glClearColor(
                0,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        font.draw(batch, "Hola!", viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0, Align.center, false);

       // batch.end();

    }
}
