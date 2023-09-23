package apiPS;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * Created on 02/02/2009
 *  Criado por PalmSoft 
 *  Vers�o 1.3
 */

public class PS_SoundPlayer {

	@SuppressWarnings("unchecked")
	ArrayList listoftracks;

	public boolean mute;

	@SuppressWarnings("unchecked")
	public PS_SoundPlayer() {
		listoftracks = new ArrayList();
		mute = false;
	}

	@SuppressWarnings("unchecked")
	public void addtrack(String fnm, int ntracks, boolean looping) {

		TrackControler tc = new TrackControler(this, fnm, ntracks, looping);

		listoftracks.add(tc);
		System.out.println("tc = "+tc);
		System.out.println("listoftracks.size() = "+listoftracks.size());
		/*
		 * AudioInputStream stream; try { stream =
		 * AudioSystem.getAudioInputStream( getClass().getResource(fnm) );
		 * 
		 * AudioFormat format = stream.getFormat(); // convert ULAW/ALAW formats
		 * to PCM format
		 * 
		 * 
		 * 
		 * if((format.getEncoding()==AudioFormat.Encoding.ULAW)||(format.getEncoding
		 * ()==AudioFormat.Encoding.ALAW)){
		 * 
		 * AudioFormat newFormat = new
		 * AudioFormat(AudioFormat.Encoding.PCM_SIGNED
		 * ,format.getSampleRate(),format
		 * .getSampleSizeInBits()*2,format.getChannels
		 * (),format.getFrameSize()*2,format.getFrameRate(), true); // big
		 * endian // update stream and format details stream =
		 * AudioSystem.getAudioInputStream(newFormat, stream);
		 * System.out.println("Converted Audio format: " + newFormat); format =
		 * newFormat; } DataLine.Info info = new DataLine.Info(Clip.class,
		 * format); // make sure the sound system supports this data line if
		 * (!AudioSystem.isLineSupported(info)) {
		 * System.out.println("Unsupported Clip File: " + fnm); System.exit(0);
		 * }
		 * 
		 * Clip clip = null; try { clip = (Clip) AudioSystem.getLine(info);
		 * clip.addLineListener(this); clip.open(stream);
		 * 
		 * double duration = clip.getBufferSize() / (format.getFrameSize() *
		 * format.getFrameRate()); } catch (IOException e) {
		 * e.printStackTrace(); } catch (LineUnavailableException e) {
		 * e.printStackTrace(); }
		 * 
		 * 
		 * listofclips.add(clip);
		 * 
		 * } catch (UnsupportedAudioFileException e) { e.printStackTrace(); }
		 * catch (IOException e) { e.printStackTrace(); }
		 */

	}

	public void playTrack(int itrack) {

		if (mute) {
			return;
		}
		
		((TrackControler) listoftracks.get(itrack)).Play();

		/*
		 * Clip clip = (Clip)listofclips.get(itrack);
		 * 
		 * if ((clip != null)&&(clip.isRunning()==false)){
		 * 
		 * //clip.stop(); clip.start(); // start playing
		 * //System.out.println(" DeuPlay"); }else
		 * if(clip.getMicrosecondPosition()>140000){ //clip.stop();
		 * //clip.setMicrosecondPosition(100); clip.setFramePosition(0);
		 * //clip.start(); // start playing //System.out.println(" replay"); }
		 * 
		 * System.out.println("play....");
		 */
	}
	
	public void playTrack(int itrack,float volume,float balance) {

		if (mute) {
			return;
		}
		
		((TrackControler) listoftracks.get(itrack)).Play(volume,balance);
	}

	public void Stop() {
		for (int i = 0; i < listoftracks.size(); i++) {
			((TrackControler) listoftracks.get(i)).Stop();
		}
	}

	public void Stop(int itrack) {
		((TrackControler) listoftracks.get(itrack)).Stop();
	}
	
	public void update(LineEvent event) {
		System.out.println("bla " + event.getFramePosition());
	}

}

class TrackControler implements LineListener {

	boolean forcedstop = false;

	@SuppressWarnings("unchecked")
	ArrayList listofclips;

	boolean looping = false;

	int ntracks;

	PS_SoundPlayer soundplayer = null;

	@SuppressWarnings("unchecked")
	public TrackControler(PS_SoundPlayer soundplayer, String fnm, int nclips,
			boolean looping) {

		this.soundplayer = soundplayer;

		this.looping = looping;

		listofclips = new ArrayList();

		ntracks = nclips;

		for (int i = 0; i < nclips; i++) {
			addtrack(fnm);
		}
	}

