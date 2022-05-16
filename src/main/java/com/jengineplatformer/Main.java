package com.jengineplatformer;

import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.GameWindow;
import com.JEngine.Game.Visual.Scenes.GameScene;
import com.jengineplatformer.LevelEditor.EditorManager;
import com.jengineplatformer.LevelEditor.LevelSaver;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {
    public static GameScene mainMenu = new GameScene(5, "MainMenu");
    public static GameCamera mainCamera;

    @Override
    public void start(Stage stage) throws IOException {
        GameWindow window = new GameWindow(mainMenu, 1f, "JEngine Platformer", stage);
        Main.mainCamera = new GameCamera(new Vector3(0,0,0), window, mainMenu, null, new Identity("Main Camera", "camera"));

        // create main menu text
        Text text = new Text("JEngine Platformer");
        text.setFill(Color.WHITE);
        text.setFont(Font.font("verdana", FontWeight.LIGHT, 30));
        text.setX(640 - text.getLayoutBounds().getWidth()/2);
        text.setY(360 - text.getLayoutBounds().getHeight()/2);
        mainMenu.addUI(text);
        window.setTargetFPS(60);
        window.setBackgroundColor(Color.BLACK);

        EditorManager.LoadEditor();

        stage.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == javafx.scene.input.KeyCode.F4) {
                EditorManager.ReloadEditScene(false);
            }
            if (event.getCode() == javafx.scene.input.KeyCode.F2) {
                EditorManager.PlayTempScene();
            }
            if (event.getCode() == javafx.scene.input.KeyCode.F3) {
                LevelSaver.SaveLevel(EditorManager.editorScene, EditorManager.sceneFP);
            }
            if (event.getCode() == javafx.scene.input.KeyCode.F1) {
                EditorManager.ReloadEditScene(true);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}