package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.Game;

public class Snake extends Game {

    private final AssetManager assetManager = new AssetManager();
    @Override
    public void create () {

        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new
                InternalFileHandleResolver()));
        
        setScreen(new LoadingScreen(this));

    }

    public AssetManager getAssetManager() {
        return assetManager;
    }




}