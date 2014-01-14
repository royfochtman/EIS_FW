package com.musicbox.util.audio;


import org.apache.commons.io.FilenameUtils;
import sun.net.www.content.audio.wav;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Quelle: http://stackoverflow.com/questions/5810164/how-can-i-write-wav-file-from-byte-array-in-java
 * WAVE-File-Format: https://ccrma.stanford.edu/courses/422/projects/WaveFormat/
 *
 *
 The thing that makes reading wav files tricky is that java has no unsigned types.  This means that the
 binary data can't just be read and cast appropriately.  Also, we have to use larger types
 than are normally necessary.

 In many languages including java, an integer is represented by 4 bytes.  The issue here is
 that in most languages, integers can be signed or unsigned, and in wav files the  integers
 are unsigned.  So, to make sure that we can store the proper values, we have to use longs
 to hold integers, and integers to hold shorts.

 Then, we have to convert back when we want to save our wav data.

 ChunkID            =>  Big Endian
 ChunkSize          =>  Little Endian
 Format             =>  Big Endian
 Subchunk1ID        =>  Big Endian
 Subchunk1Size      =>  Little Endian
 AudioFormat        =>  Little Endian
 NumChannels        =>  Little Endian
 SampleRate         =>  Little Endian
 ByteRate           =>  Little Endian
 BlockAlign         =>  Little Endian
 BitsPerSample      =>  Little Endian
 Subchunk2ID        =>  Big Endian
 Subchunk2Size      =>  Little Endian
 Data               =>  Little Endian

@author David Wachs
 */
public class WaveFile {
    protected long chunkSize;
    protected long subChunk1Size;
    protected int audioFormat;
    protected long channels;
    protected long sampleRate;
    protected long byteRate;
    protected int blockAlign;
    protected int bitsPerSample;
    protected long dataSize;
    protected byte[] audioData;
    protected String name;
    protected String formatString;
    protected String subChunk1ID;

    public WaveFile(long chunkSize, long subChunk1Size, int audioFormat, long channels,
                    long sampleRate, long byteRate, int blockAlign, int bitsPerSample, String formatString,
                    String subChunk1ID) {
        this.chunkSize = chunkSize;
        this.subChunk1Size = subChunk1Size;
        this.audioFormat = audioFormat;
        this.channels = channels;
        this.sampleRate = sampleRate;
        this.byteRate = byteRate;
        this.blockAlign = blockAlign;
        this.bitsPerSample = bitsPerSample;
        this.dataSize = 0L;
        this.audioData = null;
        this.name = "";
        this.formatString = formatString;
        this.subChunk1ID = subChunk1ID;
    }

    public WaveFile() {
        this.chunkSize = 0L;
        this.subChunk1Size = 0L;
        this.audioFormat = 0;
        this.channels = 0L;
        this.sampleRate = 0L;
        this.byteRate = 0L;
        this.blockAlign = 0;
        this.bitsPerSample = 0;
        this.dataSize = 0L;
        this.audioData = null;
        this.name = "";
        this.formatString = "";
        this.subChunk1ID = "";
    }

    public long getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(long chunkSize) {
        this.chunkSize = chunkSize;
    }

    public long getSubChunk1Size() {
        return subChunk1Size;
    }

    public void setSubChunk1Size(long subChunk1Size) {
        this.subChunk1Size = subChunk1Size;
    }

    public int getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(int audioFormat) {
        this.audioFormat = audioFormat;
    }

    public long getChannels() {
        return channels;
    }

    public void setChannels(long channels) {
        this.channels = channels;
    }

    public long getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(long sampleRate) {
        this.sampleRate = sampleRate;
    }

    public long getByteRate() {
        return byteRate;
    }

    public void setByteRate(long byteRate) {
        this.byteRate = byteRate;
    }

    public int getBlockAlign() {
        return blockAlign;
    }

    public void setBlockAlign(int blockAlign) {
        this.blockAlign = blockAlign;
    }

    public int getBitsPerSample() {
        return bitsPerSample;
    }

    public void setBitsPerSample(int bitsPerSample) {
        this.bitsPerSample = bitsPerSample;
    }

    public long getDataSize() {
        return dataSize;
    }

    public void setDataSize(long dataSize) {
        this.dataSize = dataSize;
    }

    public byte[] getAudioData() {
        return audioData;
    }

