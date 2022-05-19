package com.jengineplatformer;

import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.GameWindow;
import com.JEngine.Game.Visual.Scenes.GameScene;
import com.jengineplatformer.LevelEditor.EditorManager;
import com.jengineplatformer.LevelEditor.LevelSaver;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {
    public static GameScene mainMenu = new GameScene("MainMenu");
    public static GameCamera mainCamera;
    private static GameWindow window;
    @Override
    public void start(Stage stage) throws IOException {
        window = new GameWindow(mainMenu, 1f, "JEngine Platformer", stage);

        Main.mainCamera = new GameCamera(new Vector3(0,0,0), window, mainMenu, null, new Identity("Main Camera", "camera"));

        createMainMenu();

        //EditorManager.LoadEditor();

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

    private static void createMainMenu(){

        Text errText = new Text("");
        errText.setFill(Color.RED);
        errText.setFont(Font.font("verdana", FontWeight.LIGHT, 15));
        errText.setX(520 - errText.getLayoutBounds().getWidth()/2);
        errText.setY(330 - errText.getLayoutBounds().getHeight()/2);
        mainMenu.addUI(errText);

        // create main menu text
        Text text = new Text("JEngine Platformer");
        text.setFill(Color.WHITE);
        text.setFont(Font.font("verdana", FontWeight.LIGHT, 30));
        text.setX(640 - text.getLayoutBounds().getWidth()/2);
        text.setY(200 - text.getLayoutBounds().getHeight()/2);
        mainMenu.addUI(text);
        window.setTargetFPS(60);
        window.setBackgroundColor(Color.BLACK);

        TextField filepath = new TextField();
        filepath.setLayoutX(490 - filepath.getLayoutBounds().getWidth()/2);
        filepath.setLayoutY(200 + text.getLayoutBounds().getHeight()/2);
        filepath.setPrefWidth(300);
        filepath.setPrefHeight(30);
        filepath.setPromptText("Filepath");
        mainMenu.addUI(filepath);


        Button loadLevelButton = new Button("Create/Load Level");
        loadLevelButton.setLayoutX(580 - loadLevelButton.getLayoutBounds().getWidth()/2);
        loadLevelButton.setLayoutY(215 - loadLevelButton.getLayoutBounds().getHeight()/2 + 50);
        loadLevelButton.setOnAction(e -> {
            if(filepath.getText().equals("tmp")) {
                errText.setText("Cannot load from tmp file. It is reserved for editor use.");
                return;
            }

            if(filepath.getText().equals("")) {
                errText.setText("Cannot load from empty filepath");
                return;
            }
            if(new File("Levels/" + filepath.getText()).exists()) {
                EditorManager.sceneFP = new File("Levels/" +filepath.getText()).getAbsolutePath();
                System.out.println("loading level");
                EditorManager.LoadEditor();
            }
            else
            {
                if(filepath.getText().equals(""))
                {
                    errText.setText("Cannot load from empty filepath");
                    return;
                }
                File f = new File("Levels/"+filepath.getText());
                try {
                    if(f.createNewFile())
                    {
                        FileWriter fw = new FileWriter(f);
                        fw.write(filepath.getText() + "\n");
                        fw.close();
                        EditorManager.sceneFP = f.getAbsolutePath();
                        EditorManager.LoadEditor();
                        return;
                    }
                } catch (IOException ex) {
                    //ignore
                }
                errText.setText("Failed to create file");
                System.err.println("Failed to create file - Likely filepath is invalid (may contain special characters)");
            }
        });
        mainMenu.addUI(loadLevelButton);

    }
}