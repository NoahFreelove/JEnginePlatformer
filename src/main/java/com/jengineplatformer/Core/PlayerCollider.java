package com.jengineplatformer.Core;

import com.JEngine.Components.Colliders.Collider_Comp;
import com.JEngine.Core.GameObject;
import com.JEngine.Core.Position.Vector3;
import com.jengineplatformer.Objects.*;

public class PlayerCollider extends Collider_Comp {
    PlatformPlayer player;
    public PlayerCollider(Vector3 initialOffset, float width, float height, boolean isTrigger, GameObject parent) {
        super(initialOffset, width, height, false, parent);
        player = (PlatformPlayer) parent;
    }

    @Override
    public boolean isCollidingWith(Collider_Comp collider) {
        if(collider == null)
            return false;

        // logic to check if positions are in range (checks only x and y values, z values are irrelevant)
        float x1 = collider.getPosition().x;
        float y1 = collider.getPosition().y;
        float x2 = (collider.getPosition().x + collider.getWidth());
        float y2 = (collider.getPosition().y + collider.getHeight());
        float x3 = getPosition().x;
        float y3 = getPosition().y;
        float x4 = (getPosition().x + getWidth());
        float y4 = (getPosition().y+ getHeight());

        return (x1 <= x4) && (x3 <= x2) && (y1 <= y4) && (y3 <= y2);
    }

    /**
     * Check if the pawn can move if it has a hard collider
     * @param xDisplacement desired x displacement
     * @param yDisplacement desired y displacement
     * @return true if the pawn can move, false if it cannot
     */
    @Override
    public boolean canMove(float xDisplacement, float yDisplacement) {
        PlayerCollider tmpCollider = new PlayerCollider(getPosition(), getWidth(), getHeight(), false, getParent());
        tmpCollider.setPosition(new Vector3(getPosition().x + xDisplacement, getPosition().y + yDisplacement, getPosition().z));
        return !tmpCollider.isCollidingWithHard();
    }

    @Override
    public void onHit(Collider_Comp other) {
        if(player == null)
            return;
        switch (other.getParent().getIdentity().getTag()) {
            case "spike", "shooterprojectile" -> player.die();
            case "enemy" -> {
                if (player.isStrongGravity() && !player.getPhysicsBody().isOnGround()) {
                    ((Enemy) other.getParent()).die();
                    player.addScore(100);
                } else {
                    player.die();
                }
            }
            case "breakablewall" ->{
                if (player.isStrongGravity() && !player.getPhysicsBody().isOnGround()) {
                    ((BreakableWall) other.getParent()).DestroyWall();
                    player.addScore(50);
                }
            }
            case "boostpad" -> {
                //player.Move(Direction.Up, 30);
                ((Boostpad)other.getParent()).RequestBoost(player);
            }
            case "shooter" -> {
                if (player.isStrongGravity() && !player.getPhysicsBody().isOnGround()) {
                    ((Shooter) other.getParent()).Die();
                    player.addScore(100);
                }
            }
            case "coin" -> {
                ((GoldCoin)other.getParent()).collect();
                player.addScore(200);
            }
            case "wintile" -> player.win();
        }
    }
}