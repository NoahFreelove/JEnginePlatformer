package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.GameImage;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.Visual.Scenes.GameScene;
import com.JEngine.Utility.IO.FileOperations;
import com.JEngine.Utility.ImageProcessing.GenerateSolidTexture;
import com.jengineplatformer.Core.PlatformPlayer;
import com.jengineplatformer.Core.Spike;
import com.jengineplatformer.Core.Wall;

import java.io.File;

public class LevelLoader {

    public static GameScene loadFromFile(String filePath){
        String[] loadedLines = FileOperations.fileToStringArr(new File(filePath).getAbsolutePath());
        GameScene scene = new GameScene(6, loadedLines[0]);
        boolean inObject = false;
        int count = 0;

        Vector3 pos = new Vector3(0,0,0);
        Vector3 rot = new Vector3(0,0,0);
        Vector3 scale = new Vector3(0,0,0);

        for (int i = 0; i < loadedLines.length; i++) {

            String line = loadedLines[i];
            if(inObject)
            {
                if(line.equalsIgnoreCase("transform")){
                    pos = new Vector3(0,0,0);
                    rot = new Vector3(0,0,0);
                    scale = new Vector3(0,0,0);

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
                    Wall w = new Wall(pos, scale, new GameImage(GenerateSolidTexture.generateImage(128,128,0xFFFFFFFF)), "Wall"+count);
                    scene.add(w);
                    count++;
                    continue;
                }
                else if (line.equalsIgnoreCase("player")){
                    PlatformPlayer pp = new PlatformPlayer(pos);
                    scene.add(pp);
                    count++;
                    continue;
                }
                else if (line.equalsIgnoreCase("spike")){
                    Spike spike = new Spike(pos, rot);
                    scene.add(spike);
                    count++;
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
