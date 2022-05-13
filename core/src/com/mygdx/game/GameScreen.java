package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture snakeHead;
    private Texture snakeBody;
    private Texture bodVert;
    private Texture bodHor;
    private Texture headLeft;
    private Texture headRight;
    private Texture headTop;
    private Texture headBottom;
    private Texture apple;
    private Texture tailUp;
    private Texture tailDown;
    private Texture tailLeft;
    private Texture tailRight;
    private ShapeRenderer shapeRenderer;
    private BitmapFont bitmapFont;
    private enum STATE {
        PLAYING, GAME_OVER
    }
    private STATE state = STATE.PLAYING;
    private static final float WORLD_WIDTH = 640; //640
    private static final float WORLD_HEIGHT = 480; //480
    private Viewport viewport;
    private Camera camera;
    private static final float MOVE_TIME = 1F;
    private float timer = MOVE_TIME;
    private static final int SNAKE_MOVEMENT = 32;
    private int snakeX = 0, snakeY = 0;
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;
    private int snakeDirection = RIGHT;
    private boolean appleAvailable = false;
    private int appleX = -1, appleY = -1;
    private int gateX, gateY;
    private boolean gateAvailable = false;
    TiledMapTileLayer waterLayer;
    private boolean directionSet = false;

    private GlyphLayout layout = new GlyphLayout();
    private Array<BodyPart> bodyParts = new Array<BodyPart>();
    private Snake snake;

    // Superpower variables
    Texture superpower;
    boolean magnetOn = false;
    boolean superSpeedOn = false;
    boolean scoreMultiplierOn = false;
    boolean invincibilityOn = false;
    boolean magnetActive = false;
    boolean superSpeedActive = false;
    boolean scoreMultiplierActive = false;
    boolean invincibilityActive = false;
    private Texture L;
    private Texture L2;
    private Texture L3;
    private Texture L4;
    private Texture tail;
    int superPowerTimer = 0;
    int superX = -1, superY = -1;
    String superpowerName = "";
    private int level;
    private Texture gateTexture;


    public GameScreen(Snake snake) {
        this.snake = snake;
        this.level = 1;
    }
    public GameScreen(Snake snake, int level) {
        this.snake = snake;
        this.level = level;
    }
    @Override
    public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),
               Gdx.graphics.getHeight());
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        resize(640, 480);


        bitmapFont = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        gateTexture = new Texture(Gdx.files.internal("gate.png"));
        headLeft = new Texture(Gdx.files.internal("fhl.png"));
        headRight = new Texture(Gdx.files.internal("fhr.png"));
        headTop = new Texture(Gdx.files.internal("fht.png"));
        headBottom = new Texture(Gdx.files.internal("fh.png"));


        tailUp = new Texture(Gdx.files.internal("fb.png"));
        tailLeft = new Texture(Gdx.files.internal("fb.png"));
        tailRight = new Texture(Gdx.files.internal("fb.png"));
        tailDown = new Texture(Gdx.files.internal("fb.png"));


        L = new Texture(Gdx.files.internal("fb.png"));
        L2 = new Texture(Gdx.files.internal("fb.png"));
        L3 = new Texture(Gdx.files.internal("fb.png"));
        L4 = new Texture(Gdx.files.internal("fb.png"));

        snakeHead = headRight;
        //snakeHead = new Texture(Gdx.files.internal("snekLeft.png"));

        apple = new Texture(Gdx.files.internal("5.png"));
        //snakeBody = new Texture(Gdx.files.internal("snekbod.png"));
