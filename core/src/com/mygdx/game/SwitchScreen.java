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
public class SwitchScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = 640;
    private static final float WORLD_HEIGHT = 480;
    private Stage stage;
    private Texture backgroundTexture;
    private Texture trueBackgroundTexture;
    private Game game;
    private Snake snake;
    private Texture playTexture;
    private Texture playPressTexture;
    private Label label;
    private Label label2;
    private int level;
    public SwitchScreen(Snake snake, int level) {
        this.game = snake;
        this.snake = snake;
        this.level = level;

    }
    public void show() {
        snake.getAssetManager().finishLoading();
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g,
                Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("panels/b1.jpg"));
        Image background = new Image(backgroundTexture);
        background.setScaling(Scaling.stretch);


        stage.addActor(background);



        Label.LabelStyle skin = new Label.LabelStyle();
        BitmapFont bitmapFont = new BitmapFont();
        skin.font = bitmapFont;
        skin.fontColor = Color.WHITE;
        String s = "";
        switch(this.level) {
            case 2:
                s = "The best techniques are passed on by the survivors.";
                break;
            case 3:
                s = "What is better? To be born good or to overcome your evil nature through great effort?";
                break;
            case 4:
                s = "What is a man? A miserable little pile of secrets.";
                break;
            case 5:
                s = "The right man in the wrong place can make all the difference in the world.";
                break;
            case 6:
                s = "La fin";
                break;
        }
        label = new Label(s, skin);

        label.setOrigin(label.getWidth()/2, label.getHeight()/2);
        label.setWrap(true);
        label.setWidth(200);
        label.setHeight(400);
        label.setAlignment(Align.center);
        label.setPosition(WORLD_WIDTH*6/16, WORLD_HEIGHT*1/4);
        label.setFontScale(1.5f);




        Group group = new Group();
        group.addActor(label);
        group.scaleBy(0.001f, 0.001f);
        stage.addActor(group);
        group.addAction(fadeOut(0.001f));

        group.addAction(sequence(fadeIn(3)));
        group.addAction(sequence(delay(5), run(new Runnable() {
            public void run () {
                label.addAction(fadeOut(5));
            }
        })));


        group.addAction(sequence(delay(10), run(new Runnable() {
            public void run () {
                String next = "Hit Enter to proceed to level " + level;
                if(level == 6) next = "Thanks for playing.";
                label.setText(next);
                label.addAction(fadeIn(5));
            }
        })));




    }

    private void change() {
        if(level != 6) {
            this.snake.setScreen(new GameScreen(this.snake, level));
            dispose();
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        //titleTexture.dispose();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    public void render(float delta) {
        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            change();

        }
    }



}
