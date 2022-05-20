package com.jengineplatformer.Objects;

import com.JEngine.Components.Colliders.BoxCollider_Comp;
import com.JEngine.Core.GameImage;
import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Direction;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector2;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Pawn;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.Utility.ImageProcessing.GenerateSolidTexture;

public class ShooterProjectile extends Pawn {
    public Vector2 moveDir = new Vector2(0,0);
    float moveSpeed = 5;
    int life = 300;
    public ShooterProjectile(Vector3 position, Direction direction) {
        super(new Transform(position, Vector3.emptyVector(), Vector3.oneVector()), new GameImage(GenerateSolidTexture.generateImage(32, 32, 0xFFD00000)), new Identity("projectile", "shooterprojectile"));
        addCollider(new BoxCollider_Comp(Vector3.emptyVector(), 32,32, false,this));
        switch (direction)
        {
            case Up -> {
                moveDir = new Vector2(0,-1);
            }
            case Down -> {
                moveDir = new Vector2(0,1);
            }
            case Left -> {
                moveDir = new Vector2(-1,0);
            }
            case Right -> {
                moveDir = new Vector2(1,0);
            }
        }
    }

    @Override
    public void Update(){
        super.Update();
        life--;
        if(life <= 0)
        {
            setActive(false);
            SceneManager.getActiveScene().remove(this);
            return;
        }
        Move(moveDir, moveSpeed);
    }
}
