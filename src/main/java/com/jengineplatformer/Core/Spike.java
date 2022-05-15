package com.jengineplatformer.Core;

import com.JEngine.Components.Colliders.BoxCollider_Comp;
import com.JEngine.Core.GameImage;
import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Sprite;
import com.jengineplatformer.LevelEditor.ObjectDictionary;

public class Spike extends Sprite {
    public Spike(Vector3 position, Vector3 rotation) {
        super(new Transform(position, rotation, Vector3.oneVector()), new GameImage(ObjectDictionary.objectImages[ObjectDictionary.nameToIntIndex("spike")]), new Identity("spike","spike"));
        BoxCollider_Comp bottomCollider = new BoxCollider_Comp(new Vector3(10,32,0), 44,32, false, this);
        BoxCollider_Comp topCollider = new BoxCollider_Comp(new Vector3(31,0,0), 1,32, false, this);
        addComponent(topCollider);
        addComponent(bottomCollider);
    }
}
