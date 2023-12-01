package com.balloonpop;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.Random;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;

/**
 * Represents a balloon in the game.
 */
public class Balloon extends App {
    protected ImageView imageView;
    protected PathTransition pT;
    protected Circle circle;
    public boolean killed = false;
    protected double randomPathDur; // Moved randomPathDur to a class field

    public Balloon() {
        this.circle = new Circle(20);
        InputStream inputStream = App.class.getResourceAsStream("/balloon.png");
        this.imageView = new ImageView(new Image(inputStream));
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

    /**
     * Marks the balloon as killed and stops the animation.
     * Sets the opacity of the balloon's image view to 0 and jumps to the beginning
     * of the animation.
     * Pauses the animation at the beginning and then plays it.
     */
    public void delete() {
        this.killed = true;
        this.pT.setCycleCount(1);
        this.imageView.setOpacity(0);
        this.pT.stop(); // Stop the animation
        this.pT.jumpTo(Duration.seconds(0)); // Jump to the beginning of the animation
        this.pT.pause(); // Pause the animation at the beginning
        this.pT.play();
    }

    /**
     * 
     */
    /**
     * Generates a random X-coordinate for the balloon and sets its layout
     * accordingly.
     * Defines the path of the balloon for the up and down animation.
     */
    protected void randTransitions() {
        Random random = new Random();
        int randomInt = random.nextInt(100, App.ScreenWidth - 150); // Generate a random X-coordinate
        circle.setLayoutX(randomInt);
        // Define the path of the circle (up and down animation)
        Path path = new Path(new MoveTo(randomInt, App.ScreenLength + 300), new LineTo(randomInt, 0));
        this.pT.setPath(path);
    }

    /**
     * This method is called when a balloon is popped.
     * If the balloon is not already popped, it plays a pop sound, changes the
     * balloon's location, and adds 1 to the player's score.
     * If the balloon is already popped, it deletes the balloon.
     */
    protected void balloonPopped() {
        if (!killed) {
            playPop();
            changeLocation();
            App.p1.addScore(1);
        } else {
            delete();
        }
    } // balloonPopped

    /**
     * When called, the balloon will randomize the path transition and start from
     * the beginning.
     */
    protected void changeLocation() {
        if (!killed) {
            randTransitions();
            pT.playFromStart();
        }
    } // chngLoc
} // Balloon.java