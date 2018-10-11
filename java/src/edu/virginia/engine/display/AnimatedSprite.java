package edu.virginia.engine.display;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.File;

import edu.virginia.engine.util.GameClock;


public class AnimatedSprite extends Sprite {

    private ArrayList<Animation> animations;

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

        /* load all animation frames to arraylist frames in order: standing, right, left */

        this.animationSpeed = DEFAULT_ANIMATION_SPEED;
        this.gameClock = new GameClock();
    }

    public void initGameClock() {
        if(this.gameClock == null){
            this.gameClock = new GameClock();
        }
    }

    public void setAnimationSpeed(int animationSpeed) { this.animationSpeed = animationSpeed; }

    public void setAnimations() {

        this.animations = new ArrayList<Animation>;
        this.animations.add(new Animation("standing", 0, 0));
        this.animations.add(new Animation("right", 1, 4));
        this.animations.add(new Animation("standing", 5, 8));

    }

    public void draw(Graphics g) {

        //mess with clock stuff also
        super.setImage(frames.get(currentFrame));
        super.draw(g);

    }

}
