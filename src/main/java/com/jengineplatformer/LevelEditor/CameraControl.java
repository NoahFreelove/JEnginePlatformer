package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector2;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Pawn;
import com.JEngine.Game.PlayersAndPawns.Player;
import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.Utility.Input;
import javafx.scene.input.KeyCode;

public class CameraControl extends Player {
    private final GameCamera camera;
    private float moveSpeed = 10;

    public CameraControl() {
        super(Transform.exSimpleTransform(0,0), null, new Identity("CameraController", "controller"));
        this.camera = new GameCamera(new Vector3(0,0,0), SceneManager.getWindow(), EditorManager.editorScene, this, new Identity("EditorCamera", "camera"));
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
        if(Input.Escape_Pressed)
        {
            EditorManager.QuitEditor();
        }
    }

    @Override
    public void onKeyReleased(KeyCode key)
    {
        if(key == KeyCode.DIGIT1)
        {
            EditorManager.pointer.setSelectedObject("wall");
        }
        else if (key == KeyCode.DIGIT2)
        {
            EditorManager.pointer.setSelectedObject("spike");
        }
        else if (key == KeyCode.DIGIT3)
        {
            EditorManager.pointer.setSelectedObject("enemy");
        }
        else if (key == KeyCode.DIGIT4)
        {
            EditorManager.pointer.setSelectedObject("player");
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
