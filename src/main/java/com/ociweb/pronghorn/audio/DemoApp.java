package com.ociweb.pronghorn.audio;

import java.util.concurrent.TimeUnit;

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
        
    }

    private GraphManager buildGraph(GraphManager graphManager) {
       
        
        
        
        Pipe<AudioSchema> pipe1 = new Pipe<AudioSchema>(config);
        //Pipe<AudioSchema> pipe2 = new Pipe<AudioSchema>(config);
        //Pipe<AudioSchema> pipe3 = new Pipe<AudioSchema>(config);
        
        GenerateFakeAudio prod = new  GenerateFakeAudio(graphManager, pipe1);
        
       // PipeCleanerStage<AudioSchema> cleaner = new PipeCleanerStage<>(graphManager, pipe1);
       // ConsoleJSONDumpStage<AudioSchema> dump = new ConsoleJSONDumpStage<AudioSchema>(graphManager, pipe1);
        ConsumeAudoExample dump2 = new ConsumeAudoExample(graphManager, pipe1);
        
        return graphManager;
    }

}
