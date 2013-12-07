package com.musicbox.database.entities;

import com.musicbox.globalobject.GlobalObject;

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
    }

    public MusicRoom(int id, String name) {
        super.setDataClass(MusicRoom.class);
        super.setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
