package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.GameImage;
import com.JEngine.Core.GameObject;
import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector2;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Sprite;
import com.JEngine.Game.Visual.MousePointer;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.Utility.GameMath;

public class EditorPointer extends MousePointer {

    private Vector2 startClickPos = new Vector2(0,0);
    private Vector2 endClickPos = new Vector2(0,0);
    private String selectedObject = "wall";
    private boolean addedPlayer = false;
    private float currRot = 0f;
    public EditorPointer() {
        super(null);
    }

    @Override
    protected void onMouseReleased(boolean isLeftMouse){
        endClickPos = pointerPosToWorldPoint();
        if(isLeftMouse)
        {
            placeObject();
            EditorManager.selectRectangle.endClick();

        }
        else {
            deleteObject();
        }
    }

    @Override
    protected void onMousePressed(boolean leftClick) {
        if (leftClick)
        {
            startClickPos = pointerPosToWorldPoint();
            EditorManager.selectRectangle.startClick();
        }
    }


    private void placeObject(){
        if(!getActive())
            return;

        Vector2 clickDelta = startClickPos.subtract(endClickPos);
        Vector2 objectPlacePosition = new Vector2(startClickPos.x, startClickPos.y);
        float scaleX;
        float scaleY;

        int index = ObjectDictionary.nameToIntIndex(selectedObject);
        boolean canBeStretched = ObjectDictionary.nameToCanBeStretched(selectedObject);

        if(index == -1)
            return;

        // Don't create any object smaller than 5x5 units
        if(canBeStretched)
        {
            if(Math.abs(clickDelta.x)< 5)
                return;
            if(Math.abs(clickDelta.y)< 5)
                return;
        }

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
        Sprite newObject;

        if(ObjectDictionary.nameToCanBeStretched(selectedObject))
        {
            newObject = new Sprite(new Transform(new Vector3(objectPlacePosition, 2), new Vector3(currRot,0,0), new Vector3(scaleX,scaleY,1)),
                    ObjectDictionary.objectImages[index],
                    new Identity("levelEditorObject", selectedObject));
            EditorManager.editorScene.add(newObject);
        }
        else {
            GameImage image = new GameImage(ObjectDictionary.objectImages[index]);

            // Center object around cursor
            endClickPos.x -= image.getWidth()/2f;
            endClickPos.y -= image.getHeight()/2f;

            newObject = new Sprite(new Transform(new Vector3(endClickPos, 2), new Vector3(currRot,0,0), Vector3.oneVector()),
                    image, new Identity("levelEditorObject", selectedObject));
            EditorManager.editorScene.add(newObject);
        }
        EditorManager.AddEditorAction(new EditorActionHistory(EditorAction.ADD, newObject));

    }
    private void deleteObject(){
        if(!getActive())
            return;

        for (GameObject obj : SceneManager.getActiveScene().getObjects()) {
            if(obj == null)
                continue;
            if(obj instanceof Sprite sprite)
            {
                if(sprite.getSprite() == null)
                    continue;
                float xPos = sprite.getPosition().x;
                float yPos = sprite.getPosition().y;
                float xPos2 = xPos + sprite.getSprite().getWidth()*sprite.getTransform().scale.x;
                float yPos2 = yPos + sprite.getSprite().getHeight()*sprite.getTransform().scale.y;

                float cursorXPos = endClickPos.x;
                float cursorYPos = endClickPos.y;

                if(xPos < cursorXPos && cursorXPos < xPos2)
                {
                    if(yPos < cursorYPos && cursorYPos < yPos2 && !obj.isQueuedForDeletion() && !obj.getIdentity().compareTag("SelectRect"))
                    {
                        EditorManager.editorScene.remove(obj);
                        EditorManager.AddEditorAction(new EditorActionHistory(EditorAction.DELETE, obj));

                        break;
                    }
                }

            }
        }

    }

    @Override
    public void onScroll(double scroll) {
        int scrollClamp = (int) GameMath.clamp(-1, 1, (float) scroll);
        if (scrollClamp < 0)
        {
            currRot-=90;
            // if over 360, set to 0
            if(currRot < 0)
                currRot += 360;
        }
        else if (scrollClamp > 0)
        {
            currRot+=90;
            // if over 360, set to 0
            if(currRot > 360)
                currRot -= 360;
        }
        if(currRot == 360)
        {
            currRot = 0;
        }
        EditorManager.rotationText.setText("Rotation: " + (int)currRot + "°");
    }

    public void setAddedPlayer(boolean addedPlayer) {
        this.addedPlayer = addedPlayer;
    }

    public void setSelectedObject(String selectedObject) {
        this.selectedObject = selectedObject;
    }

    public String getSelectedObject() {
        return selectedObject.substring(0, 1).toUpperCase() + selectedObject.substring(1);
    }
}
