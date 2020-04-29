package com.mygdx.mario.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mario.Assets;
import com.mygdx.mario.Level;
import com.mygdx.mario.entities.Enemigo;
import com.mygdx.mario.entities.Fondo;
import com.mygdx.mario.entities.Llave;
import com.mygdx.mario.entities.Mario;
import com.mygdx.mario.entities.Nube;
import com.mygdx.mario.entities.Pinchos;
import com.mygdx.mario.entities.Plataforma;
import com.mygdx.mario.entities.Pocion;
import com.mygdx.mario.entities.Vida;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.Comparator;

import org.json.simple.parser.JSONParser;
public class LevelLoader {

    public static final String TAG = LevelLoader.class.toString();

    public static Level load(String nombre, Viewport viewport) {

        String path = Constants.LEVEL_DIR + File.separator + nombre + "." + Constants.LEVEL_FILE_EXTENSION;
        Level level = new Level(viewport);

        FileHandle file = Gdx.files.internal(path);
        JSONParser parser = new JSONParser();

        try {
            JSONObject rootJsonObject = (JSONObject) parser.parse(file.reader());

            JSONObject composite = (JSONObject) rootJsonObject.get(Constants.LEVEL_COMPOSITE);

            JSONArray platforms = (JSONArray) composite.get(Constants.LEVEL_9PATCHES);
            loadPlatforms(platforms, level);


        } catch (Exception ex) {
            Gdx.app.error(TAG, ex.getMessage());
            Gdx.app.error(TAG, Constants.LEVEL_ERROR_MESSAGE);
            ex.printStackTrace();
        }

        return level;
    }

    private static float safeGetFloat(JSONObject object, String key) {
        Number number = (Number) object.get(key);
        return (number == null) ? 0 : number.floatValue();
    }

    private static void loadPlatforms(JSONArray array, Level level) {

        Array<Plataforma> plataformas = new Array<Plataforma>();

        for (Object object : array) {
            final JSONObject platformObject = (JSONObject) object;

            final float x = safeGetFloat(platformObject, Constants.LEVEL_X_KEY);

            final float y = safeGetFloat(platformObject, Constants.LEVEL_Y_KEY);

            final float width = ((Number) platformObject.get(Constants.LEVEL_WIDTH_KEY)).floatValue();

            final float height = ((Number) platformObject.get(Constants.LEVEL_HEIGHT_KEY)).floatValue();

            final Plataforma plataforma = new Plataforma(x, y + height, width, height);

            plataformas.add(plataforma);

            final String identifier = (String) platformObject.get(Constants.LEVEL_IDENTIFIER_KEY);

            if (identifier != null && identifier.equals(Constants.LEVEL_ENEMY_TAG)) {
                final Enemigo enemigo = new Enemigo(plataforma);
                level.getEnemigos().add(enemigo);
                plataformas.removeValue(plataforma,true);
            }

            if (identifier != null && identifier.equals(Constants.LEVEL_NUBE_TAG)) {
                final Nube nube = new Nube(x,y, width, height);
                level.getNubes().add(nube);
                plataformas.removeValue(plataforma,true);
            }

            if (identifier != null && identifier.equals(Constants.LEVEL_MARIO_TAG)) {
                final Mario mario = new Mario(new Vector2(0,0),level);
                level.setMario(mario);
                plataformas.removeValue(plataforma,true);
            }

            if (identifier != null && identifier.equals(Constants.LEVEL_VIDA_TAG)) {
                final Vida vida = new Vida(new Vector2(x,y));
                level.getVidas().add(vida);
                plataformas.removeValue(plataforma,true);
            }

            if (identifier != null && identifier.equals(Constants.LEVEL_LLAVE_TAG)) {
                final Llave llave = new Llave(new Vector2(x,y));
                level.getLlaves().add(llave);
                plataformas.removeValue(plataforma,true);
            }

            if (identifier != null && identifier.equals(Constants.LEVEL_FONDO_TAG)) {
                final Fondo fondo = new Fondo(x,y,width,height);
                level.getFondos().add(fondo);
                plataformas.removeValue(plataforma,true);
            }

            if (identifier != null && identifier.equals(Constants.LEVEL_PINCHOS_TAG)) {
                final Pinchos pinchos = new Pinchos(new Vector2(x,y),width,height);
                level.getPinchos().add(pinchos);
                plataformas.removeValue(plataforma,true);
            }

            if (identifier != null && identifier.equals(Constants.LEVEL_POCION_TAG)) {
                final Pocion pocion= new Pocion(new Vector2(x,y),width,height);
                level.getPociones().add(pocion);
                plataformas.removeValue(plataforma,true);
            }
        }

        plataformas.sort(new Comparator<Plataforma>() {
            @Override
            public int compare(Plataforma p1, Plataforma p2) {
                if (p1.top < p2.top) {
                    return 1;
                } else if (p1.top > p2.top) {
                    return -1;
                }
                return 0;
            }
        });

        level.getPlataformas().addAll(plataformas);
    }
}