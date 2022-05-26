package com.jengineplatformer.Objects;

import com.JEngine.Components.Colliders.BoxCollider_Comp;
import com.JEngine.Core.GameImage;
import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Sprite;

public class WinTile extends Sprite {
    public WinTile(Vector3 pos) {
        super(Transform.simpleTransform(pos), new GameImage("images/wintile.png"), new Identity("wintile"));
        addCollider(new BoxCollider_Comp(Vector3.emptyVector(), 64,64, true, this));
    }

}
