package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.GameImage;
import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Sprite;
import com.JEngine.Game.Visual.Scenes.GameScene;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.Utility.ImageProcessing.GenerateSolidTexture;
import com.jengineplatformer.Core.Wall;
import com.jengineplatformer.Main;

import java.io.File;

public class EditorRenderer {
    public static GameScene editorScene = LevelLoader.loadFromFile(new File("Levels/level").getAbsolutePath());
    public static CameraControl cameraController = new CameraControl();
    private static boolean hasInit;
    public static void LoadEditor() {
        SceneManager.switchScene(editorScene);
        SceneManager.setActiveCamera(cameraController.getCamera());
        if (!hasInit)
        {
            hasInit = true;
            InitEditor();
        }
        ReloadScene();
    }

    private static void InitEditor(){

    }

    private static void ReloadScene(){
        editorScene = LevelLoader.loadFromFile(new File("Levels/level").getAbsolutePath());
    }
    public static void QuitEditor(){
        SceneManager.switchScene(Main.mainMenu);
        SceneManager.setActiveCamera(Main.mainCamera);
    }
}
