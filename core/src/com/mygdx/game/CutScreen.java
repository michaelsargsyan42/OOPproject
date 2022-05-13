package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
public class CutScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = 1920;
    private static final float WORLD_HEIGHT = 1020;
    private Stage stage;
    private Texture backgroundTexture;
    private Texture trueBackgroundTexture;
    private Game game;
    private Snake snake;
    private ImageButton act;
    private ImageButton act2;
    private ImageButton act3;
    private ImageButton act4;
    private ImageButton act5;
    private ImageButton act6;
    private Texture playTexture;
    private Texture playPressTexture;
    private Label label;

    public CutScreen(Snake snake) {
        this.game = snake;
        this.snake = snake;

    }
    public void show() {
        // loading the assets
        snake.getAssetManager().finishLoading();

        // creating a stage
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        // getting the textures
        backgroundTexture = new Texture(Gdx.files.internal("Wallpaper.jpg"));
        trueBackgroundTexture = new Texture(Gdx.files.internal("panels/b.jpg"));
        Image trueBackground = new Image(trueBackgroundTexture);
        trueBackground.setScaling(Scaling.stretch);
        Image background = new Image(backgroundTexture);
        background.setScaling(Scaling.stretch);

        // adding the backgrounds to stage as an actor
        stage.addActor(trueBackground);
        stage.addActor(background);

        // creating the actors
        playTexture = new Texture(Gdx.files.internal("panels/1.png"));
        playPressTexture = new Texture(Gdx.files.internal("panels/1.png"));
        act = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)),
                new TextureRegionDrawable(new
                        TextureRegion(playPressTexture)));

        Texture p2 = new Texture(Gdx.files.internal("panels/2.jpg"));
        Texture p2p = new Texture(Gdx.files.internal("panels/2.jpg"));
        act2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(p2)),
                new TextureRegionDrawable(new
                        TextureRegion(p2p)));

        Texture p3 = new Texture(Gdx.files.internal("panels/4.png"));
        Texture p3p = new Texture(Gdx.files.internal("panels/4.png"));
        act3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(p3)),
                new TextureRegionDrawable(new
                        TextureRegion(p3p)));

        Texture p4 = new Texture(Gdx.files.internal("panels/5.png"));
        Texture p4p = new Texture(Gdx.files.internal("panels/5.png"));
        act4 = new ImageButton(new TextureRegionDrawable(new TextureRegion(p4)),
                new TextureRegionDrawable(new
                        TextureRegion(p4p)));

        Texture p5 = new Texture(Gdx.files.internal("panels/3.png"));
        Texture p5p = new Texture(Gdx.files.internal("panels/3.png"));
        act5 = new ImageButton(new TextureRegionDrawable(new TextureRegion(p5)),
                new TextureRegionDrawable(new
                        TextureRegion(p5p)));

        // adding actors to the stage
        stage.addActor(act);
        stage.addActor(act2);
        stage.addActor(act3);
        stage.addActor(act4);
        stage.addActor(act5);

        // positiong actors
        act2.setPosition(-1000, -1000);
        act3.setPosition(2000, 2000);
        act4.setPosition(WORLD_WIDTH/2, -1000);
        act5.setPosition(WORLD_WIDTH/2, -1000);

        // enabling the movement
        act.setTransform(true);
        act2.setTransform(true);
        act3.setTransform(true);
        act4.setTransform(true);
        act5.setTransform(true);

        act.setOrigin(act.getWidth()/2, act.getHeight()/2);

        act.addAction(delay(2000));
        act.addAction(parallel(scaleBy(0.01f, 0.01f, 0.01f), moveTo(WORLD_WIDTH*1/4, WORLD_HEIGHT*1/4, 1f), rotateBy(-15, 1f),/*scaleTo(act.getScaleX() * 0.5f, act.getScaleY() * 0.5f, 2),*/ delay(10) ));
        act2.addAction(parallel(moveTo(WORLD_WIDTH*2/4, WORLD_HEIGHT*1/4, 2f), rotateBy(20, 2f)));
        act3.addAction(parallel(moveTo(WORLD_WIDTH*2/4, WORLD_HEIGHT*1/4, 2f), rotateBy(-30, 2f)));
        act4.addAction(parallel(moveTo(WORLD_WIDTH*3/8, WORLD_HEIGHT*1/8, 2.3f), rotateBy(24, 2.3f)));
        act5.addAction(parallel(moveTo(WORLD_WIDTH*2/8, WORLD_HEIGHT*1/8, 2.3f), rotateBy(-18, 2.3f)));

        // LABEL
        Label.LabelStyle skin = new Label.LabelStyle();
        BitmapFont bitmapFont = new BitmapFont();
        skin.font = bitmapFont;
        skin.fontColor = Color.WHITE;
        label = new Label("Unexplainable phenomena have started ocurring all over the globe. Creatures, not yet observed by the humanity" +
                "began to pop up in various corners of the planet. But here's the twist. You are one of them. Good luck.", skin);

        //label.setOrigin(label.getWidth()/2, label.getHeight()/2);
        label.setWrap(true);
        label.setWidth(800);
        label.setHeight(150);
        label.setAlignment(Align.center);
        label.setPosition(WORLD_WIDTH*1/4, WORLD_HEIGHT/2);
        label.setFontScale(2.5f);
        //label.scaleBy(10, 10);
        Group group = new Group();
        group.addActor(label);
        stage.addActor(group);
        group.addAction(fadeOut(0.001f));

        // panels disappearing
        background.addAction(sequence(delay(6), run(new Runnable() {
            public void run () {
                act5.addAction(fadeOut(2));
                act4.addAction(fadeOut(3));
                act3.addAction(fadeOut(3.5f));
                act2.addAction(fadeOut(4));
                act.addAction(fadeOut(4.5f));
            }
        })));
        background.addAction(sequence(delay(10.5f), fadeOut(2)));
        group.addAction(sequence(delay(12.5f), fadeIn(2)));



        background.addAction(sequence(delay(25), run(new Runnable() {
            public void run () {
                change();
            }
        })));


        //act.addAction(sequence(fadeOut(4), scaleTo(act.getScaleX() * 3, act.getScaleY() * 3, 2)));
       //act.addAction(parallel(rotateBy(360, 1, Interpolation.bounce), moveTo(320, 100, 0.5f)));
        //act.addAction(parallel(moveTo(250, 250, 2, Interpolation.circle), color(Color.RED, 6), delay(0.5f), rotateTo(180, 5, Interpolation.swing)));
        //act.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 4, Align.center);
        act.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count,
                            int button) {
                super.tap(event, x, y, count, button);
                System.out.print("Test");
                //game.setScreen(new GameScreen(snake));
            }
        });


        act.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count,
                            int button) {
                super.tap(event, x, y, count, button);

                //game.setScreen(new GameScreen(snake));
                dispose();
            }
        });





    }

    private void change() {
        this.snake.setScreen(new GameScreen(this.snake));
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
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(snake));
            dispose();
        }
    }



}
