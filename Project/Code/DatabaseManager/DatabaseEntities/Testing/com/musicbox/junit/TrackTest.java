package com.musicbox.junit;

import com.musicbox.util.Instrument;
import com.musicbox.util.WorkingAreaType;
import com.musicbox.util.database.entities.MusicRoom;
import com.musicbox.util.database.entities.Track;
import com.musicbox.util.database.entities.WorkingArea;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by David on 14.12.13.
 */
public class TrackTest {
    Track track1;
    Track track2;
    Track track3;
    Track track4;
    Track track5;
    Track track6;
    Track track7;
    Track track8;
    WorkingArea workingArea1;
    WorkingArea workingArea2;
    MusicRoom musicRoom1;
    @Before
    public void setUp() throws Exception {
        musicRoom1 = new MusicRoom(1, "TestRoom");
        workingArea1 = new WorkingArea(1, musicRoom1, "WorkingArea1", 10, "Owner1", WorkingAreaType.PRIVATE, 1F, 1L);
        workingArea2 = new WorkingArea(2, musicRoom1, "WorkingArea2", 10, "Owner1", WorkingAreaType.PRIVATE, 1F, 1L);
        track1 = new Track(1, workingArea1, Instrument.ELECTRICGUITAR, 10, "Track1", 1L);
        track2 = new Track(2, workingArea1, Instrument.ELECTRICGUITAR, 10, "Track1", 1L);
        track3 = new Track(1, workingArea2, Instrument.ELECTRICGUITAR, 10, "Track1", 1L);
        track4 = new Track(1, workingArea1, Instrument.BASSGUITAR, 10, "Track1", 1L);
        track5 = new Track(1, workingArea1, Instrument.ELECTRICGUITAR, 1, "Track1", 1L);
        track6 = new Track(1, workingArea1, Instrument.ELECTRICGUITAR, 10, "Track2", 1L);
        track7 = new Track(1, workingArea1, Instrument.ELECTRICGUITAR, 10, "Track1", 2L);
        track8 = new Track(1, workingArea1, Instrument.ELECTRICGUITAR, 10, "Track1", 1L);
    }

    @Test
    public void testIsValid() throws Exception {
        assertTrue(track1.isValid());
        assertTrue(track2.isValid());
        assertTrue(track3.isValid());
        assertTrue(track4.isValid());
        assertTrue(track5.isValid());
        assertTrue(track6.isValid());
        assertTrue(track7.isValid());
        assertTrue(track8.isValid());

        Track invalid1 = new Track(0, workingArea1, Instrument.ELECTRICGUITAR, 10, "Track1", 1L);
        Track invalid2 = new Track(1, null, Instrument.ELECTRICGUITAR, 10, "Track1", 1L);
        Track invalid3 = new Track(1, workingArea1, null, 10, "Track1", 1L);
        Track invalid4 = new Track(1, workingArea1, Instrument.ELECTRICGUITAR, -1, "Track1", 1L);
        Track invalid5 = new Track(1, workingArea1, Instrument.ELECTRICGUITAR, 10, "", 1L);
        Track invalid6 = new Track(1, workingArea1, Instrument.ELECTRICGUITAR, 10, null, 1L);
        Track invalid7 = new Track(1, workingArea1, Instrument.ELECTRICGUITAR, 10, "Track1", -1L);
        Track invalid8 = new Track(1, workingArea1, Instrument.ELECTRICGUITAR, 10, "Track1", null);
        Track invalid9 = new Track();

        assertFalse(invalid1.isValid());
        assertFalse(invalid2.isValid());
        assertFalse(invalid3.isValid());
        assertFalse(invalid4.isValid());
        assertFalse(invalid5.isValid());
        assertFalse(invalid6.isValid());
        assertFalse(invalid7.isValid());
        assertFalse(invalid8.isValid());
        assertFalse(invalid9.isValid());
    }

    @Test
    public void testEquals() throws Exception {
        Track t = track1;
        assertTrue(track1.equals(t));
        assertTrue(track1.equals(track8));
        assertTrue(track8.equals(track1));
        assertFalse(track1.equals(track2));
        assertFalse(track2.equals(track1));
        assertFalse(track1.equals(track3));
        assertFalse(track1.equals(track4));
        assertFalse(track1.equals(track5));
        assertFalse(track1.equals(track6));
        assertFalse(track1.equals(track7));
    }

    @Test
    public void testHashCode() throws Exception {
        Track t = track1;
        assertTrue(track1.hashCode() == t.hashCode());
        assertTrue(track1.hashCode() == track8.hashCode());
        assertFalse(track1.hashCode() == track2.hashCode());
        assertFalse(track1.hashCode() == track3.hashCode());
        assertFalse(track1.hashCode() == track4.hashCode());
        assertFalse(track1.hashCode() == track5.hashCode());
        assertFalse(track1.hashCode() == track6.hashCode());
        assertFalse(track1.hashCode() == track7.hashCode());
    }
}
