package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.GameImage;
import com.JEngine.Utility.ImageProcessing.GenerateSolidTexture;
import com.JEngine.Utility.ImageProcessing.MissingTexture;

import java.util.Locale;

public class ObjectDictionary {
    public static GameImage[] objectImages = new GameImage[]{
            new GameImage(GenerateSolidTexture.generateImage(128,128,0xFFFFFFFF)),
            new GameImage("images/spike.png"),
            new GameImage(GenerateSolidTexture.generateImage(64,64,0xFF55FF45)),
            new GameImage(MissingTexture.getMissingTextureImage(64,64)),
            new GameImage(GenerateSolidTexture.generateImage(128,128,0xFFFF00FF)),
            new GameImage(GenerateSolidTexture.generateImage(64,32, 0xFFFFFF00))
    };

    public static int nameToIntIndex(String objectName){
        return switch (objectName.toLowerCase(Locale.ROOT)) {
            case "wall" -> 0;
            case "spike" -> 1;
            case "player" -> 2;
            case "enemy" -> 3;
            case "breakablewall" -> 4;
            case "boostpad" -> 5;
            default -> -1;
        };
    }

    public static boolean nameToCanBeStretched(String objectName)
    {
        return switch (objectName.toLowerCase(Locale.ROOT)) {
            case "wall", "breakablewall" -> true;
            default -> false;
        };
    }
}
