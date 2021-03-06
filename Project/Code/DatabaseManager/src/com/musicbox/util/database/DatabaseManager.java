package com.musicbox.util.database;

import java.sql.*;
import java.util.ArrayList;

import com.musicbox.util.MusicRoomDataContainer;
import com.musicbox.util.WorkingAreaType;
import com.musicbox.util.Instrument;
import com.musicbox.util.database.entities.*;
import com.musicbox.util.globalobject.GlobalObject;

import javax.lang.model.element.VariableElement;

/**
 * This class/module represents the DatabaseMangaer, witch is used to connect to
 * MusicBox-MySQL-Database and insert, read, update or delete data.
 *
 * @author David Wachs
 */
public abstract class DatabaseManager {
    /**
     * stores the connection to MySQL-Database
     */
    private static Connection connect = null;
    /**
     * connection-String to MySQL-Database
     */
    private static String connectionString = null;
    private static PreparedStatement preparedStatement = null;

    //<editor-fold desc="Region: MySQL-Constants">
    /**
     * The constants below represents the database tables and columns.
     * They are needed to assemble the MySQL queries.
     */
    private static final String MUSIC_ROOM = "music_room";
    private static final String WORKING_AREA = "working_area";
    private static final String TRACK = "track";
    private static final String MUSIC_SEGMENT = "music_segment";
    private static final String VARIATION = "variation";
    private static final String VARIATION_TRACK = "variation_track";
    private static final String NAME = "name";
    private static final String OWNER = "owner";
    private static final String LENGTH = "length";
    private static final String START_TIME = "start_time";
    private static final String END_TIME = "end_time";
    private static final String AUDIO_PATH = "audio_path";
    private static final String INSTRUMENT = "instrument";
    private static final String TEMPO = "tempo";
    private static final String TYPE = "type";
    private static final String BEAT = "beat";
    private static final String VOLUME = "volume";
    private static final String MUSIC_ROOM_ID = "music_room_id";
    private static final String WORKING_AREA_ID = "working_area_id";
    private static final String VARIATION_ID = "variation_id";
    private static final String TRACK_ID = "track_id";
    private static final String MUSIC_SEGMENT_ID = "music_segment_id";
    private static final String VARIATION_TRACK_ID = "variation_track_id";
    //</editor-fold>

