package edu.virginia.lab4;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.util.GameClock;
import edu.virginia.engine.util.SoundManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class LabFour extends Game {

    private AnimatedSprite mario = new AnimatedSprite("mario", new Point(650, 200));

    private DisplayObject obstacle = new DisplayObject("obstacle", "goomba.png");

    private DisplayObject goal = new DisplayObject("goal", "mushroom.png");

    private SoundManager sounds;

    private GameClock fxClock;

    private GameClock visibilityClock;

    private String score = "100";


    public LabFour()  {
        super("Lab Four", 1400, 1000);

        mario.setScaleX(0.6);
        mario.setScaleY(0.6);

        obstacle.setScaleX(0.4);
        obstacle.setScaleY(0.4);
        obstacle.setPosition(new Point(1100, 700));

        goal.setScaleY(0.4);
        goal.setScaleX(0.4);
        goal.setPosition(new Point(200, 700));

        sounds = new SoundManager();
        sounds.LoadSoundEffect("doh", "resources/sounds/doh.wav");
        sounds.LoadSoundEffect("itsme", "resources/sounds/itsme.wav");
        sounds.LoadMusic("resources/sounds/music.wav");
        sounds.PlayMusic();

        fxClock = new GameClock();
        visibilityClock = new GameClock();
    }

    /**
     * Engine will automatically call this update method once per frame and pass to us
     * the set of keys (as strings) that are currently being pressed down
     * */
    @Override
    public void update(ArrayList<Integer> pressedKeys){
        super.update(pressedKeys);

        String currentAnimation = "standing";

        /* Exits game */
        if (pressedKeys.contains(KeyEvent.VK_P)){
            System.exit(0);
        }

        /* If game already won, pass over these controls */
        if (this.score == "You win!"){
            mario.animate(currentAnimation);
            return;
        }

        /* Arrow key movements (U, D, L, R) for Mario */
        if (pressedKeys.contains(KeyEvent.VK_UP)){
            mario.setPosition(new Point(mario.getPosition().x, mario.getPosition().y - 8));
            currentAnimation = "up";
        }
        if (pressedKeys.contains(KeyEvent.VK_DOWN)){
            mario.setPosition(new Point(mario.getPosition().x, mario.getPosition().y + 8));
            currentAnimation = "down";
        }
        if (pressedKeys.contains(KeyEvent.VK_LEFT)){
            mario.setPosition(new Point(mario.getPosition().x - 8, mario.getPosition().y));
            currentAnimation = "left";
        }
        if (pressedKeys.contains(KeyEvent.VK_RIGHT)){
            mario.setPosition(new Point(mario.getPosition().x + 8, mario.getPosition().y));
            currentAnimation = "right";
        }

        /* Pivot point movements (I (up), J (left), K (down), L (right)) for Mario */
        if (pressedKeys.contains(KeyEvent.VK_I)){
            mario.setPivotPoint(new Point(mario.getPivotPoint().x, mario.getPivotPoint().y - 5));
        }
        if (pressedKeys.contains(KeyEvent.VK_K)){
            mario.setPivotPoint(new Point(mario.getPivotPoint().x, mario.getPivotPoint().y + 5));
        }
        if (pressedKeys.contains(KeyEvent.VK_J)){
            mario.setPivotPoint(new Point(mario.getPivotPoint().x - 5, mario.getPivotPoint().y));
        }
        if (pressedKeys.contains(KeyEvent.VK_L)){
            mario.setPivotPoint(new Point(mario.getPivotPoint().x + 5, mario.getPivotPoint().y));
        }

        /* Visibility and alpha changes */
        if (pressedKeys.contains(KeyEvent.VK_V)){
            if(visibilityClock.getElapsedTime() > 200){
                mario.setVisible( !mario.isVisible());
                visibilityClock.resetGameClock();
            }
        }
        if (pressedKeys.contains(KeyEvent.VK_Z) && mario.getAlpha() > 0.02f){
            mario.setAlpha(mario.getAlpha() - 0.01f);
        }
        if (pressedKeys.contains(KeyEvent.VK_X) && mario.getAlpha() < 1.0f){
            mario.setAlpha(mario.getAlpha() + 0.01f);
        }

        /* Scale changes */
        if (pressedKeys.contains(KeyEvent.VK_A) && mario.getScaleX() > 0){
            mario.setScaleX(mario.getScaleX() - 0.01);
            mario.setScaleY(mario.getScaleY() - 0.01);
        }
        if (pressedKeys.contains(KeyEvent.VK_S)){
            mario.setScaleX(mario.getScaleX() + 0.01);
            mario.setScaleY(mario.getScaleY() + 0.01);
        }

        /* Rotation changes */
        if (pressedKeys.contains(KeyEvent.VK_W)){
            mario.setRotation(mario.getRotation() + 1.0);
        }
        if (pressedKeys.contains(KeyEvent.VK_Q)){
            mario.setRotation(mario.getRotation() - 1.0);
        }

        /* Animation speed changes */
        if (pressedKeys.contains(KeyEvent.VK_G)){
            if(mario.getAnimationSpeed() > 15){ mario.setAnimationSpeed(mario.getAnimationSpeed() - 10); }
        }
        if (pressedKeys.contains(KeyEvent.VK_F)){
            if(mario.getAnimationSpeed() < 2000){ mario.setAnimationSpeed(mario.getAnimationSpeed() + 10); }
        }

        mario.animate(currentAnimation);

        /* Create hitboxes for sprites */
        if(mario != null) mario.update(pressedKeys);
        if(obstacle != null) obstacle.update(pressedKeys);
        if(goal != null) goal.update(pressedKeys);

        /* Deal with collisions and overall game updates */
        if(mario.collidesWith(obstacle)){
            if(fxClock.getElapsedTime() > 600){
                sounds.PlaySoundEffect("doh");
                fxClock.resetGameClock();

                if(Integer.parseInt(this.score) > 0){ this.score = String.valueOf(Integer.parseInt(this.score) - 10); }
            }
        }
        if(mario.collidesWith(goal)){
            if(fxClock.getElapsedTime() > 1700){
                sounds.PlaySoundEffect("itsme");
                fxClock.resetGameClock();

                this.score = "You win!";
            }
        }

    }

    /**
     * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
     * the screen, we need to make sure to override this method and call mario's draw method.
     * */
    @Override
    public void draw(Graphics g){
        super.draw(g);

        /* Same, just check for null in case a frame gets thrown in before Mario is initialized */
        if(mario != null) mario.draw(g);
        if(obstacle != null) obstacle.draw(g);
        if(goal != null) goal.draw(g);

        g.drawString(score, 20, 40);
    }

    /**
     * Quick main class that simply creates an instance of our game and starts the timer
     * that calls update() and draw() every frame
     * */
    public static void main(String[] args) {
        LabFour game = new LabFour();
        game.start();
    }
}