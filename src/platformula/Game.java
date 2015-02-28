package platformula;
import platformula.Game;
import platformula.Display;
import platformula.audio.AudioManager;
import platformula.audio.AudioPlayer;
import platformula.graphics.Fonts;
import platformula.input.Keyboard;
import platformula.state.State;
import platformula.state.StateTitle;
import platformula.world.World;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable
{
	// Application
	public static Display display;
	public String title;
	public int width, height;
	private Thread thread;
	private boolean running = false;
	
	// Graphics
	private Graphics g;
	private BufferStrategy bs;
	
	// Audio
	public static AudioManager audio;
	
	// State Management
	public State gameState;
	public static State gameStateNew;
	public static boolean gameStateChange = false;
	
	// Game World
	public static World world;
	
	// Development Mode
	public static boolean development;

	public Game()
	{
		this.title = "Platformula";
		this.width = 1366;
		this.height = 768;
	}
	
	private void init()
	{
		// Create Display
		display = new Display(title, width, height);
		
		// Load Resources
		//Assets.init();
		Fonts.init();

		// Audio
		audio = new AudioManager();
		
		// Title Menu
		audio.playMusic("music1");
		State.setState(new StateTitle());
	}

	private void render()
	{
		// Buffer strategy
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null)
		{
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		
		// Graphics start
		g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		// Graphics draw
		if(State.getState() != null)
		{
			State.getState().render(g);
		}

		// Graphics done
		bs.show();
		g.dispose();
	}
	
	public void run()
	{
		// Render speed
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		// Create window
		init();
		
		// Main game loop
		while(running)
		{
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			if(delta >= 1)
			{
				tick();
				render();
				ticks++;
				delta--;
			}
			if(timer >= 1000000000)
			{
				ticks = 0;
				timer = 0;
			}
		}
		stop();
	}
	
	public static void setStateChange(State newState)
	{
		Game.gameStateChange = true;
		Game.gameStateNew = newState;
	}
	
	public synchronized void start()
	{
		start(false);
	}
	
	public synchronized void start(boolean dev)
	{
		if(running==false)
		{
			running = true;
			thread = new Thread(this);
			thread.start();
			development = dev;
		}
	}
	
	public synchronized void stop()
	{
		if(running==true)
		{
			try
			{
				thread.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void tick()
	{
		if(gameStateChange==true){tickStateChange();}
		State.getState().tick();
	}

	public void tickStateChange()
	{
		State.setState(gameStateNew);
		gameStateChange = false;
		Keyboard.keyPressedDone();
	}
	
	public static void worldLoad()
	{
		world = new World();
	}

}