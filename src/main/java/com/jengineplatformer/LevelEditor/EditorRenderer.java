package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.GameObject;
import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.Scenes.GameScene;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.Game.Visual.SearchType;
import com.jengineplatformer.Core.PlatformPlayer;
import com.jengineplatformer.Main;

import java.io.File;

public class EditorRenderer {
    public static GameScene editorScene = new GameScene(5, "EditorScene");
    public static CameraControl cameraController = new CameraControl();
    public static String sceneFP = new File("Levels/level").getAbsolutePath();
    private static boolean hasInit;

    public static void LoadEditor() {
        if (!hasInit)
        {
            hasInit = true;
            InitEditor();
        }
        //ReloadEditScene();
        ReloadEditScene();
    }

    private static void InitEditor(){

    }

    private static void ReloadEditScene(){
        EditorRenderer.editorScene = new GameScene(5, "EditorScene");
        LevelLoader.loadFromFile(sceneFP, EditorRenderer.editorScene);
        SceneManager.switchScene(EditorRenderer.editorScene);
        ResetCamera(EditorRenderer.cameraController.getCamera(), EditorRenderer.cameraController, true);

    }

    public static void Play(){
        EditorRenderer.editorScene = LevelLoader.loadPlayableFromFile(sceneFP);
        PlatformPlayer player = null;
        GameCamera playerCamera = null;
        for (GameObject o:EditorRenderer.editorScene.findObjectsByIdentity("Player", "player", SearchType.SearchByNameAndTag) ) {
            if(o !=null)
            {
                player = (PlatformPlayer) o;
                playerCamera = player.getCamera();
            }
        }
        SceneManager.switchScene(editorScene);
        ResetCamera(playerCamera, null, false);

    }
    public static void QuitEditor(){
        SceneManager.switchScene(Main.mainMenu);
        SceneManager.setActiveCamera(Main.mainCamera);
    }

    /**
     * Will Set the Active camera based on if you're in the editor or an actual level
     * @param camToFocus
     * @param objectToFocus
     * @param focusOnObject
     */
    public static void ResetCamera(GameCamera camToFocus, GameObject objectToFocus, boolean focusOnObject){
        EditorRenderer.cameraController = new CameraControl();
        if(focusOnObject)
        {
            EditorRenderer.editorScene.add(objectToFocus);
        }

        EditorRenderer.editorScene.add(camToFocus);
        SceneManager.setActiveCamera(camToFocus);
    }
}
