package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.GameObject;
import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Direction;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector2;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Pawn;
import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.Utility.Input;

public class CameraControl extends Pawn {
    private final GameCamera camera;
    private float moveSpeed = 10;

    public CameraControl() {
        super(Transform.exSimpleTransform(0,0), null, new Identity("CameraController", "controller"));
        this.camera = new GameCamera(new Vector3(0,0,0), SceneManager.getWindow(), EditorRenderer.editorScene, this, new Identity("EditorCamera", "camera"));

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
            EditorRenderer.QuitEditor();
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
