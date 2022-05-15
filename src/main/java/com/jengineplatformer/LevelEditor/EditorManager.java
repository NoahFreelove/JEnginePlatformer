package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.GameObject;
import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.Scenes.GameScene;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.Game.Visual.SearchType;
import com.jengineplatformer.Core.PlatformPlayer;
import com.jengineplatformer.Main;

import java.io.File;

public class EditorManager {
    public static GameScene editorScene = new GameScene(5, "EditorScene");
    public static CameraControl cameraController = new CameraControl();
    public static EditorPointer pointer = new EditorPointer();
    public static String sceneFP = new File("Levels/level2").getAbsolutePath();
    public static PlatformPlayer playerRef = null;
    private static boolean hasInit;
    private static boolean isPlaying;

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
        EditorManager.editorScene = new GameScene(5, "EditorScene");
        LevelLoader.loadFromFile(sceneFP, EditorManager.editorScene);
        SceneManager.switchScene(EditorManager.editorScene);
        ResetCamera(EditorManager.cameraController.getCamera(), EditorManager.cameraController, true);
        EditorManager.playerRef = null;
        EditorManager.editorScene.add(pointer);
        EditorManager.pointer.setActive(true);
        EditorManager.setIsPlaying(false);
    }

    public static void Play(){
        EditorManager.setIsPlaying(true);

        // Load the playable scene from the filepath
        EditorManager.editorScene = LevelLoader.loadPlayableFromFile(sceneFP);

        // Don't let the pointer add objects into the scene when we're playing!
        EditorManager.pointer.setActive(false);

        // We need to search for the player instance, so we can focus the camera around them
        PlatformPlayer player = null;
        GameCamera playerCamera = null;
        for (GameObject o: EditorManager.editorScene.findObjectsByIdentity("Player", "player", SearchType.SearchByNameAndTag) ) {
            if(o !=null)
            {
                player = (PlatformPlayer) o;
                playerCamera = player.getCamera();
            }
        }

        // After everything is ready, reset the camera's position and show the scene
        SceneManager.switchScene(editorScene);
        ResetCamera(playerCamera, null, false);

    }
    public static void QuitEditor(){
        SceneManager.switchScene(Main.mainMenu);
        SceneManager.setActiveCamera(Main.mainCamera);
    }

    /**
     * Will Set the Active camera based on if you're in the editor or an actual level
     * @param camToFocus The camera to render from
     * @param objectToFocus // the object to focus (if in game, set to null if in editor)
     * @param focusOnObject // whether to focus on an object or not
     */
    public static void ResetCamera(GameCamera camToFocus, GameObject objectToFocus, boolean focusOnObject){
        EditorManager.cameraController = new CameraControl(); // if in the editor, reset the camera's position
        if(focusOnObject)
        {
            EditorManager.editorScene.add(objectToFocus);
        }

        EditorManager.editorScene.add(camToFocus);
        SceneManager.setActiveCamera(camToFocus);
    }

    public static boolean isPlaying() {
        return isPlaying;
    }

    public static void setIsPlaying(boolean isPlaying) {
        EditorManager.isPlaying = isPlaying;
    }
}
