package com.jengineplatformer.Core;

import com.JEngine.Components.Colliders.Collider_Comp;
import com.JEngine.Core.GameImage;
import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Sprite;

public class Wall extends Sprite {
    private final Collider_Comp collider;
    public Wall(Vector3 pos, Vector3 scale, GameImage newSprite) {
        super(new Transform(pos, new Vector3(0,0,0), scale), newSprite, new Identity("Wall","wall"));
        collider = new Collider_Comp(new Vector3(0,0,0), getSprite().getWidth()*scale.x, getSprite().getHeight()*scale.y, false, this);
        addComponent(collider);
    }

    public Collider_Comp getCollider() {
        return collider;
    }
}
