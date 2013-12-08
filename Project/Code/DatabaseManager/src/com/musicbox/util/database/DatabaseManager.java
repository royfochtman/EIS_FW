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
    private static Statement queryStatement = null;
    private static PreparedStatement preparedStatement = null;
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
        ResultSet result = executeQuery(null, MUSIC_ROOM, ID + "=" + id, null);
        if(result == null)
            return null;

        MusicRoom musicRoom = new MusicRoom();
        try {
            result.first();  //only one row is needed => move cursor to the first row of the ResultSet
            musicRoom.setId(result.getInt(ID));
            musicRoom.setName(result.getString(NAME));
            result.close();
            return musicRoom;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static WorkingArea getWorkingAreaById(int id) {
        ResultSet result = executeQuery(null, WORKING_AREA, ID + "==" + id, null);
        if(result == null)
            return null;

        WorkingArea workingArea = new WorkingArea();
        try {
            result.first();  //only one row is needed => move cursor to the first row of the ResultSet
            workingArea.setId(result.getInt(ID));
            workingArea.setName(result.getString(NAME));
            workingArea.setBeat(result.getFloat(BEAT));
            workingArea.setTempo(result.getInt(TEMPO));
            workingArea.setOwner(result.getString(OWNER));
            workingArea.setWorkingAreaType(Enum.valueOf(WorkingAreaType.class,result.getString(TYPE)));
            workingArea.setMusicRoom(getMusicRoomById(result.findColumn(MUSIC_ROOM_ID)));
            workingArea.setLength(result.getLong(LENGTH));
            result.close();
            return workingArea;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Track getTrackById(int id) {
        ResultSet result = executeQuery(null, TRACK, ID + "==" + id, null);
        if(result == null)
            return null;

        Track track = new Track();
        try {
            result.first();  //only one row is needed => move cursor to the first row of the ResultSet
            track.setId(result.getInt(ID));
            track.setName(result.getString(NAME));
            track.setVolume(result.getInt(VOLUME));
            track.setInstrument(Enum.valueOf(Instrument.class,result.getString(INSTRUMENT)));
            track.setWorkingArea(getWorkingAreaById(result.findColumn(WORKING_AREA_ID)));
            track.setLength(result.getLong(LENGTH));
            result.close();
            return track;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MusicSegment getMusicSegmentById(int id) {
        ResultSet result = executeQuery(null, MUSIC_SEGMENT, ID + "==" + id, null);
        if(result == null)
            return null;

        MusicSegment musicSegment = new MusicSegment();
        try {
            result.first();  //only one row is needed => move cursor to the first row of the ResultSet
            musicSegment.setId(result.getInt(ID));
            musicSegment.setName(result.getString(NAME));
            musicSegment.setAudioPath(result.getString(AUDIO_PATH));
            musicSegment.setInstrument(Enum.valueOf(Instrument.class,result.getString(INSTRUMENT)));
            musicSegment.setOwner(result.getString(OWNER));
            musicSegment.setLength(result.getLong(LENGTH));
            result.close();
            return musicSegment;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Variation getVariationById(int id) {
        ResultSet result = executeQuery(null, VARIATION, ID + "==" + id, null);
        if(result == null)
            return null;

        Variation variation = new Variation();
        try {
            result.first();  //only one row is needed => move cursor to the first row of the ResultSet
            variation.setId(result.getInt(ID));
            variation.setName(result.getString(NAME));
            variation.setOwner(result.getString(OWNER));
            variation.setStartTime(result.getLong(START_TIME));
            variation.setEndTime(result.getLong(END_TIME));
            variation.setMusicSegment(getMusicSegmentById(result.getInt(MUSIC_SEGMENT_ID)));
            result.close();
            return variation;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static VariationTrack getVariationTrackById(int id) {
        ResultSet result = executeQuery(null, VARIATION_TRACK, ID + "==" + id, null);
        if(result == null)
            return null;

        VariationTrack variationTrack = new VariationTrack();
        try {
            result.first();  //only one row is needed => move cursor to the first row of the ResultSet
            variationTrack.setId(result.getInt(ID));
            variationTrack.setStartTimeOnTrack(result.getLong(START_TIME));
            variationTrack.setVariation(getVariationById(result.getInt(VARIATION_ID)));
            variationTrack.setTrack(getTrackById(result.getInt(TRACK_ID)));
            result.close();
            return variationTrack;
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    /**
     * Inserts a new MusicRoom-Object to the database
     * @param musicRoom the id-attribute is not needed, because the database defines the id-value.
     *                  So the id attribute of the musicRoom-Object can have any value.
     * @return true, if data saved successfully
     */
    public static boolean insertMusicRoom(MusicRoom musicRoom) {
        try {
            if(musicRoom.getName() == null || musicRoom.getName().isEmpty())
                return false;

            preparedStatement = connect.prepareStatement("INSERT INTO " + MUSIC_ROOM + " VALUES(default, ?)");
            preparedStatement.setString(1, musicRoom.getName());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }


    /**
     * Put all params together to one MySQL query and execute this new query.
     * @param columns columns which have to be selected. if it's null or empty => SELECT * ....
     * @param table table from which the data have to be chosen
     * @param whereClause
     * @param orderBy
     * @return
     */
    private static ResultSet executeQuery(String[] columns, String table, String whereClause, String orderBy){
        try {
            queryStatement = connect.createStatement();
            if(queryStatement != null) {
                String query = "SELECT ";

                if(columns == null || columns.length == 0)
                    query += "* ";
                else {
                    for(int i=0; i<columns.length; i++)
                        query += (i == columns.length - 1) ? columns[i] : columns[i] + ", ";  //only set a comma, if it's not the last column
                }

                query += "FROM " + table;
                query += " WHERE " + whereClause;

                if(orderBy != null && !orderBy.isEmpty())
                    query += " " + orderBy;

                return queryStatement.executeQuery(query);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
