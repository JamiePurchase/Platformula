package platformula.state;
import platformula.graphics.Drawing;
import platformula.graphics.Tileset;
import platformula.input.Keyboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StateGame extends State
{
	public String characterName;
	public String characterAction;
	public int characterActionTick;
	public int characterPosX;
	public int characterPosY;
	public String characterDirH;
	public String characterDirV;
	public int characterFrame;
	public int characterFrameMax;
	public int characterFrameTick;
	public int characterOffsetXRun;
	public int characterOffsetYRun;
	public BufferedImage[] characterAnimIdleR;
	public int characterVelocityJump;
	public boolean characterLand;
	
	public StateGame()
	{
		characterName = "Itachi";
		characterAction = "Idle";
		characterActionTick = 0;
		characterPosX = 300;
		characterPosY = 400;
		characterDirH = "R";
		characterDirV = "";
		characterFrame = 1;
		characterFrameMax = 1;
		characterFrameTick = 0;
		characterOffsetXRun = 0;
		characterOffsetYRun = 30;
		//characterAnimIdleR = Tileset.getTileset("sprites/ItachiIdleR.png", 7, 1);
		characterVelocityJump = 0;
		characterLand = true;
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
		String drawImage = "sprites/" + characterName + characterAction + characterFrame + ".png";
		int drawPosX = characterPosX;
		int drawPosY = characterPosY;
		if(characterAction=="Run")
		{
			drawPosX += characterOffsetXRun;
			drawPosY += characterOffsetYRun;
		}
		
		// Temp (something is wrong with the tileset - fix this!)
		/*if(characterAction=="Idle" && characterDirection=="R")
		{
			// characterFrame-1
			BufferedImage newImage = characterAnimIdleR[0];
			g.drawImage(newImage, drawPosX, drawPosY, null);
		}
		else
		{*/
			g.drawImage(Drawing.getImage(drawImage), drawPosX, drawPosY, null);
		//}
	}
	
	public void tick()
	{
		tickCharacter();
		tickKey();
	}
	
	public void tickCharacter()
	{
		tickCharacterAction();
		tickCharacterFrame();
	}
	
	public void tickCharacterAction()
	{
		// Note: This takes too long but i'm choosing to keep these variables for now
		/*characterActionTick += 1;
		if(characterActionTick>=10)
		{
			characterActionTick = 0;
			if(characterAction=="Run")
			{
				if(characterDirection=="R"){characterPosX+=1;}
				if(characterDirection=="L"){characterPosX-=1;}
			}
		}*/
		
		// Slight movement every tick
		if(characterAction=="Run")
		{
			if(characterDirH=="R"){characterPosX+=1;}
			if(characterDirH=="L"){characterPosX-=1;}
		}
		if(characterAction=="Jump" || characterAction=="Float")
		{
			if(characterDirV=="U")
			{
				characterPosY -= characterVelocityJump;
				characterVelocityJump -= 1;
				if(characterVelocityJump<1)
				{
					characterDirV = "D";
					characterVelocityJump = 1;
				}
			}
			else
			{
				characterPosY += characterVelocityJump;
				if(characterPosY>=400)
				{
					characterAction = "Idle";
					characterPosY = 400;
					characterDirV = "";
					characterFrame = 1;
					characterFrameMax = 1;
					characterFrameTick = 0;
					characterVelocityJump = 0;
					characterLand = true;
				}
			}
		}
	}
	
	public void tickCharacterFrame()
	{
		characterFrameTick += 1;
		if(characterFrameTick>=10)
		{
			characterFrameTick = 0;
			characterFrame += 1;
			if(characterFrame>characterFrameMax)
			{
				characterFrame = 1;
				if(characterAction=="Jump")
				{
					characterAction = "Float";
					characterFrame = 1;
					characterFrameMax = 1;
				}
			}
		}
	}
	
	public void tickKey()
	{
		tickKeyPressed();
		tickKeyReleased();
	}
	
	public void tickKeyPressed()
	{
		if(Keyboard.getKeyPressed()=="Space")
		{
			Keyboard.keyPressedDone();
			if(characterAction!="Jump")
			{
				// Note: If the character is running, they should jump in that direction
				if(characterLand == true)
				{
					characterAction = "Jump";
					characterActionTick = 0;
					characterDirV = "U";
					characterFrame = 1;
					characterFrameMax = 3;
					characterFrameTick = 0;
					characterVelocityJump = 20;
					characterLand = false;
				}
				// Note: Could allow pressing space again for double jump?
			}
		}
		if(Keyboard.getKeyPressed()=="Right")
		{
			Keyboard.keyPressedDone();
			if(characterAction!="Run")
			{
				if(characterLand == true)
				{
					characterAction = "Run";
					characterActionTick = 0;
					characterDirH = "R";
					characterFrame = 1;
					characterFrameMax = 6;
					characterFrameTick = 0;
				}
				// Note: Could allow some horizontal movement while in the air?
			}
		}
		if(Keyboard.getKeyPressed()=="D")
		{
			Keyboard.keyPressedDone();
			if(characterAction!="Guard")
			{
				if(characterLand == true)
				{
					characterAction = "Guard";
					characterActionTick = 0;
					characterFrame = 1;
					characterFrameMax = 1;
					characterFrameTick = 0;
				}
			}
		}
	}
	
	public void tickKeyReleased()
	{
		if(Keyboard.getKeyReleased()=="Right" && characterAction=="Run")
		{
			Keyboard.keyReleasedDone();
			if(characterLand == true)
			{
				characterAction = "Idle";
				characterActionTick = 0;
				characterFrame = 1;
				characterFrameMax = 1;
				characterFrameTick = 0;
			}
		}
		if(Keyboard.getKeyReleased()=="D" && characterAction=="Guard")
		{
			// Debug
			System.out.println("1");
			
			Keyboard.keyReleasedDone();
			if(characterLand == true)
			{
				//
				System.out.println("2");
				
				characterAction = "Idle";
				characterActionTick = 0;
				characterFrame = 1;
				characterFrameMax = 1;
				characterFrameTick = 0;
			}
		}
	}
}