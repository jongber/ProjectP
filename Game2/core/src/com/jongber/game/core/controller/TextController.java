package com.jongber.game.core.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.jongber.game.core.asset.FontManager;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.core.component.TextComponent;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class TextController extends Controller implements Controller.GraphBuilder, Controller.Renderer, Controller.InputProcessor {

    private FontManager fontManager = null;
    private List<GameObject> objs = new ArrayList<>();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    @Override
    public void build(List<GameObject> graph) {
        this.objs = this.buildSimple(graph, TextComponent.class);

        List<FontManager.TextBlock> blocks = new ArrayList<>();
        for (GameObject obj : this.objs) {
            TextComponent comp = obj.getComponent(TextComponent.class);
            blocks.add(comp.textBlock);
        }

        this.fontManager.build(blocks);

        for (GameObject obj : this.objs) {
            TextComponent comp = obj.getComponent(TextComponent.class);
            BitmapFont font = this.fontManager.getFont(comp.textBlock.fontSize);
            GlyphLayout layout = new GlyphLayout(font, comp.textBlock.text, comp.textBlock.color, comp.width, comp.align, true);
            if (comp.width < layout.width) comp.width = layout.width;
            if (comp.height < layout.height) comp.height = layout.height;
        }
    }

    @Override
    public void dispose() {
        this.fontManager.dispose();
    }

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        batch.end();

        for (GameObject obj : this.objs) {
            TextComponent button = obj.getComponent(TextComponent.class);
            if (button == null || button.textBlock == null) {
                Gdx.app.error("LabelRenderer", "why null? " + obj.name);
                continue;
            }

            BitmapFont font = this.fontManager.getFont(button.textBlock.fontSize);
            if (font != null) {
                GlyphLayout layout = new GlyphLayout(font, button.textBlock.text, button.textBlock.color, button.width, button.align, true);
                Vector2 pos = obj.transform.getWorldPos();

                shapeRenderer.setColor(button.background);
                shapeRenderer.setProjectionMatrix(camera.getCamera().projection);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                Vector2 shapePivot = this.calcShapePos(pos, button, layout);
                shapeRenderer.rect(shapePivot.x, shapePivot.y, layout.width, layout.height);
                shapeRenderer.end();

                batch.begin();
                Vector2 pivot = calcTextPos(pos, button, layout);
                font.draw(batch, layout, pivot.x, pivot.y);
                batch.end();
            }
        }

        batch.begin();
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(OrthoCameraWrapper camera, float screenX, float screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button) {

        for (GameObject obj : this.objs) {
            Vector2 pos = obj.transform.getWorldPos();
            TextComponent comp = obj.getComponent(TextComponent.class);
            BitmapFont font = this.fontManager.getFont(comp.textBlock.fontSize);

            GlyphLayout layout = new GlyphLayout(font, comp.textBlock.text, comp.textBlock.color, comp.width, comp.align, true);

            pos = this.calcShapePos(pos, comp, layout);

            float x1 = pos.x, x2 = comp.width + x1;
            float y1 = pos.y, y2 = comp.height + y1;

            if ((x1 <= worldX && worldX <= x2) &&
                    (y1 <= worldY && worldY <= y2)) {
                comp.onClick();
            }
        }

        return false;
    }

    @Override
    public boolean touchDragged(OrthoCameraWrapper camera, float screenX, float screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(OrthoCameraWrapper camera, float screenX, float screenY) {
        return false;
    }

    @Override
    public boolean scrolled(OrthoCameraWrapper camera, int amount) {
        return false;
    }

    private Vector2 calcShapePos(Vector2 pos, TextComponent component, GlyphLayout layout) {
        Vector2 result = new Vector2();
        switch (component.align) {
            case Align.left:
                result.set(pos.x, pos.y);
                break;
//            case Align.right:
//                result.set(pos.x - layout.width, pos.y - layout.height);
//                break;
            case Align.center:
                result.set(pos.x - layout.width/2, pos.y);
                break;
//            case Align.bottom:
//                result.set(pos.x - layout.width, pos.y - layout.height);
//                break;
            default:
                break;
        }
        return result;
    }

    private Vector2 calcTextPos(Vector2 pos, TextComponent component, GlyphLayout layout) {
        Vector2 result = new Vector2();
        switch (component.align) {
            case Align.left:
                result.set(pos.x, pos.y + layout.height);
                break;
//            case Align.right:
//                result.set(pos.x - layout.width, pos.y - layout.height);
//                break;
            case Align.center:
                result.set(pos.x - layout.width/2, pos.y + layout.height);
                break;
//            case Align.bottom:
//                result.set(pos.x - layout.width, pos.y - layout.height);
//                break;
            default:
                break;
        }
        return result;
    }
}
