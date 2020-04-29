package com.mygdx.mario.utils;

public class Enums {
    public enum Direction {
        LEFT, RIGHT
    }

    public enum JumpState {
        JUMPING,
        FALLING,
        GROUNDED,
        RECOILING
    }

    public enum WalkState {
        NOT_WALKING,
        WALKING
    }

    public enum Choque{
        SI,
        NO
    }

    public enum EstadoEnemigo{
        VIVO,
        MUERTO
    }
}
