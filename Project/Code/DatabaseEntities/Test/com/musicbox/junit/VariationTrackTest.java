package com.musicbox.junit;

import com.musicbox.util.Instrument;
import com.musicbox.util.WorkingAreaType;
import com.musicbox.util.database.entities.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by David on 15.12.13.
 */
public class VariationTrackTest {
    Track track1;
    Track track2;
    WorkingArea workingArea1;
    WorkingArea workingArea2;
    MusicRoom musicRoom1;
    MusicSegment musicSegment1;
    MusicSegment musicSegment2;
    Variation variation1;
    Variation variation2;
    VariationTrack variationTrack1;
    VariationTrack variationTrack2;
    VariationTrack variationTrack3;
    VariationTrack variationTrack4;
    VariationTrack variationTrack5;
    VariationTrack variationTrack6;
    @Before
    public void setUp() throws Exception {
        musicRoom1 = new MusicRoom(1, "MusicRoom");
        workingArea1 = new WorkingArea(1, musicRoom1, "WorkingArea", 10,"Owner", WorkingAreaType.PRIVATE, 10F, 10L);
        workingArea2 = new WorkingArea(2, musicRoom1, "WorkingArea2", 11,"Owner", WorkingAreaType.PRIVATE, 10F, 12L);
        musicSegment1 = new MusicSegment(1, "MusicSegment", Instrument.BASSGUITAR, "Owner", "C:\\", 10L, new MusicRoom(1, "Room"));
        musicSegment2 = new MusicSegment(2, "MusicSegment", Instrument.BASSGUITAR, "Owner", "C:\\", 10L, new MusicRoom(1, "Room1"));
        track1 = new Track(1, workingArea1, Instrument.BASSGUITAR, 10, "Track", 10L);
        track2 = new Track(2, workingArea2, Instrument.BASSGUITAR, 10, "Track", 12L);
        variation1 = new Variation(1, musicSegment1, "Variation1", 0L, 10L, "Owner");
        variation2= new Variation(2, musicSegment2, "Variation1", 0L, 10L, "Owner");

        variationTrack1 = new VariationTrack(1, variation1, track1, 5L);
        variationTrack2 = new VariationTrack(2, variation1, track1, 5L);
        variationTrack3 = new VariationTrack(1, variation2, track1, 5L);
        variationTrack4 = new VariationTrack(1, variation1, track2, 5L);
        variationTrack5 = new VariationTrack(1, variation1, track1, 7L);
        variationTrack6 = new VariationTrack(1, variation1, track1, 5L);
    }

    @Test
    public void testIsValid() throws Exception {
        assertTrue(variationTrack1.isValid());
        assertTrue(variationTrack2.isValid());
        assertTrue(variationTrack3.isValid());
        assertTrue(variationTrack4.isValid());
        assertTrue(variationTrack5.isValid());
        assertTrue(variationTrack6.isValid());

        VariationTrack invalid1 = new VariationTrack(0, variation1, track1, 5L);
        VariationTrack invalid2 = new VariationTrack(1, null, track1, 5L);
        VariationTrack invalid3 = new VariationTrack(1, variation1, null, 5L);
        VariationTrack invalid4 = new VariationTrack(0, variation1, track1, -1L);
        VariationTrack invalid5 = new VariationTrack(0, variation1, track1, null);
        VariationTrack invalid6 = new VariationTrack();

        assertFalse(invalid1.isValid());
        assertFalse(invalid2.isValid());
        assertFalse(invalid3.isValid());
        assertFalse(invalid4.isValid());
        assertFalse(invalid5.isValid());
        assertFalse(invalid6.isValid());
    }

    @Test
    public void testEquals() throws Exception {
        VariationTrack vT = variationTrack1;
        assertTrue(variationTrack1.equals(vT));
        assertTrue(vT.equals(variationTrack1));
        assertTrue(variationTrack1.equals(variationTrack6));
        assertTrue(variationTrack6.equals(variationTrack1));
        assertFalse(variationTrack1.equals(variationTrack2));
        assertFalse(variationTrack2.equals(variationTrack1));
        assertFalse(variationTrack1.equals(variationTrack3));
        assertFalse(variationTrack1.equals(variationTrack4));
        assertFalse(variationTrack1.equals(variationTrack5));
    }

    @Test
    public void testHashCode() throws Exception {
        VariationTrack vT = variationTrack1;
        assertTrue(variationTrack1.hashCode() == vT.hashCode());
        assertTrue(variationTrack1.hashCode() == variationTrack6.hashCode());
        assertFalse(variationTrack1.hashCode() == variationTrack2.hashCode());
        assertFalse(variationTrack1.hashCode() == variationTrack3.hashCode());
        assertFalse(variationTrack1.hashCode() == variationTrack4.hashCode());
        assertFalse(variationTrack1.hashCode() == variationTrack5.hashCode());
    }
}
