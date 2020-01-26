package com.jongber.game.projectz.common;

import com.badlogic.gdx.math.Vector2;

public class MapIndex {
    public static final int BlockPx = 16;
    public int xIndex;
    public int yIndex;

    public int widthRange;
    public int heightRange;

    private int xOrg;
    private int yOrg;
    private int widthOrg;
    private int heightOrg;

    public MapIndex(int xOrg, int yOrg, int widthOrg, int heightOrg) {
        this.xOrg = xOrg;
        this.yOrg = yOrg;
        this.widthOrg = widthOrg;
        this.heightOrg = heightOrg;
        this.calc();
    }

    public int getOrgX() {
        return this.xOrg;
    }

    public int getOrgY() {
        return this.yOrg;
    }

    public Vector2 getOrg() {
        return new Vector2(this.xOrg, this.yOrg);
    }

    public int getX() {
        return this.xIndex;
    }

    public int getY() {
        return this.yIndex;
    }

    public Vector2 getXY() {
        return new Vector2(this.xIndex, this.yIndex);
    }

    private void calc() {
        this.xIndex = xOrg / BlockPx;
        this.yIndex = yOrg / BlockPx;
        this.widthRange = (int)Math.ceil(this.widthOrg / BlockPx);
        this.heightRange = (int)Math.ceil(this.heightOrg / BlockPx);
    }
}
