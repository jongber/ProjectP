package com.projecta.game.desktop.editor.spriteeditor.panel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.projecta.game.core.util.Tuple2;
import com.projecta.game.desktop.common.component.TextureComponent;
import com.projecta.game.desktop.common.panel.GamePanel;
import com.projecta.game.desktop.common.pipeline.BlockGridRender;
import com.projecta.game.desktop.editor.object.TextureObject;
import com.projecta.game.desktop.editor.pipeline.TextureRender;

public class TextureRegionPanel extends GamePanel {

    private BlockGridRender blockGridRender;

    public TextureRegionPanel(Tuple2<Float, Float> screenRatio, Tuple2<Float, Float> posRatio) {
        super(screenRatio, posRatio);

        this.init();
    }

    public void onLoadTexture(Texture texture, int pixelUnitX, int pixelUnitY) {

        this.removeAllObjects();

        TextureObject obj = new TextureObject();
        obj.addComponent(TextureComponent.class, new TextureComponent(new TextureRegion(texture)));
        obj.bindComponent();

        this.getCamera().position.x = texture.getWidth() * 0.5f;
        this.getCamera().position.y = texture.getHeight() * 0.5f;

        this.addObject(obj);
        this.blockGridRender.setGridSize(pixelUnitX, pixelUnitY);
    }

    private void init() {

        blockGridRender = new BlockGridRender(this.getCamera());

        this.addPipeline(new TextureRender(this.getCamera()));
        this.addPipeline(this.blockGridRender);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer);
    }
}
