package com.example.paintapp;

import android.graphics.Path;

public class Stroke {
    private int color;
    private int width;
    private Path path;

    public Stroke(int color, int width, Path path) {
        this.color = color;
        this.width = width;
        this.path = path;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
