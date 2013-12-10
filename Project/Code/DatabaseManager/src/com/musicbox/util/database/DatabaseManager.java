package com.musicbox.util.database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import com.musicbox.util.WorkingAreaType;
import com.musicbox.util.Instrument;
import com.musicbox.util.database.entities.*;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 07.12.13
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */
public abstract class DatabaseManager {
    private static Connection connect = null;
    private static String connectionString = null;

    /*
    Constants represents the database tables and columns.
    Are needed to assemble the MySQL queries.
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

    public static MusicRoom getMusicRoomById(int id) {
        ResultSet resultSet = executeSelect(null, MUSIC_ROOM, ID + "=" + id, null);
        return convertResultSetToMusicRoom(resultSet);
    }

    public static MusicRoom getMusicRoomByName(String name) {
        ResultSet resultSet = executeSelect(null, MUSIC_ROOM, NAME + "= '" + name + "'", null);
        return convertResultSetToMusicRoom(resultSet);
    }

    public static WorkingArea getWorkingAreaById(int id) {
        ResultSet resultSet = executeSelect(null, WORKING_AREA, ID + "=" + id, null);
        return convertResultSetToWorkingArea(resultSet);
    }

    public static WorkingArea getWorkingAreaByMusicRoomId(int id) {
        ResultSet resultSet = executeSelect(null, WORKING_AREA, MUSIC_ROOM_ID + "=" + id, null);
        return convertResultSetToWorkingArea(resultSet);
    }

    public static Track getTrackById(int id) {
        ResultSet resultSet = executeSelect(null, TRACK, ID + "=" + id, null);
        return convertResultSetToTrack(resultSet);
    }

    public static MusicSegment getMusicSegmentById(int id) {
        ResultSet resultSet = executeSelect(null, MUSIC_SEGMENT, ID + "=" + id, null);
        return convertResultSetToMusicSegment(resultSet);
    }

    public static Variation getVariationById(int id) {
        ResultSet resultSet = executeSelect(null, VARIATION, ID + "=" + id, null);
        return convertResultSetToVariation(resultSet);
    }

    public static VariationTrack getVariationTrackById(int id) {
        ResultSet resultSet = executeSelect(null, VARIATION_TRACK, ID + "=" + id, null);
        return convertResultSetToVariationTrack(resultSet);
    }

    /**
     * Inserts a new MusicRoom-Object to the database
     * @param musicRoom the id-attribute is not needed, because it's auto increment.
     *                  So the id attribute of the musicRoom-Object can have any value.
     * @return true, if data saved successfully
     */
    public static boolean insertMusicRoom(MusicRoom musicRoom) {
        try {
            if(!musicRoom.isValid())
                return false;

            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO " + MUSIC_ROOM + " VALUES(default, ?)");
            preparedStatement.setString(1, musicRoom.getName());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertWorkingArea(WorkingArea workingArea) {
        try {
            if(!workingArea.isValid())
                return false;

            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO " + WORKING_AREA + " VALUES(default, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, workingArea.getMusicRoom().getId());
            preparedStatement.setString(2, workingArea.getName());
            preparedStatement.setInt(3, workingArea.getTempo());
            preparedStatement.setString(4, workingArea.getOwner());
            preparedStatement.setString(5, workingArea.getWorkingAreaType().toString());
            preparedStatement.setFloat(6, workingArea.getBeat());
            preparedStatement.setLong(7, workingArea.getLength());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertTrack(Track track) {
        try {
            if(!track.isValid())
                return false;

            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO " + TRACK + " VALUES(default, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, track.getWorkingArea().getId());
            preparedStatement.setString(2, track.getInstrument().toString());
            preparedStatement.setInt(3, track.getVolume());
            preparedStatement.setString(4, track.getName());
            preparedStatement.setLong(5, track.getLength());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertVariation(Variation variation) {
        try {
            if(!variation.isValid())
                return false;

            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO " + VARIATION + " VALUES(default, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, variation.getMusicSegment().getId());
            preparedStatement.setString(2, variation.getName());
            preparedStatement.setLong(3, variation.getStartTime());
            preparedStatement.setLong(4, variation.getEndTime());
            preparedStatement.setString(5, variation.getOwner());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertVariationTrack(VariationTrack variationTrack) {
        try {
            if(!variationTrack.isValid())
                return false;

            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO " + VARIATION + " VALUES(default, ?, ?, ?)");
            preparedStatement.setInt(1, variationTrack.getVariation().getId());
            preparedStatement.setInt(2, variationTrack.getTrack().getId());
            preparedStatement.setLong(3, variationTrack.getStartTimeOnTrack());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertMusicSegment(MusicSegment musicSegment) {
        try {
            if(!musicSegment.isValid())
                return false;

            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO " + VARIATION + " VALUES(default, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, musicSegment.getName());
            preparedStatement.setString(2, musicSegment.getInstrument().toString());
            preparedStatement.setString(3, musicSegment.getOwner());
            preparedStatement.setString(4, musicSegment.getAudioPath());
            preparedStatement.setLong(5, musicSegment.getLength());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Put all params together to one MySQL SELECT-query and execute this new query.
     * @param columns columns which have to be selected. Is NULL for SELECT *
     * @param table table from which the data have to be chosen
     * @param whereClause Where-Clause
     * @param orderBy OrderBy-Clause
     * @return
     */
    private static ResultSet executeSelect(String[] columns, String table, String whereClause, String orderBy){
        try {
            Statement selectStatement = connect.createStatement();
            if(selectStatement != null) {
                String query = "SELECT ";

                if(columns == null || columns.length == 0)
                    query += "*";
                else {
                    for(int i=0; i<columns.length; i++)
                        query += (i == columns.length - 1) ? columns[i] : columns[i] + ", ";  //only set a comma, if it's not the last column
                }

                query += " FROM " + table;
                query += " WHERE " + whereClause;

                if(orderBy != null && !orderBy.isEmpty())
                    query += " " + orderBy;

                return selectStatement.executeQuery(query);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static MusicRoom convertResultSetToMusicRoom(ResultSet resultSet){
        try {
            if(resultSet == null || resultSet.getFetchSize() == 0)
                return null;

            MusicRoom musicRoom = new MusicRoom();
            resultSet.first();  //only one row is needed => move cursor to the first row of the ResultSet
            musicRoom.setId(resultSet.getInt(ID));
            musicRoom.setName(resultSet.getString(NAME));
            resultSet.close();
            return musicRoom;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static WorkingArea convertResultSetToWorkingArea(ResultSet resultSet) {
        try {
            if(resultSet == null || resultSet.getFetchSize() == 0)
                return null;

            WorkingArea workingArea = new WorkingArea();
            resultSet.first();  //only one row is needed => move cursor to the first row of the ResultSet
            workingArea.setId(resultSet.getInt(ID));
            workingArea.setName(resultSet.getString(NAME));
            workingArea.setBeat(resultSet.getFloat(BEAT));
            workingArea.setTempo(resultSet.getInt(TEMPO));
            workingArea.setOwner(resultSet.getString(OWNER));
            workingArea.setWorkingAreaType(Enum.valueOf(WorkingAreaType.class,resultSet.getString(TYPE)));
            workingArea.setMusicRoom(getMusicRoomById(resultSet.findColumn(MUSIC_ROOM_ID)));
            workingArea.setLength(resultSet.getLong(LENGTH));
            resultSet.close();

            return workingArea;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static Track convertResultSetToTrack(ResultSet resultSet) {
        try {
            if(resultSet == null || resultSet.getFetchSize() == 0)
                return null;

            Track track = new Track();
            resultSet.first();  //only one row is needed => move cursor to the first row of the ResultSet
            track.setId(resultSet.getInt(ID));
            track.setName(resultSet.getString(NAME));
            track.setVolume(resultSet.getInt(VOLUME));
            track.setInstrument(Enum.valueOf(Instrument.class,resultSet.getString(INSTRUMENT)));
            track.setWorkingArea(getWorkingAreaById(resultSet.findColumn(WORKING_AREA_ID)));
            track.setLength(resultSet.getLong(LENGTH));
            resultSet.close();

            return track;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static MusicSegment convertResultSetToMusicSegment(ResultSet resultSet) {
        try {
            if(resultSet == null || resultSet.getFetchSize() == 0)
                return null;

            MusicSegment musicSegment = new MusicSegment();
            resultSet.first();  //only one row is needed => move cursor to the first row of the ResultSet
            musicSegment.setId(resultSet.getInt(ID));
            musicSegment.setName(resultSet.getString(NAME));
            musicSegment.setAudioPath(resultSet.getString(AUDIO_PATH));
            musicSegment.setInstrument(Enum.valueOf(Instrument.class,resultSet.getString(INSTRUMENT)));
            musicSegment.setOwner(resultSet.getString(OWNER));
            musicSegment.setLength(resultSet.getLong(LENGTH));
            resultSet.close();

            return musicSegment;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static Variation convertResultSetToVariation(ResultSet resultSet) {
        try {
            if(resultSet == null || resultSet.getFetchSize() == 0)
                return null;

            Variation variation = new Variation();
            resultSet.first();  //only one row is needed => move cursor to the first row of the ResultSet
            variation.setId(resultSet.getInt(ID));
            variation.setName(resultSet.getString(NAME));
            variation.setOwner(resultSet.getString(OWNER));
            variation.setStartTime(resultSet.getLong(START_TIME));
            variation.setEndTime(resultSet.getLong(END_TIME));
            variation.setMusicSegment(getMusicSegmentById(resultSet.getInt(MUSIC_SEGMENT_ID)));
            resultSet.close();

            return variation;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static VariationTrack convertResultSetToVariationTrack(ResultSet resultSet) {
        try {
            if(resultSet == null || resultSet.getFetchSize() == 0)
                return null;

            VariationTrack variationTrack = new VariationTrack();
            resultSet.first();  //only one row is needed => move cursor to the first row of the ResultSet
            variationTrack.setId(resultSet.getInt(ID));
            variationTrack.setStartTimeOnTrack(resultSet.getLong(START_TIME));
            variationTrack.setVariation(getVariationById(resultSet.getInt(VARIATION_ID)));
            variationTrack.setTrack(getTrackById(resultSet.getInt(TRACK_ID)));
            resultSet.close();

            return variationTrack;
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }

    }
}
