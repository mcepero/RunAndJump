package com.mygdx.mario.utils;

import com.badlogic.gdx.math.Vector2;

public class Constants {
    public final static String TEXTURE_ATLAS = "Mario.pack";
    public static final String WALK1 = "walk1";
    public static final String WALK2 = "walk2";
    public static final String WALK3 = "walk3";
    public static final String WALK4 = "walk4";
    public static final String WALK5 = "walk5";
    public static final String WALK6 = "walk6";
    public static final String STANDING = "idle";
    public static final String JUMP_STAND = "bonk";
    public static final String JUMP = "jump";
    public static final String FALL = "fall";

    public static final String WALK1LEFT = "walk1-left";
    public static final String WALK2LEFT= "walk2-left";
    public static final String WALK3LEFT = "walk3-left";
    public static final String WALK4LEFT = "walk4-left";
    public static final String WALK5LEFT = "walk5-left";
    public static final String WALK6LEFT = "walk6-left";
    public static final String STANDINGLEFT = "idle-left";
    public static final String JUMP_STANDLEFT = "bonk-left";
    public static final String JUMPLEFT = "jump1-left";
    public static final String FALLLEFT = "fall-left";

    public static final float GRAVITY = 10;
    public static final float WORLD_SIZE = 400;
    public static final float WALK_LOOP_DURATION = 0.25f;
    public static final float CHASE_CAM_MOVE_SPEED = WORLD_SIZE;
    public static final float PERSONAJE_MOVE_SPEED = 120;
    public static final Vector2 PERSONAJE_EYE_POSITION = new Vector2(16, 15);
    public static final float PERSONAJE_EYE_HEIGHT = 16.0f;
    public static final float PERSONAJE_STANCE_WIDTH = 32.0f;
    public static final Vector2 PERSONAJE_CANNON_OFFSET = new Vector2(12, -7);
    public static final float PERSONAJE_HEIGHT = 23.0f;
    public static final float JUMP_SPEED = 200;
    public static final float MAX_JUMP_DURATION = .17f;

    public static final String PLATFORM_SPRITE = "block1";
    public static final int PLATFORM_EDGE = 8;


    public static final float ENEMY_MOVE_SPEED = 80;
    public static final Vector2 ENEMY_EYE_POSITION = new Vector2(0, 20);

    public static final String ENEMIGO1 = "enemigo1";
    public static final String ENEMIGO2 = "enemigo2";
    public static final String ENEMIGO3 = "enemigo3";
    public static final String ENEMIGO4 = "enemigo4";
    public static final String ENEMIGO5 = "enemigo5";
    public static final String ENEMIGO6 = "enemigo6";
    public static final String ENEMIGO7 = "enemigo7";

    public static final String ENEMIGO1_LEFT = "enemigo1-left";
    public static final String ENEMIGO2_LEFT = "enemigo2-left";
    public static final String ENEMIGO3_LEFT = "enemigo3-left";
    public static final String ENEMIGO4_LEFT = "enemigo4-left";
    public static final String ENEMIGO5_LEFT = "enemigo5-left";
    public static final String ENEMIGO6_LEFT = "enemigo6-left";
    public static final String ENEMIGO7_LEFT = "enemigo7-left";

    public static final String ENEMIGO_MUERTO1 = "enemigomuerto1";
    public static final String ENEMIGO_MUERTO2 = "enemigomuerto2";
    public static final String ENEMIGO_MUERTO3 = "enemigomuerto3";
    public static final String ENEMIGO_MUERTO4 = "enemigomuerto4";
    public static final String ENEMIGO_MUERTO5 = "enemigomuerto5";
    public static final String ENEMIGO_MUERTO6 = "enemigomuerto6";
    public static final String ENEMIGO_MUERTO7 = "enemigomuerto7";

    public static final String ENEMIGO_MUERTO1_LEFT = "enemigomuerto1-left";
    public static final String ENEMIGO_MUERTO2_LEFT = "enemigomuerto2-left";
    public static final String ENEMIGO_MUERTO3_LEFT = "enemigomuerto3-left";
    public static final String ENEMIGO_MUERTO4_LEFT = "enemigomuerto4-left";
    public static final String ENEMIGO_MUERTO5_LEFT = "enemigomuerto15-left";
    public static final String ENEMIGO_MUERTO6_LEFT = "enemigomuerto6-left";
    public static final String ENEMIGO_MUERTO7_LEFT = "enemigomuerto7";