	@SuppressWarnings("unchecked")
	public void addtrack(String fnm) {
		AudioInputStream stream;
		System.out.println("entrou em addtrack! fnm = "+fnm);
		try {
			stream = AudioSystem.getAudioInputStream(getClass().getResource("/"+fnm));
			//stream = AudioSystem.getAudioInputStream(new BufferedInputStream(Constantes.getImputStream(fnm)));

			AudioFormat format = stream.getFormat();
			// convert ULAW/ALAW formats to PCM format

			if ((format.getEncoding() == AudioFormat.Encoding.ULAW)
					|| (format.getEncoding() == AudioFormat.Encoding.ALAW)) {

				AudioFormat newFormat = new AudioFormat(
						AudioFormat.Encoding.PCM_SIGNED,
						format.getSampleRate(),
						format.getSampleSizeInBits() * 2, format.getChannels(),
						format.getFrameSize() * 2, format.getFrameRate(), true); // big
				// endian
				// update stream and format details
				stream = AudioSystem.getAudioInputStream(newFormat, stream);
				System.out.println("Converted Audio format: " + newFormat);
				format = newFormat;
			}
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			// make sure the sound system supports this data line
			if (!AudioSystem.isLineSupported(info)) {
				System.out.println("Unsupported Clip File: " + fnm);
				System.exit(0);
			}

			Clip clip = null;
			try {

				clip = (Clip) AudioSystem.getLine(info);
				clip.addLineListener(this);
				clip.open(stream);
				
//				System.out.println("Balance suport "+clip.isControlSupported(FloatControl.Type.BALANCE));
//				if(clip.isControlSupported(FloatControl.Type.BALANCE)){
//					((FloatControl)clip.getControl(FloatControl.Type.BALANCE)).setValue(1.0f);
//				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}

			listofclips.add(clip);
			
			stream = null;

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("2222222");
			e.printStackTrace();
			
		}

	}

	public void Play(float volume,float balance){
		int nframes = 0;
		for (int i = 0; i < listofclips.size(); i++) {
			Clip clip = (Clip) listofclips.get(i);

			nframes = clip.getFrameLength();

			if ((clip != null) && (clip.isRunning() == false)) {


				
				
				// System.out.println(" play");
				clip.stop();
				clip.setFramePosition(0);
				
//				if(clip.isOpen()){
//					Control control[] = clip.getControls();
//					System.out.println(" Controls "+control.length);
//					for(int j = 0; j < control.length;j++){
//						System.out.println("Type "+control[j].getType());
//					}
//					
//				}
					
				if( clip.isControlSupported(FloatControl.Type.MASTER_GAIN)){
					FloatControl m_gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					if (volume < -80.0) volume = -80.0f;
					m_gainControl.setValue(volume);
					//System.out.println("volume "+volume);
				}
				if(clip.isControlSupported(FloatControl.Type.BALANCE)){
					((FloatControl)clip.getControl(FloatControl.Type.BALANCE)).setValue(balance);
				}
				//else{
					//System.out.println(" nao suporta "+clip.isControlSupported(FloatControl.Type.MASTER_GAIN));
				//}					

				clip.start(); // start playing
				// System.out.println(" DeuPlay");
				forcedstop = false;
				return;

			}
		}

		int minframe = (nframes * 3) / 4;

		for (int i = 0; i < listofclips.size(); i++) {
			Clip clip = (Clip) listofclips.get(i);

			if (clip.getFramePosition() > minframe) {

				if( clip.isControlSupported(FloatControl.Type.MASTER_GAIN)){
					FloatControl m_gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					m_gainControl.setValue(volume);
				}
				if(clip.isControlSupported(FloatControl.Type.BALANCE)){
					((FloatControl)clip.getControl(FloatControl.Type.BALANCE)).setValue(balance);
				}				
				// clip.stop();
				// clip.start(); // start playing
				clip.setFramePosition(0);
				// System.out.println(" DeuPlay da 3/4");

				forcedstop = false;
				return;
			}
		}
	}
	
		
	public void Play() {
		int nframes = 0;
		for (int i = 0; i < listofclips.size(); i++) {
			Clip clip = (Clip) listofclips.get(i);

			nframes = clip.getFrameLength();

			if ((clip != null) && (clip.isRunning() == false)) {

				// System.out.println(" play");
				clip.stop();
				clip.setFramePosition(0);
				clip.start(); // start playing
				// System.out.println(" DeuPlay");
				forcedstop = false;
				return;

			}
		}

		int minframe = (nframes * 3) / 4;

		for (int i = 0; i < listofclips.size(); i++) {
			Clip clip = (Clip) listofclips.get(i);

			if (clip.getFramePosition() > minframe) {

				// clip.stop();
				// clip.start(); // start playing
				clip.setFramePosition(0);
				// System.out.println(" DeuPlay da 3/4");

				forcedstop = false;
				return;
			}
		}

		// System.out.println(" descartado");

	}

	public void Stop() {
		forcedstop = true;
		for (int i = 0; i < listofclips.size(); i++) {
			Clip clip = (Clip) listofclips.get(i);
			clip.stop();
			// System.out.println("Stop");
		}
	}

	public void update(LineEvent event) {
		// event.toString();

		if (looping && (event.getType() == LineEvent.Type.STOP)) {
			if (!soundplayer.mute && !forcedstop) {
				Play();
			}
		}
		// System.out.println("Coiso "+event.getFramePosition());
	}

}
