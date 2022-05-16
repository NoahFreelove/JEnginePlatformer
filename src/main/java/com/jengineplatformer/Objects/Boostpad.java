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
import com.jengineplatformer.LevelEditor.EditorManager;

public class Boostpad extends Sprite {
    public Boostpad(Vector3 position, int width, int height, String name) {
        super(Transform.simpleTransform(position), new GameImage(GenerateSolidTexture.generateImage(width,height, 0xFFFFFF00)), new Identity(name, "boostpad"));
        addComponent(new BoxCollider_Comp(Vector3.emptyVector(), width,height, false, this));
    }
}
