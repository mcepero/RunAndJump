package com.mygdx.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.mario.entities.Llave;
import com.mygdx.mario.utils.Constants;

public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    public MarioAssets marioAssets;
    public PlatformAssets platformAssets;
    public EnemimyAssets enemimyAssets;
    public NubeAssets nubeAssets;
    public VidaAssets vidaAssets;
    public LlaveAssets llaveAssets;
    public FondoAssets fondoAssets;
    public PinchosAssets pinchosAssets;
    public PocionAssets pocionAssets;
    public  Bloque2Assets bloque2Assets;
    public Bloque3Assets bloque3Assets;
    public  BolaAssets bolaAssets;
    public ControlesMovil controlesMovil;
    private AssetManager assetManager;

    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        marioAssets = new MarioAssets(atlas);
        platformAssets = new PlatformAssets(atlas);
        enemimyAssets = new EnemimyAssets(atlas);
        nubeAssets = new NubeAssets(atlas);
        vidaAssets = new VidaAssets(atlas);
        fondoAssets = new FondoAssets(atlas);
        llaveAssets = new LlaveAssets(atlas);
        pinchosAssets = new PinchosAssets(atlas);
        pocionAssets = new PocionAssets(atlas);
        bloque2Assets = new Bloque2Assets(atlas);
        bloque3Assets = new Bloque3Assets(atlas);
        bolaAssets = new BolaAssets(atlas);
        controlesMovil = new ControlesMovil(atlas);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    public class MarioAssets {
        public final TextureAtlas.AtlasRegion standing;
        public final TextureAtlas.AtlasRegion walkingRight;
        public final TextureAtlas.AtlasRegion jumping;
        public final TextureAtlas.AtlasRegion jump_stand;
        public final TextureAtlas.AtlasRegion falling;

        public final TextureAtlas.AtlasRegion standingLeft;
        public final TextureAtlas.AtlasRegion walkingLeft;
        public final TextureAtlas.AtlasRegion jumpingLeft;
        public final TextureAtlas.AtlasRegion jump_standLeft;
        public final TextureAtlas.AtlasRegion fallingLeft;


        public final Animation walkingRightAnimation;
        public final Animation jumpingRightAnimation;

        public final Animation walkingLeftAnimation;
        public final Animation jumpingLeftAnimation;

        public MarioAssets(TextureAtlas atlas) {
            // TextureAtlas atlas = new TextureAtlas("Mario.pack");
            standing = atlas.findRegion(Constants.STANDING);
            walkingRight = atlas.findRegion(Constants.WALK6);
            jumping = atlas.findRegion(Constants.JUMP);
            jump_stand = atlas.findRegion(Constants.JUMP_STAND);
            falling = atlas.findRegion(Constants.FALL);

            Array<TextureAtlas.AtlasRegion> jumpingRightFrames = new Array<TextureAtlas.AtlasRegion>();
            jumpingRightFrames.add(jumping);
            jumpingRightFrames.add(jump_stand);
            jumpingRightFrames.add(falling);
            jumpingRightAnimation = new Animation(Constants.MAX_JUMP_DURATION, jumpingRightFrames, Animation.PlayMode.LOOP);

            Array<TextureAtlas.AtlasRegion> walkingRightFrames = new Array<TextureAtlas.AtlasRegion>();
            walkingRightFrames.add(atlas.findRegion(Constants.WALK1));
            walkingRightFrames.add(atlas.findRegion(Constants.WALK2));
            walkingRightFrames.add(atlas.findRegion(Constants.WALK3));
            walkingRightFrames.add(atlas.findRegion(Constants.WALK4));
            walkingRightFrames.add(atlas.findRegion(Constants.WALK5));
            walkingRightFrames.add(atlas.findRegion(Constants.WALK6));
            walkingRightAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingRightFrames, Animation.PlayMode.LOOP);

            standingLeft = atlas.findRegion(Constants.STANDINGLEFT);
            walkingLeft = atlas.findRegion(Constants.WALK6LEFT);
            jumpingLeft = atlas.findRegion(Constants.JUMPLEFT);
            jump_standLeft = atlas.findRegion(Constants.JUMP_STAND);
            fallingLeft = atlas.findRegion(Constants.FALL);

            Array<TextureAtlas.AtlasRegion> jumpingLeftFrames = new Array<TextureAtlas.AtlasRegion>();
            jumpingLeftFrames.add(jumpingLeft);
            jumpingLeftFrames.add(jump_standLeft);
            jumpingLeftFrames.add(fallingLeft);
            jumpingLeftAnimation = new Animation(Constants.MAX_JUMP_DURATION, jumpingLeftFrames, Animation.PlayMode.LOOP);

            Array<TextureAtlas.AtlasRegion> walkingLeftFrames = new Array<TextureAtlas.AtlasRegion>();
            walkingLeftFrames.add(atlas.findRegion(Constants.WALK1LEFT));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALK2LEFT));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALK3LEFT));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALK4LEFT));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALK5LEFT));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALK6LEFT));
            walkingLeftAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingLeftFrames, Animation.PlayMode.LOOP);
        }
    }

    public class PlatformAssets {

        public final NinePatch platformNinePatch;

        public PlatformAssets(TextureAtlas atlas) {
            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.PLATFORM_SPRITE);
            int edge = Constants.PLATFORM_EDGE;
            platformNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }

    public class EnemimyAssets {
        public final TextureAtlas.AtlasRegion standingRight;
        public final Animation walkingRightAnimation;
        public final Animation deadRightAnimation;

        public final TextureAtlas.AtlasRegion standingLeft;
        public final Animation walkingLeftAnimation;
        public final Animation deadLeftAnimation;
        public final TextureAtlas.AtlasRegion dead;

        public EnemimyAssets(TextureAtlas atlas) {

            standingRight = atlas.findRegion(Constants.ENEMIGO1);
            dead = atlas.findRegion(Constants.ENEMIGO_MUERTO1);
            Array<TextureAtlas.AtlasRegion> walkingRightFrames = new Array<TextureAtlas.AtlasRegion>();
            walkingRightFrames.add(atlas.findRegion(Constants.ENEMIGO1));
            walkingRightFrames.add(atlas.findRegion(Constants.ENEMIGO2));
            walkingRightFrames.add(atlas.findRegion(Constants.ENEMIGO3));
            walkingRightFrames.add(atlas.findRegion(Constants.ENEMIGO4));
            walkingRightFrames.add(atlas.findRegion(Constants.ENEMIGO5));
            walkingRightFrames.add(atlas.findRegion(Constants.ENEMIGO6));
            walkingRightFrames.add(atlas.findRegion(Constants.ENEMIGO7));
            walkingRightAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingRightFrames, Animation.PlayMode.LOOP);

            standingLeft = atlas.findRegion(Constants.ENEMIGO1);

            Array<TextureAtlas.AtlasRegion> walkingLeftFrames = new Array<TextureAtlas.AtlasRegion>();
            walkingLeftFrames.add(atlas.findRegion(Constants.ENEMIGO1_LEFT));
            walkingLeftFrames.add(atlas.findRegion(Constants.ENEMIGO2_LEFT));
            walkingLeftFrames.add(atlas.findRegion(Constants.ENEMIGO3_LEFT));
            walkingLeftFrames.add(atlas.findRegion(Constants.ENEMIGO4_LEFT));
            walkingLeftFrames.add(atlas.findRegion(Constants.ENEMIGO5_LEFT));
            walkingLeftFrames.add(atlas.findRegion(Constants.ENEMIGO6_LEFT));
            walkingLeftFrames.add(atlas.findRegion(Constants.ENEMIGO7_LEFT));
            walkingLeftAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingLeftFrames, Animation.PlayMode.LOOP);

            Array<TextureAtlas.AtlasRegion> deadRightFrames = new Array<TextureAtlas.AtlasRegion>();
            deadRightFrames.add(atlas.findRegion(Constants.ENEMIGO_MUERTO1));
            deadRightFrames.add(atlas.findRegion(Constants.ENEMIGO_MUERTO2));
            deadRightFrames.add(atlas.findRegion(Constants.ENEMIGO_MUERTO3));
            deadRightFrames.add(atlas.findRegion(Constants.ENEMIGO_MUERTO4));
            deadRightFrames.add(atlas.findRegion(Constants.ENEMIGO_MUERTO5));
            deadRightFrames.add(atlas.findRegion(Constants.ENEMIGO_MUERTO6));
            deadRightFrames.add(atlas.findRegion(Constants.ENEMIGO_MUERTO7));
            deadRightAnimation = new Animation(0.1f, deadRightFrames, Animation.PlayMode.LOOP);

            Array<TextureAtlas.AtlasRegion> deadLeftFrames = new Array<TextureAtlas.AtlasRegion>();
            deadLeftFrames.add(atlas.findRegion(Constants.ENEMIGO_MUERTO1_LEFT));
            deadLeftFrames.add(atlas.findRegion(Constants.ENEMIGO_MUERTO2_LEFT));
            deadLeftFrames.add(atlas.findRegion(Constants.ENEMIGO_MUERTO3_LEFT));
            deadLeftFrames.add(atlas.findRegion(Constants.ENEMIGO_MUERTO4_LEFT));
            deadLeftFrames.add(atlas.findRegion(Constants.ENEMIGO_MUERTO5_LEFT));
            deadLeftFrames.add(atlas.findRegion(Constants.ENEMIGO_MUERTO6_LEFT));
            deadLeftFrames.add(atlas.findRegion(Constants.ENEMIGO_MUERTO7_LEFT));
            deadLeftAnimation = new Animation(0.1f, deadLeftFrames, Animation.PlayMode.LOOP);


        }
    }

    public class NubeAssets {

        public final NinePatch nubeNinePatch;

        public NubeAssets(TextureAtlas atlas) {
            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.NUBE1);
            int edge = Constants.NUBE_EDGE;
            nubeNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }

    public class VidaAssets {
        public final TextureAtlas.AtlasRegion vida;

        public VidaAssets(TextureAtlas atlas) {
            vida = atlas.findRegion(Constants.VIDA);
        }
    }

    public class LlaveAssets {
        public final TextureAtlas.AtlasRegion llave;
        public final NinePatch llaveNinePatch;

        public LlaveAssets(TextureAtlas atlas) {
            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.LLAVE);
            int edge = Constants.LLAVE_EDGE;
            llaveNinePatch = new NinePatch(region, edge, edge, edge, edge);
            llave = atlas.findRegion(Constants.LLAVE);
        }
    }

    public class FondoAssets {

        public final NinePatch fondoNinePatch;
        public final NinePatch fondo2NinePatch;
        public final NinePatch fondo3NinePatch;

        public FondoAssets(TextureAtlas atlas) {
            //fondo = atlas.findRegion(Constants.FONDO);
            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.FONDO);
            int edge = Constants.FONDO_EDGE;
            fondoNinePatch = new NinePatch(region, edge, edge, edge, edge);

            TextureAtlas.AtlasRegion region2 = atlas.findRegion(Constants.FONDO2);
            int edge2 = Constants.FONDO_EDGE;
            fondo2NinePatch = new NinePatch(region2, edge2, edge2, edge2, edge2);

            TextureAtlas.AtlasRegion region3 = atlas.findRegion(Constants.FONDO3);
            int edge3 = Constants.FONDO_EDGE;
            fondo3NinePatch = new NinePatch(region3, edge3, edge3, edge3, edge3);
        }
    }

    public class PinchosAssets {
        public final TextureAtlas.AtlasRegion pinchos;
        //public final NinePatch pinchosNinePatch;

        public PinchosAssets(TextureAtlas atlas) {
            pinchos = atlas.findRegion(Constants.PINCHOS);
            /*TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.PINCHOS);
            int edge = Constants.PINCHOS_EDGE;
            pinchosNinePatch = new NinePatch(region, edge, edge, edge, edge);*/
            //fondo = atlas.findRegion(Constants.FONDO);}
        }
    }

    public class PocionAssets {
        public final TextureAtlas.AtlasRegion pocion;
        //public final NinePatch pocionNinePatch;

        public PocionAssets(TextureAtlas atlas) {
            pocion = atlas.findRegion(Constants.POCION);
            /*TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.POCION);
            int edge = Constants.POCION_EDGE;
            pocionNinePatch = new NinePatch(region, edge, edge, edge, edge);*/
        }
    }

    public class Bloque2Assets {
        public final NinePatch bloqueNinePatch;

        public Bloque2Assets(TextureAtlas atlas) {
            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.BLOQUE2);
            int edge = Constants.PLATFORM_EDGE;
            bloqueNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }

    public class Bloque3Assets {
        public final NinePatch bloque3NinePatch;

        public Bloque3Assets(TextureAtlas atlas) {
            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.BLOQUE3);
            int edge = Constants.PLATFORM_EDGE;
            bloque3NinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }

    public class BolaAssets {
        public final TextureAtlas.AtlasRegion bola;
        public final Animation moveAnimation;


        public BolaAssets(TextureAtlas atlas) {
            Array<TextureAtlas.AtlasRegion> moveFrames = new Array<TextureAtlas.AtlasRegion>();
            moveFrames.add(atlas.findRegion(Constants.BOLA));
            moveAnimation = new Animation(Constants.WALK_LOOP_DURATION, moveFrames , Animation.PlayMode.LOOP);
            bola = atlas.findRegion(Constants.BOLA);
        }
    }

    public class ControlesMovil{
        public final TextureAtlas.AtlasRegion derecha;
        public final TextureAtlas.AtlasRegion izquierda;
        public final TextureAtlas.AtlasRegion saltar;

        public ControlesMovil(TextureAtlas atlas) {
            derecha = atlas.findRegion(Constants.BOTON_DERECHA);
            izquierda = atlas.findRegion(Constants.BOTON_IZQUIERDA);
            saltar = atlas.findRegion(Constants.BOTON_SALTAR);
        }
    }
}
