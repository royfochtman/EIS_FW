package com.musicbox.database.entities;

import com.musicbox.database.util.Instrument;
import com.musicbox.globalobject.GlobalObject;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 07.12.13
 * Time: 15:16
 * To change this template use File | Settings | File Templates.
 */
public class Track extends GlobalObject {
    private WorkingArea workingArea;
    private Instrument instrument;
    private int volume;
    private String name;
    private Long length;

    public Track() {
        super.setDataClass(Track.class);
    }

    public Track(int id, WorkingArea workingArea, Instrument instrument, int volume, String name, Long length) {
        super.setDataClass(Track.class);
        super.setId(id);

        this.workingArea = workingArea;
        this.instrument = instrument;
        this.volume = volume;
        this.name = name;
        this.length = length;
    }

    public WorkingArea getWorkingArea() {
        return workingArea;
    }

    public void setWorkingArea(WorkingArea workingArea) {
        this.workingArea = workingArea;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }
}
