package org.lggl.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import org.lggl.Material;
import org.lggl.graphics.Window;

public abstract class GameObject {

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	protected int x;
	protected int y;
	protected int rotation;
	protected int width = 15;
	protected int height = 15;
	private String name = Integer.toHexString(hashCode());
	private boolean visible = true;
	protected Rectangle hitbox = new Rectangle(0, 0, 15, 15);
	protected Color color = Color.BLACK;
	
	protected Material material = new Material(); // default
	
	public Material getMaterial() {
		return material;
	}
	
	/**
	 * Disposing a GameObject will mostly set it's variables to null so they can be cleaned (this GameObject) via Garbage Collector<br/>
	 * <b>Note:</b> For performance reasons, this don't call System.gc(), It must be manually did
	 */
	public void dispose() {
		x = y = width = height = rotation = 0;
		color = null;
		material = null;
		hitbox = null;
		name = null;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public abstract void paint(Graphics g, Window source);

	public void onEvent(String type, Object... args) {

	}

	public GameObject() {
	}

	public int getX() {
		return x;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation % 360;
	}

	public String toString() {
		return getClass().getName() + "[type=" + getClass().getSimpleName() + ",x=" + x + ",y=" + y + ",width=" + width
				+ ",height=" + height + "]";
	}

	public void centerTo(Window window) {
		x = window.getViewport().width / 2 - (width / 2);
		y = window.getViewport().height / 2 - (height / 2);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setX(int nx) {
		x = nx;
	}

	public Rectangle2D getHitbox() {
		hitbox.setBounds(x, y, width, height);
		return hitbox;
	}

	public void setY(int ny) {
		y = ny;
	}

	public boolean isColliding(GameObject g) {
		return getHitbox().intersects(g.getHitbox());
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
	}

	public void setSize(Dimension dim) {
		setSize(dim.width, dim.height);
	}

	public Dimension getSize() {
		return new Dimension(width, height);
	}
}
