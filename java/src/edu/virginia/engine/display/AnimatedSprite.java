package edu.virginia.engine.display;

import java.awt.image.BufferedImage;
import java.awt.Point;
import java.util.ArrayList;
import java.io.File;

import edu.virginia.engine.util.GameClock;


public class AnimatedSprite extends Sprite {

    private ArrayList<animation> animations;

    private boolean playing;

    private String fileName;

    private GameClock gameClock;

    private ArrayList<BufferedImage> frames;

    private int currentFrame;

    private int startFrame;

    private int endFrame;

    private int animationSpeed;

    static final int DEFAULT_ANIMATION_SPEED = 0;

    public AnimatedSprite(String id, String filename, Point position) {
        super(id, filename);
        super.setPosition(position);

        this.animationSpeed = DEFAULT_ANIMATION_SPEED;
        this.gameClock = new GameClock();
    }

    public void initGameClock() {
        if(this.gameClock == null){
            this.gameClock = new GameClock();
        }
    }
}
