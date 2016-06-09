package com.ociweb.pronghorn.audio;

import com.ociweb.pronghorn.pipe.FieldReferenceOffsetManager;
import com.ociweb.pronghorn.pipe.MessageSchema;

public class AudioSchema extends MessageSchema {

    public final static FieldReferenceOffsetManager FROM = new FieldReferenceOffsetManager(
            new int[]{0xc0400002,0x88000000,0xc0200002,0xc0400003,0x88000001,0x88000001,0xc0200003,0xc0400003,0x88000002,0x90000000,0xc0200003},
            (short)0,
            new String[]{"Sample","Value",null,"StereoSample","LeftValue","RightValue",null,"SampleWithTime","Value","Time",null},
            new long[]{110, 111, 0, 120, 122, 122, 0, 130, 112, 133, 0},
            new String[]{"global",null,null,"global",null,null,null,"global",null,null,null},
            "AudioSchema.xml",
            new long[]{2, 2, 0},
            new int[]{2, 2, 0});
    
    public static AudioSchema instance = new AudioSchema();
    
    public static final int MSG_SAMPLE_110 = 0x00000000;
    public static final int MSG_SAMPLE_110_FIELD_VALUE_111 = 0x00400001;
    public static final int MSG_STEREOSAMPLE_120 = 0x00000003;
    public static final int MSG_STEREOSAMPLE_120_FIELD_LEFTVALUE_122 = 0x00400001;
    public static final int MSG_STEREOSAMPLE_120_FIELD_RIGHTVALUE_122 = 0x00400002;
    public static final int MSG_SAMPLEWITHTIME_130 = 0x00000007;
    public static final int MSG_SAMPLEWITHTIME_130_FIELD_VALUE_112 = 0x00400001;
    public static final int MSG_SAMPLEWITHTIME_130_FIELD_TIME_133 = 0x00800002;

    
    private AudioSchema() {
        super(FROM);
    }

}
