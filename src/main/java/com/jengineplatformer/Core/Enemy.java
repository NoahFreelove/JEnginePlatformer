package com.jengineplatformer.Core;

import com.JEngine.Components.Colliders.BoxCollider_Comp;
import com.JEngine.Components.PhysicsBody_Comp;
import com.JEngine.Core.GameImage;
import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Pawn;
import com.JEngine.Game.PlayersAndPawns.Sprite;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.Utility.ImageProcessing.MissingTexture;

public class Enemy extends Pawn {
    private BoxCollider_Comp enemyCollider;
    private PhysicsBody_Comp physicsBody;

    public Enemy(Vector3 initPos, Vector3 initScale) {
        super(new Transform(initPos, Vector3.emptyVector(), initScale), new GameImage(MissingTexture.getMissingTextureImage(64, 64)), new Identity("Enemy", "enemy"));
        enemyCollider = new BoxCollider_Comp(Vector3.emptyVector(), 64, 64, false, this);
        physicsBody = new PhysicsBody_Comp(true, PhysicsBody_Comp.defaultGravity());
        addComponents(enemyCollider, physicsBody);
    }
    public Enemy(Transform transform, GameImage image, String name) {
        super(transform, image, new Identity("Enemy", name));
        enemyCollider = new BoxCollider_Comp(Vector3.emptyVector(), 64, 64, false, this);
        physicsBody = new PhysicsBody_Comp(true, PhysicsBody_Comp.defaultGravity());
        addComponents(enemyCollider, physicsBody);
    }

    @Override
    public void Update(){
        super.Update();
    }

    public void die(){
        enemyCollider.setActive(false);
        physicsBody.setActive(false);
        setActive(false);
        SceneManager.getActiveScene().remove(this);
    }

}
