package GlobalUtilities;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFormat.Encoding;

public class MicRecord{
	
	//private Pipe<AudioSchema> input;
    private TargetDataLine microphone;

	/*
    protected MicRecord(GraphManager graphManager, Pipe<AudioSchema> output) {
        super(graphManager, NONE, output);
        this.input = input;
    }
    */
    
    //@Override
    public void startup() {
    	float sampleRate = 0;
        int sampleSizeInBits = 0;
        int channels = 0;
        int frameSize = 0;
        float frameRate = 0;
        boolean bigEndian = false;
        AudioFormat format = new AudioFormat(Encoding.PCM_SIGNED, sampleRate, sampleSizeInBits, channels, frameSize, frameRate, bigEndian);
        

        
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format); // format is an AudioFormat object
        if (!AudioSystem.isLineSupported(info)) {
            // Handle the error ... 

        }
        // Obtain and open the line.
        try {
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
        } catch (LineUnavailableException ex) {
            // Handle the error ... 
        }
        
    }

    //@Override
    public void run(TargetDataLine line) {
       
    	/* 
        if (PipeWriter.tryWriteFragment(input, AudioSchema.MSG_SAMPLE_110)) {
            
            //int value = (int)(1000*Math.random());
        	// Get signal from microphone
        	
        	//use line method to pull in the mic data.
            //microphone.read();
        	line.read(b, off, len)
        	
        	/*
        	int value = .get
            PipeWriter.writeInt(input, AudioSchema.MSG_SAMPLE_110_FIELD_VALUE_111, value);
            PipeWriter.publishWrites(input);
            */
        	/*
        	PipeWriter.writeInt(output, AudioSchema.MSG_SAMPLE_110_FIELD_VALUE_111, value);
            PipeWriter.publishWrites(output);
            
          //if we have written 10000 items
            //PipeWriter.publishEOF(output);
        }
        
        
        //call this when you are all done.
        requestShutdown();
        */
        
    	// ^^ copy above w/o Pronghorn stuff
    	
        
    }

    //@Override
    public void shutdown() {
        
    }

}

