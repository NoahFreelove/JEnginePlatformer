package com.jengineplatformer;

import com.JEngine.Core.GameObject;
import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.GameWindow;
import com.JEngine.Game.Visual.Scenes.GameScene;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.Game.Visual.SearchType;
import com.JEngine.Utility.About.GameInfo;
import com.jengineplatformer.Core.PlatformPlayer;
import com.jengineplatformer.LevelEditor.EditorManager;
import com.jengineplatformer.LevelEditor.LevelLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainMenu extends GameScene {

    public MainMenu() {
        super("Main Menu");
        create();
    }

    private void create(){
        Text errText = new Text("");
        errText.setFill(Color.RED);
        errText.setFont(Font.font("verdana", FontWeight.LIGHT, 15));
        errText.setX(10);
        errText.setY(30);
        addUI(errText);

        // create main menu text
        Text titleText = new Text(GameInfo.getAppName());
        titleText.setFill(Color.WHITE);
        titleText.setFont(Font.font("verdana", FontWeight.LIGHT, 30));
        titleText.setX(640 - titleText.getLayoutBounds().getWidth()/2);
        titleText.setY(200 - titleText.getLayoutBounds().getHeight()/2);
        addUI(titleText);

        Text authorText = new Text("Created by: " + GameInfo.getAuthors()[0]);
        authorText.setFill(Color.WHITE);
        authorText.setFont(Font.font("verdana", FontWeight.LIGHT, 15));
        authorText.setX(640 - authorText.getLayoutBounds().getWidth()/2);
        authorText.setY(350);
        addUI(authorText);

        TextField filepath = new TextField();
        filepath.setLayoutX(490 - filepath.getLayoutBounds().getWidth()/2);
        filepath.setLayoutY(200 + titleText.getLayoutBounds().getHeight()/2);
        filepath.setPrefWidth(300);
        filepath.setPrefHeight(30);
        filepath.setPromptText("Filepath");
        addUI(filepath);


        Button loadLevelButton = new Button("Create/Load Level");
        loadLevelButton.setLayoutX(670);
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

        Button playLevelButton = new Button("Play Level");
        playLevelButton.setLayoutX(500 - playLevelButton.getLayoutBounds().getWidth()/2);
        playLevelButton.setLayoutY(215 - playLevelButton.getLayoutBounds().getHeight()/2 + 50);
        playLevelButton.setOnAction(e -> {
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
                EditorManager.Play(false);
            }
            else
            {
                if(filepath.getText().equals(""))
                {
                    errText.setText("Cannot load from empty filepath");
                    return;
                }

                errText.setText("Can't find level to play!");
            }
        });
        addUI(loadLevelButton);
        addUI(playLevelButton);
    }

}
