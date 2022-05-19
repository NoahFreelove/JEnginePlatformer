package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.GameImage;
import com.JEngine.Core.Identity;
import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Game.PlayersAndPawns.Sprite;
import com.JEngine.Game.Visual.Scenes.GameScene;
import com.JEngine.Utility.IO.FileOperations;
import com.jengineplatformer.Core.*;
import com.jengineplatformer.Objects.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;

public class LevelLoader {

    public static GameScene loadPlayableFromFile(String filePath){
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
                    Wall w = new Wall(pos, scale, new GameImage(ObjectDictionary.objectImages[ObjectDictionary.nameToIntIndex("wall")]), "Wall"+count);
                    scene.add(w);
                    count++;
                    continue;
                }
                else if (line.equalsIgnoreCase("player")){
                    PlatformPlayer pp = new PlatformPlayer(pos);
                    EditorManager.playerRef = pp;
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
                else if(line.equalsIgnoreCase("enemy"))
                {
                    Enemy enemy = new Enemy(pos, scale);
                    scene.add(enemy);
                    count++;
                    continue;
                }
                else if(line.equalsIgnoreCase("breakablewall"))
                {
                    BreakableWall w = new BreakableWall(pos, scale, new GameImage(ObjectDictionary.objectImages[ObjectDictionary.nameToIntIndex("breakablewall")]), "Wall"+count);
                    scene.add(w);
                    count++;
                    continue;
                }
                else if (line.equalsIgnoreCase("boostpad")) {
                    Boostpad bp = new Boostpad(pos, 32,128,"Boost-Pad");
                    scene.add(bp);
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
    public static void loadFromFile(String filePath, GameScene editorScene){
        String[] loadedLines = FileOperations.fileToStringArr(new File(filePath).getAbsolutePath());

        boolean inObject = false;
        Vector3 pos = new Vector3(0,0,0);
        Vector3 rot = new Vector3(0,0,0);
        Vector3 scale = new Vector3(0,0,0);
        editorScene.setSceneName(loadedLines[0]);
        for (int i = 0; i < loadedLines.length; i++) {

            String line = loadedLines[i];
            if(inObject)
            {
                if(line.equalsIgnoreCase("transform")){
                    pos = new Vector3(Float.parseFloat(loadedLines[i+1]),Float.parseFloat(loadedLines[i+2]),Float.parseFloat(loadedLines[i+3]));
                    rot = new Vector3(Float.parseFloat(loadedLines[i+4]),Float.parseFloat(loadedLines[i+5]),Float.parseFloat(loadedLines[i+6]));
                    scale = new Vector3(Float.parseFloat(loadedLines[i+7]),Float.parseFloat(loadedLines[i+8]),Float.parseFloat(loadedLines[i+9]));
                    i+=9;
                    continue;
                }
                else if (line.equalsIgnoreCase("wall")){
                    Sprite sprite = new Sprite(new Transform(pos, rot, scale), new GameImage(ObjectDictionary.objectImages[ObjectDictionary.nameToIntIndex("wall")]), new Identity("Wall", "wall"));
                    editorScene.add(sprite);
                    continue;
                }
                else if (line.equalsIgnoreCase("player")){
                    Sprite sprite = new Sprite(new Transform(pos, rot, scale), new GameImage(ObjectDictionary.objectImages[ObjectDictionary.nameToIntIndex("player")]), new Identity("Player", "player"));
                    editorScene.add(sprite);
                    continue;
                }
                else if (line.equalsIgnoreCase("spike")){
                    Sprite sprite = new Sprite(new Transform(pos, rot, scale), new GameImage(ObjectDictionary.objectImages[ObjectDictionary.nameToIntIndex("spike")]), new Identity("Spike", "spike"));
                    editorScene.add(sprite);
                    continue;
                }
                else if(line.equalsIgnoreCase("enemy"))
                {
                    Sprite sprite = new Sprite(new Transform(pos, rot, scale), new GameImage(ObjectDictionary.objectImages[ObjectDictionary.nameToIntIndex("enemy")]), new Identity("Enemy", "enemy"));
                    editorScene.add(sprite);
                    continue;
                }
                else if(line.equalsIgnoreCase("breakablewall"))
                {
                    Sprite sprite = new Sprite(new Transform(pos, rot, scale), new GameImage(ObjectDictionary.objectImages[ObjectDictionary.nameToIntIndex("breakablewall")]), new Identity("Breakablewall", "breakablewall"));
                    editorScene.add(sprite);
                    continue;
                }
                else if(line.equalsIgnoreCase("boostpad"))
                {
                    Sprite sprite = new Sprite(new Transform(pos, rot, scale), new GameImage(ObjectDictionary.objectImages[ObjectDictionary.nameToIntIndex("boostpad")]), new Identity("Boost-Pad", "boostpad"));
                    editorScene.add(sprite);
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
    }

}
