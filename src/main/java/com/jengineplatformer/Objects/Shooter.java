package com.jengineplatformer.Objects;

import com.JEngine.Components.Colliders.BoxCollider_Comp;
import com.JEngine.Components.Colliders.Collider_Comp;
import com.JEngine.Core.GameImage;
import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.*;
import com.JEngine.Game.PlayersAndPawns.Sprite;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.Utility.Misc.GameTimer;
import com.JEngine.Utility.Misc.GenericMethod;
import com.jengineplatformer.LevelEditor.ObjectDictionary;

public class Shooter extends Sprite {
    private int shootDelay = 120;
    private int timeRemaining;
    public Shooter(Vector3 position, Vector3 rotation) {
        super(new Transform(position, rotation, Vector3.oneVector()), new GameImage(ObjectDictionary.objectImages[ObjectDictionary.nameToIntIndex("shooter")]), new Identity("Shooter","shooter"));
        addCollider(new BoxCollider_Comp(Vector3.emptyVector(), 64,64,false,this, "shooterprojectile"));
        timeRemaining = shootDelay;
    }


    private void shoot(){
        ShooterProjectile proj = new ShooterProjectile(getPosition(), DirectionAngleConversion.vecToDir(new Vector2(getTransform().getRotation())));
        //proj.setPosition(getPosition().add(proj.moveDir.multiply(-64)));
        SceneManager.getActiveScene().add(proj);
    }

    public void Die(){
        setActive(false);
        for (Collider_Comp c : getColliders())
        {
            c.setActive(false);
        }
        SceneManager.getActiveScene().remove(this);
    }
    @Override
    public void Update()
    {
        super.Update();
        timeRemaining--;
        if(timeRemaining<=0)
        {
            shoot();
            timeRemaining = shootDelay;
        }
    }
}
