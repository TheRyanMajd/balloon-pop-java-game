package com.balloonpop;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Player {
    public String name;
    protected int score;
    protected boolean isAlive;

    Player(String name) {
        this.name = name;
        this.score = 0;
        this.isAlive = true;
    }

    protected void addScore(int points) {
        this.score += points;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        int score = this.score;
        return score;
    }

    protected void killPlayer() {
        this.isAlive = false;
    }

    public String toString() {
        return (name + " reached a score of " + score);
    }
}
