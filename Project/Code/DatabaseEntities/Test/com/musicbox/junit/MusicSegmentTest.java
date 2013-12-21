package com.musicbox.junit;

import com.musicbox.util.Instrument;
import com.musicbox.util.database.entities.MusicRoom;
import com.musicbox.util.database.entities.MusicSegment;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by David on 14.12.13.
 */
public class MusicSegmentTest {
    MusicSegment musicSegment1;
    MusicSegment musicSegment2;
    MusicSegment musicSegment3;
    MusicSegment musicSegment4;
    MusicSegment musicSegment5;
    MusicSegment musicSegment6;
    MusicSegment musicSegment7;
    MusicSegment musicSegment8;
    MusicSegment musicSegment9;
    MusicRoom musicRoom1;
    MusicRoom musicRoom2;
    @Before
    public void setUp() throws Exception {
        musicRoom1 = new MusicRoom(1, "MR1");
        musicRoom2 = new MusicRoom(2, "MR2");
        musicSegment1 = new MusicSegment(1, "TestSegment", Instrument.DRUMS, "Owner", "C:\\", 2L, musicRoom1);
        musicSegment2 = new MusicSegment(2, "TestSegment", Instrument.DRUMS, "Owner", "C:\\", 2L, musicRoom1);
        musicSegment3 = new MusicSegment(1, "Test", Instrument.DRUMS, "Owner", "C:\\", 2L, musicRoom1);
        musicSegment4 = new MusicSegment(1, "TestSegment", Instrument.BASSGUITAR, "Owner", "C:\\", 2L, musicRoom1);
        musicSegment5 = new MusicSegment(1, "TestSegment", Instrument.DRUMS, "Owner2", "C:\\", 2L, musicRoom1);
        musicSegment6 = new MusicSegment(1, "TestSegment", Instrument.DRUMS, "Owner", "D:\\", 2L, musicRoom1);
        musicSegment7 = new MusicSegment(1, "TestSegment", Instrument.DRUMS, "Owner", "C:\\", 1L, musicRoom1);
        musicSegment8 = new MusicSegment(1, "TestSegment", Instrument.DRUMS, "Owner", "C:\\", 2L, musicRoom1);
        musicSegment9 = new MusicSegment(1, "TestSegment", Instrument.DRUMS, "Owner", "C:\\", 2L, musicRoom2);
    }

    @Test
    public void testIsValid() throws Exception {
        assertTrue(musicSegment1.isValid());
        assertTrue(musicSegment2.isValid());
        assertTrue(musicSegment3.isValid());
        assertTrue(musicSegment4.isValid());
        assertTrue(musicSegment5.isValid());
        assertTrue(musicSegment6.isValid());
        assertTrue(musicSegment7.isValid());
        assertTrue(musicSegment8.isValid());
        assertTrue(musicSegment9.isValid());

        MusicSegment invalid1 = new MusicSegment(0, "TestSegment", Instrument.DRUMS, "Owner", "C:\\", 2L, musicRoom1);
        MusicSegment invalid2 = new MusicSegment(1, "", Instrument.DRUMS, "Owner", "C:\\", 2L, musicRoom1);
        MusicSegment invalid3 = new MusicSegment(1, null, Instrument.DRUMS, "Owner", "C:\\", 2L, musicRoom1);
        MusicSegment invalid4 = new MusicSegment(1, "TestSegment", null, "Owner", "C:\\", 2L, musicRoom1);
        MusicSegment invalid5 = new MusicSegment(1, "TestSegment", Instrument.DRUMS, "", "C:\\", 2L, musicRoom1);
        MusicSegment invalid6 = new MusicSegment(0, "TestSegment", Instrument.DRUMS, null, "C:\\", 2L, musicRoom1);
        MusicSegment invalid7 = new MusicSegment(0, "TestSegment", Instrument.DRUMS, "Owner", "", 2L, musicRoom1);
        MusicSegment invalid8 = new MusicSegment(0, "TestSegment", Instrument.DRUMS, "Owner", null, 2L, musicRoom1);
        MusicSegment invalid9 = new MusicSegment(0, "TestSegment", Instrument.DRUMS, "Owner", "C:\\", 0L, musicRoom1);
        MusicSegment invalid10 = new MusicSegment(0, "TestSegment", Instrument.DRUMS, "Owner", "C:\\", null, musicRoom1);
        MusicSegment invalid11 = new MusicSegment();
        MusicSegment invalid12 = new MusicSegment(0, "TestSegment", Instrument.DRUMS, "Owner", "C:\\", null, null);
        MusicSegment invalid13 = new MusicSegment(0, "TestSegment", Instrument.DRUMS, "Owner", "C:\\", null, new MusicRoom(-1, ""));

        assertFalse(invalid1.isValid());
        assertFalse(invalid2.isValid());
        assertFalse(invalid3.isValid());
        assertFalse(invalid4.isValid());
        assertFalse(invalid5.isValid());
        assertFalse(invalid6.isValid());
        assertFalse(invalid7.isValid());
        assertFalse(invalid8.isValid());
        assertFalse(invalid9.isValid());
        assertFalse(invalid10.isValid());
        assertFalse(invalid11.isValid());
        assertFalse(invalid12.isValid());
        assertFalse(invalid13.isValid());
    }

    @Test
    public void testEquals() throws Exception {
        MusicSegment m = musicSegment1;
        assertTrue(musicSegment1.equals(musicSegment8));
        assertTrue(musicSegment8.equals(musicSegment1));
        assertTrue(musicSegment1.equals(m));
        assertTrue(musicSegment8.equals(m));
        assertFalse(musicSegment1.equals(musicSegment2));
        assertFalse(musicSegment2.equals(musicSegment1));
        assertFalse(musicSegment1.equals(musicSegment3));
        assertFalse(musicSegment1.equals(musicSegment4));
        assertFalse(musicSegment1.equals(musicSegment5));
        assertFalse(musicSegment1.equals(musicSegment6));
        assertFalse(musicSegment1.equals(musicSegment7));
        assertFalse(musicSegment1.equals(musicSegment9));
    }

    @Test
    public void testHashCode() throws Exception {
        MusicSegment musicSegment = musicSegment1;
        assertTrue(musicSegment1.hashCode() == musicSegment8.hashCode());
        assertTrue(musicSegment1.hashCode() == musicSegment.hashCode());
        assertTrue(musicSegment8.hashCode() == musicSegment.hashCode());
        assertFalse(musicSegment1.hashCode() == musicSegment2.hashCode());
        assertFalse(musicSegment1.hashCode() == musicSegment3.hashCode());
        assertFalse(musicSegment1.hashCode() == musicSegment4.hashCode());
        assertFalse(musicSegment1.hashCode() == musicSegment5.hashCode());
        assertFalse(musicSegment1.hashCode() == musicSegment6.hashCode());
        assertFalse(musicSegment1.hashCode() == musicSegment7.hashCode());
        assertFalse(musicSegment1.hashCode() == musicSegment9.hashCode());
    }
}
