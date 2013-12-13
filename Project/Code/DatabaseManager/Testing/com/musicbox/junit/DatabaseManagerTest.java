package com.musicbox.junit;

import static org.junit.Assert.*;
import com.musicbox.util.database.DatabaseManager;
import com.musicbox.util.database.entities.MusicRoom;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: David Wachs
 * Date: 13.12.13
 * Time: 17:05
 * Test DatabaseManager-Class
 */
public class DatabaseManagerTest {
    @Before
    public void setUp() throws Exception {
        DatabaseManager.setConnection("jdbc:mysql://localhost:3306/musicbox?user=root");
    }

    @Test
    public void testSetConnection() throws Exception {
        assertTrue(DatabaseManager.setConnection("jdbc:mysql://localhost:3306/musicbox?user=root"));
        assertFalse(DatabaseManager.setConnection(""));
    }

    @Test
    public void testGetConnectionString() throws Exception {
        String connectionString = "jdbc:mysql://localhost:3306/musicbox?user=root";
        assertTrue(connectionString.equals(DatabaseManager.getConnectionString()));
    }

    @Test
    public void testGetMusicRoomById() throws Exception {
        assertNotNull(DatabaseManager.getMusicRoomById(1));
        assertNull(DatabaseManager.getMusicRoomById(100000));
    }

    @Test
    public void testGetMusicRoomByName() throws Exception {
        assertNotNull(DatabaseManager.getMusicRoomByName("Testroom"));
        assertNull(DatabaseManager.getMusicRoomByName("ejfnejn"));
        assertNull(DatabaseManager.getMusicRoomByName(""));
        assertNull(DatabaseManager.getMusicRoomByName(null));
    }

    @Test
    public void testGetWorkingAreaById() throws Exception {

    }

    @Test
    public void testGetWorkingAreasByMusicRoomId() throws Exception {

    }

    @Test
    public void testGetTrackById() throws Exception {

    }

    @Test
    public void testGetTracksByWorkingAreaId() throws Exception {

    }

    @Test
    public void testGetMusicSegmentById() throws Exception {

    }

    @Test
    public void testGetVariationById() throws Exception {

    }

    @Test
    public void testGetVariationTrackById() throws Exception {

    }

    @Test
    public void testGetVariationTrackByTrackId() throws Exception {

    }

    @Test
    public void testGetVariationTrackByVariationId() throws Exception {

    }

    @Test
    public void testInsertMusicRoom() throws Exception {
        MusicRoom musicRoom1 = new MusicRoom(0, "TestRoom");
        MusicRoom musicRoom2 = new MusicRoom(0, "");
        MusicRoom musicRoom3 = new MusicRoom(0, null);
        assertTrue(DatabaseManager.insertMusicRoom(musicRoom1));
        assertFalse(DatabaseManager.insertMusicRoom(musicRoom2));
        assertFalse(DatabaseManager.insertMusicRoom(musicRoom3));
    }

    @Test
    public void testInsertWorkingArea() throws Exception {

    }

    @Test
    public void testInsertTrack() throws Exception {

    }

    @Test
    public void testInsertVariation() throws Exception {

    }

    @Test
    public void testInsertVariationTrack() throws Exception {

    }

    @Test
    public void testInsertMusicSegment() throws Exception {

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
    }

    @Test
    public void testUpdateWorkingArea() throws Exception {

    }

    @Test
    public void testUpdateTrack() throws Exception {

    }

    @Test
    public void testUpdateVariation() throws Exception {

    }

    @Test
    public void testUpdateVariationTrack() throws Exception {

    }

    @Test
    public void testUpdateMusicSegment() throws Exception {

    }

    @Test
    public void testDeleteTrackById() throws Exception {

    }

    @Test
    public void testDeleteVariationById() throws Exception {

    }

    @Test
    public void testDeleteVariationTrackById() throws Exception {

    }

    @Test
    public void testDeleteMusicSegmentById() throws Exception {

    }
}
