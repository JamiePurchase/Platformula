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
	int menuRef;
	String[][] menuOpt = new String[4][4];
	
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
		menuMax = 3;
		menuRef = 1;
		menuOpt[1][1] = "campaign";
		menuOpt[1][2] = "skirmish";
		menuOpt[1][3] = "options";
		menuOpt[2][1] = "new story";
		menuOpt[2][2] = "continue";
		menuOpt[2][3] = "back";
		menuOpt[3][1] = "P1 vs AI";
		menuOpt[3][2] = "P1 vs P2";
		menuOpt[3][3] = "back";

		// Background Animation
		animActive = false;
		animPosX = 0;
		animTick = 0;
		animFrame = 1;
		animFrameMax = 6;
		animTimer = 0;
		
		// Audio
		//Game.audioPlayer.loop("theme1", 1000, 1000, Game.audioPlayer.getFrames("theme1") - 1000);
		Game.audio.playMusic("music1");
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
			String drawImage = "shinobi/Itachi/ItachiRunR" + animFrame + ".png";
			g.drawImage(Drawing.getImage(drawImage), animPosX, 400, null);
		}
		else{g.drawImage(Drawing.getImage("shinobi/Itachi/ItachiIdleR1.png"), 300, 400, null);}
		// Note: Provide more animations for the title screen
	}
	
	public void renderBackground(Graphics g)
	{
		g.drawImage(Drawing.getImage("backgrounds/titleKonoha.png"),  0, 0, null);
		g.drawImage(Drawing.getImage("logo/title.png"),  0, 0, null);
	}
	
	public void renderOptions(Graphics g)
	{
		// Title Menu
		for(int opt=1;opt<=3;opt+=1)
		{
			int drawX = 500;
			if(opt==2){drawX = 520;}
			if(opt==3){drawX = 530;}
			int drawY = (90 * opt) + 360; 
			if(menuRef==1 && menuPos==opt){renderOptionsText(g, menuOpt[1][opt], drawX, drawY, true);}
			else if(menuRef==2 && opt==1){renderOptionsText(g, menuOpt[1][opt], drawX, drawY, true);}
			else if(menuRef==3 && opt==2){renderOptionsText(g, menuOpt[1][opt], drawX, drawY, true);}
			else{renderOptionsText(g, menuOpt[1][opt], drawX, drawY, false);}
		}

		// Campaign Menu
		if(menuRef==2 || menuRef==3)
		{
			for(int opt=1;opt<=3;opt+=1)
			{
				int drawX = 900;
				int drawY = (90 * opt) + 360; 
				if(menuPos==opt){renderOptionsText(g, menuOpt[menuRef][opt], drawX, drawY, true);}
				else{renderOptionsText(g, menuOpt[menuRef][opt], drawX, drawY, false);}
			}
		}
		
		// Cursor
		int cursorX = 430;
		if(menuPos==2){cursorX = 440;}
		if(menuPos==3){cursorX = 450;}
		if(menuRef>1){cursorX = 830;}
		int cursorY = (menuPos * 90) + 365;
		g.setColor(Color.GRAY);
		g.drawString(">", cursorX+2, cursorY+2);
		g.setColor(Color.WHITE);
		g.drawString(">", cursorX+1, cursorY+1);
		g.setColor(Color.ORANGE);
		g.drawString(">", cursorX, cursorY);
	}
	
	public void renderOptionsText(Graphics g, String text, int posX, int posY, boolean select)
	{
		g.setFont(Fonts.fontLargeBold);
		g.setColor(Color.GRAY);
		if(select==true){g.drawString(text, posX+2, posY+2);}
		if(select==true){g.setColor(Color.WHITE);}
		g.drawString(text, posX+1, posY+1);
		g.setColor(Color.WHITE);
		if(select==true){g.setColor(Color.ORANGE);}
		g.drawString(text, posX, posY);
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
			if(menuRef==1)
			{
				if(menuPos==1)
				{
					menuRef = 2;
					menuPos = 1;
				}
				if(menuPos==2)
				{
					menuRef = 3;
					menuPos = 1;
				}
				if(menuPos==2)
				{
					// TBD: Options
				}
			}
			else if(menuRef==2)
			{
				if(menuPos==1)
				{
					Game.worldLoad();
					Game.setStateChange(new StateGame());
				}
				if(menuPos==2)
				{
					// TBD: Load Campaign
				}
				if(menuPos==3)
				{
					menuRef = 1;
					menuPos = 1;
				}
			}
			else if(menuRef==3)
			{
				if(menuPos==1)
				{
					// Skirmish (vs AI)
					Game.worldLoad();
					Game.setStateChange(new StateGame());
				}
				if(menuPos==2)
				{
					// Skirmish (vs 2P)
					Game.worldLoad();
					Game.setStateChange(new StateGame());
				}
				if(menuPos==3)
				{
					menuRef = 1;
					menuPos = 2;
				}
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