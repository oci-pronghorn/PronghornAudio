package com.ociweb.pronghorn.audio;

import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.pipe.PipeWriter;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;

public class GenerateFakeAudio extends PronghornStage {

    private Pipe<AudioSchema> output;
    
    protected GenerateFakeAudio(GraphManager graphManager, Pipe<AudioSchema> output) {
        super(graphManager, NONE, output);
        this.output = output;
    }
    
    @Override
    public void startup() {
        
    }

    @Override
    public void run() {
       
        if (PipeWriter.tryWriteFragment(output, AudioSchema.MSG_SAMPLE_110)) {
            
            int value = (int)(1000*Math.random());
            PipeWriter.writeInt(output, AudioSchema.MSG_SAMPLE_110_FIELD_VALUE_111, value);
            PipeWriter.publishWrites(output);
        }
        
        
        //call this when you are all done.
        //requestShutdown();
        
    }

    @Override
    public void shutdown() {
        
    }
}
