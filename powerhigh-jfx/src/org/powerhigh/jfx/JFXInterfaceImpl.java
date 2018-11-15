package org.powerhigh.jfx;

import org.powerhigh.graphics.Interface;
import org.powerhigh.input.Input;
import org.powerhigh.jfx.input.JFXMouse;
import org.powerhigh.utils.Area;
import org.powerhigh.utils.Color;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


// TODO use com.sun.media.jfxmediaimpl.AudioClipProvider.create for sound
public class JFXInterfaceImpl extends Interface {

	public static Stage stage;
	protected static boolean closeRequested;
	private Object answer;
	public static Canvas gameCanvas;
	private boolean inited;
	private GCDrawer drawer;
	private boolean visible;
	private Color background = Color.BLUE;
	static JFXInterfaceImpl instance;
	private JFXMouse mouse;
	
	public static class JFXApp extends Application {

		private BorderPane pane;
		private Scene scene;
		
		public JFXApp() {
			
		}
		
		@Override
		public void start(Stage primaryStage) throws Exception {
			pane = new BorderPane();
			scene = new Scene(pane, 620, 480);
			gameCanvas = new Canvas();
			pane.setTop(gameCanvas);
			gameCanvas.setOnMouseMoved((e) -> {
				instance.mouse.mouseMoved((int) e.getX(), (int) e.getY(), (int) e.getScreenX(), (int) e.getScreenY());
			});
			gameCanvas.setOnMouseDragged((e) -> {
				instance.mouse.mouseDragged(e.getButton().ordinal() - 1, (int) e.getX(), (int) e.getY());
			});
			gameCanvas.setOnMousePressed((e) -> {
				instance.mouse.mousePressed(e.getButton().ordinal() - 1, (int) e.getX(), (int) e.getY());
			});
			gameCanvas.setOnMouseReleased((e) -> {
				instance.mouse.mouseReleased(e.getButton().ordinal() - 1, (int) e.getX(), (int) e.getY());
			});
			stage = primaryStage;
			stage.setScene(scene);
			stage.setOnCloseRequest((event) -> {
				closeRequested = true;
			});
		}
		
	}
	
	public JFXInterfaceImpl() {
		instance = this;
		Thread t = new Thread(() -> {
			Application.launch(JFXApp.class);
		});
		t.start();
		drawer = new GCDrawer();
		init();
	}
	
	@Override
	public void setBackground(Color color) {
		background = color;
	}

	@Override
	public Color getBackground() {
		return background;
	}

	@Override
	public void show() {
		if (!inited) {
			inited = true;
			instance.mouse = new JFXMouse(0, 0, instance);
			Input.setMouseImpl(mouse);
		}
		Platform.runLater(() -> {
			stage.show();
		});
		visible = true;
	}

	@Override
	public void hide() {
		Platform.runLater(() -> {
			stage.hide();
		});
	}

	@Override
	public boolean isCloseRequested() {
		return closeRequested;
	}

	@Override
	public boolean isVisible() {
		answer = null;
		Platform.runLater(() -> {
			answer = stage.isShowing();
		});
		while (answer == null) {
			Thread.onSpinWait();
		}
		return (Boolean) answer;
	}

	@Override
	public void setSize(int width, int height) {
		Platform.runLater(() -> {
			stage.setWidth(width);
			stage.setHeight(height);
		});
		System.out.println("Hello World!");
	}
	
	
	
	@Override
	public void update() {
		if (visible) {
			super.update();
			Platform.runLater(() -> {
				gameCanvas.setTranslateX(getViewport().getX());
				gameCanvas.setTranslateY(getViewport().getY());
				gameCanvas.setWidth(getViewport().getWidth());
				gameCanvas.setHeight(getViewport().getHeight());
				GraphicsContext gc = gameCanvas.getGraphicsContext2D();
				drawer.setGC(gc);
				JFXInterfaceImpl.getRenderer().render(JFXInterfaceImpl.this, drawer);
			});
		}
	}

	@Override
	public Area getSize() {
		answer = null;
		Platform.runLater(() -> {
			answer = new Area((int) stage.getWidth(), (int) stage.getHeight());
		});
		while (answer == null) {
			Thread.onSpinWait();
		}
		return (Area) answer;
	}

	@Override
	public int getWidth() {
		return getSize().getWidth();
	}

	@Override
	public int getHeight() {
		return getSize().getHeight();
	}

}
