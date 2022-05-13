package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CutScene implements ApplicationListener {



    public class MyActor extends Actor {
        Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        @Override
        public void draw(Batch batch, float alpha){
            batch.draw(texture,0,0);
        }
    }

    private Stage stage;
    private Viewport viewport;
    @Override
    public void create() {
        Camera camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        MyActor myActor = new MyActor();
        stage.addActor(myActor);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
