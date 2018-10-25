package edu.virginia.engine.display;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

    static final int DEFAULT_ANIMATION_SPEED = 110;

    public AnimatedSprite(String id, Point position) {
        super(id);
        super.setPosition(position);

        this.currentFrame = 0;

        /* load all frames to arraylist frames in order: standing, right, left */
        this.setFrames();
        this.setAnimations();

        this.animationSpeed = DEFAULT_ANIMATION_SPEED;
        this.gameClock = new GameClock();
    }

    public void initGameClock() {
        if(this.gameClock == null){
            this.gameClock = new GameClock();
        }
    }

    public void setFrames() {
        this.frames = new ArrayList<BufferedImage>();
        frames.add(readImage("frames/mario_stand_0.png"));
        frames.add(readImage("frames/mario_right_1.png"));
        frames.add(readImage("frames/mario_right_2.png"));
        frames.add(readImage("frames/mario_right_3.png"));
        frames.add(readImage("frames/mario_right_4.png"));
        frames.add(readImage("frames/mario_left_5.png"));
        frames.add(readImage("frames/mario_left_6.png"));
        frames.add(readImage("frames/mario_left_7.png"));
        frames.add(readImage("frames/mario_left_8.png"));
        frames.add(readImage("frames/mario_down_9.png"));
        frames.add(readImage("frames/mario_down_10.png"));
        frames.add(readImage("frames/mario_up_11.png"));
        frames.add(readImage("frames/mario_up_12.png"));
    }

    public void setAnimationSpeed(int animationSpeed) { this.animationSpeed = animationSpeed; }

    public int getAnimationSpeed() { return animationSpeed; }


    public void setAnimations() {

        this.animations = new ArrayList<Animation>();
        this.animations.add(new Animation("standing", 0, 0));
        this.animations.add(new Animation("right", 1, 4));
        this.animations.add(new Animation("left", 5, 8));
        this.animations.add(new Animation("down", 9, 10));
        this.animations.add(new Animation("up", 11, 12));

    }

    public Animation getAnimation(String id){
        for (int i = 0; i < frames.size(); i++){
            if (this.animations.get(i).getId() == id) {
                return this.animations.get(i);
            }
        }
        return null;
    }

    public void animate(Animation a){
        this.startFrame = a.getStartFrame();
        this.endFrame = a.getEndFrame();
    }

    public void animate(String id){
        this.startFrame = this.getAnimation(id).getStartFrame();
        this.endFrame = this.getAnimation(id).getEndFrame();
    }

    public void animate(int start, int end){
        this.startFrame = start;
        this.endFrame = end;
    }

    public void draw(Graphics g) {

        //mess with clock stuff also
        super.setImage(frames.get(currentFrame));

        if(gameClock.getElapsedTime() >= animationSpeed){

            currentFrame++;
            if(currentFrame > endFrame || currentFrame < startFrame){
                currentFrame = startFrame;
            }

            gameClock.resetGameClock();
        }


        super.draw(g);

    }

}
