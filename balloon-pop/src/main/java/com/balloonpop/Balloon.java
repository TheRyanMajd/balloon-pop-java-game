package com.balloonpop;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.util.Random;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;

public class Balloon extends App {
    protected ImageView imageView;
    protected PathTransition pT;
    private Circle circle;
    private boolean killed = false;
    private double randomPathDur; // Moved randomPathDur to a class field

    public Balloon() {
        this.circle = new Circle(20);
        Image bln = new Image(
                "file:/Users/ryanmajd/Projects/balloon pop java game/balloon-pop/src/main/resources/balloon.png");
        this.imageView = new ImageView(bln);
        imageView.setOnMouseClicked(event -> balloonPopped());
        Pane gameScreen = App.gameScreen;
        // Set the initial position of the circle (you can adjust this as needed)
        circle.setLayoutX(App.ScreenWidth / 2); // Centered horizontally
        circle.setLayoutY(App.ScreenLength); // Starts at the bottom of the screen

        // Create the PathTransition
        Random rand = new Random();
        this.randomPathDur = rand.nextInt(3, 8); // in seconds, bigger numbers = slower balloons.
        this.pT = new PathTransition(Duration.seconds(randomPathDur), circle, imageView);
        pT.setCycleCount(Timeline.INDEFINITE); // You can set the desired number of cycles

        // Add the circle and imageView to the game screen
        gameScreen.getChildren().addAll(circle, imageView);
        randTransitions();
    }

    public void delete() {
        this.killed = true;
        this.pT.setCycleCount(1);
        this.imageView.setOpacity(0);
        this.pT.stop(); // Stop the animation
        this.pT.jumpTo(Duration.seconds(0)); // Jump to the beginning of the animation
        this.pT.pause(); // Pause the animation at the beginning
        this.pT.play();
    }

    private void randTransitions() {
        Random random = new Random();
        int randomInt = random.nextInt(100, App.ScreenWidth - 150); // Generate a random X-coordinate
        circle.setLayoutX(randomInt);
        // Define the path of the circle (up and down animation)
        Path path = new Path(new MoveTo(randomInt, App.ScreenLength + 300), new LineTo(randomInt, 0));
        this.pT.setPath(path);
    }

    private void balloonPopped() {
        if (!killed) {
            playPop();
            changeLocation();
            App.p1.addScore(1);
        } else {
            delete();
        }
    }

    private void changeLocation() {
        if (!killed) {
            randTransitions();
            pT.playFromStart();
        }
    }
}
