package edu.virginia.lab3;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class LabThreeSimulator extends Game {

    private DisplayObjectContainer sun;

    private DisplayObjectContainer earth;

    private DisplayObjectContainer uranus;

    private DisplayObject moon;

    private double spinDirection;

    public LabThreeSimulator()  {
        super("Lab Three Simulator", 1400, 1000);
        spinDirection = 1.0;

        //Create sun object
        sun = new DisplayObjectContainer("sun", "solar/sun.png");
        sun.setPosition(new Point(620, 420));
        sun.setScaleX(0.5);
        sun.setScaleY(0.5);


        earth = new DisplayObjectContainer("earth", "solar/earth.png");
        sun.addChild(earth);
        earth.setPosition(new Point(400, 400));
        earth.setScaleX(0.5);
        earth.setScaleY(0.5);
        earth.setPivotPoint(new Point(-2 * earth.getPosition().x + 350, -2 * earth.getPosition().y + 350));


        uranus = new DisplayObjectContainer("uranus", "solar/uranus.png");
        sun.addChild(uranus);
        uranus.setPosition(new Point(-500, -500));
        uranus.setScaleX(0.5);
        uranus.setScaleY(0.5);
        uranus.setPivotPoint(new Point(-2 * uranus.getPosition().x + 350, -2 * uranus.getPosition().y + 350));

        moon = new DisplayObject("moon", "solar/moon.png");
        earth.addChild(moon);
        moon.setPosition(new Point(400, 400));
        moon.setScaleX(0.5);
        moon.setScaleY(0.5);
        moon.setPivotPoint(new Point(-2 * earth.getPosition().x + 350, -2 * earth.getPosition().y + 350));
    }

    /**
     * Engine will automatically call this update method once per frame and pass to us
     * the set of keys (as strings) that are currently being pressed down
     * */
    @Override
    public void update(ArrayList<Integer> pressedKeys){
        super.update(pressedKeys);

        if (pressedKeys.contains(KeyEvent.VK_A)){
            this.spinDirection = -1.0;
        }
        if (pressedKeys.contains(KeyEvent.VK_S)){
            this.spinDirection = 1.0;
        }

        if (pressedKeys.contains(KeyEvent.VK_UP)){
            sun.getPosition().translate(0, -10);
        }
        if (pressedKeys.contains(KeyEvent.VK_DOWN)){
            sun.getPosition().translate(0, 10);
        }
        if (pressedKeys.contains(KeyEvent.VK_LEFT)){
            sun.getPosition().translate(-10, 0);
        }
        if (pressedKeys.contains(KeyEvent.VK_RIGHT)){
            sun.getPosition().translate(10, 0);
        }

        if (pressedKeys.contains(KeyEvent.VK_Q)){
            sun.setScaleX(sun.getScaleX() + 0.01);
            sun.setScaleY(sun.getScaleY() + 0.01);
        }
        if (pressedKeys.contains(KeyEvent.VK_W)){
            if(sun.getScaleX() >= 0.03){
                sun.setScaleX(sun.getScaleX() - 0.01);
                sun.setScaleY(sun.getScaleY() - 0.01);
            }
        }

        earth.setRotation(earth.getRotation() + 1.4 * spinDirection);
        uranus.setRotation(uranus.getRotation() + 0.6 * spinDirection);
        moon.setRotation(moon.getRotation() + 0.8);

    }

    /**
     * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
     * the screen, we need to make sure to override this method and call mario's draw method.
     * */
    @Override
    public void draw(Graphics g){
        super.draw(g);

        /* Same, just check for null in case a frame gets thrown in before Mario is initialized */
        if(sun != null) sun.draw(g);
    }

    /**
     * Quick main class that simply creates an instance of our game and starts the timer
     * that calls update() and draw() every frame
     * */
    public static void main(String[] args) {
        LabThreeSimulator game = new LabThreeSimulator();
        game.start();
    }
}
