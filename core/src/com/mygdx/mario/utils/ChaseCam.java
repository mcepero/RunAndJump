package com.mygdx.mario.utils;

import com.badlogic.gdx.graphics.Camera;
import com.mygdx.mario.entities.Mario;

public class ChaseCam {
    private Camera camera;
    private Mario target;

    public ChaseCam(Camera camera, Mario target) {
        this.camera = camera;
        this.target = target;
    }

    public void update() {
        camera.position.x = target.position.x;
        camera.position.y = 202;
    }



}
