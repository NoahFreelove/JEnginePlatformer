package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.GameObject;
import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.Scenes.GameScene;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.Game.Visual.SearchType;
import com.JEngine.Utility.GameMath;
import com.jengineplatformer.Core.PlatformPlayer;
import com.jengineplatformer.Main;

import java.io.File;

public class EditorManager {
    public static GameScene editorScene = new GameScene(5, "EditorScene");
    public static EditorCamera editorCameraController = new EditorCamera();
    public static EditorPointer pointer = new EditorPointer();
    public static String sceneFP = new File("Levels/level2").getAbsolutePath();
    public static PlatformPlayer playerRef = null;
    private static boolean hasInit;
    private static boolean isPlaying;

    private static EditorActionHistory[] actionHistory = new EditorActionHistory[25];
    private static int actionIndex;

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
        editorScene = new GameScene(5, "EditorScene");
        LevelLoader.loadFromFile(sceneFP, editorScene);
        SceneManager.switchScene(editorScene);
        ResetCamera(editorCameraController.getCamera(), editorCameraController, true);
        playerRef = null;
        editorScene.add(pointer);
        pointer.setActive(true);
        setIsPlaying(false);
    }

    public static void Play(){
        setIsPlaying(true);

        // Load the playable scene from the filepath
        editorScene = LevelLoader.loadPlayableFromFile(sceneFP);

        // Don't let the pointer add objects into the scene when we're playing!
        pointer.setActive(false);

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
        editorCameraController = new EditorCamera(); // if in the editor, reset the camera's position
        if(focusOnObject)
        {
            EditorManager.editorScene.add(objectToFocus);
        }

        editorScene.add(camToFocus);
        SceneManager.setActiveCamera(camToFocus);
    }

    public static boolean isPlaying() {
        return isPlaying;
    }

    public static void setIsPlaying(boolean isPlaying) {
        EditorManager.isPlaying = isPlaying;
    }

    public static void AddEditorAction(EditorActionHistory newAction) {
        actionHistory[actionIndex] = newAction;
        actionIndex++;
        if (actionIndex >= actionHistory.length)
        {
            // set first index to null and shift everything down
            actionHistory[0] = null;
            System.arraycopy(actionHistory, 1, actionHistory, 0, actionHistory.length - 1);
            actionIndex = actionHistory.length - 1;
        }

    }

    public static void Undo(){
        EditorActionHistory editorAction = actionHistory[actionIndex-1];
        if(actionHistory[actionIndex-1] == null)
            return;

        if(editorAction.action == EditorAction.ADD)
        {

            editorAction.object.setActive(false);
            editorScene.remove(editorAction.object);
        }
        else if (editorAction.action == EditorAction.DELETE)
        {
            editorAction.object.setActive(true);
            editorAction.object.setQueuedForDeletion(false);
            editorScene.add(editorAction.object);
        }
        actionIndex--;
        actionIndex = GameMath.clamp(0,actionHistory.length, actionIndex);
    }
}
