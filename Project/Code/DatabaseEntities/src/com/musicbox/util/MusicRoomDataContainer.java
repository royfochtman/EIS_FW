package com.musicbox.util;

import com.musicbox.util.database.entities.*;
import com.musicbox.util.globalobject.GlobalObject;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * This Class is used to store all Data of one music-room in one object and
 * send it to startup clients or clients which are joining to this music-room.
 *
 * @author David Wachs
 */
public class MusicRoomDataContainer extends GlobalObject {
    /**
     * music-room which the data belongs to
     */
    private MusicRoom musicRoom;
    private ArrayList<WorkingArea> workingAreas;
    private ArrayList<Track> tracks;
    private ArrayList<MusicSegment> musicSegments;
    private ArrayList<Variation> variations;
    private ArrayList<VariationTrack> variationTracks;

    public MusicRoomDataContainer(MusicRoom musicRoom, ArrayList<WorkingArea> workingAreas, ArrayList<Track> tracks, ArrayList<MusicSegment> musicSegments,
                                  ArrayList<Variation> variations, ArrayList<VariationTrack> variationTracks) {
        setMusicRoom(musicRoom);
        setWorkingAreas(workingAreas);
        setTracks(tracks);
        setMusicSegments(musicSegments);
        setVariations(variations);
        setVariationTracks(variationTracks);
    }

    public MusicRoomDataContainer(MusicRoom musicRoom) {
        setMusicRoom(musicRoom);
        this.workingAreas = new ArrayList<>();
        this.tracks = new ArrayList<>();
        this.musicSegments = new ArrayList<>();
        this.variations  = new ArrayList<>();
        this.variationTracks  = new ArrayList<>();
    }

    public MusicRoomDataContainer(){
        this.musicRoom = new MusicRoom();
        this.workingAreas = new ArrayList<>();
        this.tracks = new ArrayList<>();
        this.musicSegments = new ArrayList<>();
        this.variations  = new ArrayList<>();
        this.variationTracks  = new ArrayList<>();
    }

    public MusicRoom getMusicRoom() {
        return musicRoom;
    }

    public void setMusicRoom(MusicRoom musicRoom) {
        if(musicRoom == null)
            this.musicRoom = new MusicRoom();
        else
            this.musicRoom = musicRoom;
    }

    public ArrayList<WorkingArea> getWorkingAreas() {
        return workingAreas;
    }

    public void setWorkingAreas(ArrayList<WorkingArea> workingAreas) {
        if(workingAreas == null)
            this.workingAreas = new ArrayList<>();
        else
            this.workingAreas = workingAreas;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        if(tracks == null)
            this.tracks = new ArrayList<>();
        else
            this.tracks = tracks;
    }

    public ArrayList<MusicSegment> getMusicSegments() {
        return musicSegments;
    }

    public void setMusicSegments(ArrayList<MusicSegment> musicSegments) {
        if(musicSegments == null)
            this.musicSegments = new ArrayList<>();
        else
            this.musicSegments = musicSegments;
    }

    public ArrayList<Variation> getVariations() {
        return variations;
    }

    public void setVariations(ArrayList<Variation> variations) {
        if(variations == null)
            this.variations = new ArrayList<>();
        else
            this.variations = variations;
    }

    public ArrayList<VariationTrack> getVariationTracks() {
        return variationTracks;
    }

    public void setVariationTracks(ArrayList<VariationTrack> variationTracks) {
        if(variationTracks == null)
            this.variationTracks = new ArrayList<>();
        else
            this.variationTracks = variationTracks;
    }

    public void addWorkingArea(WorkingArea workingArea) {
        workingAreas.add(workingArea);
    }

    public void addWorkingAreas(ArrayList<WorkingArea> workingAreas){
        if(workingAreas != null && !workingAreas.isEmpty())
            this.workingAreas.addAll(workingAreas);
    }

    public void addTrack(Track track) {
        tracks.add(track);
    }

    public void addTracks(ArrayList<Track> tracks){
        if(tracks != null && !tracks.isEmpty())
            this.tracks.addAll(tracks);
    }

    public void addMusicSegment(MusicSegment musicSegment) {
        musicSegments.add(musicSegment);
    }

    public void addMusicSegments(ArrayList<MusicSegment> musicSegments){
        if(musicSegments != null && !musicSegments.isEmpty())
            this.musicSegments.addAll(musicSegments);
    }

    public void addVariation(Variation variation) {
        variations.add(variation);
    }

    public void addVariations(ArrayList<Variation> variations){
        if(variations != null && !variations.isEmpty())
            this.variations.addAll(variations);
    }

    public void addVariationTrack(VariationTrack variationTrack) {
        variationTracks.add(variationTrack);
    }

    public void addVariationTracks(ArrayList<VariationTrack> variationTracks) {
        if(variationTracks != null && !variationTracks.isEmpty())
            this.variationTracks.addAll(variationTracks);
    }

    /**
     * Gets the id of the music-room
     * @return the id of the music-room
     */
    @Override
    public int getId() {
        if(musicRoom == null)
            return -1;

        return musicRoom.getId();
    }

    /**
     * Set id of music-room-object
     * @param id of music-room
     */
    @Override
    public void setId(int id) {
        if(musicRoom != null)
            musicRoom.setId(id);
    }

    @Override
    public boolean isValid() {
        return musicRoom.isValid() && workingAreas != null && musicSegments != null
                && tracks != null && variations != null && variationTracks != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        MusicRoomDataContainer musicRoomDataContainer = (MusicRoomDataContainer) obj;

        return musicRoom != null && musicRoom.equals(musicRoomDataContainer.getMusicRoom())
                && workingAreas != null && workingAreas.equals(musicRoomDataContainer.getWorkingAreas())
                && tracks != null && tracks.equals(musicRoomDataContainer.getTracks())
                && musicSegments != null && musicSegments.equals(musicRoomDataContainer.getMusicSegments())
                && variations != null && variations.equals(musicRoomDataContainer.getVariations())
                && variationTracks != null && variationTracks.equals(musicRoomDataContainer.getVariationTracks());
    }

    @Override
    public int hashCode() {
        int result = musicRoom != null ? musicRoom.hashCode() : 0;
        result = 31 * result + (workingAreas != null ? workingAreas.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        result = 31 * result + (musicSegments != null ? musicSegments.hashCode() : 0);
        result = 31 * result + (variations != null ? variations.hashCode() : 0);
        result = 31 * result + (variationTracks != null ? variationTracks.hashCode() : 0);
        return result;
    }
}
