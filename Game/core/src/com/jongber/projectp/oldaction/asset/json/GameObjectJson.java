package com.jongber.projectp.oldaction.asset.json;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jongber.projectp.oldaction.common.SceneryJsonElement;
import com.jongber.projectp.oldaction.common.SpriteDefineJson;

import java.io.IOException;
import java.util.List;

public class GameObjectJson {
    public String name;
    public List<SpriteDefineJson> sprites;
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
