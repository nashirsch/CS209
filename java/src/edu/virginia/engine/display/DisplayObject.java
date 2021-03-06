package edu.virginia.engine.display;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * A very basic display object for a java based gaming engine
 * 
 * */
public class DisplayObject {

	/* All DisplayObject have a unique id */
	private String id;

	private DisplayObject parent;

	/* The image that is displayed by this object */
	private BufferedImage displayImage;

	private Point position;

	private Point pivotPoint;

	private Shape hitbox;

	private AffineTransform transform;

	private double Rotation;

	private boolean visible;

	private float alpha;

	private float oldAlpha;

	private double scaleX;

	private double scaleY;

	private boolean physical;

	private float gravity;

	private float xMomentum;

	private float yMomentum;

	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */

	public DisplayObject(){
		this.position = new Point(0,0);
		this.pivotPoint = new Point(0,0);
		this.Rotation = 0;
		this.visible = true;
		this.alpha = 1.0f;
		this.oldAlpha = 0.0f;
		this.scaleX = 1.0;
		this.scaleY = 1.0;
		this.parent = null;
		this.hitbox = new Rectangle(0, 0, 1, 1);
		this.transform = new AffineTransform();
		this.physical = false;
		this.gravity = 0.0f;
		this.yMomentum = 0.0f;
		this.xMomentum = 0.0f;
	}

	public DisplayObject(String id) {
		this.setId(id);
		this.position = new Point(0,0);
		this.pivotPoint = new Point(0,0);
		this.Rotation = 0;
		this.visible = true;
		this.alpha = 1.0f;
		this.oldAlpha = 0.0f;
		this.scaleX = 1.0;
		this.scaleY = 1.0;
		this.parent = null;
		this.hitbox = new Rectangle(0, 0, 1, 1);
		this.transform = new AffineTransform();
		this.physical = false;
		this.gravity = 0.0f;
		this.yMomentum = 0.0f;
		this.xMomentum = 0.0f;
	}

	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setImage(fileName);
		this.position = new Point(0,0);
		this.pivotPoint = new Point(0,0);
		this.Rotation = 0;
		this.visible = true;
		this.alpha = 1.0f;
		this.oldAlpha = 0.0f;
		this.scaleX = 1.0;
		this.scaleY = 1.0;
		this.parent = null;
		this.hitbox = new Rectangle(0, 0, 1, 1);
		this.transform = new AffineTransform();
		this.physical = false;
		this.gravity = 0.0f;
		this.yMomentum = 0.0f;
		this.xMomentum = 0.0f;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public DisplayObject getParent() { return parent; }

	public void setParent(DisplayObject parent) { this.parent = parent; }

	public Point getPosition() { return position; }

	public void setPosition(Point position) { this.position = position; }

	public Point getPivotPoint() { return pivotPoint; }

	public void setPivotPoint(Point pivotPoint) { this.pivotPoint = pivotPoint; }

	public double getRotation() { return Rotation; }

	public void setRotation(double rotation) { Rotation = rotation; }

	public boolean isVisible() { return visible; }

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}

	public boolean isPhysical() { return physical; }

	public void setPhysical(boolean physical) { this.physical = physical; }

	public float getGravity() { return gravity; }

	public void setGravity(float gravity) { this.gravity = gravity; }

	public float getxMomentum() { return xMomentum; }

	public void setxMomentum(float xMomentum) { this.xMomentum = xMomentum; }

	public float getyMomentum() { return yMomentum; }

	public void setyMomentum(float yMomentum) { this.yMomentum = yMomentum; }

	/**
	 * Returns the unscaled width and height of this display object
	 * */
	public int getUnscaledWidth() {
		if(displayImage == null) return 0;
		return displayImage.getWidth();
	}

	public int getUnscaledHeight() {
		if(displayImage == null) return 0;
		return displayImage.getHeight();
	}

	public Point localToGlobal(Point p){
		DisplayObject d = this;
		while(d != null){
			p.x += d.getPosition().getX();
			p.y += d.getPosition().getY();
			d = d.getParent();
		}
		return(p);
	}

	public Point globalToLocal(Point p){
		DisplayObject d = this;
		while(d != null){
			p.x -= d.getPosition().getX();
			p.y -= d.getPosition().getY();
			d = d.getParent();
		}
		return(p);
	}

	public Shape getHitbox(){ return(this.hitbox); }

	public boolean collidesWith(DisplayObject other){
		Shape h = other.getHitbox();
		return this.hitbox.intersects(h.getBounds());
	}

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	protected void setImage(String imageName) {
		if (imageName == null) {
			return;
		}
		displayImage = readImage(imageName);
		if (displayImage == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
		}
	}


	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 * */
	public BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			String file = ("resources" + File.separator + imageName);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		if(image == null) return;
		displayImage = image;
	}


	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */
	public void update(ArrayList<Integer> pressedKeys) {
		this.transform.setToIdentity();
		this.transform.translate(this.localToGlobal(new Point(0, 0)).x, this.localToGlobal(new Point(0, 0)).y);
		this.transform.rotate(Math.toRadians(this.getRotation()), this.pivotPoint.x, this.pivotPoint.y);
		this.transform.scale(this.scaleX, this.scaleY);
		this.hitbox = new Rectangle(0, 0, this.getUnscaledWidth(), this.getUnscaledHeight());
		this.hitbox = this.transform.createTransformedShape(this.hitbox);

		if(this.physical == true){
			this.yMomentum += this.gravity;

			if(this.id == "obstacle"){
				System.out.println(this.xMomentum + " " + this.yMomentum);
			}

			if(this.yMomentum > 8){ this.yMomentum = 8; }
			else if(this.yMomentum < -8){ this.yMomentum = -8; }
			if(this.xMomentum > 8){ this.xMomentum = 8; }
			else if(this.xMomentum < -8){ this.xMomentum = -8; }

			this.setPosition(new Point(this.getPosition().x + (int)this.xMomentum, this.getPosition().y + (int)this.yMomentum));
		}
	}

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {
		
		if (displayImage != null && this.visible) {
			
			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;

			//g2d.translate(this.pivotPoint.x, this.pivotPoint.y);
			//g2d.translate(-1 * this.pivotPoint.x, -1 * this.pivotPoint.y);

			applyTransformations(g2d);

			/* Actually draw the image, perform the pivot point translation here */
			g2d.drawImage(displayImage, 0, 0,
					(int) (getUnscaledWidth()),
					(int) (getUnscaledHeight()), null);

			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
			reverseTransformations(g2d);
		}

	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {
		g2d.translate(this.position.x, this.position.y);
		g2d.rotate(Math.toRadians(this.getRotation()), this.getPivotPoint().x, this.getPivotPoint().y);
		g2d.scale(this.scaleX, this.scaleY);
		float curAlpha;
		this.oldAlpha = curAlpha = ((AlphaComposite) g2d.getComposite()).getAlpha();
		g2d.setComposite(AlphaComposite.getInstance(3, curAlpha * this.alpha));
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
		g2d.setComposite(AlphaComposite.getInstance(3, this.oldAlpha));
		g2d.scale(1 / this.scaleX, 1 / this.scaleY);
		g2d.rotate(-1 * Math.toRadians(this.getRotation()), this.getPivotPoint().x, this.getPivotPoint().y);
		g2d.translate(-1 * this.position.x, -1 * this.position.y);
	}

}
