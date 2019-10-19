package com.jongber.projectp.asset.json;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jongber.projectp.asset.common.wh;

import java.io.IOException;

public class GameSettingJson {
    public wh viewport;

    public static GameSettingJson load() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            FileHandle file = Gdx.files.internal("gamesetting.json");
            String json = file.readString();

            GameSettingJson settingJson = mapper.readValue(json, GameSettingJson.class);
            Gdx.app.log("DEBUG", "gamesetting json loaded");

            return settingJson;

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return null;
    }
}
