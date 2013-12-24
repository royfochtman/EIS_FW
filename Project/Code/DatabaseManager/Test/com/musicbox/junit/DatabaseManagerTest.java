package com.musicbox.junit;

import static org.junit.Assert.*;

import com.musicbox.util.Instrument;
import com.musicbox.util.WorkingAreaType;
import com.musicbox.util.database.DatabaseManager;
import com.musicbox.util.database.entities.*;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: David Wachs
 * Date: 13.12.13
 * Time: 17:05
 * Test DatabaseManager-Class
 */
public class DatabaseManagerTest {
    Date date;
    DateFormat dateFormat;
    @Before
    public void setUp() throws Exception {
        DatabaseManager.setConnection("jdbc:mysql://localhost:3306/musicbox?user=root");
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }

    @Test
    public void testSetConnection() throws Exception {
        assertTrue(DatabaseManager.setConnection("jdbc:mysql://localhost:3306/musicbox?user=root"));
        assertFalse(DatabaseManager.setConnection("jdbc:mysql://localhost:3306/musicbox"));
        assertFalse(DatabaseManager.setConnection(""));
        assertFalse(DatabaseManager.setConnection(null));
    }

    @Test
    public void testGetConnectionString() throws Exception {
        String connectionString = "jdbc:mysql://localhost:3306/musicbox?user=root";
        assertTrue(connectionString.equals(DatabaseManager.getConnectionString()));
    }

    @Test
    public void testInsertMusicRoom() throws Exception {
        date = new Date();
        MusicRoom musicRoom1 = new MusicRoom(1, "TestRoom " + dateFormat.format(date));
        MusicRoom musicRoom2 = new MusicRoom(0, "TestRoom");
        MusicRoom musicRoom3 = new MusicRoom(1, null);
        assertTrue(DatabaseManager.insertMusicRoom(musicRoom1));
        assertFalse(DatabaseManager.insertMusicRoom(musicRoom2));
        assertFalse(DatabaseManager.insertMusicRoom(musicRoom3));
    }

    @Test
    public void testInsertWorkingArea() throws Exception {
        date = new Date();
        WorkingArea workingArea1 = new WorkingArea(1, DatabaseManager.getMusicRoomById(1), "WorkingArea " + dateFormat.format(date), 10, "Owner",
                WorkingAreaType.PRIVATE, 2F, 10L);
        WorkingArea workingArea2 = new WorkingArea(1, new MusicRoom(100000, "MusicRoom"), "WorkingArea " + dateFormat.format(date), 10, "Owner",
                WorkingAreaType.PRIVATE, 2F, 10L);

        assertTrue(DatabaseManager.insertWorkingArea(workingArea1));
        assertFalse(DatabaseManager.insertWorkingArea(workingArea2));
    }

    @Test
    public void testInsertTrack() throws Exception {
        date = new Date();
        WorkingArea workingArea1 = DatabaseManager.getWorkingAreaById(1);
        WorkingArea workingArea2 = new WorkingArea(2, new MusicRoom(1, "MusicRoom"), "WorkingArea " + dateFormat.format(date), 10, "Owner",
                WorkingAreaType.PRIVATE, 2F, 10L);
        Track track1 = new Track(1, workingArea1, Instrument.BASSGUITAR, 10, "Track" + dateFormat.format(date) , 10L);
        Track track2 = new Track(1, workingArea1, Instrument.BASSGUITAR, 10, "Track" + dateFormat.format(date) , 10L);
        Track track3 = new Track(1, workingArea1, Instrument.BASSGUITAR, 10, "Track" + dateFormat.format(date) , 10L);
        Track track4 = new Track(1, workingArea2, Instrument.BASSGUITAR, 10, "Track" + dateFormat.format(date), 10L);
        assertTrue(DatabaseManager.insertTrack(track1));
        assertTrue(DatabaseManager.insertTrack(track2));
        assertTrue(DatabaseManager.insertTrack(track3));
        assertFalse(DatabaseManager.insertTrack(track4));
    }

    @Test
    public void testInsertMusicSegment() throws Exception {
        date = new Date();
        MusicRoom musicRoom = DatabaseManager.getMusicRoomById(1);
        MusicSegment musicSegment1 = new MusicSegment(1, "MusicSegment" + dateFormat.format(date), Instrument.BASSGUITAR, "Owner", "C:\\", 10L, musicRoom);
        MusicSegment musicSegment2 = new MusicSegment(1, "MusicSegment" + dateFormat.format(date), Instrument.BASSGUITAR, "Owner", "C:\\", 10L, musicRoom);
        MusicSegment musicSegment3 = new MusicSegment(1, "MusicSegment" + dateFormat.format(date), Instrument.BASSGUITAR, "Owner", "C:\\", 10L, musicRoom);
        MusicSegment musicSegment4 = new MusicSegment(1, "MusicSegment" + dateFormat.format(date), Instrument.BASSGUITAR, "Owner", "C:\\", 10L, new MusicRoom(100000, "MusicRoom"));
        assertTrue(DatabaseManager.insertMusicSegment(musicSegment1));
        assertTrue(DatabaseManager.insertMusicSegment(musicSegment2));
        assertTrue(DatabaseManager.insertMusicSegment(musicSegment3));
        assertFalse(DatabaseManager.insertMusicSegment(musicSegment4));
    }

