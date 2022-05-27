package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector2;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Player;
import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.Utility.Input;
import javafx.scene.input.KeyCode;

public class EditorCamera extends Player {
    private final GameCamera camera;
    private float moveSpeed = 10;
    private float normalSpeed = 10;
    private float sprintSpeed = 20;
    public EditorCamera() {
        super(Transform.exSimpleTransform(0,0), null, new Identity("CameraController", "controller"));
        this.camera = new GameCamera(new Vector3(0,0,0), SceneManager.getWindow(), EditorManager.editorScene, this, new Identity("EditorCamera", "camera"));
        SceneManager.setActiveCamera(camera);
    }

    @Override
    public void Update(){
        camera.setPosition(getPosition());
        if(Input.W_Pressed)
        {
            Move(new Vector2(0,-1), moveSpeed);
        }
        if(Input.S_Pressed)
        {
            Move(new Vector2(0,1), moveSpeed);
        }
        if(Input.A_Pressed)
        {
            Move(new Vector2(-1,0), moveSpeed);
        }
        if(Input.D_Pressed)
        {
            Move(new Vector2(1,0), moveSpeed);
        }
        if(Input.Shift_Pressed)
        {
            moveSpeed = sprintSpeed;
        }
        else
        {
            moveSpeed = normalSpeed;
        }
    }

    @Override
    public void onKeyReleased(KeyCode key) {
        switch (key)
        {
            case DIGIT1 -> {
                EditorManager.pointer.setSelectedObject("wall");
                EditorManager.currentSelectedObjectText.setText("Selected: Wall");
            }

            case DIGIT2 -> {EditorManager.pointer.setSelectedObject("spike");
                EditorManager.currentSelectedObjectText.setText("Selected: Spike");
            }

            case DIGIT3 -> {EditorManager.pointer.setSelectedObject("enemy");
                EditorManager.currentSelectedObjectText.setText("Selected: Enemy");
            }
            case DIGIT4 -> {EditorManager.pointer.setSelectedObject("player");
                EditorManager.currentSelectedObjectText.setText("Selected: Player");
            }
            case DIGIT5 -> {EditorManager.pointer.setSelectedObject("breakablewall");
                EditorManager.currentSelectedObjectText.setText("Selected: Breakable Wall");
            }
            case DIGIT6 -> {EditorManager.pointer.setSelectedObject("boostpad");
                EditorManager.currentSelectedObjectText.setText("Selected: Boostpad");
            }
            case DIGIT7 -> {EditorManager.pointer.setSelectedObject("shooter");
                EditorManager.currentSelectedObjectText.setText("Selected: Shooter");
            }
            case DIGIT8 -> {EditorManager.pointer.setSelectedObject("coin");
                EditorManager.currentSelectedObjectText.setText("Selected: Gold Coin");
            }
            case DIGIT9 -> {EditorManager.pointer.setSelectedObject("wintile");
                EditorManager.currentSelectedObjectText.setText("Selected: Win Tile");
            }
            case Z -> {
                if (Input.Control_Pressed)
                {
                    EditorManager.Undo();
                }
            }
            case S -> {
                if (Input.Control_Pressed)
                {
                    LevelSaver.SaveLevel(EditorManager.editorScene, EditorManager.sceneFP);
                }
            }
            case ESCAPE -> EditorManager.QuitEditor();
        }
    }


    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public GameCamera getCamera() {
        return camera;
    }
}
