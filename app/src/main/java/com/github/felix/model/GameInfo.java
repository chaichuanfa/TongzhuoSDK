package com.github.felix.model;

import java.io.Serializable;

/**
 * Created by chaichuanfa on 2017/11/18.
 */

public class GameInfo implements Serializable {

    private String gameUrl;

    private boolean isPortrait;

    public GameInfo(String gameUrl, boolean isPortrait) {
        this.gameUrl = gameUrl;
        this.isPortrait = isPortrait;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public boolean isPortrait() {
        return isPortrait;
    }
}
