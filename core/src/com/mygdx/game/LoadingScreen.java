package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadingScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 640;
    private static final float WORLD_HEIGHT = 480;
    private static final float PROGRESS_BAR_WIDTH = 100;
    private static final float PROGRESS_BAR_HEIGHT = 25;
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private OrthographicCamera
            camera;
    private float progress = 0;
    private final Snake snake;

    public LoadingScreen(Snake snake) {
        this.snake = snake;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();
        snake.getAssetManager().load("level1.tmx", TiledMap.class);
        snake.getAssetManager().load("level2.tmx", TiledMap.class);
        snake.getAssetManager().load("level3.tmx", TiledMap.class);
        snake.getAssetManager().load("level4.tmx", TiledMap.class);
        snake.getAssetManager().load("level5.tmx", TiledMap.class);
        snake.getAssetManager().load("level6.tmx", TiledMap.class);
        snake.getAssetManager().load("level7.tmx", TiledMap.class);
        snake.getAssetManager().load("level8.tmx", TiledMap.class);
        snake.getAssetManager().load("level9.tmx", TiledMap.class);
        snake.getAssetManager().load("level10.tmx", TiledMap.class);
    }

    @Override
    public void render(float delta) {
        update();
        clearScreen();
        draw();
    }
    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
    private void update() {
        if (snake.getAssetManager().update()) {
            snake.setScreen(new StartScreen(snake));
        } else {
            progress = snake.getAssetManager().getProgress();
        }
    }
    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g,
                Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    private void draw() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(
                (WORLD_WIDTH - PROGRESS_BAR_WIDTH) / 2, WORLD_HEIGHT / 2 -
                        PROGRESS_BAR_HEIGHT / 2,
                progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
        shapeRenderer.end();
    }
}

