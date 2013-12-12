package com.musicbox.util.database;

import java.sql.*;
import java.util.ArrayList;

import com.musicbox.util.WorkingAreaType;
import com.musicbox.util.Instrument;
import com.musicbox.util.database.entities.*;

/**
 * Created with IntelliJ IDEA.
 * User: David Wachs
 * Date: 07.12.13
 * Time: 16:09
 *
 * This class/module represents the DatabaseMangaer, witch is used to connect to
 * MusicBox-MySQL-Database and insert, read, update or delete data.
 */
public abstract class DatabaseManager {
    private static Connection connect = null;
    private static String connectionString = null;
    private static PreparedStatement preparedStatement = null;

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
    private static final String ID = "id";
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

    /**
     * Create connection based on Connections-String in conn-param.
     * @param conn represents the Connection-String to the MySQL-Database
     *             Format: "jdbc:mysql://[host]/[database]?user=[username]&password=[password]"
     * @return true, if connection succeed
     */
    public static boolean setConnection(String conn){
        try {
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

    public static ArrayList<MusicRoom> getMusicRoomById(int musicRoomId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + MUSIC_ROOM + " WHERE " + ID + " = ?", new Object[]{ musicRoomId });
        return convertResultSetToMusicRoomArrayList(resultSet);
    }

    public static ArrayList<MusicRoom> getMusicRoomByName(String musicRoomName) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + MUSIC_ROOM + " WHERE " + NAME + "=?", new Object[]{ musicRoomName });
        return convertResultSetToMusicRoomArrayList(resultSet);
    }

    public static ArrayList<WorkingArea> getWorkingAreaById(int workingAreaId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + WORKING_AREA + " WHERE " + ID + "=?", new Object[]{ workingAreaId });
        return convertResultSetToWorkingAreaArrayList(resultSet);
    }

    public static ArrayList<WorkingArea> getWorkingAreasByMusicRoomId(int musicRoomId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + WORKING_AREA + " WHERE " + MUSIC_ROOM_ID + "=?", new Object[]{ musicRoomId });
        return convertResultSetToWorkingAreaArrayList(resultSet);
    }

    public static ArrayList<Track> getTrackById(int trackId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + TRACK + " WHERE " + ID + "=?", new Object[]{ trackId });
        return convertResultSetToTrackArrayList(resultSet);
    }

    public static ArrayList<Track> getTracksByWorkingAreaId(int workingAreaId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + TRACK + " WHERE " + WORKING_AREA_ID + "=?", new Object[]{ workingAreaId });
        return convertResultSetToTrackArrayList(resultSet);
    }

    public static ArrayList<MusicSegment> getMusicSegmentById(int musicSegmentId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + MUSIC_SEGMENT + " WHERE " + ID + "=?", new Object[]{ musicSegmentId });
        return convertResultSetToMusicSegmentArrayList(resultSet);
    }

    public static ArrayList<Variation> getVariationById(int variationId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + VARIATION + " WHERE " + ID + "=?", new Object[]{ variationId });
        return convertResultSetToVariationArrayList(resultSet);
    }

    public static ArrayList<VariationTrack> getVariationTrackById(int variationTrackId) {
        ResultSet resultSet = executePreparedSelect("SELECT * FROM " + VARIATION_TRACK + " WHERE " + ID + "=?", new Object[]{ variationTrackId });
        return convertResultSetToVariationTrackArrayList(resultSet);
    }

    /**
     * Inserts a new MusicRoom-Object to the database
     * @param musicRoom the id-attribute is not needed, because it's auto increment.
     *                  So the id attribute of the musicRoom-Object can have any value.
     * @return true, if data saved successfully
     */
    public static boolean insertMusicRoom(MusicRoom musicRoom) {
        if(musicRoom.isValid())
            return executePreparedUpdate("INSERT INTO " + MUSIC_ROOM + " VALUES(default, ?)", new Object[]{musicRoom.getName()});
        return false;
    }

    public static boolean insertWorkingArea(WorkingArea workingArea) {
        if(workingArea.isValid())
            return executePreparedUpdate("INSERT INTO " + WORKING_AREA + " VALUES(default, ?, ?, ?, ?, ?, ?, ?)",
                    new Object[]{workingArea.getMusicRoom().getId(), workingArea.getName(), workingArea.getTempo(),
                            workingArea.getOwner(), workingArea.getWorkingAreaType().toString(), workingArea.getBeat(), workingArea.getLength()});
        return false;
    }

    public static boolean insertTrack(Track track) {
        if(track.isValid())
            return executePreparedUpdate("INSERT INTO " + TRACK + " VALUES(default, ?, ?, ?, ?, ?)",
                    new Object[]{track.getWorkingArea().getId(), track.getInstrument().toString(), track.getVolume(),
                            track.getName(), track.getLength()});
        return false;
    }

    public static boolean insertVariation(Variation variation) {
        if(variation.isValid())
            return executePreparedUpdate("INSERT INTO " + VARIATION + " VALUES(default, ?, ?, ?, ?, ?)",
                    new Object[]{variation.getMusicSegment().getId(), variation.getName(), variation.getStartTime(),
                            variation.getEndTime(), variation.getOwner()});
        return false;
    }

    public static boolean insertVariationTrack(VariationTrack variationTrack) {
        if(variationTrack.isValid())
            return executePreparedUpdate("INSERT INTO " + VARIATION_TRACK + " VALUES(default, ?, ?, ?)",
                    new Object[]{variationTrack.getVariation().getId(), variationTrack.getTrack().getId(), variationTrack.getStartTimeOnTrack()});
        return false;
    }

    public static boolean insertMusicSegment(MusicSegment musicSegment) {
        if(musicSegment.isValid())
            return executePreparedUpdate("INSERT INTO " + MUSIC_SEGMENT + " VALUES(default, ?, ?, ?, ?, ?)",
                    new Object[]{musicSegment.getName(), musicSegment.getInstrument().toString(), musicSegment.getOwner(),
                            musicSegment.getAudioPath(), musicSegment.getLength()});
        return false;
    }

    public static boolean updateMusicRoom(MusicRoom musicRoom) {
        if(musicRoom.isValid())
            return executePreparedUpdate("UPDATE " + MUSIC_ROOM + " SET " + NAME + " = ? " + "WHERE " + ID + " = ?", new Object[]{musicRoom.getName(), musicRoom.getId()});
        return false;
    }


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

    private static ResultSet executePreparedSelect(String preparedSelectString, Object[] values) {
        if(preparedSelectString == null || preparedSelectString.isEmpty())
            return null;

        try {
            preparedStatement = connect.prepareStatement(preparedSelectString);
            preparedStatement.clearParameters();
            for(int i=0; i<values.length; i++){
                preparedStatement.setObject(i+1, values[i]);//, getSQLTypeIntFromJavaObject(values[i]));
            }
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int getSQLTypeIntFromJavaObject(Object value) {
        if(value instanceof Integer)
            return Types.INTEGER;
        if(value instanceof String)
            return Types.VARCHAR;
        if(value instanceof Long)
            return Types.BIGINT;
        if(value instanceof Float)
            return Types.FLOAT;

        return -1;
    }

    private static ArrayList<MusicRoom> convertResultSetToMusicRoomArrayList(ResultSet resultSet){
        try {
            if(resultSet == null || !resultSet.next())
                return null;

            ArrayList<MusicRoom> musicRoomArrayList = new ArrayList<>();
            resultSet.beforeFirst();

            while(resultSet.next()) {
                MusicRoom musicRoom = new MusicRoom();
                musicRoom.setId(resultSet.getInt(ID));
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
            if(resultSet == null || resultSet.getFetchSize() == 0)
                return null;

            ArrayList<WorkingArea> workingAreaArrayList = new ArrayList<>();

            while (resultSet.next()) {
                WorkingArea workingArea = new WorkingArea();
                workingArea.setId(resultSet.getInt(ID));
                workingArea.setName(resultSet.getString(NAME));
                workingArea.setBeat(resultSet.getFloat(BEAT));
                workingArea.setTempo(resultSet.getInt(TEMPO));
                workingArea.setOwner(resultSet.getString(OWNER));
                workingArea.setWorkingAreaType(Enum.valueOf(WorkingAreaType.class,resultSet.getString(TYPE)));
                workingArea.setMusicRoom((getMusicRoomById(resultSet.findColumn(MUSIC_ROOM_ID)).get(0)));
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
            if(resultSet == null || resultSet.getFetchSize() == 0)
                return null;

           ArrayList<Track> trackArrayList = new ArrayList<>();

            while(resultSet.next()) {
                Track track = new Track();
                track.setId(resultSet.getInt(ID));
                track.setName(resultSet.getString(NAME));
                track.setVolume(resultSet.getInt(VOLUME));
                track.setInstrument(Enum.valueOf(Instrument.class, resultSet.getString(INSTRUMENT)));
                track.setWorkingArea((getWorkingAreaById(resultSet.findColumn(WORKING_AREA_ID)).get(0)));
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
            if(resultSet == null || resultSet.getFetchSize() == 0)
                return null;

            ArrayList<MusicSegment> musicSegmentArrayList = new ArrayList<>();

            while(resultSet.next()) {
                MusicSegment musicSegment = new MusicSegment();
                musicSegment.setId(resultSet.getInt(ID));
                musicSegment.setName(resultSet.getString(NAME));
                musicSegment.setAudioPath(resultSet.getString(AUDIO_PATH));
                musicSegment.setInstrument(Enum.valueOf(Instrument.class,resultSet.getString(INSTRUMENT)));
                musicSegment.setOwner(resultSet.getString(OWNER));
                musicSegment.setLength(resultSet.getLong(LENGTH));

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
            if(resultSet == null || resultSet.getFetchSize() == 0)
                return null;

            ArrayList<Variation> variationArrayList = new ArrayList<>();

            while(resultSet.next()) {
                Variation variation = new Variation();
                variation.setId(resultSet.getInt(ID));
                variation.setName(resultSet.getString(NAME));
                variation.setOwner(resultSet.getString(OWNER));
                variation.setStartTime(resultSet.getLong(START_TIME));
                variation.setEndTime(resultSet.getLong(END_TIME));
                variation.setMusicSegment((getMusicSegmentById(resultSet.getInt(MUSIC_SEGMENT_ID)).get(0)));
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
            if(resultSet == null || resultSet.getFetchSize() == 0)
                return null;

            ArrayList<VariationTrack> variationTrackArrayList = new ArrayList<>();

            while(resultSet.next()) {
                VariationTrack variationTrack = new VariationTrack();
                variationTrack.setId(resultSet.getInt(ID));
                variationTrack.setStartTimeOnTrack(resultSet.getLong(START_TIME));
                variationTrack.setVariation((getVariationById(resultSet.getInt(VARIATION_ID)).get(0)));
                variationTrack.setTrack((getTrackById(resultSet.getInt(TRACK_ID)).get(0)));
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

    private static void closePreparedStatement(){
        try {
            if(preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
