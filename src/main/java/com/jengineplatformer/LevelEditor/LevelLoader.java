package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.GameImage;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.Visual.Scenes.GameScene;
import com.JEngine.Utility.IO.FileOperations;
import com.JEngine.Utility.ImageProcessing.GenerateSolidTexture;
import com.jengineplatformer.Core.Wall;

import java.io.File;

public class LevelLoader {

    /**
     * Format:
     * Line 0: title
     * START GAMEOBJECT
     *
     * END GAMEOBJECT
     */
    public static GameScene loadFromFile(String filePath){
        String[] loadedLines = FileOperations.fileToStringArr(new File(filePath).getAbsolutePath());
        GameScene scene = new GameScene(3, loadedLines[0]);
        boolean inObject = false;

        Vector3 pos = new Vector3(0,0,0);
        Vector3 rot = new Vector3(0,0,0);
        Vector3 scale = new Vector3(0,0,0);

        for (int i = 0; i < loadedLines.length; i++) {

            String line = loadedLines[i];
            if(inObject)
            {
                if(line.equalsIgnoreCase("transform")){
                    pos.x = Float.parseFloat(loadedLines[i+1]);
                    pos.y = Float.parseFloat(loadedLines[i+2]);
                    pos.z= Float.parseFloat(loadedLines[i+3]);

                    rot.x = Float.parseFloat(loadedLines[i+4]);
                    rot.y = Float.parseFloat(loadedLines[i+5]);
                    rot.z= Float.parseFloat(loadedLines[i+6]);

                    scale.x = Float.parseFloat(loadedLines[i+7]);
                    scale.y = Float.parseFloat(loadedLines[i+8]);
                    scale.z= Float.parseFloat(loadedLines[i+9]);
                    i+=9;
                    continue;
                }
                else if (line.equalsIgnoreCase("wall")){
                    Wall w = new Wall(pos, scale, new GameImage(GenerateSolidTexture.generateImage(128,128,0xFFFFFFF)));

                    scene.add(w);
                    continue;
                }
            }
            if(line.equalsIgnoreCase("START GAMEOBJECT"))
            {
                inObject = true;
            }
            else if (line.equalsIgnoreCase("END GAMEOBJECT"))
            {
                inObject = false;
            }
        }
        return scene;
    }
}
