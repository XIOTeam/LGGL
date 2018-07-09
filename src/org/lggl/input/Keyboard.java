package org.lggl.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import org.lggl.graphics.Window;

public class Keyboard extends KeyCodes implements KeyListener {
	
	private ArrayList<Integer> downKeys = new ArrayList<Integer>();
	private Window win;
	
	public Keyboard(Window win) {
		this.win = win;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (downKeys.indexOf(key) == -1) {
			downKeys.add(key);
		}
		win.fireEvent("keyPressed", e.getKeyChar(), e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (downKeys.indexOf(key) != -1) {
			downKeys.remove(downKeys.indexOf(key));
		}
		win.fireEvent("keyReleased", e.getKeyChar(), e.getKeyCode());
	}

	public void keyTyped(KeyEvent e) {
		win.fireEvent("keyTyped", e.getKeyChar(), e.getKeyCode());
	}
	
	public void setKeyDown(int key, boolean press) {
		if (press == false) {
			if (isKeyDown(key)) {
				downKeys.remove(downKeys.indexOf(key));
			}
		} else {
			if (!isKeyDown(key)) {
				downKeys.add(key);
			}
		}
	}
	
	public Integer[] getDownKeys() {
		return downKeys.toArray(new Integer[downKeys.size()]);
	}

	public boolean isKeyDown(int key) {
		if (downKeys.indexOf(key) != -1) {
			return true;
		}
		return false;
	}

}
