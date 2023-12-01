package com.balloonpop;

/* imports */
import java.io.File;
import java.io.InputStream;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Random;
import javafx.util.Duration;

// TODO: Rm ROOT Directory so others can download and run it

public class App extends Application {
    /* Class Variables */
    private VBox root;
    public static Pane gameScreen = new Pane();
    public static Label gameScore;
    private Label title, splashText, scoreInformation, infoTab, playScoreLabel;
    private String phrases[] = { "Hello, world!", "Under Construction", "Internships r Cool!",
            "Programming is fun.",
            "What the dog doin?!", "Can you even remember why you came here?",
            "Value your time!", "Made using: Procrastination", "Hope this won't crash!", "🚫🚿"
    };
    public static int ScreenWidth = 900;
    public static int ScreenLength = 720;
    protected static Player p1 = new Player("Hero");
    Button playButton, playgroundButton, musicButton, getPlayerNameFromField; // title buttons
    TextField playerNameField;
    Button backButton, bkBtn, infoButton, clearButton; // game buttons
    Pane playgroundBoard;
    ArrayList<Balloon> currentBalloons = new ArrayList<>();

    /*
     * Starts the game!
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Set up the main scene
        Scene menu = new Scene(root, App.ScreenWidth, App.ScreenLength);

        // Set up the primary stage
        primaryStage.setTitle("Balloon Pop Game");
        primaryStage.setScene(menu);
        String css = this.getClass().getResource("game.css").toExternalForm();
        menu.getStylesheets().add(css);
        primaryStage.show();

        // Play background music
        String musicFile = "balloon-pop/src/main/resources/spinningmonkeys.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        // Ribbon setup
        HBox ribbon = new HBox();
        ribbon.alignmentProperty();
        VBox ribbonItems = new VBox();
        // Assuming YourGameClass is in the com.yourpackage package
        InputStream inputStream = App.class.getResourceAsStream("/funBtn.jpg");
        Image funBtnImg = new Image(inputStream);
        ImageView funBtn = new ImageView(funBtnImg);
        gameScore = new Label("Score\n" + 0); // Score starts at 0
        gameScore.setFont(new Font("Comic Sans MS", 24));
        // Set up buttons with images
        backButton = new Button("Back");
        backButton.getStyleClass().add("button-cartoony");
        backButton.setStyle("-fx-font-size: 10;");
        bkBtn = new Button("Quit");
        bkBtn.getStyleClass().add("button-cartoony");
        bkBtn.setStyle("-fx-font-size: 10;");
        infoButton = new Button();
        infoButton.setGraphic(funBtn);
        infoButton.getStyleClass().add("button-cartoony");
        InputStream isFB = App.class.getResourceAsStream("/cls.jpg");
        Image clsBtn = new Image(isFB);
        ImageView clsView = new ImageView(clsBtn);
        clearButton = new Button();
        clsView.setFitWidth(30);
        clsView.setFitHeight(30);
        clearButton.setGraphic(clsView);
        clearButton.getStyleClass().add("button-cartoony");
        clearButton.setFont(new Font(10));
        Button balloonButton = new Button();
        Button blueBalloonButton = new Button();
        InputStream isB = App.class.getResourceAsStream("/Balloon.png");
        Image bln = new Image(isB);
        ImageView blnView = new ImageView(bln);
        blnView.setFitWidth(30);
        blnView.setFitHeight(60);
        balloonButton.setGraphic(blnView);
        balloonButton.setMaxSize(blnView.getFitWidth() / 4, blnView.getFitHeight() / 4);

        balloonButton.getStyleClass().add("button-cartoony");
        InputStream isBB = App.class.getResourceAsStream("/blueBalloon.png");
        Image blueBlnBtn = new Image(isBB);
        ImageView blueBlnView = new ImageView(blueBlnBtn);
        blueBlnView.setFitWidth(30);
        blueBlnView.setFitHeight(60);
        blueBalloonButton.setGraphic(blueBlnView);
        // blueBalloonButton.setMaxSize(blueBlnView.getFitWidth() / 4,
        // blueBlnView.getFitHeight() / 4);
        blueBalloonButton.getStyleClass().add("button-cartoony");
        // Display player score information
        Pane infoPane = new Pane();
        infoPane.setPadding(new Insets(20, 10, 10, 5));
        ribbonItems.getChildren().addAll(backButton, infoButton, clearButton, balloonButton, blueBalloonButton,
                gameScore);
        ribbonItems.setSpacing(10);
        // Set up background image
        InputStream pgBG = App.class.getResourceAsStream("/playgroundbg.jpg");
        ImageView img = new ImageView(new Image(pgBG));
        InputStream bgSM = App.class.getResourceAsStream("/bgsm.png");
        ImageView pimg = new ImageView(new Image(bgSM));
        img.setX(10);
        img.setY(0);
        img.resize(1280, 720);
        this.playgroundBoard = new Pane(img);
        ribbon.getChildren().addAll(ribbonItems, playgroundBoard);
        Pane playBoard = new Pane(pimg);
        this.playScoreLabel = new Label();
        playScoreLabel.getStyleClass().add("label-subtitle");
        playScoreLabel.setText(" " + p1.name + "Score: " + p1.score);
        Label timer = new Label();
        timer.getStyleClass().add("label-subtitle");
        int time = 30;
        timer.setText(time + "s");
        timer.setPadding(new Insets(0, 10, 0, 10));
        playBoard.getChildren().addAll(playScoreLabel, timer);
        VBox playRibbon = new VBox();
        bkBtn.setMinWidth(70);
        playRibbon.getChildren().addAll(bkBtn, infoButton);
        playRibbon.setSpacing(10);
        playRibbon.setPadding(new Insets(10, 10, 20, 0));
        HBox playArea = new HBox(playRibbon, playBoard);
        // playArea.setSpacing(20);
        // CreateplaygorundMOde scene
        Scene playgroundMode = new Scene(ribbon, ScreenWidth, ScreenLength);
        playgroundMode.getStylesheets().add(css);
        Scene playMode = new Scene(playArea, ScreenWidth, ScreenLength);
        playMode.getStylesheets().add(css);

        /* Buttons! */

