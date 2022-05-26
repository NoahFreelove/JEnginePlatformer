package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.GameImage;
import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector2;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Sprite;
import com.JEngine.Utility.ImageProcessing.GenerateSolidTexture;

public class SelectRectangle extends Sprite {
    Vector2 startPos;
    public SelectRectangle() {
        super(Transform.exSimpleTransform(0,0), new GameImage("images/outline.png",128,128), new Identity("SelectRect"));
        this.startPos = EditorManager.pointer.pointerPosToWorldPoint();
        setActive(false);
    }


    public void startClick(){
        setScale(new Vector3(0.1f,0.1f,0.1f));
        this.startPos = EditorManager.pointer.pointerPosToWorldPoint();
        setPosition(new Vector3(startPos));
        setActive(true);
    }

    public void endClick(){
        setActive(false);
    }

    @Override
    public void Update(){
        Vector2 currPos = EditorManager.pointer.pointerPosToWorldPoint();
        Vector2 clickDelta = startPos.subtract(currPos);

        Vector2 objectPlacePosition = new Vector2(startPos.x, startPos.y);
        float scaleX;
        float scaleY;

        if(Math.abs(clickDelta.x)< 5)
            return;
        if(Math.abs(clickDelta.y)< 5)
            return;

        // scale object based on cursor start and end positions
        if(clickDelta.x > 0){
            scaleX = clickDelta.x/128f;
            objectPlacePosition.x -= clickDelta.x;
        }
        else{
            scaleX = -clickDelta.x/128f;
        }

        if(clickDelta.y > 0){
            scaleY = clickDelta.y/128f;
            objectPlacePosition.y -= clickDelta.y;
        }
        else{
            scaleY = -clickDelta.y/128f;
        }

        setPosition(new Vector3(objectPlacePosition));
        setScale(new Vector3(scaleX,scaleY));
    }
}
