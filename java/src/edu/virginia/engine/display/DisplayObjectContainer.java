package edu.virginia.engine.display;

import java.awt.*;
import java.util.ArrayList;

public class DisplayObjectContainer extends DisplayObject{

    ArrayList<DisplayObject> displayChilden;

    public DisplayObjectContainer(){ displayChilden = new ArrayList<DisplayObject>(); }

    public DisplayObjectContainer(String gameId){
        super(gameId);
        displayChilden = new ArrayList<DisplayObject>();
    }

    public DisplayObjectContainer(String id, String imageFileName) {
        super(id, imageFileName);
        displayChilden = new ArrayList<DisplayObject>();
    }

    public void addChild(DisplayObject child){
        this.displayChilden.add(child);
        child.setParent(this);
    }

    public void addChildAtIndex(DisplayObject child, int i){
        this.displayChilden.add(i, child);
        child.setParent(this);
    }

    public void removeChild(String id){
        for(DisplayObject i : displayChilden){
            if(i.getId() == id){ displayChilden.remove(i); break; }
        }
    }

    public void removeChildAtIndex(DisplayObject child, int i){
        this.displayChilden.remove(i);
    }

    public void removeAll() { displayChilden = new ArrayList<DisplayObject>(); }

    public void contains(DisplayObject child) { displayChilden.contains(child); }

    public ArrayList<DisplayObject> getDisplayChilden() { return displayChilden; }

    public void draw(Graphics g){
        super.draw(g);

        Graphics2D g2d = (Graphics2D) g;
        applyTransformations(g2d);
        g2d.scale(1 / this.getScaleX(), 1 / this.getScaleY());

        for(DisplayObject i : displayChilden){
            i.draw(g);
        }

        g2d.scale(this.getScaleX(), this.getScaleY());
        reverseTransformations(g2d);
    }
}
