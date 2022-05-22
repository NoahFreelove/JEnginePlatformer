package com.jengineplatformer.Objects;

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
import com.JEngine.Utility.Misc.GenericMethod;
import com.jengineplatformer.LevelEditor.EditorManager;
import com.jengineplatformer.LevelEditor.ObjectDictionary;

public class Enemy extends Pawn {
    private BoxCollider_Comp enemyCollider;
    private PhysicsBody_Comp physicsBody;
    private Pathfinding_Comp patrol;

    private int patrolIndex;

    private GameObject[] patrolPoints;

    private GenericMethod onPatrolSuccess = new GenericMethod() {
        @Override
        public void call(Object[] args) {
            float timeInSeconds = ((long)args[0])/1000.0f;
            //System.out.println("Patrol took: " + timeInSeconds + " Seconds");
            patrolIndex++;
            if(patrolIndex == patrolPoints.length)
            {
                patrolIndex = 0;
            }
            patrol.setTarget(patrolPoints[patrolIndex]);
        }
    };

    public Enemy(Vector3 initPos, Vector3 initScale) {
        super(new Transform(initPos, Vector3.emptyVector(), initScale), new GameImage(ObjectDictionary.objectImages[ObjectDictionary.nameToIntIndex("enemy")]), new Identity("Enemy", "enemy"));
        enemyCollider = new BoxCollider_Comp(Vector3.emptyVector(), 64, 64, false, this);
        patrolPoints = new GameObject[]{
                new GameObject(new Vector2(getPosition().x-200, getPosition().y)),
                new GameObject(new Vector2(getPosition().x +200,getPosition().y))
        };
        physicsBody = new PhysicsBody_Comp(true, PhysicsBody_Comp.getGlobalGravity());
        patrol = new Pathfinding_Comp(patrolPoints[0]);
        patrol.setOnTargetReachedEvent(onPatrolSuccess);
        addComponents(physicsBody, patrol);
        addCollider(enemyCollider);
    }
    public Enemy(Transform transform, GameImage image, String name) {
        super(transform, image, new Identity("Enemy", name));
        enemyCollider = new BoxCollider_Comp(Vector3.emptyVector(), 64, 64, false, this);
        physicsBody = new PhysicsBody_Comp(true, PhysicsBody_Comp.getGlobalGravity());
        patrol = new Pathfinding_Comp(EditorManager.playerRef);
        patrol.setOnTargetReachedEvent(onPatrolSuccess);

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