    public void setAudioData(byte[] audioData) {
        this.audioData = audioData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormatString() {
        return formatString;
    }

    public void setFormatString(String formatString) {
        this.formatString = formatString;
    }

    public String getSubChunk1ID() {
        return subChunk1ID;
    }

    public void setSubChunk1ID(String subChunk1ID) {
        this.subChunk1ID = subChunk1ID;
    }

    public static WaveFile fromWave(WaveFile waveFile){
        return new WaveFile(waveFile.chunkSize, waveFile.subChunk1Size, waveFile.audioFormat,
                waveFile.channels, waveFile.sampleRate, waveFile.byteRate, waveFile.blockAlign,
                waveFile.bitsPerSample, waveFile.formatString, waveFile.subChunk1ID);
    }

    /**
     * Read a file and chechs, if it is a wav-file and store the data in a WaveFile-Object
     * @param file  File to read
     * @return  WaveFile-Object with the data of the file
     */
    public static WaveFile readFile(File file) {
        WaveFile waveFile = new WaveFile();

        if(file == null || !file.exists() || !FilenameUtils.getExtension(file.getAbsolutePath()).equals("wav"))
            return null;
        waveFile.setName(file.getName());

        DataInputStream in;
        //waveFile.audioData = null;
        byte[] tmpInt = new byte[4];
        byte[] tmpShort = new byte[2];
        try
        {
            in = new DataInputStream(new FileInputStream(file));

            String chunkID = "" + (char) in.readByte() + (char) in.readByte() + (char) in.readByte() + (char) in.readByte();

            in.read(tmpInt); // read the ChunkSize
            waveFile.chunkSize = ByteHelper.byteArrayToLong(tmpInt);

            waveFile.formatString = "" + (char) in.readByte() + (char) in.readByte() + (char) in.readByte() + (char) in.readByte();

            waveFile.subChunk1ID = "" + (char) in.readByte() + (char) in.readByte() + (char) in.readByte() + (char) in.readByte();

            in.read(tmpInt); // read the SubChunk1Size
            waveFile.subChunk1Size = ByteHelper.byteArrayToLong(tmpInt);

            in.read(tmpShort); // read the audio format.  This should be 1 for PCM
            waveFile.audioFormat = ByteHelper.byteArrayToInt(tmpShort);

            in.read(tmpShort); // read the # of channels (1 or 2)
            waveFile.channels = ByteHelper.byteArrayToInt(tmpShort);

            in.read(tmpInt); // read the samplerate
            waveFile.sampleRate = ByteHelper.byteArrayToLong(tmpInt);

            in.read(tmpInt); // read the byterate
            waveFile.byteRate = ByteHelper.byteArrayToLong(tmpInt);

            in.read(tmpShort); // read the blockalign
            waveFile.blockAlign = ByteHelper.byteArrayToInt(tmpShort);

            in.read(tmpShort); // read the bitspersample
            waveFile.bitsPerSample = ByteHelper.byteArrayToInt(tmpShort);

            // read the data chunk header - reading this IS necessary, because not all wav files will have the data chunk here - for now, we're just assuming that the data chunk is here
            String dataChunkID = "" + (char) in.readByte() + (char) in.readByte() + (char) in.readByte() + (char) in.readByte();

            in.read(tmpInt); // read the size of the data
            waveFile.dataSize = ByteHelper.byteArrayToLong(tmpInt);


            // read the data chunk
            waveFile.audioData = new byte[(int) waveFile.dataSize];
            in.read(waveFile.audioData);

            // close the input stream
            in.close();

            return waveFile;
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    /**
     * Saves the given WaveFile-Object as wav-file at a specific path with the date stored in the WaveFile-Object
     * @param path path were the file has to be created
     * @return true if success, otherwise returns false
     */
    public boolean saveOnDisk(String path){
        try {
            File file = new File(path + "\\" + this.name);
            if(!file.exists())
                file.createNewFile();
            else
                return false;

            DataOutputStream fileOut = new DataOutputStream(new FileOutputStream(file));
            fileOut.writeBytes("RIFF");                 // 00 - RIFF
            fileOut.write(ByteHelper.intToByteArray((int) this.chunkSize), 0, 4);     // 04 - how big is the rest of this file?
            fileOut.writeBytes("WAVE");                 // 08 - WAVE
            fileOut.writeBytes("fmt ");                 // 12 - fmt
            fileOut.write(ByteHelper.intToByteArray((int) this.subChunk1Size), 0, 4); // 16 - size of this chunk
            fileOut.write(ByteHelper.shortToByteArray((short) this.audioFormat), 0, 2);        // 20 - what is the audio format? 1 for PCM = Pulse Code Modulation
            fileOut.write(ByteHelper.shortToByteArray((short) this.channels), 0, 2);  // 22 - mono or stereo? 1 or 2?  (or 5 or ???)
            fileOut.write(ByteHelper.intToByteArray((int) this.sampleRate), 0, 4);        // 24 - samples per second (numbers per second)
            fileOut.write(ByteHelper.intToByteArray((int) this.byteRate), 0, 4);      // 28 - bytes per second
            fileOut.write(ByteHelper.shortToByteArray((short) this.blockAlign), 0, 2);    // 32 - # of bytes in one sample, for all channels
            fileOut.write(ByteHelper.shortToByteArray((short) this.bitsPerSample), 0, 2); // 34 - how many bits in a sample(number)?  usually 16 or 24
            fileOut.writeBytes("data");                 // 36 - data
            fileOut.write(ByteHelper.intToByteArray((int)this.dataSize), 0, 4);
            fileOut.write(this.audioData);
            fileOut.close();

            return true;
        } catch(IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WaveFile waveFile = (WaveFile) o;

        return audioFormat == waveFile.audioFormat
                && bitsPerSample == waveFile.bitsPerSample
                && blockAlign == waveFile.blockAlign
                && byteRate == waveFile.byteRate
                && channels == waveFile.channels
                && chunkSize == waveFile.chunkSize
                && dataSize == waveFile.dataSize
                && sampleRate == waveFile.sampleRate
                && subChunk1Size == waveFile.subChunk1Size
                && audioData.equals(waveFile.audioData)
                && name != null && name.equals(waveFile.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (chunkSize ^ (chunkSize >>> 32));
        result = 31 * result + (int) (subChunk1Size ^ (subChunk1Size >>> 32));
        result = 31 * result + audioFormat;
        result = 31 * result + (int) (channels ^ (channels >>> 32));
        result = 31 * result + (int) (sampleRate ^ (sampleRate >>> 32));
        result = 31 * result + (int) (byteRate ^ (byteRate >>> 32));
        result = 31 * result + blockAlign;
        result = 31 * result + bitsPerSample;
        result = 31 * result + (int) (dataSize ^ (dataSize >>> 32));
        result = 31 * result + (audioData != null ? Arrays.hashCode(audioData) : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }


}
