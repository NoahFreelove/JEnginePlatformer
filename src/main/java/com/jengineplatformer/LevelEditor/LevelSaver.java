package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.GameObject;
import com.JEngine.Game.Visual.Scenes.GameScene;
import com.JEngine.Utility.IO.FileOperations;

import java.io.File;

public class LevelSaver {
    public static void SaveLevel(GameScene scene, String filepath)
    {
        String[] data = new String[3000];
        int i = 0;
        data[0] = scene.getSceneName();
        for (GameObject obj: scene.getObjects()) {
            if(obj == null)
                continue;
            String tag = obj.getIdentity().getTag();
            if(ObjectDictionary.nameToIntIndex(tag) == -1)
            {
                continue;
            }
            data[i+1] = "START GAMEOBJECT";
            data[i+2] = "transform";
            data[i+3] = String.valueOf(obj.getPosition().x);
            data[i+4] = String.valueOf(obj.getPosition().y);
            data[i+5] = String.valueOf(obj.getPosition().z);
            data[i+6] = String.valueOf(obj.getTransform().getRotation().x);
            data[i+7] = String.valueOf(obj.getTransform().getRotation().y);
            data[i+8] = String.valueOf(obj.getTransform().getRotation().z);
            data[i+9] = String.valueOf(obj.getTransform().getScale().x);
            data[i+10] = String.valueOf(obj.getTransform().getScale().y);
            data[i+11] = String.valueOf(obj.getTransform().getScale().z);
            data[i+12] = obj.getIdentity().getTag();
            data[i+13] = "END GAMEOBJECT";

            i+=13;
        }
        FileOperations.stringArrToFile(data, new File(filepath).getAbsolutePath());
    }
}