    public static final String BLOQUE2 = "block4";
    public static final Vector2 BLOQUE_CENTER = new Vector2(0, 0);

    public static final String BLOQUE3 = "block2";

    public static final float ENEMIGO_COLLISION_RADIUS = 2;

    public static final String NUBE1 = "nube";
    public static final String NUBE2 = "nube2";
    public static final int NUBE_EDGE = 10;

    public static final String VIDA = "heart";
    public static final Vector2 VIDA_CENTER = new Vector2(0, 0);
    public static final float VIDA_COLLISION_RADIUS = 8;

    public static final String LLAVE = "key";
    public static final int LLAVE_EDGE = 5;
    public static final float LLAVE_COLLISION_RADIUS = 7;

    public static final String BOLA = "bola";
    public static final Vector2 BOLA_CENTER = new Vector2(8,8);
    public static final float BOLA_SIZE = 16;
    public static final float BOLA_MOVE_SPEED = 100;
   // public static final Vector2 BOLA_EYE_POSITION = new Vector2(16, 35);
    public static final float BOLA_COLLISION_RADIUS = 5;

    public static final String LEVEL_DIR = "levels";
    public static final String LEVEL_FILE_EXTENSION = "dt";
    public static final String LEVEL_COMPOSITE = "composite";
    public static final String LEVEL_9PATCHES = "sImage9patchs";
    public static final String LEVEL_IMAGES = "sImages";
    public static final String LEVEL_ERROR_MESSAGE = "Error al cargar nivel";
    public static final String LEVEL_IMAGENAME_KEY = "imageName";
    public static final String LEVEL_X_KEY = "x";
    public static final String LEVEL_Y_KEY = "y";
    public static final String LEVEL_WIDTH_KEY = "width";
    public static final String LEVEL_HEIGHT_KEY = "height";
    public static final String LEVEL_IDENTIFIER_KEY = "itemIdentifier";
    public static final String LEVEL_ENEMY_TAG = "Enemigo";
    public static final String LEVEL_NUBE_TAG = "Nube";
    public static final String LEVEL_MARIO_TAG = "Mario";
    public static final String LEVEL_VIDA_TAG = "Vida";
    public static final String LEVEL_LLAVE_TAG = "Llave";
    public static final String LEVEL_FONDO_TAG = "Fondo";
    public static final String LEVEL_FONDO2_TAG = "Fondo2";
    public static final String LEVEL_FONDO3_TAG = "Fondo3";
    public static final String LEVEL_PINCHOS_TAG = "Pinchos";
    public static final String LEVEL_POCION_TAG = "Pocion";
    public static final String LEVEL_BLOQUE2_TAG = "Bloque2";
    public static final String LEVEL_BLOQUE3_TAG = "Bloque3";
    public static final String LEVEL_BOLA_TAG = "Bola";

    public static final String REINICIAR_BUTTON = "reiniciar.png";
    public static final float HUD_VIEWPORT_SIZE = 480;
    public static final float HUD_MARGIN = 2;


    public static final float LEVEL_END_DURATION = 5;
    public static final String GAME_OVER_MESSAGE = "¡¡¡Has perdido!!!";
    public static final String VICTORIA_MENSAJE = "¡¡¡Has ganado!!!";
    public static final String FONT_FILE = "font/header.fnt";

    public static final String FONDO = "fondo";
    public static final String FONDO2 = "fondo2";
    public static final String FONDO3 = "fondo3";
    public static final Vector2 FONDO_CENTER = new Vector2(0, 0);
    public static final int FONDO_EDGE = 20;

    public static final String PINCHOS = "spikes1";
    public static final Vector2 PINCHOS_CENTER = new Vector2(0, -2);
    public static final float PINCHOS_COLLISION_RADIUS = 8;

    public static final String POCION = "pocion";
    public static final Vector2 POCION_CENTER = new Vector2(0, 0);
    public static final float POCION_COLLISION_RADIUS = 7;

    public static final float ONSCREEN_CONTROLS_VIEWPORT_SIZE = 200;
    public static final String BOTON_IZQUIERDA = "izquierda";
    public static final String BOTON_DERECHA = "derecha";
    public static final String BOTON_SALTAR = "saltar";
    public static final Vector2 BUTTON_CENTER = new Vector2(15, 15);
    public static final float BUTTON_RADIUS = 32;
}


