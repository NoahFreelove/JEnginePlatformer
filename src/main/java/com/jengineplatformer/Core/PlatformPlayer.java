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
import com.jengineplatformer.LevelEditor.ObjectDictionary;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PlatformPlayer extends Player {
    private PhysicsBody_Comp physicsBody;

    private GameCamera camera;
    private float moveSpeed = 5f;

    private Vector2 normalGravity = PhysicsBody_Comp.getGlobalGravity();
    private Vector2 strongGravity = new Vector2(0,100f);
    private boolean isStrongGravity;
    private long score;
    public static Text scoreText = new Text();
    public static PlatformPlayer instance;

    public PlatformPlayer(Vector3 position) {
        super(new Transform(position, Vector3.emptyVector(), Vector3.oneVector()), new GameImage(ObjectDictionary.objectImages[ObjectDictionary.nameToIntIndex("player")]), new Identity("Player","player"));
        physicsBody = new PhysicsBody_Comp(true, PhysicsBody_Comp.getGlobalGravity());
        addComponent(physicsBody);
        addCollider(new PlayerCollider(new Vector3(0,0,0), 64, 64, false, this));
        this.camera = new GameCamera(getPosition(), SceneManager.getWindow(), SceneManager.getActiveScene(), this, new Identity("PlayerCamera","camera"));
        camera.setFocus(this);
        camera.setFocusOffset(new Vector2(0,-100));
        scoreText = new Text("Score: " + score);
        scoreText.setTranslateX(100);
        scoreText.setTranslateY(50);

        scoreText.setScaleX(2);
        scoreText.setScaleY(2);
        scoreText.setFill(Color.LIGHTGREEN);

        PlatformPlayer.instance = this;
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
        scoreText.setText("Score: " + score);
        super.Update();
    }

    public void jump(){
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
            if(!physicsBody.isOnGround())
            {
                isStrongGravity = true;
                physicsBody.setGravity(strongGravity);
                physicsBody.addVelocity(new Vector2(0,10));
            }
        }
    }

    @Override
    public void onKeyReleased(KeyCode keyCode)
    {

        switch (keyCode)
        {
            case S ->{
                isStrongGravity = false;
                physicsBody.setGravity(normalGravity);
            }

        }
    }

    public void die(){
        EditorManager.Play(true);
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

    public void setPlayerGravity(Vector2 newGravity)
    {
        this.normalGravity = newGravity;
        this.physicsBody.setGravity(newGravity);
    }

    public void addScore(int score)
    {
        this.score += score;
    }
}
