package com.mygdx.mario.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mario.Assets;
import com.mygdx.mario.utils.Enums;

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
        }
    }

    public Enums.Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Enums.Nivel nivel) {
        this.nivel = nivel;
    }
}
