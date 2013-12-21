package com.musicbox.junit;

import com.musicbox.util.database.entities.MusicRoom;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by David Wachs on 14.12.13.
 */
public class MusicRoomTest {
    MusicRoom musicRoom1;
    MusicRoom musicRoom2;
    MusicRoom musicRoom3;
    MusicRoom musicRoom4;
    @Before
    public void setUp() throws Exception {
        musicRoom1 = new MusicRoom(1, "Testroom");
        musicRoom2 = new MusicRoom(1, "Hello");
        musicRoom3 = new MusicRoom(2, "Testroom");
        musicRoom4 = new MusicRoom(1, "Testroom");
    }

    @Test
    public void testIsValid() throws Exception {
        MusicRoom invalid1 = new MusicRoom(0, null);
        MusicRoom invalid2 = new MusicRoom(0, "Testroom");
        MusicRoom invalid3 = new MusicRoom(1, "");
        MusicRoom invalid4 = new MusicRoom();

        assertTrue(musicRoom1.isValid());
        assertTrue(musicRoom2.isValid());
        assertTrue(musicRoom3.isValid());
        assertTrue(musicRoom4.isValid());
        assertFalse(invalid1.isValid());
        assertFalse(invalid2.isValid());
        assertFalse(invalid3.isValid());
        assertFalse(invalid4.isValid());
    }

    @Test
    public void testEquals() throws Exception {
        MusicRoom m = musicRoom1;
        assertTrue(musicRoom1.equals(m));
        assertTrue(m.equals(musicRoom1));
        assertTrue(musicRoom1.equals(musicRoom4));
        assertTrue(musicRoom4.equals(musicRoom1));
        assertFalse(musicRoom1.equals(musicRoom2));
        assertFalse(musicRoom2.equals(musicRoom1));
        assertFalse(musicRoom1.equals(musicRoom3));
        assertFalse(musicRoom1.equals(null));
    }

    @Test
    public void testHashCode() throws Exception {
        MusicRoom m = musicRoom1;
        assertTrue(musicRoom1.hashCode() == m.hashCode());
        assertTrue(musicRoom1.hashCode() == musicRoom4.hashCode());
        assertFalse(musicRoom1.hashCode() == musicRoom2.hashCode());
        assertFalse(musicRoom1.hashCode() == musicRoom2.hashCode());
        assertFalse(musicRoom1.hashCode() == musicRoom3.hashCode());
    }
}
