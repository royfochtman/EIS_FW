package com.musicbox.util.database.entities;

import com.musicbox.util.EntityClass;
import com.musicbox.util.globalobject.GlobalObject;

/**
 * An instance of this Class represents one tupel of MusicBox MySQL
 * database table <i>music_room</i>. See database ERM-diagram for details
 *
 * @author David Wachs
 */
public class MusicRoom extends GlobalObject{
    /**
     * the name of the music room
     */
    private String name;

    public MusicRoom() {
        super.setEntityClass(EntityClass.MUSIC_ROOM_CLASS);
        name = "";
    }

    public MusicRoom(int id, String name) {
        super.setEntityClass(EntityClass.MUSIC_ROOM_CLASS);
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
