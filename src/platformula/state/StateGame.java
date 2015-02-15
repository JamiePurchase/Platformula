package platformula.state;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import platformula.graphics.Drawing;

public class StateGame extends State
{
	
	public StateGame()
	{
		
	}
	
	public void render(Graphics g)
	{
		renderBackground(g);
		renderCharacter(g);
	}
	
	public void renderBackground(Graphics g)
	{
		g.drawImage(Drawing.getImage("backgrounds/bkg1.png"),  0, 0, null);
	}
	
	public void renderCharacter(Graphics g)
	{
		g.drawImage(Drawing.getImage("sprites/ItachiIdle1.png"),  400, 400, null);
	}
	
	public void tick()
	{
		tickKey();
	}
	
	public void tickKey()
	{
		
	}
}