package se233.asteroids.view;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import se233.asteroids.Launcher;
import se233.asteroids.controller.GameStageController;

public class GameStage extends Pane {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private GameStageController controller;
    private Pane gameoverScreen;
    private Label scoreLabel;
    private Label livesLabel;

    public GameStage() {
        setPrefSize(WIDTH, HEIGHT);

        Image backgroundImage = new Image(Launcher.class.getResourceAsStream("/se233/asteroids/assets/background/background.png"));

        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        setBackground(new Background(bgImage));

        controller = new GameStageController(this);

        scoreLabel = new Label("Score: " + controller.getScore());
        scoreLabel.setStyle("-fx-text-fill: white;");
        scoreLabel.setLayoutX(10);
        scoreLabel.setLayoutY(10);

        livesLabel = new Label("Lives: 3");
        livesLabel.setStyle("-fx-text-fill: white;");
        livesLabel.setLayoutX(WIDTH-100);
        livesLabel.setLayoutY(10);

        getChildren().addAll(scoreLabel, livesLabel);

        setOnKeyPressed(event -> {
            controller.handleKeyPressed(event);
        });

        setOnKeyReleased(event -> {
            controller.handleKeyReleased(event);
        });

        controller.startGameLoop();
    }

    public void showGameOverScreen(int score){
        if (gameoverScreen != null) {
            getChildren().remove(gameoverScreen);
        }

        gameoverScreen = new Pane();
        gameoverScreen.setPrefSize(WIDTH, HEIGHT);

        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setStyle("-fx-text-fill: white;");
        gameOverLabel.setFont(new Font("Arial", 40));
        gameOverLabel.setLayoutX(350);
        gameOverLabel.setLayoutY(150);

        Label finalScoreLabel = new Label("Final Score: " + controller.getScore());
        finalScoreLabel.setStyle("-fx-text-fill: white;");
        finalScoreLabel.setFont(new Font("Arial", 20));
        finalScoreLabel.setLayoutX(360);
        finalScoreLabel.setLayoutY(250);

        Button closeButton = new Button("Close");
        closeButton.setLayoutX(375);
        closeButton.setLayoutY(300);
        closeButton.setOnAction(e -> {System.exit(0);});

        gameoverScreen.getChildren().addAll(gameOverLabel,finalScoreLabel, closeButton);
        getChildren().add(gameoverScreen);
    }
    public void updateScore(int score){
        scoreLabel.setText("Score: " + score);
    }

    public void updateLives(int lives) {
        livesLabel.setText("Lives: " + lives);
    }

    public Label getLivesLabel(){ return livesLabel; }

    public Label getScoreLabel() {return scoreLabel;}

    public int getWidthValue() {
        return WIDTH;
    }

    public int getHeightValue() {
        return HEIGHT;
    }
}
