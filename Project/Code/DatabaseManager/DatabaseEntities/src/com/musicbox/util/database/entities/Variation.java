package com.musicbox.util.database.entities;

import com.musicbox.util.globalobject.GlobalObject;
import com.sun.istack.internal.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 07.12.13
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 */
public class Variation extends GlobalObject {
    private MusicSegment musicSegment;
    private String name;
    private Long startTime;
    private Long endTime;
    private String owner;

    public Variation() {
        super.setDataClass(Variation.class);
        musicSegment = null;
        name = "";
        startTime = 0L;
        endTime = 0L;
        owner = "";
    }

    public Variation(int id, MusicSegment musicSegment, String name, Long startTime, long endTime, String owner) {
        super.setDataClass(Variation.class);
        super.setId(id);
        setMusicSegment(musicSegment);
        setName(name);
        setStartTime(startTime);
        setEndTime(endTime);
        setOwner(owner);
    }

    public MusicSegment getMusicSegment() {
        return musicSegment;
    }

    public void setMusicSegment(MusicSegment musicSegment) {
        this.musicSegment = musicSegment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null)
            name = "";

        this.name = name;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        if(owner == null)
            owner = "";
        this.owner = owner;
    }

    public boolean isValid() {
        if(!name.isEmpty() && !owner.isEmpty() && musicSegment != null &&
                startTime >= 0L && startTime < musicSegment.getLength() &&  endTime >= 0L &&  endTime <= musicSegment.getLength())
            return true;
        else
            return false;


    }
}
