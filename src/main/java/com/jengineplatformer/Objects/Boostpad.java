package com.jengineplatformer.Objects;

import com.JEngine.Components.Colliders.BoxCollider_Comp;
import com.JEngine.Components.PhysicsBody_Comp;
import com.JEngine.Core.GameImage;
import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector2;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Sprite;
import com.JEngine.Utility.ImageProcessing.GenerateSolidTexture;
import com.JEngine.Utility.Misc.GameUtility;
import com.jengineplatformer.Core.PlatformPlayer;

public class Boostpad extends Sprite {
    boolean ableToBoost = true;
    private Vector2 boostDirection = new Vector2(0, -30);
    public Boostpad(Vector3 position, int width, int height, String name) {
        super(Transform.simpleTransform(position), new GameImage(GenerateSolidTexture.generateImage(width,height, 0xFFFFFF00)), new Identity(name, "boostpad"));
        addComponent(new BoxCollider_Comp(Vector3.emptyVector(), width,height, false, this));
    }
    public Boostpad(Vector3 position, int width, int height, String name, Vector2 boostDirection) {
        super(Transform.simpleTransform(position), new GameImage(GenerateSolidTexture.generateImage(width,height, 0xFFFFFF00)), new Identity(name, "boostpad"));
        addComponent(new BoxCollider_Comp(Vector3.emptyVector(), width,height, false, this));
        this.boostDirection = boostDirection;
    }

    public void RequestBoost(PlatformPlayer player) {

        if(ableToBoost) {
            ableToBoost = false;
            GameUtility.waitForSeconds(0.2, args -> ableToBoost = true, null);
            boostObject(player.getPhysicsBody());

        }
    }

    private void boostObject(PhysicsBody_Comp physicsBody) {
        physicsBody.addVelocity(boostDirection);
    }
}
