import javax.sound.sampled.*;

public class Synth
{
	public static final int BUFFER_SIZE = 1;
	public static final int SAMPLE_RATE = 44100;
	public static final int SAMPLE_SIZE = 8; // 16 increases buffer size to 2
	
	public static void tone(double hz, int length, double volume) throws LineUnavailableException
	{
		byte[] buffer = new byte[BUFFER_SIZE];
		AudioFormat format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE, 1, true, false);
		SourceDataLine sdl = AudioSystem.getSourceDataLine(format);
		
		sdl.open(format);
		sdl.start();
		
		for (int i = 0; i < length * SAMPLE_SIZE; i++)
		{
			double angle = i / ((double) SAMPLE_RATE / hz) * 2.0 * Math.PI;
			buffer[0] = (byte) (Math.sin(angle) * 127.0 * volume);
			sdl.write(buffer, 0, 1);
			System.out.println(buffer[0]);
		}
		
		sdl.drain();
		sdl.stop();
		sdl.close();
	}
	
	public static void main(String[] args)
	{
		try
		{
			tone(440.0, 1000, 1.0);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
