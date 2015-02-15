package platformula;
import platformula.Game;
import platformula.Display;
import platformula.graphics.Fonts;
import platformula.input.Keyboard;
import platformula.state.State;
import platformula.state.StateTitle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JPanel;

public class Game extends JPanel implements Runnable
{
	public static Display display;
	public String title;
	public int width, height;
	private Thread thread;
	private boolean running = false;
	private BufferStrategy bs;
	private Graphics g;
	
	// State Management
	public State gameState;
	public static State gameStateNew;
	public static boolean gameStateChange = false;

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
		if(running==false)
		{
			running = true;
			thread = new Thread(this);
			thread.start();
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
	}

}