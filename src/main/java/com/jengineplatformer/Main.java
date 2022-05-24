package com.jengineplatformer;

import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.GameWindow;
import com.JEngine.Game.Visual.Scenes.GameScene;
import com.JEngine.Utility.About.GameInfo;
import com.jengineplatformer.LevelEditor.EditorManager;
import com.jengineplatformer.LevelEditor.LevelSaver;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static GameScene mainMenu;
    public static GameCamera mainCamera;
    private static GameWindow window;

    @Override
    public void start(Stage stage) throws IOException {
        setupEngineInfo();
        mainMenu = new MainMenu();

        window = new GameWindow(mainMenu, 1f, "JEngine Platformer", stage);
        window.setTargetFPS(60);
        window.setBackgroundColor(Color.BLACK);
        Main.mainCamera = new GameCamera(new Vector3(0,0,0), window, mainMenu, null, new Identity("Main Camera", "camera"));

        stage.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, (event) -> {
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
        EditorManager.LoadEditor();
    }

    public static void main(String[] args) {
        launch();
    }

    static void setupEngineInfo(){
        GameInfo.authors = new String[]{"Noah Freelove"};
        GameInfo.appName = "JEngine Platformer";
        GameInfo.appVersionMajor = 1;
        GameInfo.appVersionMinor = 0.1f;
    }
}