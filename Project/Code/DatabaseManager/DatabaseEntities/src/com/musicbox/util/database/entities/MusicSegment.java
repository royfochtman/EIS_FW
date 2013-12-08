package com.musicbox.util.database.entities;

import com.musicbox.util.Instrument;
import com.musicbox.util.globalobject.GlobalObject;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 07.12.13
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public class MusicSegment extends GlobalObject {
    private String name;
    private Instrument instrument;
    private String owner;
    private String audioPath;
    private Long length;

    public MusicSegment() {
        super.setDataClass(MusicSegment.class);
    }

    public MusicSegment(int id, String name, Instrument instrument, String owner, String audioPath, Long length) {
        super.setDataClass(MusicSegment.class);
        super.setId(id);
        this.name = name;
        this.instrument = instrument;
        this.owner = owner;
        this.audioPath = audioPath;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }
}
