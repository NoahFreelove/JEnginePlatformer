package com.jengineplatformer;

import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.GameWindow;
import com.JEngine.Game.Visual.Scenes.GameScene;
import com.JEngine.Utility.Settings.EnginePrefs;
import com.jengineplatformer.LevelEditor.EditorRenderer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static GameScene mainMenu = new GameScene(5, "MainMenu");
    public static GameCamera mainCamera;
    @Override
    public void start(Stage stage) throws IOException {
        GameWindow window = new GameWindow(mainMenu, 1f, "JEngine Platformer", stage);
        Main.mainCamera = new GameCamera(new Vector3(0,0,0), window, mainMenu, null, new Identity("Main Camera", "camera"));
        EnginePrefs.logExtra = true;
        // create main menu text
        Text text = new Text("JEngine Platformer");
        text.setFill(Color.WHITE);
        text.setFont(text.getFont().font(50));
        text.setX(1280/2 - text.getLayoutBounds().getWidth()/2);
        text.setY(720/2 - text.getLayoutBounds().getHeight()/2);
        mainMenu.addUI(text);
        window.setTargetFPS(60);
        window.setBackgroundColor(Color.BLACK);
        window.start();

        EditorRenderer.LoadEditor();

        stage.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == javafx.scene.input.KeyCode.F1) {
                EditorRenderer.LoadEditor();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}