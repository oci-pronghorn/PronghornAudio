package com.ociweb.pronghorn.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
import javax.sound.sampled.TargetDataLine;

import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.pipe.PipeWriter;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;

public class GenerateFakeAudio extends PronghornStage {

    private Pipe<AudioSchema> output;
    private TargetDataLine line;
    
    protected GenerateFakeAudio(GraphManager graphManager, Pipe<AudioSchema> output) {
        super(graphManager, NONE, output);
        this.output = output;
    }
    
    @Override
    public void startup() {
        
        float sampleRate = 0;
        int sampleSizeInBits = 0;
        int channels = 0;
        int frameSize = 0;
        float frameRate = 0;
        boolean bigEndian = false;
        AudioFormat af = new AudioFormat(Encoding.PCM_SIGNED, sampleRate, sampleSizeInBits, channels, frameSize, frameRate, bigEndian);
        
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, af); // format is an AudioFormat object
        if (!AudioSystem.isLineSupported(info)) {
            // Handle the error ... 

        }
        // Obtain and open the line.
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(af);
        } catch (LineUnavailableException ex) {
            // Handle the error ... 
        }
        
    }

    @Override
    public void run() {
       
        if (PipeWriter.tryWriteFragment(output, AudioSchema.MSG_SAMPLE_110)) {
            
            int value = (int)(1000*Math.random());
            
            //use line method to pull in the mic data.
            //line.re
            
            
            
            PipeWriter.writeInt(output, AudioSchema.MSG_SAMPLE_110_FIELD_VALUE_111, value);
            PipeWriter.publishWrites(output);
        }
        
        //if we have written 10000 items
        //PipeWriter.publishEOF(output);
        
        
        //call this when you are all done.
        //requestShutdown();
        
    }

    @Override
    public void shutdown() {
        
    }
}