        clearButton.setOnAction(e -> {
            clearBoard();
        });

        balloonButton.setOnAction(e -> {
            Balloon tempBalloon = new Balloon();
            playgroundBoard.getChildren().add(tempBalloon.imageView);
            addEnemyToList(tempBalloon);
            Platform.runLater(() -> {
                tempBalloon.pT.play();
            });
        });
        blueBalloonButton.setOnAction(e -> {
            BlueBalloon tempBalloon = new BlueBalloon();
            playgroundBoard.getChildren().add(tempBalloon.imageView);
            addEnemyToList(tempBalloon);
            Platform.runLater(() -> {
                tempBalloon.pT.play();
            });
        });

        getPlayerNameFromField.setOnAction(e ->

        {
            playerNameField.setDisable(true);
            p1.setName(playerNameField.getText());
            getPlayerNameFromField.setDisable(true);
        });
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO); // Seek to the beginning when media ends
        });

        infoButton.setOnAction(e -> {
            System.out.println("Use for testing/debugging/developmenet 🫡");
            p1.addScore(100);
        });

        playgroundButton.setOnAction(e -> {
            primaryStage.setScene(playgroundMode);
            primaryStage.show();
        });
        playButton.setOnAction(e -> {
            primaryStage.setScene(playMode);
            primaryStage.show();
        });
        backButton.setOnAction(e -> {
            backToMainMenu(primaryStage, menu);
        });
        bkBtn.setOnAction(e -> {
            backToMainMenu(primaryStage, menu);
        });

        musicButton.setOnAction(e -> {
            if (mediaPlayer.statusProperty().get() == MediaPlayer.Status.PAUSED) {
                musicButton.setText("Music!");
                mediaPlayer.play();
            } else {
                musicButton.setText("No Music!");
                mediaPlayer.pause();
            }
        });

        playgroundMode.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                clearBoard();
                backToMainMenu(primaryStage, menu);
            }
        });

        menu.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        });

    } // start

    public void playPop() {
        Platform.runLater(() -> {
            gameScore.setText("Score\n" + p1.getScore());
        });
        String musicFile = "balloon-pop/src/main/resources/pop.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.dispose(); // Garbage Collection
        });
        mediaPlayer.play();
    }

    public void init() {
        // Player initialization
        p1.setName("Player");

        // Ribbon setup
        HBox ribbon = new HBox();
        ribbon.setStyle("-fx-alignment: LEFT");
        VBox ribbonItems = new VBox(ribbon);
        backButton = new Button("Back");
        infoButton = new Button("Settings");
        Text t = new Text(p1.toString());
        t.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        Pane infoPane = new Pane(t);
        infoPane.setPadding(new Insets(10, 10, 20, 0));
        ribbonItems.getChildren().addAll(backButton, infoButton, infoPane);
        ribbonItems.setPadding(new Insets(10, 10, 20, 0));
        // Title and logo
        title = new Label("Balloon Pop!");
        title.setFont(new Font(64));
        splashText = new Label(randomPhrase());
        splashText.setFont(new Font(24));
        splashText.setTextFill(Color.VIOLET);
        Pane logo = new Pane();
        logo.setPadding(new Insets(0, 0, 10, 0));
        logo.getChildren().addAll(title);
        HBox titleBox = new HBox(logo);
        titleBox.setStyle("-fx-alignment: center;"); // Center the title horizontally
        playerNameField = new TextField("Player Name");
        getPlayerNameFromField = new Button("Enter");
        playerNameField.getStyleClass().add("button-cartoony");
        getPlayerNameFromField.getStyleClass().add("button-cartoony");
        HBox nameField = new HBox();
        nameField.getChildren().addAll(playerNameField, getPlayerNameFromField);
        nameField.setStyle("-fx-alignment: center;");
        // Root layout setup
        Label temp = new Label("Can only be set once!");
        root = new VBox(temp, nameField, titleBox);

        // Buttons setup
        playButton = new Button("Play!");
        playButton.getStyleClass().add("button-cartoony");
        playgroundButton = new Button("Playground Mode");
        playgroundButton.getStyleClass().add("button-cartoony");
        musicButton = new Button("Music!");
        musicButton.getStyleClass().add("button-cartoony");
        playButton.setPadding(new Insets(0, 20, 30, 20));
        playgroundButton.setPadding(new Insets(30, 20, 30, 20));
        musicButton.setPadding(new Insets(30, 20, 20, 20));

        // Information labels
        infoTab = new Label("Created by: Ryan Majd\nVersion 0.2");
        scoreInformation = new Label(
                "Music by Kevin MacLeod (incompetech.com)\nLicensed under Creative Commons:\nBy Attribution 3.0 License http://creativecommons.org/licenses/by/3.0/");
        scoreInformation.setFont(new Font(8));
        infoTab.setStyle("-fx-alignment: center; -fx-text-align: center;");
        scoreInformation.setStyle("-fx-alignment: center; -fx-text-align: center;");
        splashText.setPadding(new Insets(0, 0, 5, 0));
        // Adding elements to the root layout
        root.getChildren().addAll(splashText, playButton, playgroundButton, musicButton, infoTab, scoreInformation);
    }

    /** {@inheritDoc} */
    @Override
    public void stop() {
        System.out.println("Thank you for trying out my game! 💗");
    } // stop

    /** Helper Methods */

    private void backToMainMenu(Stage primaryStage, Scene scene) {
        splashText.setText(randomPhrase());
        primaryStage.setScene(scene);
        scoreInformation.setText(p1.name + ": " + p1.getScore());
        scoreInformation.setFont(new Font(12));
        primaryStage.show();
    }

    /*
     * Clears the current board of all enemies.
     */
    private void clearBoard() {
        for (Balloon balloon : currentBalloons) {
            balloon.imageView.setOpacity(0);
            balloon.delete();
        }
        currentBalloons.clear();
    }

    /*
     * Randomly chooses splashtext from a predetermined list.
     * In the future will utilize an online list as well as one available for
     * offline users.
     */
    private String randomPhrase() {
        Random random = new Random();
        int index = random.nextInt(this.phrases.length);
        String str = this.phrases[index];
        return str;
    }

    /*
     * Adds balloons to the ArrayList of current balloons in the game.
     */
    private void addEnemyToList(Balloon newBalloon) {
        currentBalloons.add(newBalloon);
    }
}
