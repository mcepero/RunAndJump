package com.mygdx.mario.utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mario.Assets;

public class VidasHUD {
    public final Viewport viewport;
    final BitmapFont font;

    public VidasHUD() {
        this.viewport = new ExtendViewport(com.mygdx.mario.utils.Constants.HUD_VIEWPORT_SIZE, com.mygdx.mario.utils.Constants.HUD_VIEWPORT_SIZE);
        font = new BitmapFont();
        font.getData().setScale(1);
    }

    public void render(SpriteBatch batch, int lives) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        final TextureRegion vida = Assets.instance.vidaAssets.vida;
        for (int i = 1; i <= lives; i++) {
            final Vector2 drawPosition = new Vector2(
                    viewport.getWorldWidth() - i * (com.mygdx.mario.utils.Constants.HUD_MARGIN / 2 + vida.getRegionWidth()),
                    viewport.getWorldHeight() - Constants.HUD_MARGIN - vida.getRegionHeight()
            );
            Utils.drawTextureRegion(
                    batch,
                    vida,
                    drawPosition.x,
                    drawPosition.y
            );
        }
    }
}
