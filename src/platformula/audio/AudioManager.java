package platformula.audio;

public class AudioManager
{
	private static boolean active = true;
	private static boolean musicActive = false;
	private static String musicFile;
	private static boolean soundActive = false;
	private static String soundFile;
	private static int soundTick;
	private static int volume = 100;

	public AudioManager()
	{
		AudioPlayer.init();
		initMusic();
		initSounds();
	}

	public boolean getMusicActive()
	{
		return musicActive;
	}
	
	public boolean getSoundActive()
	{
		return soundActive;
	}
	
	public void initMusic()
	{
		AudioPlayer.load("bgm1.wav", "theme1");
	}
	
	public void initSounds()
	{
		// Note: Do we need these?
		/*AudioPlayer.load("/sounds/collectGarnet.wav", "Garnet");
		AudioPlayer.load("/sounds/collectMushroom.wav", "Mushroom");
		AudioPlayer.load("/sounds/collectTreasure.wav", "Treasure");*/
	}
	
	public void playMusic(String music)
	{
		AudioPlayer.play(music);
		musicActive = true;
		musicFile = music;
	}
	
	public void playSound(String sound)
	{
		AudioPlayer.play(sound);
		soundActive = true;
		soundFile = sound;
		soundTick = 0;
	}
	
	public void setVolume(int newVolume)
	{
		volume = newVolume;
		if(newVolume<1){active = false;}
		else{active = true;}
	}
	
	public void stopMusic()
	{
		AudioPlayer.stop(musicFile);
		musicActive = false;
		musicFile = "";
	}
	
	public void stopSound()
	{
		//AudioPlayer.stop(soundFile);
		soundActive = false;
		soundFile = "";
	}
	
	public void tick()
	{
		if(getSoundActive())
		{
			soundTick += 1;
			//if(soundTick>1){stopSound();}
		}
	}
}