    /**
     * Create connection based on connections-string in <i>conn</i> param.
     * @param conn represents the Connection-String to the MySQL-Database
     *             Format: <i>jdbc:mysql://[host]/[database]?user=[username]&password=[password]</i>
     * @return true, if connection succeed
     */
    public static boolean setConnection(String conn){
        try {
            if(conn == null || conn.isEmpty())
                return false;

            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(conn);
            connectionString = conn;
            return true;
        } catch (SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getConnectionString() {
        return connectionString;
    }

    /**
     * Gets one tupel from <i>music_room</i> table by the given <i>musicRoomId</i>-param
     * @param musicRoomId the id of the music room to be returned
     * @return MusicRoom-Object, if tupel could be found. Otherwise return <i>null</i>
     */
    public static MusicRoom getMusicRoomById(int musicRoomId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + MUSIC_ROOM + " WHERE " + MUSIC_ROOM_ID + " = ? LIMIT 1", new Object[]{ musicRoomId });
        ArrayList<MusicRoom> musicRoomArrayList = convertResultSetToMusicRoomArrayList(resultSet);
        if(musicRoomArrayList != null && !musicRoomArrayList.isEmpty())
            return musicRoomArrayList.get(0);
        else
            return null;
    }

    /**
     * Gets one tupel from <i>music_room</i> table by the given <i>musicRoomName</i>-param
     * @param musicRoomName the name of the music room to be returned
     * @return MusicRoom-Object, if tupel could be found. Otherwise return <i>null</i>
     */
    public static MusicRoom getMusicRoomByName(String musicRoomName) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + MUSIC_ROOM + " WHERE " + NAME + "=? LIMIT 1", new Object[]{ musicRoomName });
        ArrayList<MusicRoom> musicRoomArrayList = convertResultSetToMusicRoomArrayList(resultSet);
        if(musicRoomArrayList != null && !musicRoomArrayList.isEmpty())
            return musicRoomArrayList.get(0);
        else
            return null;
    }

    /**
     * Gets all music rooms in the table <i>music_room</i>
     * @return an ArrayList of MusicRoom-Object. Return <i>null</i>, if no tupels found
     */
    public static ArrayList<MusicRoom> getMusicRooms() {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + MUSIC_ROOM, null);
        return convertResultSetToMusicRoomArrayList(resultSet);
    }

    /**
     * Gets one tupel from <i>working_area</i> table by the given <i>workingAreaId</i>-param
     * @param workingAreaId the id of the working area to be returned
     * @return WorkingArea-Object, if tupel could be found. Otherwise return <i>null</i>
     */
    public static WorkingArea getWorkingAreaById(int workingAreaId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + WORKING_AREA + " WHERE " + WORKING_AREA_ID + "=? LIMIT 1", new Object[]{ workingAreaId });
        ArrayList<WorkingArea> workingAreaArrayList = convertResultSetToWorkingAreaArrayList(resultSet);
        if(workingAreaArrayList != null && !workingAreaArrayList.isEmpty())
            return workingAreaArrayList.get(0);
        else
            return null;
    }

    /**
     * Gets all working areas from <i>working_area</i> table by the given <i>musicRoomId</i>-param
     * @param musicRoomId the id of the music room from which the working areas are to be returned
     * @return ArrayList of WorkingArea-Objects, if tupel could be found. Otherwise return <i>null</i>
     */
    public static ArrayList<WorkingArea> getWorkingAreasByMusicRoomId(int musicRoomId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + WORKING_AREA + " WHERE " + MUSIC_ROOM_ID + "=?", new Object[]{ musicRoomId });
        return convertResultSetToWorkingAreaArrayList(resultSet);
    }

    /**
     * Gets one tupel from <i>track</i> table by the given <i>trackId</i>-param
     * @param trackId the id of the track to be returned
     * @return Track-Object, if tupel could be found. Otherwise return <i>null</i>
     */
    public static Track getTrackById(int trackId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + TRACK + " WHERE " + TRACK_ID + "=? LIMIT 1", new Object[]{ trackId });
        ArrayList<Track> trackArrayList = convertResultSetToTrackArrayList(resultSet);
        if(trackArrayList != null && !trackArrayList.isEmpty())
            return trackArrayList.get(0);
        else
            return null;
    }

    /**
     * Gets all tracks from <i>track</i> table by the given <i>workingAreaId</i>-param
     * @param workingAreaId the id of the working area from which the tracks are to be returned
     * @return ArrayList of Track-Objects, if tupel could be found. Otherwise return <i>null</i>
     */
    public static ArrayList<Track> getTracksByWorkingAreaId(int workingAreaId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + TRACK + " WHERE " + WORKING_AREA_ID + "=?", new Object[]{ workingAreaId });
        return convertResultSetToTrackArrayList(resultSet);
    }

    /**
     * Gets all tracks from <i>track</i> table by the given <i>musicRoomId</i>-param
     * @param musicRoomId the id of the music room from which the tracks are to be returned
     * @return ArrayList of Track-Objects, if tupel could be found. Otherwise return <i>null</i>
     */
    public static ArrayList<Track> getTracksByMusicRoomId(int musicRoomId) {
        ResultSet resultSet = executePreparedSelect("SELECT " + TRACK + "." + TRACK_ID + ", " + TRACK + "." + WORKING_AREA_ID + ", " + TRACK + "."+ INSTRUMENT + ", " + TRACK + "." + VOLUME
                + ", " + TRACK + "." + NAME + ", " + TRACK + "." + LENGTH + " FROM " + TRACK
                + " INNER JOIN " + WORKING_AREA + " USING (" + WORKING_AREA_ID + ")"
                + " INNER JOIN " + MUSIC_ROOM + "  USING (" + MUSIC_ROOM_ID + ")"
                + " WHERE " + MUSIC_ROOM + "." + MUSIC_ROOM_ID + " = ?", new Object[] {musicRoomId});
        return convertResultSetToTrackArrayList(resultSet);
    }

    /**
     * Gets one tupel from <i>music_segement</i> table by the given <i>musicSegmentId</i>-param
     * @param musicSegmentId the id of the music segment to be returned
     * @return MusicSegment-Object, if tupel could be found. Otherwise return <i>null</i>
     */
    public static MusicSegment getMusicSegmentById(int musicSegmentId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + MUSIC_SEGMENT + " WHERE " + MUSIC_SEGMENT_ID + "=? LIMIT 1", new Object[]{ musicSegmentId });
        ArrayList<MusicSegment> musicSegmentArrayList = convertResultSetToMusicSegmentArrayList(resultSet);
        if(musicSegmentArrayList != null && !musicSegmentArrayList.isEmpty())
            return musicSegmentArrayList.get(0);
        else
            return null;
    }

    /**
     * Gets all music segments from <i>music_segment</i> table by the given <i>musicRoomId</i>-param
     * @param musicRoomId the id of the music room from which the music segments are to be returned
     * @return ArrayList of MusicSegment-Objects, if tupel could be found. Otherwise return <i>null</i>
     */
    public static ArrayList<MusicSegment> getMusicSegmentsByMusicRoomId(int musicRoomId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + MUSIC_SEGMENT + " WHERE " + MUSIC_ROOM_ID + "=?", new Object[]{ musicRoomId });
        return convertResultSetToMusicSegmentArrayList(resultSet);
    }

    /**
     * Gets one tupel from <i>variation</i> table by the given <i>variationId</i>-param
     * @param variationId the id of the variation to be returned
     * @return Variation-Object, if tupel could be found. Otherwise return <i>null</i>
     */
    public static Variation getVariationById(int variationId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + VARIATION + " WHERE " + VARIATION_ID + "=? LIMIT 1", new Object[]{ variationId });
        ArrayList<Variation> variationArrayList = convertResultSetToVariationArrayList(resultSet);
        if(variationArrayList != null && !variationArrayList.isEmpty())
            return variationArrayList.get(0);
        else
            return null;
    }

    /**
     * Gets all variations from <i>variation</i> table by the given <i>musicRoomId</i>-param
     * @param musicRoomId the id of the music room from which the variations are to be returned
     * @return ArrayList of Variation-Objects, if tupel could be found. Otherwise return <i>null</i>
     */
    public static ArrayList<Variation> getVariationsByMusicRoomId(int musicRoomId) {
        ResultSet resultSet = executePreparedSelect("SELECT " + VARIATION + "." + VARIATION_ID + ", " + VARIATION + "." + MUSIC_SEGMENT_ID + ", "
                + VARIATION + "." + NAME + ", " + VARIATION + "." + OWNER + ", " + VARIATION + "." + START_TIME + ", " + VARIATION + "." + END_TIME
                + " FROM " + VARIATION
                + " INNER JOIN " + MUSIC_SEGMENT + " USING (" + MUSIC_SEGMENT_ID + ")"
                + " INNER JOIN " + MUSIC_ROOM + " USING (" + MUSIC_ROOM_ID + ")"
                + " WHERE " + MUSIC_ROOM + "." + MUSIC_ROOM_ID + " = ?", new Object[] {musicRoomId});
        return convertResultSetToVariationArrayList(resultSet);
    }

    /**
     * Gets one tupel from <i>variationTrack</i> table by the given <i>variationTrackId</i>-param
     * @param variationTrackId the id of the variationTrack to be returned
     * @return VariationTrack-Object, if tupel could be found. Otherwise return <i>null</i>
     */
    public static VariationTrack getVariationTrackById(int variationTrackId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + VARIATION_TRACK + " WHERE " + VARIATION_TRACK_ID + "=? LIMIT 1", new Object[]{ variationTrackId });
        ArrayList<VariationTrack> variationTrackArrayList = convertResultSetToVariationTrackArrayList(resultSet);
        if(variationTrackArrayList != null && !variationTrackArrayList.isEmpty())
            return variationTrackArrayList.get(0);
        else
            return null;
    }

    /**
     * Gets all variationTracks from <i>variationTrack</i> table by the given <i>trackId</i>-param
     * @param trackId the id of the track from which the variationTracks are to be returned
     * @return ArrayList of VariationTrack-Objects, if tupel could be found. Otherwise return <i>null</i>
     */
    public static ArrayList<VariationTrack> getVariationTracksByTrackId(int trackId){
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + VARIATION_TRACK + " WHERE " + TRACK_ID + "=?", new Object[]{trackId});
        return convertResultSetToVariationTrackArrayList(resultSet);
    }

    /**
     * Gets all variationTracks from <i>variationTrack</i> table by the given <i>variationId</i>-param
     * @param variationId the id of the variation from which the variationTracks are to be returned
     * @return ArrayList of VariationTrack-Objects, if tupel could be found. Otherwise return <i>null</i>
     */
    public static ArrayList<VariationTrack> getVariationTracksByVariationId(int variationId){
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + VARIATION_TRACK + " WHERE " + VARIATION_ID + "=?", new Object[]{variationId});
        return convertResultSetToVariationTrackArrayList(resultSet);
    }

    /**
     * Gets all variationTracks from <i>variationTrack</i> table by the given <i>musicRoomId</i>-param
     * @param musicRoomId the id of the music room which the variationTracks are to be returned
     * @return ArrayList of VariationTrack-Objects, if tupel could be found. Otherwise return <i>null</i>
     */
    public static ArrayList<VariationTrack> getVariationTracksByMusicRoomId(int musicRoomId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM "+ VARIATION_TRACK
                + " INNER JOIN " + TRACK + " USING (" + TRACK_ID + ")"
                + " INNER JOIN " + WORKING_AREA + " USING (" + WORKING_AREA_ID + ")"
                + " WHERE " + WORKING_AREA + "." + MUSIC_ROOM_ID + " = ?", new Object[] {musicRoomId});
        return convertResultSetToVariationTrackArrayList(resultSet);
    }

    /**
     * Gets the complete data of a music room by the specific id
     * @param musicRoomId the id of the music room from which the data to be returned
     * @return MusicRoomDataContainer-Object with the entire data
     */
    public static MusicRoomDataContainer getCompleteMusicRoomDataByMusicRoomId(int musicRoomId) {
        MusicRoom musicRoom = getMusicRoomById(musicRoomId);
        return getAllDataFromMusicRoom(musicRoom);
    }

    /**
     * Gets the complete data of a music room by the specific name
     * @param musicRoomName the name of the music room from which the data to be returned
     * @return MusicRoomDataContainer-Object with the entire data
     */
    public static MusicRoomDataContainer getCompleteMusicRoomDataByMusicRoomName(String musicRoomName) {
        MusicRoom musicRoom = getMusicRoomByName(musicRoomName);
        return getAllDataFromMusicRoom(musicRoom);
    }

    /**
     * Insert any database-entity-object into the mysql-database
     * @param obj the object which has to be inserted
     * @return <i>true</i>, if success. Otherwise <i>false</i>
     */
    public static boolean insertGlobalObject(GlobalObject obj) {
        switch (obj.getEntityClass()) {
            case MUSIC_ROOM_CLASS:
                MusicRoom musicRoom = (MusicRoom)obj;
                return insertMusicRoom(musicRoom);
            case WORKING_AREA_CLASS:
                WorkingArea workingArea = (WorkingArea)obj;
                return insertWorkingArea(workingArea);
            case TRACK_CLASS:
                Track track = (Track)obj;
                return insertTrack(track);
            case MUSIC_SEGMENT_CLASS:
                MusicSegment musicSegment = (MusicSegment)obj;
                return insertMusicSegment(musicSegment);
            case VARIATION_CLASS:
                Variation variation = (Variation)obj;
                return insertVariation(variation);
            case VARIATION_TRACK_CLASS:
                VariationTrack variationTrack = (VariationTrack)obj;
                return insertVariationTrack(variationTrack);
        }
        return false;
    }

    /**
     * Update data of any database-entity-object in the mysql-database
     * @param obj the object which has to be updated
     * @return <i>true</i>, if success. Otherwise <i>false</i>
     */
    public static boolean updateGlobalObject(GlobalObject obj) {
        switch (obj.getEntityClass()) {
            case MUSIC_ROOM_CLASS:
                MusicRoom musicRoom = (MusicRoom)obj;
                return updateMusicRoom(musicRoom);
            case WORKING_AREA_CLASS:
                WorkingArea workingArea = (WorkingArea)obj;
                return updateWorkingArea(workingArea);
            case TRACK_CLASS:
                Track track = (Track)obj;
                return updateTrack(track);
            case MUSIC_SEGMENT_CLASS:
                MusicSegment musicSegment = (MusicSegment)obj;
                return updateMusicSegment(musicSegment);
            case VARIATION_CLASS:
                Variation variation = (Variation)obj;
                return updateVariation(variation);
            case VARIATION_TRACK_CLASS:
                VariationTrack variationTrack = (VariationTrack)obj;
                return updateVariationTrack(variationTrack);
        }
        return false;
    }

    /**
     * Delete any database-entity-object in mysql-database
     * @param obj the object which has to be deleted
     * @return <i>true</i>, if success. Otherwise <i>false</i>
     */
    public static boolean deleteGlobalObject(GlobalObject obj) {
        switch (obj.getEntityClass()) {
            case TRACK_CLASS:
                Track track = (Track)obj;
                return deleteTrackById(track.getId());
            case MUSIC_SEGMENT_CLASS:
                MusicSegment musicSegment = (MusicSegment)obj;
                return deleteMusicSegmentById(musicSegment.getId());
            case VARIATION_CLASS:
                Variation variation = (Variation)obj;
                return deleteVariationById(variation.getId());
            case VARIATION_TRACK_CLASS:
                VariationTrack variationTrack = (VariationTrack)obj;
                return deleteVariationTrackById(variationTrack.getId());
        }
        return false;
    }

    /**
     * Inserts a new MusicRoom-Object into the database
     * @param musicRoom the id-attribute is not needed, because it's auto increment.
     *                  So the id attribute of the musicRoom-Object can have any value.
     * @return true, if data saved successfully
     */
    public static boolean insertMusicRoom(MusicRoom musicRoom) {
        if(musicRoom.isValid())
            return executePreparedUpdate("INSERT INTO " + MUSIC_ROOM + " VALUES(default, ?)", new Object[]{musicRoom.getName()});
        return false;
    }

    /**
     * Inserts a new WorkingArea-Object into the database
     * @param workingArea the id-attribute is not needed, because it's auto increment.
     *                  So the id attribute of the WorkingArea-Object can have any value.
     * @return true, if data saved successfully
     */
    public static boolean insertWorkingArea(WorkingArea workingArea) {
        if(workingArea.isValid())
            return executePreparedUpdate("INSERT INTO " + WORKING_AREA + " VALUES(default, ?, ?, ?, ?, ?, ?, ?)",
                    new Object[]{workingArea.getMusicRoom().getId(), workingArea.getName(), workingArea.getTempo(),
                            workingArea.getOwner(), workingArea.getWorkingAreaType().toString(), workingArea.getBeat(), workingArea.getLength()});
        return false;
    }

    /**
     * Inserts a new Track-Object into the database
     * @param track the id-attribute is not needed, because it's auto increment.
     *                  So the id attribute of the Track-Object can have any value.
     * @return true, if data saved successfully
     */
    public static boolean insertTrack(Track track) {
        if(track.isValid())
            return executePreparedUpdate("INSERT INTO " + TRACK + " VALUES(default, ?, ?, ?, ?, ?)",
                    new Object[]{track.getWorkingArea().getId(), track.getInstrument().toString(), track.getVolume(),
                            track.getName(), track.getLength()});
        return false;
    }

    /**
     * Inserts a new Variation-Object into the database
     * @param variation the id-attribute is not needed, because it's auto increment.
     *                  So the id attribute of the Variation-Object can have any value.
     * @return true, if data saved successfully
     */
    public static boolean insertVariation(Variation variation) {
        if(variation.isValid())
            return executePreparedUpdate("INSERT INTO " + VARIATION + " VALUES(default, ?, ?, ?, ?, ?)",
                    new Object[]{variation.getMusicSegment().getId(), variation.getName(), variation.getStartTime(),
                            variation.getEndTime(), variation.getOwner()});
        return false;
    }

    /**
     * Inserts a new VariationTrack-Object into the database
     * @param variationTrack the id-attribute is not needed, because it's auto increment.
     *                  So the id attribute of the VariationTrack-Object can have any value.
     * @return true, if data saved successfully
     */
    public static boolean insertVariationTrack(VariationTrack variationTrack) {
        if(variationTrack.isValid())
            return executePreparedUpdate("INSERT INTO " + VARIATION_TRACK + " VALUES(default, ?, ?, ?)",
                    new Object[]{variationTrack.getVariation().getId(), variationTrack.getTrack().getId(), variationTrack.getStartTimeOnTrack()});
        return false;
    }

    /**
     * Inserts a new MusicSegment-Object into the database
     * @param musicSegment the id-attribute is not needed, because it's auto increment.
     *                  So the id attribute of the MusicSegment-Object can have any value.
     * @return true, if data saved successfully
     */
    public static boolean insertMusicSegment(MusicSegment musicSegment) {
        if(musicSegment.isValid())
            return executePreparedUpdate("INSERT INTO " + MUSIC_SEGMENT + " VALUES(default, ?, ?, ?, ?, ?, ?)",
                    new Object[]{musicSegment.getMusicRoom().getId(), musicSegment.getName(), musicSegment.getInstrument().toString(), musicSegment.getOwner(),
                            musicSegment.getAudioPath(), musicSegment.getLength()});
        return false;
    }

    /**
     * Inserts a new MusicRoom-Object into the database
     * @param musicRoom the id-attribute is not needed, because it's auto increment.
     *                  So the id attribute of the MusicRoom-Object can have any value.
     * @return true, if data saved successfully
     */
    public static boolean updateMusicRoom(MusicRoom musicRoom) {
        if(musicRoom == null)
            return false;

        if(musicRoom.isValid())
            return executePreparedUpdate("UPDATE " + MUSIC_ROOM + " SET " + NAME + " = ? " + "WHERE " + MUSIC_ROOM_ID + " = ?", new Object[]{musicRoom.getName(), musicRoom.getId()});
        return false;
    }

    /**
     * Updates a WorkingArea-Tupel in the database
     * @param workingArea WorkingArea-Object which has to be updated
     * @return true, if data saved successfully
     */
    public static boolean updateWorkingArea(WorkingArea workingArea) {
        if(workingArea == null)
            return false;

        if(workingArea.isValid())
            return executePreparedUpdate("UPDATE " + WORKING_AREA + " SET " + MUSIC_ROOM_ID + "=?, " + NAME + "=?, "
                    + TEMPO + "=?, " + OWNER + "=?, " + TYPE + "=?, " + BEAT + "=?, " + LENGTH + "=? " + "WHERE " + WORKING_AREA_ID + " = ?",
                    new Object[]{workingArea.getMusicRoom().getId(), workingArea.getName(), workingArea.getTempo(), workingArea.getOwner(), workingArea.getWorkingAreaType().toString(),
                    workingArea.getBeat(), workingArea.getLength(), workingArea.getId()});
        return false;
    }

    /**
     * Updates a Track-Tupel in the database
     * @param track Track-Object which has to be updated
     * @return true, if data saved successfully
     */
    public static boolean updateTrack(Track track) {
        if(track == null)
            return false;

        if(track.isValid())
            return executePreparedUpdate("UPDATE " + TRACK + " SET " + WORKING_AREA_ID + "=?, " + INSTRUMENT + "=?, "
                    + VOLUME + "=?, " + NAME + "=?, " + LENGTH + "=? " + "WHERE " + TRACK_ID + " = ?",
                    new Object[]{track.getWorkingArea().getId(), track.getInstrument().toString(), track.getVolume(),
                            track.getName(), track.getLength(), track.getId()});
        return false;
    }

    /**
     * Updates a Variation-Tupel in the database
     * @param variation Variation-Object which has to be updated
     * @return true, if data saved successfully
     */
    public static boolean updateVariation(Variation variation) {
        if(variation == null)
            return false;

        if(variation.isValid())
            return executePreparedUpdate("UPDATE " + VARIATION + " SET " + MUSIC_SEGMENT_ID + "=?, " + NAME + "=?, "
                    + START_TIME + "=?, " + END_TIME + "=?, " + OWNER+ "=? " + "WHERE " + VARIATION_ID + " = ?",
                    new Object[]{variation.getMusicSegment().getId(), variation.getName(), variation.getStartTime(),
                            variation.getEndTime(), variation.getOwner(), variation.getId()});
        return false;
    }

    /**
     * Updates a VariationTrack-Tupel in the database
     * @param variationTrack VariationTrack-Object which has to be updated
     * @return true, if data saved successfully
     */
    public static boolean updateVariationTrack(VariationTrack variationTrack) {
        if(variationTrack == null)
            return false;

        if(variationTrack.isValid())
            return executePreparedUpdate("UPDATE " + VARIATION_TRACK + " SET " + VARIATION_ID + "=?, " + TRACK_ID + "=?, "
                    + START_TIME + "=? " + "WHERE " + VARIATION_TRACK_ID + " = ?",
                    new Object[]{variationTrack.getVariation().getId(), variationTrack.getTrack().getId(),
                            variationTrack.getStartTimeOnTrack(), variationTrack.getId()});
        return false;
    }

    /**
     * Updates a MusicSegment-Tupel in the database
     * @param musicSegment MusicSegment-Object which has to be updated
     * @return true, if data saved successfully
     */
    public static boolean updateMusicSegment(MusicSegment musicSegment) {
        if(musicSegment == null)
            return false;

        if(musicSegment.isValid())
            return executePreparedUpdate("UPDATE " + MUSIC_SEGMENT + " SET " + MUSIC_ROOM_ID + "=?, " + NAME + "=?, " + INSTRUMENT + "=?, "
                    + OWNER + "=?, " + AUDIO_PATH + "=?, " + LENGTH + "=? " + "WHERE " + MUSIC_SEGMENT_ID + " = ?",
                    new Object[]{musicSegment.getMusicRoom().getId(), musicSegment.getName(), musicSegment.getInstrument().toString(), musicSegment.getOwner(),
                            musicSegment.getAudioPath(), musicSegment.getLength(), musicSegment.getId()});
        return false;
    }

    /**
     * Deletes a Track-Tupel in the database
     * @param trackId the id of the track to be deleted
     * @return true, if data saved successfully
     */
    public static boolean deleteTrackById(int trackId) {
        return executePreparedUpdate("DELETE FROM " + TRACK + " WHERE " + TRACK_ID + "=?", new Object[]{trackId});
    }

    /**
     * Deletes a Variation-Tupel in the database
     * @param variationId the id of the variation to be deleted
     * @return true, if data saved successfully
     */
    public static boolean deleteVariationById(int variationId) {
        return executePreparedUpdate("DELETE FROM " + VARIATION + " WHERE " + VARIATION_ID + "=?", new Object[]{variationId});
    }

    /**
     * Deletes a VariationTrack-Tupel in the database
     * @param variationTrackId the id of the variationTrack to be deleted
     * @return true, if data saved successfully
     */
    public static boolean deleteVariationTrackById(int variationTrackId) {
        return executePreparedUpdate("DELETE FROM " + VARIATION_TRACK + " WHERE " + VARIATION_TRACK_ID + "=?", new Object[]{variationTrackId});
    }

    /**
     * Deletes a MusicSegment-Tupel in the database
     * @param musicSegmentId the id of the music Segment to be deleted
     * @return true, if data saved successfully
     */
    public static boolean deleteMusicSegmentById(int musicSegmentId) {
        return executePreparedUpdate("DELETE FROM " + MUSIC_SEGMENT + " WHERE " + MUSIC_SEGMENT_ID + "=?", new Object[]{musicSegmentId});
    }

    /**
     * Executes a given SQL-Update Statement
     * @param preparedUpdateString Update-Statement that has to be executed. Can be INSER, UPDATE or DELETE
     * @param values values to be insert
     * @return true, if succeed
     */
    private static boolean executePreparedUpdate(String preparedUpdateString, Object[] values) {
        if(preparedUpdateString == null || preparedUpdateString.isEmpty())
            return false;

        try {
            preparedStatement = connect.prepareStatement(preparedUpdateString);
            for(int i=0; i<values.length; i++){
                preparedStatement.setObject(i+1, values[i]);//, getSQLTypeIntFromJavaObject(values[i]));
            }
            int updateCount = preparedStatement.executeUpdate();

            if(updateCount>0)
                return true;

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Executes a given SQL-SELECT Statement
     * @param preparedSelectString SELECT-statement that has to be executed.
     * @param values values of the SELECT-statement
     * @return true, if succeed
     */
    private static ResultSet executePreparedSelect(String preparedSelectString, Object[] values) {
        if(preparedSelectString == null || preparedSelectString.isEmpty())
            return null;

        try {
            preparedStatement = connect.prepareStatement(preparedSelectString);
            if(values !=null) {
                preparedStatement.clearParameters();
                for(int i=0; i<values.length; i++){
                    preparedStatement.setObject(i+1, values[i]);//, getSQLTypeIntFromJavaObject(values[i]));
                }
            }
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ArrayList<MusicRoom> convertResultSetToMusicRoomArrayList(ResultSet resultSet){
        try {
            if(resultSet == null || !resultSet.next())
                return null;

            ArrayList<MusicRoom> musicRoomArrayList = new ArrayList<>();
            resultSet.beforeFirst();

            while(resultSet.next()) {
                MusicRoom musicRoom = new MusicRoom();
                musicRoom.setId(resultSet.getInt(MUSIC_ROOM_ID));
                musicRoom.setName(resultSet.getString(NAME));
                musicRoomArrayList.add(musicRoom);
            }
            resultSet.close();
            closePreparedStatement();
            return musicRoomArrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static ArrayList<WorkingArea> convertResultSetToWorkingAreaArrayList(ResultSet resultSet) {
        try {
            if(resultSet == null || !resultSet.next())
                return null;

            ArrayList<WorkingArea> workingAreaArrayList = new ArrayList<>();
            resultSet.beforeFirst();

            while (resultSet.next()) {
                WorkingArea workingArea = new WorkingArea();
                workingArea.setId(resultSet.getInt(WORKING_AREA_ID));
                workingArea.setName(resultSet.getString(NAME));
                workingArea.setBeat(resultSet.getFloat(BEAT));
                workingArea.setTempo(resultSet.getInt(TEMPO));
                workingArea.setOwner(resultSet.getString(OWNER));
                workingArea.setWorkingAreaType(WorkingAreaType.fromString(resultSet.getString(TYPE)));
                workingArea.setMusicRoom((getMusicRoomById(resultSet.getInt(MUSIC_ROOM_ID))));
                workingArea.setLength(resultSet.getLong(LENGTH));
                workingAreaArrayList.add(workingArea);
            }
            resultSet.close();
            closePreparedStatement();
            return workingAreaArrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static ArrayList<Track> convertResultSetToTrackArrayList(ResultSet resultSet) {
        try {
            if(resultSet == null || !resultSet.next())
                return null;

           ArrayList<Track> trackArrayList = new ArrayList<>();
            resultSet.beforeFirst();

            while(resultSet.next()) {
                Track track = new Track();
                track.setId(resultSet.getInt(TRACK_ID));
                track.setName(resultSet.getString(NAME));
                track.setVolume(resultSet.getInt(VOLUME));
                track.setInstrument(Instrument.fromString(resultSet.getString(INSTRUMENT)));
                track.setWorkingArea((getWorkingAreaById(resultSet.getInt(WORKING_AREA_ID))));
                track.setLength(resultSet.getLong(LENGTH));
                trackArrayList.add(track);
            }
            resultSet.close();
            closePreparedStatement();
            return trackArrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static ArrayList<MusicSegment> convertResultSetToMusicSegmentArrayList(ResultSet resultSet) {
        try {
            if(resultSet == null || !resultSet.next())
                return null;

            ArrayList<MusicSegment> musicSegmentArrayList = new ArrayList<>();
            resultSet.beforeFirst();

            while(resultSet.next()) {
                MusicSegment musicSegment = new MusicSegment();
                musicSegment.setId(resultSet.getInt(MUSIC_SEGMENT_ID));
                musicSegment.setName(resultSet.getString(NAME));
                musicSegment.setAudioPath(resultSet.getString(AUDIO_PATH));
                musicSegment.setInstrument(Instrument.fromString(resultSet.getString(INSTRUMENT)));
                musicSegment.setOwner(resultSet.getString(OWNER));
                musicSegment.setLength(resultSet.getLong(LENGTH));
                musicSegment.setMusicRoom(getMusicRoomById(resultSet.getInt(MUSIC_ROOM_ID)));

                musicSegmentArrayList.add(musicSegment);
            }
            resultSet.close();
            closePreparedStatement();
            return musicSegmentArrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static ArrayList<Variation> convertResultSetToVariationArrayList(ResultSet resultSet) {
        try {
            if(resultSet == null || !resultSet.next())
                return null;

            ArrayList<Variation> variationArrayList = new ArrayList<>();
            resultSet.beforeFirst();

            while(resultSet.next()) {
                Variation variation = new Variation();
                variation.setId(resultSet.getInt(VARIATION_ID));
                variation.setName(resultSet.getString(NAME));
                variation.setOwner(resultSet.getString(OWNER));
                variation.setStartTime(resultSet.getLong(START_TIME));
                variation.setEndTime(resultSet.getLong(END_TIME));
                variation.setMusicSegment((getMusicSegmentById(resultSet.getInt(MUSIC_SEGMENT_ID))));
                variationArrayList.add(variation);
            }

            resultSet.close();
            closePreparedStatement();
            return variationArrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static  ArrayList<VariationTrack> convertResultSetToVariationTrackArrayList(ResultSet resultSet) {
        try {
            if(resultSet == null || !resultSet.next())
                return null;

            ArrayList<VariationTrack> variationTrackArrayList = new ArrayList<>();
            resultSet.beforeFirst();

            while(resultSet.next()) {
                VariationTrack variationTrack = new VariationTrack();
                variationTrack.setId(resultSet.getInt(VARIATION_TRACK_ID));
                variationTrack.setStartTimeOnTrack(resultSet.getLong(START_TIME));
                variationTrack.setVariation((getVariationById(resultSet.getInt(VARIATION_ID))));
                variationTrack.setTrack((getTrackById(resultSet.getInt(TRACK_ID))));
                variationTrackArrayList.add(variationTrack);
            }
            resultSet.close();
            closePreparedStatement();
            return variationTrackArrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static MusicRoomDataContainer getAllDataFromMusicRoom(MusicRoom musicRoom) {
        MusicRoomDataContainer dataContainer = new MusicRoomDataContainer();
        if(musicRoom != null) {
            dataContainer.setMusicRoom(musicRoom);
            dataContainer.setWorkingAreas(getWorkingAreasByMusicRoomId(musicRoom.getId()));
            dataContainer.setMusicSegments(getMusicSegmentsByMusicRoomId(musicRoom.getId()));
            dataContainer.setVariations(getVariationsByMusicRoomId(musicRoom.getId()));
            dataContainer.setTracks(getTracksByMusicRoomId(musicRoom.getId()));
            dataContainer.setVariationTracks(getVariationTracksByMusicRoomId(musicRoom.getId()));
            return dataContainer;
        }
        return null;
    }

    private static void closePreparedStatement(){
        try {
            if(preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
