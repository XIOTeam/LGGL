package org.powerhigh.utils;

/**
 * 24-bit (RGB) / 32-bit (RGBA) color class.
 * @author zenith391
 *
 */
public class Color {

	private int r, g, b, a;
	private boolean hasAlpha;
	
	public Color(int r, int g, int b) {
		setRed(r);
		setGreen(g);
		setBlue(b);
		this.hasAlpha = false;
	}
	
	public Color(int r, int g, int b, int a) {
		this(r, g, b);
		setAlpha(a);
		this.hasAlpha = true;
	}
	
	public int getRed() {
		return r;
	}
	
	public int getBlue() {
		return b;
	}
	
	public int getGreen() {
		return g;
	}
	
	public int getAlpha() {
		if (hasAlphaComponent())
			return a;
		return 255;
	}
	
	public void setRed(int red) {
		if (red > 255 || red < 0)
			throw new IllegalArgumentException("Red component (" + red + ") out of bounds!");
		r = red;
	}
	
	public void setGreen(int green) {
		if (green > 255 || green < 0)
			throw new IllegalArgumentException("Green component (" + green + ") out of bounds!");
		g = green;
	}
	
	public void setBlue(int blue) {
		if (blue > 255 || blue < 0)
			throw new IllegalArgumentException("Blue component (" + blue + ") out of bounds!");
		b = blue;
	}
	
	/**
	 * Change alpha value of the current color object.
	 * If the color did'nt had alpha component, it will be added
	 * @param alpha
	 */
	public void setAlpha(int alpha) {
		if (!hasAlpha)
			hasAlpha = true;
		if (alpha > 255 || alpha < 0)
			throw new IllegalArgumentException("Alpha component (" + alpha + ") out of bounds!");
		a = alpha;
	}
	
	public boolean hasAlphaComponent() {
		return hasAlpha;
	}
	
	public Color clone() {
		if (hasAlpha)
			return new Color(r, g, b, a);
		else
			return new Color(r, g, b);
	}
	
	/**
	 * "mix" 2 colors by creating an average value of the components of the target color.
	 * If the current color or the target color have an alpha component, the final color will also have one.
	 * Changes are not applied to the current object, instead a clone is created and returned.
	 * @param x
	 * @return A clone using averaged components.
	 */
	public Color mix(Color x) {
		Color c = clone();
		c.setRed((r + x.r) / 2);
		c.setGreen((g + x.g) / 2);
		c.setBlue((b + x.b) / 2);
		if (hasAlpha || x.hasAlpha) {
			c.setAlpha((a + x.a) / 2);
		}
		return c;
	}
	
	
	public static final Color BLACK = new   Color(0, 0, 0);
	public static final Color RED = new     Color(255, 0, 0);
	public static final Color GREEN = new   Color(0, 255, 0);
	public static final Color BLUE = new    Color(0, 0, 255);
	public static final Color WHITE = new   Color(255, 255, 255);
	public static final Color MAGENTA = new Color(0, 0, 0);
	public static final Color CYAN = new    Color(0, 255, 255);
	public static final Color YELLOW = new  Color(255, 255, 0);
	public static final Color GRAY = new    Color(127, 127, 127);
   public static final Color LIGHT_GRAY=new Color(157, 157, 157);
	
}
