package com.mygdx.mario;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.mario.utils.Constants;
import com.mygdx.mario.utils.ControlesMovil;
import com.mygdx.mario.utils.Enums;
import com.mygdx.mario.utils.LevelLoader;
import com.mygdx.mario.utils.VidasHUD;
import com.mygdx.mario.utils.TiempoChoqueHUD;
import com.mygdx.mario.utils.Utils;
import com.mygdx.mario.utils.YouLostHUD;

public class GamePlayScreen extends ScreenAdapter {

    Level level;
    public ExtendViewport gameplayViewport;

    SpriteBatch batch;
    com.mygdx.mario.utils.ChaseCam chaseCam;

    private VidasHUD hud;
    private YouLostHUD gameOverOverlay;
    private TiempoChoqueHUD tiempoChoqueHUD;
    long levelEndOverlayStartTime;
    private ControlesMovil controlesMovil;
    private String numeroNivel = "Nivel1";
    //Mario mario;
    public String nivel;
    Sound sound;
    Music music;
    Estado estadoPartida;

    public GamePlayScreen(String nivel) {
        this.nivel=nivel;
        music = Gdx.audio.newMusic(Gdx.files.internal("sound/principal2.mp3"));
        music.setVolume(0.2f);
        music.setLooping(true);
        music.play();
        estadoPartida=Estado.JUGANDO;
    }

    @Override
    public void show() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        level = new Level(gameplayViewport);
        batch = new SpriteBatch();
        gameplayViewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        Gdx.gl.glClearColor(0,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        level = LevelLoader.load(nivel, level.getViewport(),3);
        //mario=level.getMario();
        chaseCam = new com.mygdx.mario.utils.ChaseCam(gameplayViewport.getCamera(), level.getMario());

        hud = new VidasHUD();
        tiempoChoqueHUD = new TiempoChoqueHUD();
        gameOverOverlay = new YouLostHUD(this);
        controlesMovil = new ControlesMovil(level.getMario());

        if (onMobile()) {
            Gdx.input.setInputProcessor(controlesMovil);
        }


        /*AssetManager amm = new AssetManager();
        Assets.instance.init(am);
        batch = new SpriteBatch();
        hud = new VidasHUD();
        starNewLevel();*/
    }

    @Override
    public void resize(int width, int height) {
        gameplayViewport.update(width, height, true);
        hud.viewport.update(width, height, true);

        tiempoChoqueHUD.viewport.update(width, height, true);

        gameOverOverlay.viewport.update(width, height, true);

        controlesMovil.viewport.update(width, height, true);
        controlesMovil.recalculateButtonPositions();
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
        music.dispose();
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

        level.render(batch);
        hud.render(batch, level.getMario().getVidasPersonaje());
        if (level.getMario().getChoque()== Enums.Choque.SI){
            tiempoChoqueHUD.render(batch,level.getMario().getCuentaAtras().getCountdown());
        }

        nuevoNivel(batch);
        batch.end();
        nivelAcabado(batch);

        if (onMobile()) {
            controlesMovil.render(batch);
        }

    }

    private boolean onMobile() {
        return Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS;
    }

    private void nivelAcabado(SpriteBatch batch) {
        if (level.getMario().getVidasPersonaje()<=0 || (level.getMario().isTerminado()==true && numeroNivel.equals("Nivel3"))) {
            if (levelEndOverlayStartTime == 0) {
                levelEndOverlayStartTime = TimeUtils.nanoTime();
                gameOverOverlay.init();
            }
            //sound.dispose();
            gameOverOverlay.render(batch);
            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0;
            }
        }
    }

    public void nuevoNivel(SpriteBatch batch) {
        if (level.getLlaves().size==0) {
            if (numeroNivel.equals("Nivel1")) {
                level = LevelLoader.load("Nivel2", gameplayViewport, level.getMario().getVidasPersonaje());
                numeroNivel="Nivel2";
            }else if(numeroNivel.equals("Nivel2")){
                level = LevelLoader.load("Nivel3", gameplayViewport, level.getMario().getVidasPersonaje());
                numeroNivel="Nivel3";
            }
            chaseCam = new com.mygdx.mario.utils.ChaseCam(gameplayViewport.getCamera(), level.getMario());
            resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            controlesMovil.mario = level.getMario();
        }
    }

    public void nuevaPartida(SpriteBatch batch) {
        music.dispose();
        level = LevelLoader.load("Nivel1", gameplayViewport, level.getMario().getVidasPersonaje());
        chaseCam = new com.mygdx.mario.utils.ChaseCam(gameplayViewport.getCamera(), level.getMario());
        music = Gdx.audio.newMusic(Gdx.files.internal("sound/principal2.mp3"));
        music.setVolume(0.2f);
        music.setLooping(true);
        music.play();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        controlesMovil.mario = level.getMario();
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getNumeroNivel() {
        return numeroNivel;
    }

    public void setNumeroNivel(String numeroNivel) {
        this.numeroNivel = numeroNivel;
    }

    public Estado getEstadoPartida() {
        return estadoPartida;
    }

    public void setEstadoPartida(Estado estadoPartida) {
        this.estadoPartida = estadoPartida;
    }

    public enum Estado{
        PAUSA,
        JUGANDO
    }

}
