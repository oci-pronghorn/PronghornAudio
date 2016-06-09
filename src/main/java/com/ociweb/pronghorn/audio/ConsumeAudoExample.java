package com.ociweb.pronghorn.audio;

import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.pipe.PipeReader;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;

public class ConsumeAudoExample extends PronghornStage {

    private final Pipe<AudioSchema> input;
    
    
    protected ConsumeAudoExample(GraphManager graphManager, Pipe<AudioSchema> input) {
        super(graphManager, input, NONE);
        this.input = input;
    }

    
    @Override
    public void startup() {
        
    }
    
    
    @Override
    public void run() {
        while (PipeReader.tryReadFragment(input)) {
            switch(PipeReader.getMsgIdx(input)) {
                case AudioSchema.MSG_SAMPLE_110:
                    
                    int value = PipeReader.readInt(input, AudioSchema.MSG_SAMPLE_110_FIELD_VALUE_111);
                    
                    System.out.println("Value: "+value);
                    
                    break;
                case AudioSchema.MSG_SAMPLEWITHTIME_130:
                       
                    long time = PipeReader.readLong(input, AudioSchema.MSG_SAMPLEWITHTIME_130_FIELD_TIME_133);
                    int val = PipeReader.readInt(input, AudioSchema.MSG_SAMPLEWITHTIME_130_FIELD_VALUE_112);
                    
                    
                    break;
                case AudioSchema.MSG_STEREOSAMPLE_120:
                
                    int valueLeft = PipeReader.readInt(input, AudioSchema.MSG_STEREOSAMPLE_120_FIELD_LEFTVALUE_122);
                    int valueRight = PipeReader.readInt(input, AudioSchema.MSG_STEREOSAMPLE_120_FIELD_RIGHTVALUE_122);
                    
                    break;    
                case -1:
                    requestShutdown();
                    return;
                default:
                    throw new UnsupportedOperationException();
            }
            
            PipeReader.releaseReadLock(input);
        }
        
        
    }
    
    
    @Override
    public void shutdown() {
        
    }
    

}
