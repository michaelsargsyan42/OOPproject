package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class StartScreen extends ScreenAdapter {
    private Texture backgroundTexture;
    private Texture playTexture;
    private Texture playPressTexture;
    private static final float WORLD_WIDTH = 1280; //1280
    private static final float WORLD_HEIGHT = 659; //659
    private Stage stage;

    private final Game game;
    public StartScreen(Snake snake) {
        this.game = snake;
        this.snake = snake;
    }
    private Snake snake;

    public void show() {
        // creating a stage
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        // make stage read keyboard
        Gdx.input.setInputProcessor(stage);

        // creating background
        backgroundTexture = new Texture(Gdx.files.internal("bg.png"));
        Image background = new Image(backgroundTexture);
        // adding background as an actor to the stage
        stage.addActor(background);

        // creating the image button
        playTexture = new Texture(Gdx.files.internal("play.png"));
        playPressTexture = new Texture(Gdx.files.internal("playPress.png"));
        ImageButton play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)),
                new TextureRegionDrawable(new
                TextureRegion(playPressTexture)));

        stage.addActor(play);
        play.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 4, Align.center);
        play.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count,
                            int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new CutScreen(snake));
            }
        });


        play.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count,
                            int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new CutScreen(snake));
                dispose();
            }
        });





    }
    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        playTexture.dispose();
        playPressTexture.dispose();
        //titleTexture.dispose();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(snake));
            dispose();
        }
    }
}
