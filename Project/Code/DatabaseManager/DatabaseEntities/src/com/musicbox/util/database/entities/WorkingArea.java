package com.musicbox.util.database.entities;

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
        super.setDataClass(WorkingArea.class);
    }

    public WorkingArea(int id, MusicRoom musicRoom, String name, int tempo, String owner, WorkingAreaType workingAreaType, float beat, Long length) {
        super.setDataClass(WorkingArea.class);
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
        if(musicRoom != null && !name.isEmpty() && tempo >= 0 && workingAreaType != null && beat >= 0F && length >=0L) {
            if((workingAreaType == WorkingAreaType.PRIVATE && !owner.isEmpty()) || workingAreaType == WorkingAreaType.PUBLIC)
                return true;
        }
        return false;
    }
}
