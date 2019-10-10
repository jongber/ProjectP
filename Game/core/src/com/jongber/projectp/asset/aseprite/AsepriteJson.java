package com.jongber.projectp.asset.aseprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class AsepriteJson {
    public List<Frame> frames;
    public Meta meta;

    public static AsepriteJson load(String filename) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            FileHandle file = Gdx.files.internal(filename);
            String json = file.readString();

            return mapper.readValue(json, AsepriteJson.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
