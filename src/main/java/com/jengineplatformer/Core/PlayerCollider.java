package com.jengineplatformer.Core;

import com.JEngine.Components.Colliders.BoxCollider_Comp;
import com.JEngine.Components.Colliders.Collider_Comp;
import com.JEngine.Core.GameObject;
import com.JEngine.Core.Position.Vector3;
import com.jengineplatformer.LevelEditor.EditorRenderer;

public class PlayerCollider extends BoxCollider_Comp {
    public PlayerCollider(Vector3 initialOffset, float width, float height, boolean isTrigger, GameObject parent) {
        super(initialOffset, width, height, isTrigger, parent);
    }

    @Override
    public void onHit(Collider_Comp other)
    {
        System.out.println("Player");
    }
}
