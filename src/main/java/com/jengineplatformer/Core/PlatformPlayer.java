package com.jengineplatformer.Core;

import com.JEngine.Components.PhysicsBody_Comp;
import com.JEngine.Core.GameImage;
import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector2;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Player;
import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.Utility.ImageProcessing.GenerateSolidTexture;
import com.JEngine.Utility.Input;
import com.jengineplatformer.LevelEditor.EditorManager;
import javafx.scene.input.KeyCode;

public class PlatformPlayer extends Player {
    private PhysicsBody_Comp physicsBody;

    private GameCamera camera;
    private float moveSpeed = 5f;

    private Vector2 normalGravity = PhysicsBody_Comp.defaultGravity();
    private Vector2 strongGravity = new Vector2(0,100f);
    private boolean isStrongGravity;

    public PlatformPlayer(Vector3 position) {
        super(new Transform(position, Vector3.emptyVector(), Vector3.oneVector()), new GameImage(GenerateSolidTexture.generateImage(64,64,0xFF55FF45)), new Identity("Player","player"));
        physicsBody = new PhysicsBody_Comp(true, PhysicsBody_Comp.defaultGravity());
        addComponents(new PlayerCollider(new Vector3(0,0,0), 64, 64, false, this), physicsBody);
        this.camera = new GameCamera(getPosition(), SceneManager.getWindow(), SceneManager.getActiveScene(), this, new Identity("PlayerCamera","camera"));
        camera.setFocus(this);
        camera.setFocusOffset(new Vector2(0,-100));
    }

    @Override
    public void Update(){
        if(Input.Left)
        {
            physicsBody.addVelocity(new Vector2(-moveSpeed,0));
        }
        if(Input.Right)
        {
            physicsBody.addVelocity(new Vector2(moveSpeed,0));
        }
        if(Input.Up || Input.Space_Pressed)
        {
            jump();
        }
        super.Update();
    }

    private void jump(){
        if(physicsBody.isOnGround())
        {
            physicsBody.addVelocity(new Vector2(0,-15));
        }
    }

    @Override
    public void onKeyPressed(KeyCode keyCode)
    {
        if(keyCode == KeyCode.S || keyCode == KeyCode.DOWN)
        {
            isStrongGravity = true;
            physicsBody.setGravity(strongGravity);
            physicsBody.addVelocity(new Vector2(0,10));
        }
    }

    @Override
    public void onKeyReleased(KeyCode keyCode)
    {
        if(keyCode == KeyCode.S || keyCode == KeyCode.DOWN)
        {
            isStrongGravity = false;
            physicsBody.setGravity(normalGravity);
        }
    }

    public void die(){
        EditorManager.Play();
    }

    public GameCamera getCamera() {
        return camera;
    }

    public boolean isStrongGravity() {
        return isStrongGravity;
    }

    public PhysicsBody_Comp getPhysicsBody() {
        return physicsBody;
    }
}
