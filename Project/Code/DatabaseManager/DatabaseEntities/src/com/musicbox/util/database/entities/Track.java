package com.musicbox.util.database.entities;

import com.musicbox.util.Instrument;
import com.musicbox.util.globalobject.GlobalObject;

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
        workingArea = null;
        instrument = null;
        volume = 0;
        name = "";
        length = 0L;
    }

    public Track(int id, WorkingArea workingArea, Instrument instrument, int volume, String name, Long length) {
        super.setDataClass(Track.class);
        super.setId(id);
        setWorkingArea(workingArea);
        setInstrument(instrument);
        setName(name);
        setLength(length);
        setVolume(volume);
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
        if(name == null)
            name = "";

        this.name = name;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public boolean isValid() {
        if(workingArea != null && instrument != null && volume >= 0 && !name.isEmpty() && length >= 0L)
            return true;
        else
            return false;
    }
}
