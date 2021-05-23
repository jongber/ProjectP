package com.projecta.game.desktop.common;

public class Adjuster {

    public static String adjustFilePath(String path) {
        return path.replace("\\", "/");
    }
}
