package com.balloonpop;

import java.util.Random;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class BlueBalloon extends Balloon {

    public BlueBalloon() {
        this.circle = new Circle(20);
        Image bln = new Image(
                "file:/Users/ryanmajd/Projects/balloon pop java game/balloon-pop/src/main/resources/blueBalloon.png");

        this.imageView = new ImageView(bln);
        imageView.setOnMouseClicked(event -> balloonPopped());
        Pane gameScreen = App.gameScreen;
        // Set the initial position of the circle (you can adjust this as needed)
        circle.setLayoutX(App.ScreenWidth / 2); // Centered horizontally
        circle.setLayoutY(App.ScreenLength); // Starts at the bottom of the screen

        // Create the PathTransition
        Random rand = new Random();
        this.randomPathDur = rand.nextInt(1, 3); // in seconds, bigger numbers = slower balloons.
        this.pT = new PathTransition(Duration.seconds(randomPathDur), circle, imageView);
        pT.setCycleCount(Timeline.INDEFINITE); // You can set the desired number of cycles

        // Add the circle and imageView to the game screen
        gameScreen.getChildren().addAll(circle, imageView);
        randTransitions();
    }

    // FIX THIS, NEEDS TO RESET bALLOON WHEN PATH IS DONE CORRECTLY
    @Override
    protected void randTransitions() {
        Random random = new Random();
        this.randomPathDur = random.nextInt(1, 5); // in seconds, bigger numbers = slower balloons.

        // Update the duration of the existing PathTransition
        pT.setDuration(Duration.seconds(randomPathDur));

        int randomInt = random.nextInt(100, App.ScreenWidth - 150); // Generate a random X-coordinate
        circle.setLayoutX(randomInt);

        // Update the path of the existing PathTransition
        Path path = new Path(new MoveTo(randomInt, App.ScreenLength + 300), new LineTo(randomInt, -20));
        pT.setPath(path);
        pT.setOnFinished(e -> {
            randTransitions();
            this.pT.playFromStart();
        });
    }

    /**
     * When called, the balloon will randomize the path transition and start from
     * the beginning.
     */
    @Override
    protected void changeLocation() {
        if (!killed) {
            randTransitions();
            this.pT.playFromStart();
        }
    }

    /**
     * This method is called when the blue balloon is popped.
     * If the balloon is not already killed, it plays a pop sound, changes the
     * balloon's location, and adds 3 points to the player's score.
     * If the balloon is already killed, it deletes the balloon.
     */
    @Override
    protected void balloonPopped() {
        if (!killed) {
            playPop();
            changeLocation();
            App.p1.addScore(3);
        } else {
            delete();
        }
    }
}
