package com.musicbox.util.database.entities;

import com.musicbox.util.globalobject.GlobalObject;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 07.12.13
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class MusicRoom extends GlobalObject{
    private String name;

    public MusicRoom() {
        super.setDataClass(MusicRoom.class);
        name = "";
    }

    public MusicRoom(int id, String name) {
        super.setDataClass(MusicRoom.class);
        super.setId(id);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null)
            name = "";

        this.name = name;
    }

    public boolean isValid(){
        if(!name.isEmpty())
            return true;
        else
            return false;
    }
}
