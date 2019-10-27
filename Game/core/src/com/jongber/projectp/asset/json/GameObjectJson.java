package com.jongber.projectp.asset.json;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jongber.projectp.common.SceneryJsonElement;
import com.jongber.projectp.common.SpriteStateJson;

import java.io.IOException;
import java.util.List;

public class GameObjectJson {
    public String name;
    public List<SpriteStateJson> sprites;
    public SceneryJsonElement scenery;

    public static GameObjectJson load(String filename) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            FileHandle file = Gdx.files.internal(filename);
            GameObjectJson json = mapper.readValue(file.readString(), GameObjectJson.class);

            Gdx.app.log("DEBUG", "GameObject json [" + filename + "]");

            return json;

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return null;
    }
}
