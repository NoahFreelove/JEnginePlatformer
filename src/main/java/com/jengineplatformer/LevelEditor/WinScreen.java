package com.jengineplatformer.LevelEditor;

import com.JEngine.Game.Visual.Scenes.GameScene;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.jengineplatformer.MainMenu;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class WinScreen extends GameScene {
    long score;
    public WinScreen(long score) {
        super("Win Scene");
        this.score = score;
        addObjects();
    }
    private void addObjects(){
        // Create win text
        Text winText = new Text("You Win!");
        winText.setTranslateX(1280/2 - winText.getLayoutBounds().getWidth()/2);
        winText.setTranslateY(50);
        winText.setScaleX(2);
        winText.setScaleY(2);
        winText.setFill(Color.LIGHTGREEN);
        addUI(winText);

        // Create score text
        Text scoreText = new Text("Score: " + score);
        scoreText.setTranslateX(1280/2 - winText.getLayoutBounds().getWidth()/2);
        scoreText.setTranslateY(100);
        scoreText.setScaleX(2);
        scoreText.setScaleY(2);
        scoreText.setFill(Color.LIGHTGREEN);
        addUI(scoreText);

        // Create editor button
        Text editorButton = new Text("Edit Level");

        editorButton.setScaleX(2);
        editorButton.setScaleY(2);
        editorButton.setTranslateX(1280/2 - winText.getLayoutBounds().getWidth()/2);
        editorButton.setTranslateY(150);
        editorButton.setFill(Color.WHITE);
        // set background color to blue
        editorButton.setStyle("-fx-background-color: #8383e5;");
        editorButton.setOnMouseClicked(e -> {
            EditorManager.ReloadEditScene(true);
        });
        addUI(editorButton);

        // Create exit button
        Text exitButton = new Text("Exit To Main Menu");
        exitButton.setScaleX(2);
        exitButton.setScaleY(2);
        exitButton.setTranslateX(600);
        exitButton.setTranslateY(200);
        exitButton.setStyle("-fx-background-color: #e76f6f;");

        exitButton.setOnMouseClicked(e -> {
            SceneManager.switchScene(new MainMenu());
        });
        exitButton.setFill(Color.WHITE);
        addUI(exitButton);


    }
}
