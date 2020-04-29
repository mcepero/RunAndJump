package com.mygdx.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.mario.utils.Constants;
import com.mygdx.mario.utils.Enums;
import com.mygdx.mario.utils.LevelLoader;
import com.mygdx.mario.utils.MarioHUD;
import com.mygdx.mario.utils.TiempoChoqueHUD;
import com.mygdx.mario.utils.Utils;
import com.mygdx.mario.utils.YouLostHUD;

public class GamePlayScreen extends ScreenAdapter {


    Level level;
    public ExtendViewport gameplayViewport;

    SpriteBatch batch;
    com.mygdx.mario.utils.ChaseCam chaseCam;

    private MarioHUD hud;
    private YouLostHUD gameOverOverlay;
    private TiempoChoqueHUD tiempoChoqueHUD;
    private NuevoNivel nuevoNivel;
    long levelEndOverlayStartTime;

    public String nivel;

    public GamePlayScreen(String nivel) {
        this.nivel=nivel;
    }

    @Override
    public void show() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        level = new Level(gameplayViewport);
        batch = new SpriteBatch();
        gameplayViewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        level = LevelLoader.load(nivel, level.getViewport());

        chaseCam = new com.mygdx.mario.utils.ChaseCam(gameplayViewport.getCamera(), level.getMario());

        hud = new MarioHUD();
        tiempoChoqueHUD = new TiempoChoqueHUD();
        gameOverOverlay = new YouLostHUD();
        nuevoNivel = new NuevoNivel();
    }

    @Override
    public void resize(int width, int height) {
        gameplayViewport.update(width, height, true);
        hud.viewport.update(width, height, true);

        tiempoChoqueHUD.viewport.update(width, height, true);

        nuevoNivel.viewport.update(width, height, true);
        gameOverOverlay.viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }

    @Override
    public void render(float delta) {
        level.update(delta);
        chaseCam.update();
        gameplayViewport.apply();
        Gdx.gl.glClearColor(
                0,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(gameplayViewport.getCamera().combined);
        batch.begin();

        //batch.draw(Assets.instance.fondoAssets.fondo, level.getMario().getPosition().x - gameplayViewport.getScreenWidth()/2, gameplayViewport.getScreenY() - Constants.WORLD_SIZE+210, gameplayViewport.getScreenWidth(), gameplayViewport.getScreenHeight());

        level.render(batch);
        hud.render(batch, level.getMario().getVidasPersonaje());
        if (level.getMario().getChoque()== Enums.Choque.SI){
            tiempoChoqueHUD.render(batch,level.getMario().getCuentaAtras().getCountdown());
        }
        renderNuevoNivel(batch);
        batch.end();
        renderLevelEndOverlays(batch);

    }

    private void renderLevelEndOverlays(SpriteBatch batch) {
        if (level.getMario().getVidasPersonaje()<=0) {
            if (levelEndOverlayStartTime == 0) {
                levelEndOverlayStartTime = TimeUtils.nanoTime();
                gameOverOverlay.init();
            }

            gameOverOverlay.render(batch);
            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0;
            }
        }
    }

    private void renderNuevoNivel(SpriteBatch batch) {
        if (level.getLlaves().size==0) {
            nuevoNivel.init();
        }
    }
}
