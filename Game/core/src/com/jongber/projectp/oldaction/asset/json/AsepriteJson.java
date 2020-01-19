package com.jongber.projectp.oldaction.asset.json;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jongber.projectp.oldaction.asset.aseprite.Frame;
import com.jongber.projectp.oldaction.asset.aseprite.Meta;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AsepriteJson {
    public List<Frame> frames;
    public Meta meta;
    public String parentPath;


    public static AsepriteJson load(String filename) {
        ObjectMapper mapper = new ObjectMapper();
        File filePath = new File(filename);

        try {
            FileHandle file = Gdx.files.internal(filename);
            String json = file.readString();

            AsepriteJson asepriteJson = mapper.readValue(json, AsepriteJson.class);
            asepriteJson.parentPath = filePath.getParent();

            Gdx.app.log("DEBUG", "Aseprite json [" + filename + "]" + " loaded, parentPath[" + asepriteJson.parentPath + "]");

            return asepriteJson;

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return null;
    }

    public String getImgPath() {
        return parentPath + "/" + meta.image;
    }
}
