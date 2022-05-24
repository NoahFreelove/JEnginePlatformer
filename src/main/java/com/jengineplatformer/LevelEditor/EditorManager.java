package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.GameObject;
import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.Scenes.GameScene;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.Game.Visual.SearchType;
import com.JEngine.Utility.GameMath;
import com.jengineplatformer.Core.PlatformPlayer;
import com.jengineplatformer.Main;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;

public class EditorManager {
    public static GameScene editorScene = new GameScene(5, "EditorScene");
    public static EditorCamera editorCameraController = new EditorCamera();
    public static EditorPointer pointer = new EditorPointer();
    public static String sceneFP = new File("Levels/level").getAbsolutePath();
    public static PlatformPlayer playerRef = null;
    private static boolean hasInit;
    private static boolean isPlaying;
    public static Text helpText;
    public static Text currentSelectedObjectText;
    public static Text rotationText;
    private static EditorActionHistory[] actionHistory = new EditorActionHistory[25];
    private static int actionIndex;

    public static void LoadEditor() {
        if (!hasInit)
        {
            hasInit = true;
            InitEditor();
        }
        //ReloadEditScene();
        ReloadEditScene(false);
        LevelSaver.SaveLevel(editorScene, sceneFP);
    }

    public static void PlayTempScene(){
        LevelSaver.SaveLevel(editorScene, "Levels/tmp");
        Play(true);
    }

    private static void InitEditor(){

    }

    public static void ReloadEditScene(boolean loadTmp){
        editorScene = new GameScene(5, "EditorScene");
        if(sceneFP != null && !sceneFP.equals(""))
        {
            if(loadTmp)
            {
                LevelLoader.loadFromFile("Levels/tmp", editorScene);
            }
            else {
                LevelLoader.loadFromFile(sceneFP, editorScene);
            }
        }
        SceneManager.switchScene(editorScene);
        ResetCamera(editorCameraController.getCamera(), editorCameraController, true);
        playerRef = null;
        editorScene.add(pointer);
        pointer.setActive(true);
        setIsPlaying(false);

        //Create help text at the top left of the screen
        helpText = new Text("""
                WASD - Movement
                Left Click - Place Object (Drag for walls)
                Right Click - Remove Object
                Ctr+Z - Undo
                Number Keys - Select Object
                Scroll Wheel - Rotate
                F1 - Load Editor
                F2 - Play
                F3 - Save
                Escape - Quit Editor
                """);
        helpText.setFill(Color.WHITE);
        helpText.setFont(Font.font("Verdana", FontWeight.LIGHT, 15));
        helpText.setX(10);
        helpText.setY(30);
        editorScene.addUI(helpText);

        currentSelectedObjectText = new Text("Selected: " + pointer.getSelectedObject());
        currentSelectedObjectText.setFill(Color.RED);
        currentSelectedObjectText.setFont(Font.font("Verdana", FontWeight.LIGHT, 15));
        currentSelectedObjectText.setX(10);
        currentSelectedObjectText.setY(250);
        editorScene.addUI(currentSelectedObjectText);

        rotationText = new Text("Rotation: 0°");
        rotationText.setFill(Color.WHITE);
        rotationText.setFont(Font.font("Verdana", FontWeight.LIGHT, 15));
        rotationText.setX(10);
        rotationText.setY(280);
        editorScene.addUI(rotationText);
    }

    public static void Play(boolean isTmp){
        setIsPlaying(true);

        if(isTmp)
        {
            // Load the playable scene from the filepath
            editorScene = LevelLoader.loadPlayableFromFile("Levels/tmp");
        }
        else {
            // Load the playable scene from the filepath
            editorScene = LevelLoader.loadPlayableFromFile(sceneFP);
        }


        // Don't let the pointer add objects into the scene when we're playing!
        pointer.setActive(false);

        // We need to search for the player instance, so we can focus the camera around them
        PlatformPlayer player;
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
