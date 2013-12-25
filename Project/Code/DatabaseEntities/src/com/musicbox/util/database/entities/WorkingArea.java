package com.musicbox.util.database.entities;

import com.musicbox.util.EntityClass;
import com.musicbox.util.WorkingAreaType;
import com.musicbox.util.globalobject.GlobalObject;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 07.12.13
 * Time: 15:08
 * To change this template use File | Settings | File Templates.
 */
public class WorkingArea extends GlobalObject {
    private MusicRoom musicRoom;
    private String name;
    private int tempo;
    private String owner;
    private WorkingAreaType workingAreaType;
    private float beat;
    private Long length;

    public WorkingArea() {
        super.setEntityClass(EntityClass.WORKING_AREA_CLASS);
        musicRoom = null;
        name = "";
        tempo = 0;
        owner = "";
        workingAreaType = null;
        beat = 0F;
        length = 0L;
    }

    public WorkingArea(int id, MusicRoom musicRoom, String name, int tempo, String owner, WorkingAreaType workingAreaType, float beat, Long length) {
        super.setEntityClass(EntityClass.WORKING_AREA_CLASS);
        super.setId(id);
        setMusicRoom(musicRoom);
        setName(name);
        setTempo(tempo);
        setOwner(owner);
        setWorkingAreaType(workingAreaType);
        setBeat(beat);
        setLength(length);
    }

    public MusicRoom getMusicRoom() {
        return musicRoom;
    }

    public void setMusicRoom(MusicRoom musicRoom) {
        this.musicRoom = musicRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null)
            name = "";

        this.name = name;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        if(owner == null)
            owner = "";
        this.owner = owner;
    }

    public WorkingAreaType getWorkingAreaType() {
        return workingAreaType;
    }

    public void setWorkingAreaType(WorkingAreaType workingAreaType) {
        this.workingAreaType = workingAreaType;
    }

    public float getBeat() {
        return beat;
    }

    public void setBeat(float beat) {
        this.beat = beat;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public boolean isValid() {
        if(getId() > 0 && musicRoom != null && musicRoom.isValid() && name != null && !name.isEmpty() && tempo > 0 && workingAreaType != null && beat > 0F && length != null && length >=0L) {
            if((workingAreaType == WorkingAreaType.PRIVATE && owner != null && !owner.isEmpty()) || workingAreaType == WorkingAreaType.PUBLIC)
                return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        WorkingArea workingArea = (WorkingArea) obj;
        return getId() == workingArea.getId()
                && tempo == workingArea.getTempo()
                && beat == workingArea.getBeat()
                && name != null && name.equals(workingArea.getName())
                && owner != null && owner.equals(workingArea.getOwner())
                && workingAreaType != null && workingAreaType.equals(workingArea.getWorkingAreaType())
                && length != null && length.equals(workingArea.getLength())
                && musicRoom != null && musicRoom.equals(workingArea.getMusicRoom());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (musicRoom != null ? musicRoom.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + tempo;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (workingAreaType != null ? workingAreaType.hashCode() : 0);
        result = 31 * result + (beat != +0.0f ? Float.floatToIntBits(beat) : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        return result;
    }
}
