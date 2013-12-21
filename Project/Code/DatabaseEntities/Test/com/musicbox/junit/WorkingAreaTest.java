package com.musicbox.junit;

import com.musicbox.util.WorkingAreaType;
import com.musicbox.util.database.entities.MusicRoom;
import com.musicbox.util.database.entities.WorkingArea;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by David on 14.12.13.
 */
public class WorkingAreaTest {
    MusicRoom musicRoom1;
    MusicRoom musicRoom2;
    WorkingArea workingArea1;
    WorkingArea workingArea2;
    WorkingArea workingArea3;
    WorkingArea workingArea4;
    WorkingArea workingArea5;
    WorkingArea workingArea6;
    WorkingArea workingArea7;
    WorkingArea workingArea8;
    WorkingArea workingArea9;
    WorkingArea workingArea10;
    WorkingArea workingArea11;
    @Before
    public void setUp() throws Exception {
        musicRoom1 = new MusicRoom(1, "Testroom1");
        musicRoom2 = new MusicRoom(2, "Testroom2");

        workingArea1 = new WorkingArea(1, musicRoom1, "WorkingArea", 10, "Owner", WorkingAreaType.PRIVATE, 1F, 1L);
        workingArea2 = new WorkingArea(2, musicRoom1, "WorkingArea", 10, "Owner", WorkingAreaType.PRIVATE, 1F, 1L);
        workingArea3 = new WorkingArea(1, musicRoom2, "WorkingArea", 10, "Owner", WorkingAreaType.PRIVATE, 1F, 1L);
        workingArea4 = new WorkingArea(1, musicRoom1, "WorkingArea1", 10, "Owner", WorkingAreaType.PRIVATE, 1F, 1L);
        workingArea5 = new WorkingArea(1, musicRoom1, "WorkingArea", 11, "Owner", WorkingAreaType.PRIVATE, 1F, 1L);
        workingArea6 = new WorkingArea(1, musicRoom1, "WorkingArea", 10, "Owner1", WorkingAreaType.PRIVATE, 1F, 1L);
        workingArea7 = new WorkingArea(1, musicRoom1, "WorkingArea", 10, "Owner", WorkingAreaType.PUBLIC, 1F, 1L);
        workingArea8 = new WorkingArea(1, musicRoom1, "WorkingArea", 10, "Owner", WorkingAreaType.PRIVATE, 2F, 1L);
        workingArea9 = new WorkingArea(1, musicRoom1, "WorkingArea", 10, "Owner", WorkingAreaType.PRIVATE, 1F, 2L);
        workingArea10 = new WorkingArea(1, musicRoom1, "WorkingArea", 10, "", WorkingAreaType.PUBLIC, 1F, 1L);
        workingArea11 = new WorkingArea(1, musicRoom1, "WorkingArea", 10, "Owner", WorkingAreaType.PRIVATE, 1F, 1L);
    }

    @Test
    public void testIsValid() throws Exception {
        assertTrue(workingArea1.isValid());
        assertTrue(workingArea2.isValid());
        assertTrue(workingArea3.isValid());
        assertTrue(workingArea4.isValid());
        assertTrue(workingArea5.isValid());
        assertTrue(workingArea6.isValid());
        assertTrue(workingArea7.isValid());
        assertTrue(workingArea8.isValid());
        assertTrue(workingArea9.isValid());
        assertTrue(workingArea10.isValid());
        assertTrue(workingArea11.isValid());

        WorkingArea invalid1 = new WorkingArea(0, musicRoom1, "WorkingArea", 10, "Owner", WorkingAreaType.PRIVATE, 1F, 1L);
        WorkingArea invalid2= new WorkingArea(1, null, "WorkingArea", 10, "Owner", WorkingAreaType.PRIVATE, 1F, 1L);
        WorkingArea invalid3 = new WorkingArea(1, musicRoom1, "", 10, "Owner", WorkingAreaType.PRIVATE, 1F, 1L);
        WorkingArea invalid4 = new WorkingArea(1, musicRoom1, null, 10, "Owner", WorkingAreaType.PRIVATE, 1F, 1L);
        WorkingArea invalid5 = new WorkingArea(1, musicRoom1, "WorkingArea", 0, "Owner", WorkingAreaType.PRIVATE, 1F, 1L);
        WorkingArea invalid6 = new WorkingArea(1, musicRoom1, "WorkingArea", 10, "", WorkingAreaType.PRIVATE, 1F, 1L);
        WorkingArea invalid7 = new WorkingArea(1, musicRoom1, "WorkingArea", 10, null, WorkingAreaType.PRIVATE, 1F, 1L);
        WorkingArea invalid8 = new WorkingArea(1, musicRoom1, "WorkingArea", 10, "Owner", null, 1F, 1L);
        WorkingArea invalid9 = new WorkingArea(1, musicRoom1, "WorkingArea", 10, "Owner", WorkingAreaType.PRIVATE, 0F, 2L);
        WorkingArea invalid10 = new WorkingArea(1, musicRoom1, "WorkingArea", 10, "Owner", WorkingAreaType.PRIVATE, 1F, -1L);
        WorkingArea invalid11 = new WorkingArea(1, musicRoom1, "WorkingArea", 10, "Owner", WorkingAreaType.PRIVATE, 1F, null);
        WorkingArea invalid12 = new WorkingArea();

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
    }

    @Test
    public void testEquals() throws Exception {
        WorkingArea w = workingArea1;
        assertTrue(workingArea1.equals(w));
        assertTrue(workingArea1.equals(workingArea11));
        assertTrue(workingArea11.equals(workingArea1));
        assertFalse(workingArea1.equals(workingArea2));
        assertFalse(workingArea2.equals(workingArea1));
        assertFalse(workingArea1.equals(workingArea3));
        assertFalse(workingArea1.equals(workingArea4));
        assertFalse(workingArea1.equals(workingArea5));
        assertFalse(workingArea1.equals(workingArea6));
        assertFalse(workingArea1.equals(workingArea7));
        assertFalse(workingArea1.equals(workingArea8));
        assertFalse(workingArea1.equals(workingArea9));
        assertFalse(workingArea1.equals(workingArea10));
        assertFalse(workingArea1.equals(null));
    }

    @Test
    public void testHashCode() throws Exception {
        WorkingArea w = workingArea1;
        assertTrue(workingArea1.hashCode() == w.hashCode());
        assertTrue(workingArea1.hashCode() == workingArea11.hashCode());
        assertFalse(workingArea1.hashCode() == workingArea2.hashCode());
        assertFalse(workingArea1.hashCode() == workingArea3.hashCode());
        assertFalse(workingArea1.hashCode() == workingArea4.hashCode());
        assertFalse(workingArea1.hashCode() == workingArea5.hashCode());
        assertFalse(workingArea1.hashCode() == workingArea6.hashCode());
        assertFalse(workingArea1.hashCode() == workingArea7.hashCode());
        assertFalse(workingArea1.hashCode() == workingArea8.hashCode());
        assertFalse(workingArea1.hashCode() == workingArea9.hashCode());
        assertFalse(workingArea1.hashCode() == workingArea10.hashCode());
    }
}
