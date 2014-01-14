package com.musicbox.util.audio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author David Wachs
 */
public abstract class WaveMixer {

    /**
     * Concatenates two WaveFile-Objects an return the result as a new WaveFile-object
     * @param waveFile1 Used as base for the concatenate process
     * @param waveFile2 WaveFile which has to be concatenate to the first one
     * @param spacing  spacing between the two audio-data (in seconds)
     * @return resulting WaveFile-Object
     */
    public static WaveFile concatenate(WaveFile waveFile1, WaveFile waveFile2, int spacing) {
        byte[] data1 = waveFile1.audioData;
        byte[] data2 = waveFile2.audioData;

        int space = 44100 * 2 * 2 * spacing;
        ByteBuffer newDataBuffer = ByteBuffer.allocate(data1.length + data2.length + space).order(ByteOrder.LITTLE_ENDIAN);
        newDataBuffer.put(data1);
        newDataBuffer.put(data2, data1.length + space, data2.length);
        byte[] newData = newDataBuffer.array();

        WaveFile newWave = WaveFile.fromWave(waveFile1);
        newWave.chunkSize += space + data2.length;
        newWave.dataSize += space + data2.length;
        newWave.audioData = newData;

        return newWave;
    }


    /**
     * Mix two wave files together. Quelle: http://stackoverflow.com/questions/16810228/how-to-remove-noise-from-wav-file-after-mixed
     * @param dataToMix
     * @param dataToMixSize
     * @param destFile
     * @throws java.io.IOException
     */
    public void mix(byte[] dataToMix, long dataToMixSize, File destFile, int second) throws IOException {
        DataOutputStream out;
        byte[] mixedData;
        int startByte = 0;
        if(second > 0)
            startByte = 44100 * 2 * 2 * second;
        if(dataSize >= dataToMixSize + startByte){
            mixedData = new byte[(int)dataSize];
            out = writeHeader(writeFile(destFile), 40 + dataSize);
            out.write(ByteHelper.intToByteArray((int) dataSize), 0, 4);
        }
        else{
            mixedData = new byte[(int)dataToMixSize + startByte];
            out = writeHeader(writeFile(destFile), 40 + dataToMixSize + startByte);
            out.write(ByteHelper.intToByteArray((int) dataToMixSize + startByte), 0, 4);
        }
        int j = 0;
        for(int i=0; i<mixedData.length; i++)
        {
            byte mixedByte = 0;
            if(i<startByte)
                mixedByte = data[i];
            else
            {
                float samplef1 = 0;
                float samplef2 = 0;
                if(i<dataSize)
                    samplef1 = data[i] / 128.0f;
                if(j<dataToMixSize)
                    samplef2 = dataToMix[j++] / 128.0f;

                float mixed = samplef1 + samplef2;
                // hard clipping
                if (mixed > 1.0f)  mixed = 1.0f;
                if (mixed < -1.0f) mixed = -1.0f;

                mixedByte = (byte) (mixed *128.0f);
            }
            mixedData[i] = mixedByte;
        }
        out.write(mixedData);
        out.close();
    }

    public static WaveFile mix(WaveFile waveFile1, WaveFile waveFile2, int starMixSecond){
        byte[] data1 = waveFile1.audioData;
        byte[] data2 = waveFile2.audioData;
        byte[] mixedData;
        int startByte = 0;
        WaveFile newWave = WaveFile.fromWave(waveFile1);

        if(starMixSecond > 0)
            startByte = 44100 * 2 * 2 * starMixSecond;

        if(waveFile1.dataSize >= waveFile2.dataSize + startByte){
            mixedData = new byte[(int)dataSize];
            newWave.chunkSize =
            out = writeHeader(writeFile(destFile), 40 + dataSize);
            out.write(ByteHelper.intToByteArray((int) dataSize), 0, 4);
        }
        else{
            mixedData = new byte[(int)dataToMixSize + startByte];
            out = writeHeader(writeFile(destFile), 40 + dataToMixSize + startByte);
            out.write(ByteHelper.intToByteArray((int) dataToMixSize + startByte), 0, 4);
        }

    }
}