//        bodHor = new Texture(Gdx.files.internal("snakeBodHor.png"));
//        bodVert = new Texture(Gdx.files.internal("snakeBodVert.png"));;
        bodHor = new Texture(Gdx.files.internal("fb.png"));
       bodVert = new Texture(Gdx.files.internal("fb.png"));;
        snakeBody = bodHor;
        superpower = new Texture(Gdx.files.internal("superpower.png"));

        switch (this.level) {
            case 1:
                tiledMap = snake.getAssetManager().get("level1.tmx");
                break;
            case 2:
                tiledMap = snake.getAssetManager().get("level2.tmx");
                break;
            case 3:
                tiledMap = snake.getAssetManager().get("level3.tmx");
                break;
            case 4:
                tiledMap = snake.getAssetManager().get("level4.tmx");
                break;
            case 5:
                tiledMap = snake.getAssetManager().get("level5.tmx");
                break;
        }

        orthogonalTiledMapRenderer = new
               OrthogonalTiledMapRenderer(tiledMap, batch);
       orthogonalTiledMapRenderer.setView((OrthographicCamera) camera);

        TiledMapTileLayer grassLayer = (TiledMapTileLayer)tiledMap.getLayers().get(0);
        waterLayer = (TiledMapTileLayer)tiledMap.getLayers().get(1);






    }
    private static final int GRID_CELL = 32;
    private static final String GAME_OVER_TEXT = "Game Over... Tap space to restart!";
    private int score = 0;
    private static final int POINTS_PER_APPLE = 20;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private void drawGrid() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int x = 0; x < viewport.getWorldWidth(); x += GRID_CELL) {
            for (int y = 0; y < viewport.getWorldHeight(); y += GRID_CELL) {
                shapeRenderer.rect(x,y, GRID_CELL, GRID_CELL);
            }
        }
        shapeRenderer.end();
    }

    @Override
    public void render(float delta) {
        switch(state) {
            case PLAYING: {
                queryInput();
                updateSnake(delta);
                checkWaterCollision();
                checkAppleCollision();
                checkAndPlaceApple();
                checkAndPlaceSuperpower();
                checkSuperPowerCollison();

                if(score > 99 && !gateAvailable) openTheGate();
                if(gateAvailable) checkGateCollision();
            }
            break;
            case GAME_OVER: {
                checkForRestart();
            }
            break;
        }
        clearScreen();
        //drawGrid();
        draw();
    }
    private void checkGateCollision() {
        if(snakeX == gateX && snakeY == gateY) {
            this.snake.setScreen(new SwitchScreen(this.snake, this.level+1));
        }
    }
    private void openTheGate() {
        int x, y;
        do {
            x = MathUtils.random((int)viewport.getWorldWidth() / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
            y = MathUtils.random((int)viewport.getWorldHeight() / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
        } while (!checkIfAvailable(x, y));
        gateX = x;
        gateY = y;
        gateAvailable = true;



    }

    private void checkSuperPowerCollison() {
        if(snakeX == superX && snakeY == superY) {

            if(superSpeedOn) {
                superSpeedActive = true;
                superPowerTimer = 100;
                superSpeedOn = false;
                superpowerName = "superspeed";
            }
            if(invincibilityOn) {
                invincibilityOn = false;
                superPowerTimer = 200;
                invincibilityActive = true;
                superpowerName = "invinciblity";
            }
            if(magnetOn) {
                magnetOn = false;
                superPowerTimer = 400;
                magnetActive = true;
                superpowerName = "magnet";
            }
            if(scoreMultiplierOn) {
                scoreMultiplierOn = false;
                superPowerTimer = 1000;
                scoreMultiplierActive = true;
                superpowerName = "score";
            }

        }
    }
    private void checkAndPlaceSuperpower() {
        if(magnetOn || superSpeedOn || scoreMultiplierOn || invincibilityOn || magnetActive || superSpeedActive
        || scoreMultiplierActive || invincibilityActive) return;
        int r;
        r = MathUtils.random(1000);
        if (r < 3) {
            r = MathUtils.random(100);
            int x, y;
            do {
                x = MathUtils.random((int)viewport.getWorldWidth() / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
                y = MathUtils.random((int)viewport.getWorldHeight() / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
            } while (!checkIfAvailable(x, y));
            superX = x;
            superY = y;
            if(r%4 == 0) superSpeedOn = true;
            if(r%4 == 1) scoreMultiplierOn = true;
            if(r%4 == 2) invincibilityOn = true;
            if(r%4 == 3) magnetOn = true;
        }
    }

    private void checkWaterCollision() {
        if(waterLayer.getCell(snakeX/32, snakeY/32) != null && !invincibilityActive) {
            System.out.println("LETS GOO");
            state = STATE.GAME_OVER;
        }
    }
    private void checkForRestart() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) doRestart();
    }
    private void doRestart() {
        state = STATE.PLAYING;
        bodyParts.clear();
        score = 0;
        snakeDirection = RIGHT;
        directionSet = false;
        timer = MOVE_TIME;
        snakeX = 0;
        snakeY = 0;
        snakeXBeforeUpdate = 0;
        snakeYBeforeUpdate = 0;
        appleAvailable = false;
        superSpeedActive = false;
        magnetActive = false;
        scoreMultiplierActive = false;
        invincibilityActive = false;
        superSpeedOn = false;
        magnetOn = false;
        scoreMultiplierOn = false;
        invincibilityOn = false;
    }
    private void updateSnake(float delta) {
        if (superPowerTimer != 0) superPowerTimer -= delta;
        else {
            superSpeedActive = false;
            magnetActive = false;
            scoreMultiplierActive = false;
            invincibilityActive = false;
        }

        if(superSpeedActive) timer -= delta*16;
        else timer -= delta*8;
        if (timer <= 0) {
            timer = MOVE_TIME;
            moveSnake();
            checkForOutOfBounds();
            updateBodyPartsPosition();
            checkSnakeBodyCollision();
            directionSet = false;
        }
    }
    private void checkAndPlaceApple() {
//        if (!appleAvailable) {
//            do {
//                appleX = MathUtils.random((int)viewport.getWorldWidth()
//                        / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
//                appleY = MathUtils.random((int)viewport.getWorldHeight()
//                        / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
//                appleAvailable = true;
//            } while (appleX == snakeX && appleY == snakeY);
//        }
        if(!appleAvailable) {
            int x, y;
            do {
                x = MathUtils.random((int)viewport.getWorldWidth() / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
                y = MathUtils.random((int)viewport.getWorldHeight() / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
            } while (!checkIfAvailable(x, y));
            appleAvailable = true;
            appleX = x;
            appleY = y;
        }
    }
    private boolean checkIfAvailable(int x, int y) {
        if (x == appleX && y == appleY) return false;
        if (x == gateX && y == gateY) return false;
        if (x == snakeX && y == snakeY) return false;
        if (x == superX && y == superY) return false;
        if(waterLayer.getCell(x/32, y/32) != null) return false;
        return true;
    }
    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g,
                Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    private void draw() {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        orthogonalTiledMapRenderer.render();
        batch.begin();
        batch.draw(snakeHead, snakeX, snakeY);

        int size = bodyParts.size;
        int c = 0;
        boolean b = false;
        for(int i = size - 1; i >= 0; i--) {

            c = -1;
            for(BodyPart bodyPart : bodyParts) {
                c++;
                if(c == i) {
                    //System.out.println(c + " current direction is " + bodyParts.get(c).pieceDirection);
                    if(c == size - 1) bodyPart.updateDirection(this.snakeDirection);
                    else if(c == 0) bodyPart.setTail(bodyParts.get(c+1).pieceDirection);

                    else {
                        bodyPart.updateDirection(bodyParts.get(c+1).pieceDirection);
                    }
                    b = true;
                    break;
                }
                if(b) break;


            }
        }
        c = 0;
        for(BodyPart bodyPart : bodyParts) {
            if(c > 0 && c < size-1) {

                if(bodyParts.get(c-1).pieceDirection != bodyParts.get(c+1).pieceDirection) {
                    if(c < size-2)bodyParts.get(c+2).setL(bodyParts.get(c-1).pieceDirection, bodyParts.get(c+1).pieceDirection);
                    else bodyParts.get(c+1).setL(bodyParts.get(c-1).pieceDirection, bodyParts.get(c+1).pieceDirection);
                    c++;
                }
            }
            c++;
        }
        for (BodyPart bodyPart : bodyParts) {
            bodyPart.draw(batch);
        }

        if (appleAvailable) {
            batch.draw(apple, appleX, appleY);
        }
        if (appleAvailable) {
            batch.draw(apple, appleX, appleY);
        }
        if (magnetOn || superSpeedOn || scoreMultiplierOn || invincibilityOn) {
            if(state != STATE.GAME_OVER) batch.draw(superpower, superX, superY);
        }
        if(gateAvailable) batch.draw(gateTexture, gateX, gateY);

        if (state == STATE.GAME_OVER) {
            layout.setText(bitmapFont, GAME_OVER_TEXT);
            bitmapFont.draw(batch, GAME_OVER_TEXT, (viewport.getWorldWidth() -
                    layout.width) / 2, (viewport.getWorldHeight() - layout.height) * 1/4);
        }
        String sup = "";
        if(magnetActive || superSpeedActive || scoreMultiplierActive || invincibilityActive) {
            if(magnetActive) sup = "Magnet";
            else if(superSpeedActive) sup = "SuperSpeed";
            else if (scoreMultiplierActive) sup = "Score Multiplier";
            else if (invincibilityActive) sup = "Invincibility";
            else sup = "";

            bitmapFont.draw(batch, "" + score + " " + sup + " active", (viewport.getWorldWidth() -
                    layout.width) / 2, (viewport.getWorldHeight() - layout.height) / 2);
        }
        else bitmapFont.draw(batch, "" + score + " ", (viewport.getWorldWidth() -
                layout.width) / 2, (viewport.getWorldHeight() - layout.height) / 2);

batch.end();
    }
    private void updateIfNotOppositeDirection(int newSnakeDirection, int
            oppositeDirection) {
        if (snakeDirection != oppositeDirection || bodyParts.size == 0) {
            snakeDirection = newSnakeDirection;
        }
    }
    private void updateDirection(int newSnakeDirection) {
        if (!directionSet && snakeDirection != newSnakeDirection) {
            directionSet = true;
            switch (newSnakeDirection) {
                case LEFT: {
                    updateIfNotOppositeDirection(newSnakeDirection, RIGHT);
                    if (snakeDirection != RIGHT || bodyParts.size == 0)snakeHead = headLeft;
                    if (snakeDirection != RIGHT || bodyParts.size == 0)snakeBody = bodHor;
                }
                break;
                case RIGHT: {
                    updateIfNotOppositeDirection(newSnakeDirection, LEFT);
                    if (snakeDirection != LEFT || bodyParts.size == 0)snakeHead = headRight;
                    if (snakeDirection != LEFT || bodyParts.size == 0)snakeBody = bodHor;
                }
                break;
                case UP: {
                    updateIfNotOppositeDirection(newSnakeDirection, DOWN);
                    if (snakeDirection != DOWN || bodyParts.size == 0)snakeHead = headTop;
                    if (snakeDirection != DOWN || bodyParts.size == 0)snakeBody = bodVert;
                }
                break;
                case DOWN: {
                    updateIfNotOppositeDirection(newSnakeDirection, UP);
                    if (snakeDirection != UP || bodyParts.size == 0)snakeHead = headBottom;
                    if (snakeDirection != UP || bodyParts.size == 0)snakeBody = bodVert;
                }
                break;
            }
        }

    }
    private void checkSnakeBodyCollision() {
        for (BodyPart bodyPart : bodyParts) {
            if (bodyPart.x == snakeX && bodyPart.y == snakeY && !invincibilityActive) state = STATE.GAME_OVER;
        }
    }
    private void checkForOutOfBounds() {
        if (snakeX >= viewport.getWorldWidth()) {
            snakeX = 0;
        }
        if (snakeX < 0) {
            snakeX = (int)viewport.getWorldWidth() - SNAKE_MOVEMENT;
        }
        if (snakeY >= viewport.getWorldHeight()) {
            snakeY = 0;
        }
        if (snakeY < 0) {
            snakeY = (int)viewport.getWorldHeight() - SNAKE_MOVEMENT;
        }
    }
    private int snakeXBeforeUpdate = 0, snakeYBeforeUpdate = 0;
    private void moveSnake() {
        snakeXBeforeUpdate = snakeX;
        snakeYBeforeUpdate = snakeY;
        switch (snakeDirection) {


            case RIGHT: {
                snakeX += SNAKE_MOVEMENT;


                return;
            }
            case LEFT: {
                snakeX -= SNAKE_MOVEMENT;
                return;
            }
            case UP: {
                snakeY += SNAKE_MOVEMENT;
                return;
            }
            case DOWN: {
                snakeY -= SNAKE_MOVEMENT;
                return;
            }

        }

    }
    private void updateBodyPartsPosition() {
        if (bodyParts.size > 0) {
            BodyPart bodyPart = bodyParts.removeIndex(0);
            bodyPart.updateBodyPosition(snakeXBeforeUpdate,
                    snakeYBeforeUpdate);
            bodyParts.add(bodyPart);
        }
    }

    private void addToScore() {
        score += POINTS_PER_APPLE;
        if (scoreMultiplierActive) score += POINTS_PER_APPLE;
    }
    private void checkAppleCollision() {
        boolean magnetEffect = false;
        if(magnetActive && appleAvailable) {
            if(Math.abs(snakeX - appleX) <= 64 && Math.abs(snakeY-appleY) <= 64) {
                magnetEffect = true;
            }
        }

        if ( (appleAvailable && appleX == snakeX && appleY == snakeY) || magnetEffect) {
            BodyPart bodyPart = new BodyPart(snakeBody, this.snakeDirection);
            bodyPart.updateBodyPosition(snakeX, snakeY);
            bodyParts.insert(0,bodyPart);

            addToScore();
            appleAvailable = false;
        }
    }

    private void queryInput() {
        boolean lPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean uPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        if (lPressed) {
            updateDirection(LEFT);

        }
        if (rPressed) {
            updateDirection(RIGHT);

        }
        if (uPressed) {
            updateDirection(UP);

        }
        if (dPressed) {
            updateDirection(DOWN);

        }
    }

    private class BodyPart {
        private int x, y;
        private int pieceDirection;
        private Texture texture;
        boolean isTail = false;
        boolean isL = false;
        Texture LT;
        public BodyPart(Texture texture, int pieceDirection) {

            if(pieceDirection == UP || pieceDirection == DOWN) this.texture = bodVert;
            else if(pieceDirection == LEFT || pieceDirection == RIGHT) this.texture = bodHor;


            this.pieceDirection = pieceDirection;
        }
        public void updateBodyPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public void updateDirection(int direction) {
            this.pieceDirection = direction;
            if(pieceDirection == UP || pieceDirection == DOWN) this.texture = bodVert;
            else if(pieceDirection == LEFT || pieceDirection == RIGHT) this.texture = bodHor;
        }
        public void draw(Batch batch) {
            if(pieceDirection == UP || pieceDirection == DOWN) this.texture = bodVert;
            else if(pieceDirection == LEFT || pieceDirection == RIGHT) this.texture = bodHor;
            if(isL) {
                if (!(x == snakeX && y == snakeY)) {
                    batch.draw(LT, x, y);
                }
                isL = false;
            }
            else if(isTail) {
                if (!(x == snakeX && y == snakeY)) {
                    switch (this.pieceDirection) {
                        case UP:
                            batch.draw(tailUp, x, y);
                            break;


                        case LEFT:
                            batch.draw(tailRight, x, y);
                            break;
                        case RIGHT:
                            batch.draw(tailLeft, x, y);
                            break;

                        case DOWN:
                            batch.draw(tailDown, x, y);
                            break;
                    }

                }
                isTail = false;
            }
            else if (!(x == snakeX && y == snakeY)) batch.draw(this.texture,
                    x, y);

        }

        public void setTail(int pieceDirection) {
            this.updateDirection(pieceDirection);
            isTail = true;
        }

        public void setL(int d1, int d2) {

            isL = true;
            System.out.println("" + d1 + " " + d2);
            if(d1 == 0 && d2 == 2) this.LT = L3;
            else if(d1 == 2 && d2 == 1) this.LT = L2;
            else if(d1 == 3 && d2 == 0) this.LT = L4;
            else if(d1 == 0 && d2 == 3) this.LT = L2;
            else if(d1 == 3 && d2 == 1) this.LT = L3;
            else if(d1 == 1 && d2 == 2) this.LT = L4;
            else this.LT = L;

            this.pieceDirection = d2;
        }
    }
}