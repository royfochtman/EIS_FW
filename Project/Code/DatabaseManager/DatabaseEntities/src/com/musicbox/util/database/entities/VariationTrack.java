package com.musicbox.util.database.entities;

import com.musicbox.util.globalobject.GlobalObject;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 07.12.13
 * Time: 15:33
 * To change this template use File | Settings | File Templates.
 */
public class VariationTrack extends GlobalObject {
    private Variation variation;
    private Track track;
    private Long startTimeOnTrack;

    public VariationTrack() {
        super.setDataClass(VariationTrack.class);
    }

    public VariationTrack(int id, Variation variation, Track track, Long startTimeOnTrack) {
        super.setDataClass(VariationTrack.class);
        super.setId(id);
        this.variation = variation;
        this.track = track;
        this.startTimeOnTrack = startTimeOnTrack;
    }

    public Variation getVariation() {
        return variation;
    }

    public void setVariation(Variation variation) {
        this.variation = variation;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Long getStartTimeOnTrack() {
        return startTimeOnTrack;
    }

    public void setStartTimeOnTrack(Long startTimeOnTrack) {
        this.startTimeOnTrack = startTimeOnTrack;
    }
}