    @Test
    public void testInsertVariation() throws Exception {
        date = new Date();
        MusicSegment musicSegment1 = DatabaseManager.getMusicSegmentById(1);
        MusicSegment musicSegment2 = DatabaseManager.getMusicSegmentById(2);
        MusicSegment musicSegment3 = DatabaseManager.getMusicSegmentById(3);
        MusicSegment musicSegment4 = new MusicSegment(100000, "MusicSegment1", Instrument.BASSGUITAR, "Owner1", "C:\\", 10L, new MusicRoom(1, "TestRoom"));

        Variation variation1 = new Variation(1, musicSegment1, "Variation" + dateFormat.format(date), 0L, 10L, "Owner1");
        Variation variation2 = new Variation(1, musicSegment2, "Variation" + dateFormat.format(date), 0L, 10L, "Owner1");
        Variation variation3 = new Variation(1, musicSegment3, "Variation" + dateFormat.format(date), 0L, 10L, "Owner1");
        Variation variation4 = new Variation(1, musicSegment4, "Variation" + dateFormat.format(date), 0L, 10L, "Owner2");

        assertTrue(DatabaseManager.insertVariation(variation1));
        assertTrue(DatabaseManager.insertVariation(variation2));
        assertTrue(DatabaseManager.insertVariation(variation3));
        assertFalse(DatabaseManager.insertVariation(variation4));
    }

    @Test
    public void testInsertVariationTrack() throws Exception {
        date = new Date();
        Variation variation1 = DatabaseManager.getVariationById(1);
        Track track1 = DatabaseManager.getTrackById(1);
        VariationTrack variationTrack1 = new VariationTrack(1, variation1, track1, 5L);
        assertTrue(DatabaseManager.insertVariationTrack(variationTrack1));
        variationTrack1.setTrack(DatabaseManager.getTrackById(2));
        assertTrue(DatabaseManager.insertVariationTrack(variationTrack1));
        variationTrack1.setVariation(DatabaseManager.getVariationById(2));
        assertTrue(DatabaseManager.insertVariationTrack(variationTrack1));
        variationTrack1.setVariation(DatabaseManager.getVariationById(2));
        variationTrack1.setTrack(DatabaseManager.getTrackById(2));
        assertTrue(DatabaseManager.insertVariationTrack(variationTrack1));
    }

    @Test
    public void testGetMusicRoomById() throws Exception {
        assertNotNull(DatabaseManager.getMusicRoomById(1));
        assertNull(DatabaseManager.getMusicRoomById(100000));
    }

    @Test
    public void testGetMusicRoomByName() throws Exception {
        MusicRoom musicRoom = DatabaseManager.getMusicRoomById(1);
        assertNotNull(DatabaseManager.getMusicRoomByName(musicRoom.getName()));
        assertNull(DatabaseManager.getMusicRoomByName("ejfnejn"));
        assertNull(DatabaseManager.getMusicRoomByName(""));
        assertNull(DatabaseManager.getMusicRoomByName(null));
    }

    @Test
    public void testGetMusicRooms() throws Exception {
        assertNotNull(DatabaseManager.getMusicRooms());
    }

    @Test
    public void testGetWorkingAreaById() throws Exception {
        assertNotNull(DatabaseManager.getWorkingAreaById(1));
        assertNull(DatabaseManager.getWorkingAreaById(100000));
    }

    @Test
    public void testGetWorkingAreasByMusicRoomId() throws Exception {
        assertNotNull(DatabaseManager.getWorkingAreasByMusicRoomId(1));
        assertNull(DatabaseManager.getWorkingAreasByMusicRoomId(100000));
    }

    @Test
    public void testGetTrackById() throws Exception {
        assertNotNull(DatabaseManager.getTrackById(1));
        assertNull(DatabaseManager.getTrackById(10000));
    }

    @Test
    public void testGetTracksByWorkingAreaId() throws Exception {
        assertNotNull(DatabaseManager.getTracksByWorkingAreaId(1));
        assertNull(DatabaseManager.getTracksByWorkingAreaId(1000000));
    }

    @Test
    public void testGetMusicSegmentById() throws Exception {
        assertNotNull(DatabaseManager.getMusicSegmentById(1));
        assertNull(DatabaseManager.getMusicSegmentById(100000));
    }

    @Test
    public void testGetMusicSegmentsByMusicRoomId() throws Exception {
        assertNotNull(DatabaseManager.getMusicSegmentsByMusicRoomId(1));
        assertNull(DatabaseManager.getMusicSegmentsByMusicRoomId(100000));;
    }

