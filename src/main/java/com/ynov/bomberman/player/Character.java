package com.ynov.bomberman.player;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Character extends Pane {

//	Character configuration
	ImageView imageView;
	int count = 3;
	int columns = 3;
	int offSetX = 0;
	int offSetY = 0;
	int width = 32;
	int height = 32;
	public SpriteHandler charachterAnimation;

	public Circle bomb;
	public boolean bombPlanted = false;
	public boolean bombExplosed = false;
	public Timer timerBomb;

	public Character(ImageView imageView) {

		this.imageView = imageView;
		this.imageView.setViewport(new Rectangle2D(offSetX, offSetY, width, height));
		charachterAnimation = new SpriteHandler(imageView, Duration.millis(200), count, columns, offSetX, offSetY,
				width, height);

		getChildren().addAll(imageView);
	}

	public void moveX(int x) {
		boolean right = x > 0 ? true : false;
		for (int i = 0; i < Math.abs(x); i++) {
			if (right) {
				this.setTranslateX(this.getTranslateX() + 1);
			} else {
				this.setTranslateX(this.getTranslateX() - 1);
			}
		}
	}

	public void moveY(int y) {
		boolean right = y > 0 ? true : false;
		for (int i = 0; i < Math.abs(y); i++) {
			if (right) {
				this.setTranslateY(this.getTranslateY() + 1);
			} else {
				this.setTranslateY(this.getTranslateY() - 1);
			}
		}
	}

	public Circle generateBomb(ArrayList<Rectangle> mapPlaces) {
		this.bombPlanted = true;

		this.timerBomb = new Timer();

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				bombPlanted = false;
				bombExplosed = true;
				System.out.println("Bomb explosed");

				cancel();
			}
		};
		this.timerBomb.schedule(task, 3000L);

		for (Rectangle rectangle : mapPlaces) {
			if (rectangle.intersects(this.getBoundsInParent().getCenterX() - 32,
					this.getBoundsInParent().getCenterY() - 32, width, height)) {
				this.bomb = new Circle(rectangle.getX() + 32 / 2, rectangle.getY() + 32 / 2, 10,
						new ImagePattern(new Image("/Bomb.png")));
			}
		}

		return this.bomb;
	}

}