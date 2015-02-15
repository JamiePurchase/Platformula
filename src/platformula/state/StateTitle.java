package platformula.state;
import platformula.Game;
import platformula.graphics.Fonts;
import platformula.input.Keyboard;
import platformula.state.State;

import java.awt.Color;
import java.awt.Graphics;

public class StateTitle extends State
{
	int menuPos;
	int menuMax;
	
	public StateTitle()
	{
		menuPos = 1;
		menuMax = 1;
	}
	
	public void render(Graphics g)
	{
		renderBackground(g);
		renderOptions(g);
	}
	
	public void renderBackground(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1366, 768);
	}
	
	public void renderOptions(Graphics g)
	{
		g.setFont(Fonts.fontStandard);
		g.setColor(Color.WHITE);
		g.drawString("Press any key to continue", 200, 300);
	}
	
	public void tick()
	{
		tickKey();
	}
	
	public void tickKey()
	{
		if(Keyboard.getKeyPressed()=="Space" || Keyboard.getKeyPressed()=="Enter")
		{
			Keyboard.setKeyDone();
			if(menuPos==1)
			{
				Game.setStateChange(new StateGame());
				Keyboard.setKeyDone();
			}
		}
		if(Keyboard.getKeyPressed()=="Escape")
		{
			// Quit
			System.exit(0);
		}
		if(Keyboard.getKeyPressed()=="Up" && menuPos>1)
		{
			menuPos-=1;
			Keyboard.setKeyDone();
		}
		if(Keyboard.getKeyPressed()=="Down" && menuPos<menuMax)
		{
			menuPos+=1;
			Keyboard.setKeyDone();
		}
	}
}