package com.musicbox.junit;


import com.musicbox.util.database.entities.MusicRoom;
import org.junit.Before;
import org.junit.Test;
import com.musicbox.util.database.DatabaseManager;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 08.12.13
 * Time: 14:47
 * To change this template use File | Settings | File Templates.
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
    public void testInsertMusicRoom() throws Exception {
        MusicRoom musicRoom1 = new MusicRoom(0, "TestRoom4");
        MusicRoom musicRoom2 = new MusicRoom(0, "");
        MusicRoom musicRoom3 = new MusicRoom(0, null);
        assertTrue(DatabaseManager.insertMusicRoom(musicRoom1));
        assertFalse(DatabaseManager.insertMusicRoom(musicRoom2));
        assertFalse(DatabaseManager.insertMusicRoom(musicRoom3));
    }

    @Test
    public void testGetMusicRoomById() throws Exception {
        assertNotNull(DatabaseManager.getMusicRoomById(1));
        assertNull(DatabaseManager.getMusicRoomById(100000));
    }

    @Test
    public void testGetMusicRoomByName() throws Exception {
        MusicRoom musicRoom = new MusicRoom();
        assertNotNull(DatabaseManager.getMusicRoomByName("Testroom"));
        assertNull(DatabaseManager.getMusicRoomByName("ejfnejn"));
        assertNull(DatabaseManager.getMusicRoomByName(""));
        assertNull(DatabaseManager.getMusicRoomByName(null));
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


}
