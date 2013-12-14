package com.musicbox.junit;

import com.musicbox.util.Instrument;
import com.musicbox.util.database.entities.MusicSegment;
import com.musicbox.util.database.entities.Variation;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by David on 14.12.13.
 */
public class VariationTest {
    MusicSegment musicSegment1;
    MusicSegment musicSegment2;
    Variation variation1;
    Variation variation2;
    Variation variation3;
    Variation variation4;
    Variation variation5;
    Variation variation6;
    Variation variation7;
    @Before
    public void setUp() throws Exception {
        musicSegment1 = new MusicSegment(1, "MS1", Instrument.BASSGUITAR, "Owner", "C:\\", 10L);
        musicSegment2 = new MusicSegment(2, "MS2", Instrument.BASSGUITAR, "Owner", "C:\\", 12L);
        variation1 = new Variation(1, musicSegment1, "Var", 0L, 10L, "Owner");
        variation2 = new Variation(2, musicSegment1, "Var", 0L, 10L, "Owner");
        variation3 = new Variation(1, musicSegment2, "Var", 0L, 10L, "Owner");
        variation4 = new Variation(1, musicSegment1, "Variation", 0L, 10L, "Owner");
        variation5 = new Variation(1, musicSegment1, "Var", 2L, 10L, "Owner");
        variation6 = new Variation(1, musicSegment1, "Var", 0L, 5L, "Owner");
        variation7 = new Variation(1, musicSegment2, "Var", 0L, 10L, "Owner2");
    }

    @Test
    public void testIsValid() throws Exception {
        assertTrue(variation1.isValid());
        assertTrue(variation2.isValid());
        assertTrue(variation3.isValid());
        assertTrue(variation4.isValid());
        assertTrue(variation5.isValid());
        assertTrue(variation6.isValid());
        assertTrue(variation7.isValid());

        Variation invalid1 = new Variation(0, musicSegment1, "Var", 0L, 10L, "Owner");
        Variation invalid2 = new Variation(1, null, "Var", 0L, 10L, "Owner");
        Variation invalid3 = new Variation(1, musicSegment1, "", 0L, 10L, "Owner");
        Variation invalid4 = new Variation(1, musicSegment1, null, 0L, 10L, "Owner");
        Variation invalid5 = new Variation(1, musicSegment1, "Var", null, 10L, "Owner");
        Variation invalid6 = new Variation(1, musicSegment1, "Var", 0L, null, "Owner");
        Variation invalid7 = new Variation(1, musicSegment1, "Var", 0L, 10L, "");
        Variation invalid8 = new Variation(1, musicSegment1, "Var", 0L, 10L, null);
        Variation invalid9 = new Variation(1, musicSegment1, "Var", -1L, 10L, "Owner");
        Variation invalid10 = new Variation(1, musicSegment1, "Var", 11L, 10L, "Owner");
        Variation invalid11 = new Variation(1, musicSegment1, "Var", 0L, 0L, "Owner");
        Variation invalid12 = new Variation(1, musicSegment1, "Var", 0L, 12L, "Owner");
        Variation invalid13 = new Variation();

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

    }

    @Test
    public void testHashCode() throws Exception {

    }
}
