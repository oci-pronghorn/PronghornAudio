package com.ociweb.pronghorn.audio;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.pipe.PipeConfig;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;
import com.ociweb.pronghorn.stage.scheduling.ThreadPerStageScheduler;
import com.ociweb.pronghorn.stage.test.ConsoleJSONDumpStage;
import com.ociweb.pronghorn.stage.test.PipeCleanerStage;

public class DemoApp {

    private final PipeConfig<AudioSchema> config = new PipeConfig<AudioSchema>(AudioSchema.instance, 1000);

    public static void main(String[] args) {
       
        DemoApp instance = new DemoApp();
        
        // RECORD/WRITE
        
		String	strFilename = "recording1.wav";
		File	outputFile = new File(strFilename);

		/* For simplicity, the audio data format used for recording
		   is hardcoded here. We use PCM 44.1 kHz, 16 bit signed,
		   stereo.
		*/
		AudioFormat	audioFormat = new AudioFormat(
			AudioFormat.Encoding.PCM_SIGNED,
			44100.0F, 16, 2, 4, 44100.0F, false);

		/* Now, we are trying to get a TargetDataLine. The
		   TargetDataLine is used later to read audio data from it.
		   If requesting the line was successful, we are opening
		   it (important!).
		*/
		DataLine.Info	info = new DataLine.Info(TargetDataLine.class, audioFormat);
		TargetDataLine	targetDataLine = null;
		try
		{
			targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
			targetDataLine.open(audioFormat);
		}
		catch (LineUnavailableException e)
		{
			out("unable to get a recording line");
			e.printStackTrace();
			System.exit(1);
		}

		/* Again for simplicity, we've hardcoded the audio file
		   type, too.
		*/
		AudioFileFormat.Type	targetType = AudioFileFormat.Type.WAVE;

        
		GraphManager graphManager = instance.buildGraph(new GraphManager());
        
        ThreadPerStageScheduler scheduler = new ThreadPerStageScheduler(graphManager);
        
        scheduler.startup();
        
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        
        scheduler.shutdown();
        scheduler.awaitTermination(10, TimeUnit.SECONDS);
        
        // TODO
        // READ
        
        // PROCESS
        
        // MONITORING / REALTIME RESPONSE
        
        
    }
        

    private GraphManager buildGraph(GraphManager graphManager) {
       
        Pipe<AudioSchema> pipe1 = new Pipe<AudioSchema>(config);
        //Pipe<AudioSchema> pipe2 = new Pipe<AudioSchema>(config);
        //Pipe<AudioSchema> pipe3 = new Pipe<AudioSchema>(config);
        
        MicRecord prod = new  MicRecord(graphManager, pipe1);
        
        PipeCleanerStage<AudioSchema> cleaner = new PipeCleanerStage<>(graphManager, pipe1);
        ConsoleJSONDumpStage<AudioSchema> dump = new ConsoleJSONDumpStage<AudioSchema>(graphManager, pipe1);
        //ConsumeAudoExample dump2 = new ConsumeAudoExample(graphManager, pipe1);
        
        return graphManager;
    }

	private static void out(String strMessage)
	{
		System.out.println(strMessage);
	}

    
    

}


