package com.musicbox.util.database.entities;

import com.musicbox.util.EntityClass;
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
        super.setEntityClass(EntityClass.TRACK_CLASS);
        workingArea = null;
        instrument = null;
        volume = 0;
        name = "";
        length = 0L;
    }

    public Track(int id, WorkingArea workingArea, Instrument instrument, int volume, String name, Long length) {
        super.setEntityClass(EntityClass.TRACK_CLASS);
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
        if(getId() > 0 && workingArea != null && workingArea.isValid() && instrument != null && volume >= 0 && (name != null && !name.isEmpty()) && length != null &&  length >= 0L)
            return true;
        else
            return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Track track = (Track)obj;
        return getId() == track.getId()
                && volume == track.getVolume()
                && instrument != null && instrument.equals(track.getInstrument())
                && name != null && name.equals(track.getName())
                && length != null && length.equals(track.getLength())
                && workingArea != null && workingArea.equals(track.workingArea);
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (workingArea != null ? workingArea.hashCode() : 0);
        result = 31 * result + (instrument != null ? instrument.hashCode() : 0);
        result = 31 * result + volume;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        return result;
    }
}
