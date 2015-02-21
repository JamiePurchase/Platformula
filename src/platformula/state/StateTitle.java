package platformula.state;
import platformula.Game;
import platformula.graphics.Drawing;
import platformula.graphics.Fonts;
import platformula.input.Keyboard;
import platformula.state.State;

import java.awt.Color;
import java.awt.Graphics;

public class StateTitle extends State
{
	// Title Menu
	int menuPos;
	int menuMax;
	
	// Background Animation
	boolean animActive = false;
	int animPosX;
	int animTick;
	int animFrame;
	int animFrameMax;
	int animTimer;
	boolean animDone = false;
	
	public StateTitle()
	{
		// Title Menu
		menuPos = 1;
		menuMax = 1;

		// Background Animation
		animActive = false;
		animPosX = 0;
		animTick = 0;
		animFrame = 1;
		animFrameMax = 6;
		animTimer = 0;
	}
	
	public void render(Graphics g)
	{
		renderBackground(g);
		renderOptions(g);
		if(animActive==true){renderAnimation(g);}
	}
	
	public void renderAnimation(Graphics g)
	{
		if(animDone==false)
		{
			String drawImage = "sprites/ItachiRunR" + animFrame + ".png";
			g.drawImage(Drawing.getImage(drawImage), animPosX, 430, null);
		}
		else{g.drawImage(Drawing.getImage("sprites/ItachiIdleR1.png"), 300, 400, null);}
		// Note: Provide more animations for the title screen
	}
	
	public void renderBackground(Graphics g)
	{
		g.drawImage(Drawing.getImage("backgrounds/bkg1.png"),  0, 0, null);
		g.drawImage(Drawing.getImage("logo/title.png"),  0, 0, null);
	}
	
	public void renderOptions(Graphics g)
	{
		g.setFont(Fonts.fontLargeBold);
		g.setColor(Color.BLACK);
		g.drawString("press any key", 501, 451);
		g.drawString("press any key", 502, 452);
		g.drawString("to continue", 521, 541);
		g.drawString("to continue", 522, 542);
		g.setColor(Color.WHITE);
		g.drawString("press any key", 500, 450);
		g.drawString("to continue", 520, 540);
	}
	
	public void tick()
	{
		if(animDone==false){tickAnim();}
		tickKey();
	}
	
	public void tickAnim()
	{
		animTimer += 1;
		if(animActive==true)
		{
			tickAnimFrame();
			tickAnimMove();
		}
		else
		{
			if(animTimer>=60){tickAnimStart();}
		}
	}
	
	public void tickAnimFrame()
	{
		animTick += 1;
		if(animTick>=10)
		{
			animTick = 0;
			animFrame += 1;
			if(animFrame>animFrameMax){animFrame = 1;}
		}
	}
	
	public void tickAnimMove()
	{
		animPosX += 1;
		if(animPosX==300){animDone = true;}
	}
	
	public void tickAnimStart()
	{
		animActive = true;
		animFrame = 1;
		animFrameMax = 6;
	}
	
	public void tickKey()
	{
		if(Keyboard.getKeyPressed()=="Space" || Keyboard.getKeyPressed()=="Enter")
		{
			Keyboard.keyPressedDone();
			if(menuPos==1)
			{
				Game.worldLoad();
				Game.setStateChange(new StateGame());
			}
		}
		if(Keyboard.getKeyPressed()=="Escape")
		{
			Keyboard.keyPressedDone();
			System.exit(0);
		}
		if(Keyboard.getKeyPressed()=="Up" && menuPos>1)
		{
			Keyboard.keyPressedDone();
			menuPos-=1;
		}
		if(Keyboard.getKeyPressed()=="Down" && menuPos<menuMax)
		{
			Keyboard.keyPressedDone();
			menuPos+=1;
		}
	}
}