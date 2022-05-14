package com.jengineplatformer.Core;

import com.JEngine.Components.Colliders.BoxCollider_Comp;
import com.JEngine.Components.Pathfinding_Comp;
import com.JEngine.Components.PhysicsBody_Comp;
import com.JEngine.Core.GameImage;
import com.JEngine.Core.GameObject;
import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector2;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Pawn;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.Utility.ImageProcessing.MissingTexture;
import com.JEngine.Utility.Misc.GenericMethod;
import com.jengineplatformer.LevelEditor.EditorManager;

public class Enemy extends Pawn {
    private BoxCollider_Comp enemyCollider;
    private PhysicsBody_Comp physicsBody;
    private Pathfinding_Comp patrol;

    private int patrolIndex;

    private GameObject[] patrolPoints = new GameObject[]{
            new GameObject(new Vector2(700,535)),
            new GameObject(new Vector2(900,535))
    };
    private GenericMethod success = new GenericMethod() {
        @Override
        public void call(Object[] args) {
            System.out.println(patrolIndex);
            patrolIndex++;
            if(patrolIndex == patrolPoints.length)
            {
                patrolIndex = 0;
            }
            patrol.setTarget(patrolPoints[patrolIndex]);
        }
    };

    public Enemy(Vector3 initPos, Vector3 initScale) {
        super(new Transform(initPos, Vector3.emptyVector(), initScale), new GameImage(MissingTexture.getMissingTextureImage(64, 64)), new Identity("Enemy", "enemy"));
        enemyCollider = new BoxCollider_Comp(Vector3.emptyVector(), 64, 64, false, this);
        physicsBody = new PhysicsBody_Comp(true, PhysicsBody_Comp.defaultGravity());
        patrol = new Pathfinding_Comp(patrolPoints[0]);
        patrol.setOnTargetReachedEvent(success);
        addComponents(enemyCollider, physicsBody, patrol);
    }
    public Enemy(Transform transform, GameImage image, String name) {
        super(transform, image, new Identity("Enemy", name));
        enemyCollider = new BoxCollider_Comp(Vector3.emptyVector(), 64, 64, false, this);
        physicsBody = new PhysicsBody_Comp(true, PhysicsBody_Comp.defaultGravity());
        patrol = new Pathfinding_Comp(EditorManager.playerRef);
        patrol.setOnTargetReachedEvent(success);

        addComponents(enemyCollider, physicsBody, patrol);
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
