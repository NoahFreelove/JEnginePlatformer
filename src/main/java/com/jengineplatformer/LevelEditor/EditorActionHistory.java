package com.jengineplatformer.LevelEditor;

import com.JEngine.Core.GameObject;

public class EditorActionHistory {
    public EditorAction action;
    public GameObject object;

    public EditorActionHistory(EditorAction action, GameObject object) {
        this.action = action;
        this.object = object;
    }

    @Override
    public String toString(){
        return String.format("Action: %s, ObjectName: %s", action.name(), object.getIdentity().getName());
    }
}
