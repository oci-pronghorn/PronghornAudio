package GlobalUtilities;

import java.io.Writer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

// TODO Modify TestMic.java to record instead of monitor mic

public class TestMicRecord {
	public static void main(String[] args) {
		AudioFormat format = new AudioFormat(44100, 16, 2, true, true);

		DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format);
		DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, format);
		//System.out.println(targetInfo.toString());
		//System.out.println(sourceInfo.toString());
		
		/*
		source[mic] -> SourceLine -> mixer -> TargetLine-> target[speaker]
		1 										open target
		2 										start target
		3 open source
		4 start source
		 loop: 
		5 target.read ??, reads bytes into input buffer
		6 source.write ?? writes bytes/plays bytes?
		 */

		try {
			TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
			targetLine.open(format);
			targetLine.start();
			
			SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(sourceInfo);
			sourceLine.open(format);
			sourceLine.start();

			int numBytesRead;
			byte[] targetData = new byte[targetLine.getBufferSize() / 5];
			sound2 record = new sound2();
			//record.audioFormat = format;

			while (true) {
				numBytesRead = targetLine.read(targetData, 0, targetData.length);

				if (numBytesRead == -1)	break;

				sourceLine.write(targetData, 0, numBytesRead); // this appears to play the bytes read
				//sourceLine.write(record.myData, 0, numBytesRead);
			}
			record.save();
		}
		catch (Exception e) {
			System.err.println(e);
		}
		 
		
	}

}
