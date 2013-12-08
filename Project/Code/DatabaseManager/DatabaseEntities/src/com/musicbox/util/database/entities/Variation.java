package com.musicbox.util.database.entities;

import com.musicbox.util.globalobject.GlobalObject;

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
    }

    public Variation(int id, MusicSegment musicSegment, String name, Long startTime, long endTime, String owner) {
        super.setDataClass(Variation.class);
        super.setId(id);
        this.musicSegment = musicSegment;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.owner = owner;
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
        this.owner = owner;
    }
}
