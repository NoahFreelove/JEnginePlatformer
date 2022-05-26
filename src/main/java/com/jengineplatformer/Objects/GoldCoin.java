package com.jengineplatformer.Objects;

import com.JEngine.Components.Colliders.BoxCollider_Comp;
import com.JEngine.Core.GameImage;
import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Sprite;
import com.JEngine.Game.Visual.Scenes.SceneManager;

public class GoldCoin extends Sprite {
    BoxCollider_Comp collider;
    public GoldCoin(Vector3 pos) {
        super(Transform.simpleTransform(pos), new GameImage("images/coin.png"), new Identity("coin"));
        collider = new BoxCollider_Comp(Vector3.emptyVector(), 64,64, true, this);
        addCollider(collider);
    }

    public void collect(){
        SceneManager.getActiveScene().remove(this);
        setActive(false);
        collider.setActive(false);
    }

}