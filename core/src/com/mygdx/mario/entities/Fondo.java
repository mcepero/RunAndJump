package com.mygdx.mario.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.mario.Assets;
import com.mygdx.mario.utils.Constants;
import com.mygdx.mario.utils.Enums;
import com.mygdx.mario.utils.Utils;

public class Fondo {
    float y;
    float x;
    float width;
    float height;
    public Enums.Nivel nivel;

    public Fondo(float x, float y, float width, float height) {
        this.y=y;
        this.x=x;
        this.width=width;
        this.height=height;
        this.nivel= Enums.Nivel.Nivel1;
    }

    public void render(SpriteBatch batch) {
        if (nivel.equals(Enums.Nivel.Nivel1)) {
            Assets.instance.fondoAssets.fondoNinePatch.draw(batch, x, y, width, height*2);
        }else if(nivel.equals(Enums.Nivel.Nivel2)){
            Assets.instance.fondoAssets.fondo2NinePatch.draw(batch, x, y, width*2, height*2);
        }else if(nivel.equals(Enums.Nivel.Nivel3)){
            Assets.instance.fondoAssets.fondo3NinePatch.draw(batch, x, y, width*2, height*2);
        }
    }

    public Enums.Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Enums.Nivel nivel) {
        this.nivel = nivel;
    }
}
