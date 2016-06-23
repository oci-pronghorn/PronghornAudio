package com.ociweb.pronghorn.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;

import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.pipe.PipeWriter;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;

public class MicRecord extends PronghornStage{
	
	private Pipe<AudioSchema> input;
    private TargetDataLine microphone;
    private AudioInputStream audioInputStream;
    private AudioFileFormat.Type	targetType;
    private File			outputFile;

	
    protected MicRecord(GraphManager graphManager, Pipe<AudioSchema> output) {
        super(graphManager, NONE, output);
        this.input = input;
    }
    
    @Override
    public void startup() {
    	/*
    	float sampleRate = 0;
        int sampleSizeInBits = 0;
        int channels = 0;
        int frameSize = 0;
        float frameRate = 0;
        boolean bigEndian = false;
        AudioFormat format = new AudioFormat(Encoding.PCM_SIGNED, sampleRate, sampleSizeInBits, channels, frameSize, frameRate, bigEndian);
        */
        
        // ^skip that, hard code the format encoding
        //AudioFormat format = new AudioFormat(Encoding.PCM_SIGNED, 44100.0F, 16, 1, 4, 44100.0F, false);
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 1, 4, 44100.0F, false);
    	
        audioInputStream = new AudioInputStream(microphone);
        
        
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
       
        if (PipeWriter.tryWriteFragment(input, AudioSchema.MSG_SAMPLE_110)) {
            
            //int value = (int)(1000*Math.random());
        	// Get signal from microphone
        	
        	/* We are waiting for the user to press ENTER to
   		   start the recording. (You might find it
   		   inconvenient if recording starts immediately.)
   		*/
         	System.out.println("Press ENTER to start the recording.");
      		try
      		{
      			System.in.read();
      		}
      		catch (IOException e)
      		{
      			e.printStackTrace();
      		}
      		/* Here, the recording is actually started.
      		 */
      		microphone.start();
      		System.out.println("Recording...");
      		
      		System.out.println("Buffer size = " + microphone.getBufferSize());
      		// *** 
      		//microphone.read(b, off, len);
     		try
			{
            	AudioSystem.write(audioInputStream, targetType, outputFile);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}        	//use line method to pull in the mic data.
        	//line.start();
            //microphone.read();
        	

     		/* And now, we are waiting again for the user to press ENTER,
     		   this time to signal that the recording should be stopped.
     		*/
     		
     		System.out.println("Press ENTER to stop the recording.");
     		try
     		{
     			System.in.read();
     		}
     		catch (IOException e)
     		{
     			e.printStackTrace();
     		}

     		/* Here, the recording is actually stopped.
     		 */
     		microphone.stop();
     		microphone.close();
     		System.out.println("Recording stopped.");
     		
     		        	
        	//line.read(byte[] b, int off, int len)
     		// TODO: override read() to use int array instead or cast byte array?
     		// need to use signed shorts instead of bytes
     		/*
     		line.read(b, off, len);
     		try
			{
            	AudioSystem.write(audioInputStream, targetType, outputFile);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			*/
     		
     		
        	/*
        	int value = .get
            PipeWriter.writeInt(input, AudioSchema.MSG_SAMPLE_110_FIELD_VALUE_111, value);
            PipeWriter.publishWrites(input);
            */
        	
        	PipeWriter.writeInt(output, AudioSchema.MSG_SAMPLE_110_FIELD_VALUE_111, value);
            PipeWriter.publishWrites(output);
            
          //if we have written 10000 items
            //PipeWriter.publishEOF(output);
        }
        
        
        //call this when you are all done.
        requestShutdown();
        
    }

    @Override
    public void shutdown() {
        
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
