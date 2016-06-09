package com.ociweb.pronghorn.audio;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ociweb.pronghorn.pipe.util.build.FROMValidation;

public class SchemaValidation {

    @Test
    public void groveResponseFROMTest() {
        assertTrue(FROMValidation.testForMatchingFROMs("/AudioSchema.xml", AudioSchema.instance));
        assertTrue(FROMValidation.testForMatchingLocators(AudioSchema.instance));
    }
    
  
    
    
}