    @Test
    public void testGetVariationById() throws Exception {
        assertNotNull(DatabaseManager.getVariationById(1));
        assertNull(DatabaseManager.getVariationById(1000000));
    }

    @Test
    public void testGetVariationsByMusicRoomId() throws Exception {
        assertNotNull(DatabaseManager.getVariationsByMusicRoomId(1));
        assertNull(DatabaseManager.getVariationsByMusicRoomId(10000));
    }

    @Test
    public void testGetVariationTrackById() throws Exception {
        assertNotNull(DatabaseManager.getVariationTrackById(1));
        assertNull(DatabaseManager.getVariationTrackById(100000));
    }

    @Test
    public void testGetVariationTracksByTrackId() throws Exception {
        assertNotNull(DatabaseManager.getVariationTracksByTrackId(1));
        assertNull(DatabaseManager.getVariationTracksByTrackId(100000));
    }

    @Test
    public void testGetVariationTracksByVariationId() throws Exception {
        assertNotNull(DatabaseManager.getVariationTracksByVariationId(1));
        assertNull(DatabaseManager.getVariationTracksByVariationId(1000000));
    }

    @Test
    public void testUpdateMusicRoom() throws Exception {
        MusicRoom musicRoom = new MusicRoom(1, "jfnejkfn");
        MusicRoom musicRoom1 = new MusicRoom(1, null);
        MusicRoom musicRoom2 = new MusicRoom(-1, "dnejfne");
        MusicRoom musicRoom3 = new MusicRoom(1, "");
        assertTrue(DatabaseManager.updateMusicRoom(musicRoom));
        assertFalse(DatabaseManager.updateMusicRoom(musicRoom1));
        assertFalse(DatabaseManager.updateMusicRoom(musicRoom2));
        assertFalse(DatabaseManager.updateMusicRoom(musicRoom3));
        assertFalse(DatabaseManager.updateMusicRoom(null));
    }

    @Test
    public void testUpdateWorkingArea() throws Exception {
        WorkingArea workingArea1 = DatabaseManager.getWorkingAreaById(1);
        workingArea1.setName("jrgrwgpr");
        assertTrue(DatabaseManager.updateWorkingArea(workingArea1));
        workingArea1.setOwner(null);
        assertFalse(DatabaseManager.updateWorkingArea(workingArea1));
        assertFalse(DatabaseManager.updateWorkingArea(null));
    }

    @Test
    public void testUpdateTrack() throws Exception {
        Track track1 = DatabaseManager.getTrackById(1);
        track1.setName("Trackfake");
        assertTrue(DatabaseManager.updateTrack(track1));
        track1.setName("");
        assertFalse(DatabaseManager.updateTrack(track1));
        assertFalse(DatabaseManager.updateTrack(null));
    }

    @Test
    public void testUpdateVariation() throws Exception {
        Variation variation1 = DatabaseManager.getVariationById(1);
        variation1.setName("dfefe");
        assertTrue(DatabaseManager.updateVariation(variation1));
        variation1.setName("");
        assertFalse(DatabaseManager.updateVariation(variation1));
        assertFalse(DatabaseManager.updateVariation(null));
    }

    @Test
    public void testUpdateVariationTrack() throws Exception {
        VariationTrack variationTrack1 = DatabaseManager.getVariationTrackById(1);
        variationTrack1.setStartTimeOnTrack(2L);
        assertTrue(DatabaseManager.updateVariationTrack(variationTrack1));
        variationTrack1.setStartTimeOnTrack(-1L);
        assertFalse(DatabaseManager.updateVariationTrack(variationTrack1));
        assertFalse(DatabaseManager.updateVariationTrack(null));
    }

    @Test
    public void testUpdateMusicSegment() throws Exception {
        MusicSegment musicSegment1 = DatabaseManager.getMusicSegmentById(1);
        musicSegment1.setName("feqef");
        assertTrue(DatabaseManager.updateMusicSegment(musicSegment1));
        musicSegment1.setName("");
        assertFalse(DatabaseManager.updateMusicSegment(musicSegment1));
        assertFalse(DatabaseManager.updateMusicSegment(null));
    }

    @Test
    public void testDeleteTrackById() throws Exception {
        assertTrue(DatabaseManager.deleteTrackById(1));
        assertFalse(DatabaseManager.deleteTrackById(1000000));
    }

    @Test
    public void testDeleteVariationById() throws Exception {
        assertTrue(DatabaseManager.deleteVariationById(1));
        assertFalse(DatabaseManager.deleteVariationById(10000));
    }

    @Test
    public void testDeleteVariationTrackById() throws Exception {
        assertTrue(DatabaseManager.deleteVariationTrackById(1));
        assertFalse(DatabaseManager.deleteVariationTrackById(1000000));

    }

    @Test
    public void testDeleteMusicSegmentById() throws Exception {
        assertTrue(DatabaseManager.deleteMusicSegmentById(1));
        assertFalse(DatabaseManager.deleteMusicSegmentById(100000));
    }
}
