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
        if(getId() > 0 && name != null && !name.isEmpty())
            return true;
        else
            return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        MusicRoom musicRoom = (MusicRoom) obj;

        return getId() == musicRoom.getId()
                && (name != null && name.equals(musicRoom.getName()));
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
