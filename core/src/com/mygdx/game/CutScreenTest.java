package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;



public class CutScreenTest extends GameScreen {
    private Image _transitionImage;
    private Action _screenFadeOutAction;
    private Action _screenFadeInAction;

    public CutScreenTest(Snake snake) {
        super(snake);



        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        Drawable drawable = new TextureRegionDrawable(
                new TextureRegion(new Texture(pixmap)));
        _transitionImage = new Image();
        _transitionImage.setFillParent(true);
        _transitionImage.setDrawable(drawable);


        _screenFadeOutAction = new Action() {
            @Override
            public boolean act(float delta) {
                _transitionImage.addAction(
                        Actions.sequence(
                                Actions.alpha(0),
                                Actions.fadeIn(3)
                        ));
                return true;
            }
        };
        _screenFadeInAction = new Action() {
            @Override
            public boolean act(float delta) {
                _transitionImage.addAction(
                        Actions.sequence(
                                Actions.alpha(1),
                                Actions.fadeOut(3)
                        ));
                return true;
            }
        };
    }


}
