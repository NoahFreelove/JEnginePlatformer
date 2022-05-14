package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.GameImage;
import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector2;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Sprite;
import com.JEngine.Game.Visual.MousePointer;
import com.JEngine.Utility.GameMath;
import com.JEngine.Utility.ImageProcessing.GenerateSolidTexture;
import com.jengineplatformer.Core.Wall;

public class EditorPointer extends MousePointer {

    private Vector2 startClickPos = new Vector2(0,0);
    private Vector2 endClickPos = new Vector2(0,0);
    private String selectedObject = "spike";
    private boolean addedPlayer = false;

    public EditorPointer() {
        super(null);
    }

    @Override
    protected void onMouseReleased(){
        endClickPos = pointerPosToWorldPoint();
        placeObject();
    }

    @Override
    protected void onMousePressed(){
        startClickPos = pointerPosToWorldPoint();
    }


    private void placeObject(){
        if(!getActive())
            return;
        Vector2 clickDelta = startClickPos.subtract(endClickPos);
        float scaleX = 0.1f;
        float scaleY = 0.1f;
        Vector2 pos = new Vector2(startClickPos.x, startClickPos.y);

        // Don't create any object smaller than 5x5 units
        if(ObjectDictionary.nameToCanBeStretched(selectedObject))
        {
            if(Math.abs(clickDelta.x)< 5)
                return;
            if(Math.abs(clickDelta.y)< 5)
                return;
        }


        // scale object based on cursor start and end positions
        if(clickDelta.x > 0){
            scaleX = clickDelta.x/128f;
            pos.x -= clickDelta.x;
        }
        else{
            scaleX = -clickDelta.x/128f;
        }

        if(clickDelta.y > 0){
            scaleY = clickDelta.y/128f;
            pos.y -= clickDelta.y;
        }
        else{
            scaleY = -clickDelta.y/128f;
        }
        if(ObjectDictionary.nameToCanBeStretched(selectedObject))
        {
            EditorManager.editorScene.add(new Sprite(new Transform(new Vector3(pos, 2), Vector3.emptyVector(), new Vector3(scaleX,scaleY,1)),
                    ObjectDictionary.objectImages[ObjectDictionary.nameToIntIndex(selectedObject)],
                    new Identity("levelEditorObject", selectedObject)));
        }
        else {
            GameImage image = new GameImage(ObjectDictionary.objectImages[ObjectDictionary.nameToIntIndex(selectedObject)]);
            endClickPos.x -= image.getWidth()/2f;
            endClickPos.y -= image.getHeight()/2f;

            EditorManager.editorScene.add(new Sprite(new Transform(new Vector3(endClickPos, 2), Vector3.emptyVector(), Vector3.oneVector()),
                    image, new Identity("levelEditorObject", selectedObject)));
        }
    }

    public void setAddedPlayer(boolean addedPlayer) {
        this.addedPlayer = addedPlayer;
    }
}
