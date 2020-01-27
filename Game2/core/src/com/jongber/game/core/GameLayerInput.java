package com.jongber.game.core;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;

import java.util.List;

public class GameLayerInput implements InputProcessor {

    private List<Controller.InputProcessor> inputProcessors;
    private OrthoCameraWrapper cameraWrapper;

    public GameLayerInput(List<Controller.InputProcessor> inputs,
                          OrthoCameraWrapper cameraWrapper) {
        this.inputProcessors = inputs;
        this.cameraWrapper = cameraWrapper;
    }

    @Override
    public boolean keyDown(int keycode) {
        boolean processed = false;
        for (Controller.InputProcessor input : this.inputProcessors) {
            if (input.keyDown(keycode)) {
                processed = true;
            }
        }
        return processed;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean processed = false;
        for (Controller.InputProcessor input : this.inputProcessors) {
            if (input.keyUp(keycode)) {
                processed = true;
            }
        }
        return processed;
    }

    @Override
    public boolean keyTyped(char character) {
        boolean processed = false;
        for (Controller.InputProcessor input : this.inputProcessors) {
            if (input.keyTyped(character)) {
                processed = true;
            }
        }
        return processed;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean processed = false;

        Vector3 worldPos = cameraWrapper.getCamera().unproject(new Vector3(screenX, screenY, cameraWrapper.getCamera().zoom));

        for (Controller.InputProcessor input : this.inputProcessors) {
            if (input.touchDown(cameraWrapper, worldPos.x, worldPos.y, pointer, button)) {
                processed = true;
            }
        }
        return processed;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        boolean processed = false;

        Vector3 worldPos = cameraWrapper.getCamera().unproject(new Vector3(screenX, screenY, cameraWrapper.getCamera().zoom));

        for (Controller.InputProcessor input : this.inputProcessors) {
            if (input.touchUp(cameraWrapper, worldPos.x, worldPos.y, pointer, button)) {
                processed = true;
            }
        }
        return processed;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        boolean processed = false;

        Vector3 worldPos = cameraWrapper.getCamera().unproject(new Vector3(screenX, screenY, cameraWrapper.getCamera().zoom));

        for (Controller.InputProcessor input : this.inputProcessors) {
            if (input.touchDragged(cameraWrapper, worldPos.x, worldPos.y, pointer)) {
                processed = true;
            }
        }
        return processed;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        boolean processed = false;

        Vector3 worldPos = cameraWrapper.getCamera().unproject(new Vector3(screenX, screenY, cameraWrapper.getCamera().zoom));

        for (Controller.InputProcessor input : this.inputProcessors) {
            if (input.mouseMoved(cameraWrapper, worldPos.x, worldPos.y)) {
                processed = true;
            }
        }
        return processed;
    }

    @Override
    public boolean scrolled(int amount) {
        boolean processed = false;
        for (Controller.InputProcessor input : this.inputProcessors) {
            if (input.scrolled(this.cameraWrapper, amount)) {
                processed = true;
            }
        }
        return processed;
    }
}
