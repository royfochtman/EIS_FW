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

    public Variation(int id, MusicSegment musicSegment, String name, Long startTime, Long endTime, String owner) {
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
        if(getId() > 0 && name != null && !name.isEmpty() && owner != null && !owner.isEmpty() && musicSegment != null && musicSegment.isValid() && startTime != null && endTime != null
                && startTime >= 0L && startTime < musicSegment.getLength() &&  endTime > 0L &&  endTime <= musicSegment.getLength())
            return true;
        else
            return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Variation variation = (Variation)obj;
        return getId() == variation.getId()
                && name != null && name.equals(variation.getName())
                && startTime != null && startTime.equals(variation.getStartTime())
                && endTime!= null && endTime.equals(variation.getEndTime())
                && owner != null && owner.equals(variation.getOwner())
                && musicSegment != null && musicSegment.equals(variation.getMusicSegment());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (musicSegment != null ? musicSegment.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }
}
