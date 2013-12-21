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
        variation = null;
        track = null;
        startTimeOnTrack = 0L;
    }

    public VariationTrack(int id, Variation variation, Track track, Long startTimeOnTrack) {
        super.setDataClass(VariationTrack.class);
        super.setId(id);
        setVariation(variation);
        setTrack(track);
        setStartTimeOnTrack(startTimeOnTrack);
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
        if(startTimeOnTrack == null)
            this.startTimeOnTrack = 0L;
        else
            this.startTimeOnTrack = startTimeOnTrack;
    }

    public boolean isValid() {
        if(getId() > 0 && variation != null && track != null && startTimeOnTrack >= 0L && startTimeOnTrack < track.getLength()
                && variation.getMusicSegment().getInstrument().equals(track.getInstrument()))
            return true;
        else
            return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        VariationTrack variationTrack = (VariationTrack) obj;
        return getId() == variationTrack.getId()
                && startTimeOnTrack != null && startTimeOnTrack.equals(variationTrack.getStartTimeOnTrack())
                && variation != null && variation.equals(variationTrack.getVariation())
                && track != null && track.equals(variationTrack.getTrack());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (variation != null ? variation.hashCode() : 0);
        result = 31 * result + (track != null ? track.hashCode() : 0);
        result = 31 * result + (startTimeOnTrack != null ? startTimeOnTrack.hashCode() : 0);
        return result;
    }
}
