package com.musicbox.util.audio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author David Wachs
 */
public abstract class WaveMixer {

    /**
     * Concatenates two WaveFile-Objects and return the result as a new WaveFile-object
     * @param waveFile1 Used as base for the concatenate process
     * @param waveFile2 WaveFile which has to be concatenate to the first one
     * @param spacing  spacing between the two audio-data (in seconds)
     * @return resulting WaveFile-Object
     */
    public static WaveFile concatenate(WaveFile waveFile1, WaveFile waveFile2, int spacing) {
        byte[] data1 = waveFile1.getData();
        byte[] data2 = waveFile2.getData();

        long space = waveFile1.getSampleRate() * waveFile1.getNumChannels() * (waveFile1.getBitsPerSample()/8) * spacing;
        ByteBuffer newDataBuffer = ByteBuffer.allocate((int)waveFile1.getSubChunk2Size() + (int)waveFile2.getSubChunk2Size() + (int)space).order(ByteOrder.LITTLE_ENDIAN);
        newDataBuffer.put(data1);
        if(space > 0)
            newDataBuffer.put(new byte[(int)space]);

        newDataBuffer.put(data2);

        WaveFile newWave = WaveFile.fromWave(waveFile1);
        newWave.setData(newDataBuffer.array());

        return newWave;
    }

    /**
     * Mix two WaveFile-Objects and return the result as a new WaveFile-Object
     * @param waveFile1 Used as base for the mix process
     * @param waveFile2 WaveFile which will be mixed to the first one
     * @param startMixSecond The second where mixing starts
     * @return resulting WaveFile-Object
     */
    public static WaveFile mix(WaveFile waveFile1, WaveFile waveFile2, int startMixSecond){
        byte[] data1 = waveFile1.getData();
        byte[] data2 = waveFile2.getData();
        byte[] mixedData;
        long startByte = 0;
        WaveFile newWave = WaveFile.fromWave(waveFile1);

        if(startMixSecond > 0)
            startByte = newWave.getSampleRate() * newWave.getNumChannels() * (newWave.getBitsPerSample()/8) * startMixSecond;

        if(waveFile1.getSubChunk2Size() >= waveFile2.getSubChunk2Size() + startByte)
            mixedData = new byte[(int)waveFile1.getSubChunk2Size()];
        else
            mixedData = new byte[(int)waveFile2.getSubChunk2Size() + (int)startByte];

        int j = 0;
        for(int i=0; i<mixedData.length; i++)
        {
            byte mixedByte = 0;
            if(i<startByte)
                mixedByte = data1[i];
            else
            {
                float samplef1 = 0;
                float samplef2 = 0;
                if(i<waveFile1.getSubChunk2Size())
                    samplef1 = data1[i] / 128.0f;
                if(j<waveFile2.getSubChunk2Size())
                    samplef2 = data2[j++] / 128.0f;

                float mixed = samplef1 + samplef2;
                // hard clipping
                if (mixed > 1.0f)  mixed = 1.0f;
                if (mixed < -1.0f) mixed = -1.0f;

                mixedByte = (byte) (mixed *128.0f);
            }
            mixedData[i] = mixedByte;
        }
        newWave.setData(mixedData);
        return newWave;
    }

    public static WaveFile mix2(WaveFile waveFile1, WaveFile waveFile2, int startMixSecond) {
        ByteBuffer buffer1 = ByteBuffer.wrap(waveFile1.getData()).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer buffer2 = ByteBuffer.wrap(waveFile2.getData()).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer mixedBuffer;

        long startByte = 0;
        WaveFile newWave = WaveFile.fromWave(waveFile1);

        if(startMixSecond > 0)
            startByte = newWave.getSampleRate() * newWave.getNumChannels() * (newWave.getBitsPerSample()/8) * startMixSecond;

        if(waveFile1.getSubChunk2Size() >= waveFile2.getSubChunk2Size() + startByte)
            mixedBuffer = ByteBuffer.allocate((int)waveFile1.getSubChunk2Size()).order(ByteOrder.LITTLE_ENDIAN);
        else
            mixedBuffer = ByteBuffer.allocate((int)waveFile2.getSubChunk2Size() + (int)startByte).order(ByteOrder.LITTLE_ENDIAN);

        while(mixedBuffer.remaining() > 0){
            float samplef1 = 0;
            float samplef2 = 0;
            if(buffer1.remaining() > 2)
                samplef1 = getSampleFloat(buffer1.get(), buffer1.get());
            if(buffer2.remaining() > 2)
                samplef2 = getSampleFloat(buffer2.get(), buffer2.get());

            float mixed = 0;

            if(samplef1 < 0F && samplef2 < 0F)
                mixed = (samplef1 + samplef2) - ((samplef1 * samplef2)/Float.MIN_VALUE);
            else if(samplef1 > 0F && samplef2 > 0F){
                mixed = (samplef1 + samplef2) - ((samplef1 * samplef2)/Float.MAX_VALUE);
            }
            else
                mixed = samplef1 + samplef2;

            /*if (mixed > 1.0F)  mixed = 1.0F;
            if (mixed < -1.0F) mixed = -1.0F;*/

            byte[] bytes = floatToBytes(mixed);


            mixedBuffer.put(bytes);
        }

        newWave.setData(mixedBuffer.array());
        return newWave;
    }

    private static final float getSampleFloat(byte hi, byte lo) {
        return (short) (hi << 8) | (short) (lo & 0xFF);
    }

    public static byte[]  floatToBytes(float f)
    {
        byte[] bytes = new byte[2];
        bytes[0]	= (byte)((int)f);
        bytes[1]	= (byte)((int)f >> 8);
        return bytes;
    }
}
