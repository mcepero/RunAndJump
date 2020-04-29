package com.mygdx.mario.utils;

import com.mygdx.mario.entities.Mario;

import java.util.TimerTask;

public class CuentaAtras extends TimerTask {
    int countdown = 5;
    Mario mario;
    public CuentaAtras(Mario mario) {
        this.mario=mario;
    }

    public void run() {
        if (countdown!=0) {
            countdown = countdown - 1;
            System.out.println(countdown);
        }else if(countdown==0){
            mario.setChoque(Enums.Choque.NO);
        }
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }
}
