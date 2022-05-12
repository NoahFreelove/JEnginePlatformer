package com.jengineplatformer;

import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.GameWindow;
import com.JEngine.Game.Visual.Scenes.GameScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GameScene mainMenu = new GameScene(5, "MainMenu");
        GameWindow window = new GameWindow(mainMenu, 1f, "JEngine Platformer", stage);
        GameCamera mainCamera = new GameCamera(new Vector3(0,0,0), window, mainMenu, null, new Identity("Main Camera", "camera"));

        mainMenu.add(mainCamera);
        window.setTargetFPS(60);
        window.setBackgroundColor(Color.AQUAMARINE);
        window.start();
    }

    public static void main(String[] args) {
        launch();
    }
}