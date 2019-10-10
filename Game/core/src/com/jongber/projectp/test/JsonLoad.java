package com.jongber.projectp.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.jongber.projectp.asset.aseprite.AsepriteJson;

public class JsonLoad extends ApplicationAdapter {

    @Override
    public void create () {
        AsepriteJson json = AsepriteJson.load("object/hero.json");

    }

    @Override
    public void render() {
    }

    @Override
    public void dispose() {
    }
}