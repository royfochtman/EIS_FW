package com.musicbox.junit;

import com.musicbox.util.Instrument;
import com.musicbox.util.database.entities.MusicRoom;
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
    Variation variation8;
    @Before
    public void setUp() throws Exception {
        musicSegment1 = new MusicSegment(1, "MS1", Instrument.BASSGUITAR, "Owner", "C:\\", 10L, new MusicRoom(1, "Roon"));
        musicSegment2 = new MusicSegment(2, "MS2", Instrument.BASSGUITAR, "Owner", "C:\\", 12L, new MusicRoom(1, "Room"));
        variation1 = new Variation(1, musicSegment1, "Var", 0L, 10L, "Owner");
        variation2 = new Variation(2, musicSegment1, "Var", 0L, 10L, "Owner");
        variation3 = new Variation(1, musicSegment2, "Var", 0L, 10L, "Owner");
        variation4 = new Variation(1, musicSegment1, "Variation", 0L, 10L, "Owner");
        variation5 = new Variation(1, musicSegment1, "Var", 2L, 10L, "Owner");
        variation6 = new Variation(1, musicSegment1, "Var", 0L, 5L, "Owner");
        variation7 = new Variation(1, musicSegment2, "Var", 0L, 10L, "Owner2");
        variation8 = new Variation(1, musicSegment1, "Var", 0L, 10L, "Owner");
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
        assertTrue(variation8.isValid());

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
        Variation v = variation1;
        assertTrue(variation1.equals(v));
        assertTrue(variation1.equals(variation8));
        assertTrue(variation8.equals(variation1));
        assertFalse(variation1.equals(variation2));
        assertFalse(variation2.equals(variation1));
        assertFalse(variation1.equals(variation3));
        assertFalse(variation1.equals(variation4));
        assertFalse(variation1.equals(variation5));
        assertFalse(variation1.equals(variation6));
        assertFalse(variation1.equals(variation7));
    }

    @Test
    public void testHashCode() throws Exception {
        Variation v = variation1;
        assertTrue(variation1.hashCode() == v.hashCode());
        assertTrue(variation1.hashCode() == variation8.hashCode());
        assertFalse(variation1.hashCode() == variation2.hashCode());
        assertFalse(variation1.hashCode() == variation3.hashCode());
        assertFalse(variation1.hashCode() == variation4.hashCode());
        assertFalse(variation1.hashCode() == variation5.hashCode());
        assertFalse(variation1.hashCode() == variation6.hashCode());
        assertFalse(variation1.hashCode() == variation7.hashCode());
    }
}
