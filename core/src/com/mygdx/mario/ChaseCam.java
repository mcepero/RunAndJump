package com.mygdx.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.mygdx.mario.entities.Mario;
import com.mygdx.mario.utils.Constants;

public class ChaseCam {
    private final Camera camera;
    private final Mario target;
    private Boolean following;


    public ChaseCam(Camera camera, Mario target) {
        this.camera = camera;
        this.target = target;
        following = true;
    }


    public void update(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            following = !following;
        }

        if (following) {
            camera.position.x = target.getPosition().x;
            camera.position.y = target.getPosition().y;
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                camera.position.x -= delta * Constants.CHASE_CAM_MOVE_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                camera.position.x += delta * Constants.CHASE_CAM_MOVE_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                camera.position.y += delta * Constants.CHASE_CAM_MOVE_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                camera.position.y -= delta * Constants.CHASE_CAM_MOVE_SPEED;
            }
        }
    }
}
