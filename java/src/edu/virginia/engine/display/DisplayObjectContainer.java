package edu.virginia.engine.display;

import java.awt.*;
import java.util.ArrayList;

public class DisplayObjectContainer extends DisplayObject{

    ArrayList<DisplayObject> displayChilden;

    public DisplayObjectContainer(){ displayChilden = new ArrayList<DisplayObject>(); }

    public DisplayObjectContainer(String gameId){ super(gameId); }

    public DisplayObjectContainer(String id, String imageFileName) {
        super(id, imageFileName);
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

        applyTransformations((Graphics2D) g);

        for(DisplayObject i : displayChilden){
            i.draw(g);
        }

        reverseTransformations((Graphics2D) g);
    }
}
