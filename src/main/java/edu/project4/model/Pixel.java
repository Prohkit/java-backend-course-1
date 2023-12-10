package edu.project4.model;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Pixel {
    private static final Random random = new Random();
    private int red;
    private int green;
    private int blue;
    private int hitCount;
    private double normal;

    public Pixel() {
        this.red = 0;
        this.green = 0;
        this.blue = 0;
        this.hitCount = 0;
        this.normal = 0;
    }

    public double getNormal() {
        return normal;
    }

    public void setNormal(double normal) {
        this.normal = normal;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public void incrementHitCount() {
        this.hitCount++;
    }
}
