package com.jongber.projectp.asset.json;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jongber.projectp.common.FnTr;

import java.io.IOException;
import java.util.List;

public class GameWorldJson {
    public List<FnTr> sceneries;
    public List<FnTr> objects;

    public static GameSettingJson load(String filename) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            FileHandle file = Gdx.files.internal(filename);
            String json = file.readString();

            GameSettingJson settingJson = mapper.readValue(json, GameSettingJson.class);
            Gdx.app.log("DEBUG", "[" + filename + "]json loaded");

            return settingJson;

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return null;
    }
}
