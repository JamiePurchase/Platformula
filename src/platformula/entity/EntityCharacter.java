package platformula.entity;
import platformula.Game;
import platformula.graphics.Drawing;
import platformula.input.Keyboard;

import java.awt.Color;
import java.awt.Graphics;

public class EntityCharacter
{
	public String characterName;
	public String characterAction;
	public boolean characterActionBusy;
	public boolean characterActionEffect;
	public int characterActionEffectFrame;
	public String characterActionEffectName;
	public int characterActionEffectOffsetH;
	public int characterActionEffectOffsetV;
	public int characterActionTick;
	public String characterActionType;
	public String characterActionTypeChange;
	public int characterActionTypeFrame;
	public String characterEffectName;
	public int characterPosH;
	public int characterPosV;
	public String characterDirH;
	public String characterDirV;
	public int characterFrame;
	public int characterFrameMax;
	public int characterFrameTick;
	public int characterFrameTickMax;
	public boolean characterLand;
	public int characterVelocityJump;
	public boolean characterEffectAdd;
	public EntityEffect characterEffectEntity;
	public int characterStatHealthNow;
	public int characterStatHealthMax;
	public int characterStatHealthReduce;
	public int characterStatChakraNow;
	public int characterStatChakraMax;
	public int characterStatChakraReduce;
	public int characterCollisionRadius;
	
	public EntityCharacter(String name, int posH, int posV)
	{
		characterName = name;
		characterDirV = "";
		characterLand = true;
		characterVelocityJump = 0;
		characterEffectAdd = false;
		characterStatHealthNow = 100;
		characterStatHealthMax = 100;
		characterStatChakraNow = 100;
		characterStatChakraMax = 100;
		characterCollisionRadius = 64;
		setAction("Idle");
		setDirection("R");
		setPosition(posH, posV);
	}
	
	public int getStatChakraPercent()
	{
		float percent = (characterStatChakraNow / characterStatChakraMax) * 100;
		
		// Debug
		//System.out.println("(" + characterStatChakraNow + "/" + characterStatChakraMax + ") * 100 = " +percent);
		
		return (int) percent;
	}
	
	public int getStatHealthPercent()
	{
		float percent = (characterStatHealthNow / characterStatHealthMax) * 100;
		
		// Debug
		//System.out.println("(" + characterStatHealthNow + "/" + characterStatHealthMax + ") * 100 = " +percent);
		return (int) percent;
	}
	
	public void render(Graphics g)
	{
		String drawImage = "sprites/" + characterName + characterAction + characterDirH + characterFrame + ".png";
		g.drawImage(Drawing.getImage(drawImage), characterPosH, characterPosV, null);
		if(Game.development==true){renderCollision(g);}
	}
	
	public void renderCollision(Graphics g)
	{
		int drawX = characterPosH + 16;
		int drawY = characterPosV + 16;
		g.setColor(Color.BLUE);
		g.drawOval(drawX, drawY, characterCollisionRadius, characterCollisionRadius);
		g.drawOval(drawX, drawY, characterCollisionRadius-1, characterCollisionRadius-1);
	}
	
	public void setAction(String action)
	{
		characterAction = action;
		characterActionBusy = false;
		characterActionTick = 0;
		characterActionEffect = false;
		characterFrame = 1;
		characterFrameTick = 0;
		characterFrameTickMax = 10;
		if(action=="AttackC")
		{
			characterActionBusy = true;
			characterActionType = "Change";
			characterActionTypeChange = "Idle";
			characterFrameMax = 4;
		}
		if(action=="Charge")
		{
			characterActionBusy = false;
			characterActionType = "Repeat";
			characterActionTypeFrame = 2;
			characterFrameMax = 2;
		}
		if(action=="DamageA")
		{
			characterActionBusy = true;
			characterActionType = "Change";
			characterActionTypeChange = "Idle";
			characterFrameMax = 3;
		}
		if(action=="Dash")
		{
			characterActionBusy = true;
			characterActionType = "Change";
			characterActionTypeChange = "Idle";
			characterFrameMax = 3;
			characterFrameTickMax = 5;
		}
		if(action=="Float")
		{
			characterActionBusy = true;
			characterActionType = "Infinite";
			characterFrameMax = 1;
		}
		if(action=="Guard")
		{
			characterActionBusy = false;
			characterActionType = "Infinite";
			characterFrameMax = 1;
		}
		if(action=="Hide")
		{
			characterActionBusy = false;
			characterActionType = "Infinite";
			characterFrameMax = 1;
		}
		if(action=="Idle")
		{
			characterActionBusy = false;
			characterActionType = "Infinite";
			characterFrameMax = 7;
		}
		if(action=="Jump")
		{
			characterActionBusy = false;
			characterActionType = "Change";
			characterActionTypeChange = "Float";
			characterDirV = "U";
			characterFrameMax = 3;
			characterLand = false;
			characterVelocityJump = 20;
		}
		if(action=="JutsuA")
		{
			characterActionBusy = true;
			characterActionEffect = true;
			characterActionEffectFrame = 5;
			characterActionEffectName = "Fireball";
			characterActionEffectOffsetH = 96;
			characterActionEffectOffsetV = 0;
			characterActionType = "Change";
			characterActionTypeChange = "Idle";
			characterFrameMax = 7;
		}
		if(action=="Run")
		{
			characterActionBusy = false;
			characterActionType = "Infinite";
			characterFrameMax = 6;
		}
		if(action=="SpcA")
		{
			characterActionBusy = true;
			characterActionType = "Change";
			characterActionTypeChange = "Hide";
			characterFrameMax = 7;
			characterFrameTickMax = 5;
		}
	}
	
	public void setDirection(String direction)
	{
		characterDirH = direction;
	}
	
	public void setPosition(int posH, int posV)
	{
		characterPosH = posH;
		characterPosV = posV;
	}

	public void tick()
	{
		tickCharacter();
		tickKey();
	}
	
	public void tickCharacter()
	{
		tickCharacterStat();
		tickCharacterAction();
		tickCharacterFrame();
	}
	
	public void tickCharacterStat()
	{
		if(characterStatHealthReduce>0)
		{
			characterStatHealthNow -= 1;
			characterStatHealthReduce -= 1;
		}
		if(characterStatChakraReduce>0)
		{
			characterStatChakraNow -= 1;
			characterStatChakraReduce -= 1;
		}
		if(characterAction=="Charge" && characterFrame==2)
		{
			if(characterStatChakraNow<characterStatChakraMax){characterStatChakraNow += 1;}
		}
	}
	
	public void tickCharacterAction()
	{
		if(characterActionEffect==true && characterActionEffectFrame==characterFrame)
		{
			int addPosH = characterPosH - characterActionEffectOffsetH;
			if(characterDirH=="R"){addPosH = characterPosH + characterActionEffectOffsetH;}
			Game.world.effectAdd(new EntityEffect(characterActionEffectName, addPosH, characterPosV, characterDirH));
			characterActionEffect = false;
		}
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
		if(characterAction=="Dash")
		{
			if(characterFrame==3)
			{
				if(characterDirH=="R"){characterPosH+=10;}
				if(characterDirH=="L"){characterPosH-=10;}
			}
		}
		if(characterAction=="Run")
		{
			if(characterDirH=="R"){characterPosH+=1;}
			if(characterDirH=="L"){characterPosH-=1;}
		}
		// Note: For the above, use a general velocity int and move accordingly (rather than looking for specific actions)
		
		if(characterAction=="Jump" || characterAction=="Float")
		{
			if(characterDirV=="U")
			{
				characterPosV -= characterVelocityJump;
				characterVelocityJump -= 1;
				if(characterVelocityJump<1)
				{
					characterDirV = "D";
					characterVelocityJump = 1;
				}
			}
			else
			{
				characterPosV += characterVelocityJump;
				if(characterPosV>=400)
				{
					setAction("Idle");
					characterPosV = 400;
					characterDirV = "";
					characterVelocityJump = 0;
					characterLand = true;
				}
			}
		}
	}
	
	public void tickCharacterFrame()
	{
		characterFrameTick += 1;
		if(characterFrameTick>=characterFrameTickMax)
		{
			characterFrameTick = 0;
			characterFrame += 1;
			if(characterFrame>characterFrameMax)
			{
				// Debug
				/*String debug = "Action: " + characterAction + ", Type: " + characterActionType;
				if(characterActionType=="Change"){debug += ", Change: " + characterActionTypeChange;}
				if(characterActionType=="Repeat"){debug += ", Frame: " + characterActionTypeFrame;}
				System.out.println(debug);*/
				
				// If the action repeats indefinitely, rewind the action frame to the start
				if(characterActionType=="Infinite")
				{
					characterFrame = 1;
				}
				
				// If the action changes to another after the final frame, call set action with the action string specified
				else if(characterActionType=="Change")
				{
					setAction(characterActionTypeChange);
				}
				
				// If the action repeats until something else happens, rewind the action frame to the int specified
				else if(characterActionType=="Repeat")
				{
					characterFrame = characterActionTypeFrame;
				}
			}
		}
	}
	
	public void tickKey()
	{
		if(characterActionBusy==false)
		{
			tickKeyPressed();
			tickKeyReleased();
		}
		else
		{
			Keyboard.keyPressedDone();
			// Note: Do we need key released here too?
		}
	}
	
	public void tickKeyPressed()
	{
		if(Keyboard.getKeyPressed()=="Space")
		{
			Keyboard.keyPressedDone();
			if(characterAction!="Jump")
			{
				// Note: If the character is running, they should jump in that direction
				if(characterLand == true){setAction("Jump");}
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
					setAction("Run");
					characterDirH = "R";
				}
				// Note: Could allow some horizontal movement while in the air?
			}
		}
		if(Keyboard.getKeyPressed()=="Left")
		{
			Keyboard.keyPressedDone();
			if(characterAction!="Run")
			{
				if(characterLand == true)
				{
					setAction("Run");
					characterDirH = "L";
				}
			}
		}
		if(Keyboard.getKeyPressed()=="A")
		{
			Keyboard.keyPressedDone();
			if(characterAction!="AttackC")
			{
				if(characterLand == true){setAction("AttackC");}
			}
		}
		if(Keyboard.getKeyPressed()=="D")
		{
			Keyboard.keyPressedDone();
			if(characterAction!="Guard")
			{
				if(characterLand == true){setAction("Guard");}
			}
		}
		if(Keyboard.getKeyPressed()=="E")
		{
			Keyboard.keyPressedDone();
			if(characterAction!="Charge")
			{
				if(characterLand == true){setAction("Charge");}
			}
		}
		if(Keyboard.getKeyPressed()=="S")
		{
			Keyboard.keyPressedDone();
			if(characterAction!="JutsuA")
			{
				if(characterStatChakraNow>=40)
				{
					if(characterLand == true){setAction("JutsuA");}
					characterStatChakraReduce = 40;
				}
			}
		}
		if(Keyboard.getKeyPressed()=="Q")
		{
			Keyboard.keyPressedDone();
			if(characterAction!="Dash")
			{
				if(characterLand == true){setAction("Dash");}
			}
		}
		if(Keyboard.getKeyPressed()=="W")
		{
			Keyboard.keyPressedDone();
			if(characterAction!="SpcA")
			{
				if(characterLand == true){setAction("SpcA");}
			}
		}
	}
	
	public void tickKeyReleased()
	{
		if(Keyboard.getKeyReleased()=="Right" && characterAction=="Run")
		{
			Keyboard.keyReleasedDone();
			if(characterLand == true){setAction("Idle");}
		}
		if(Keyboard.getKeyReleased()=="Left" && characterAction=="Run")
		{
			Keyboard.keyReleasedDone();
			if(characterLand == true){setAction("Idle");}
		}
		if(Keyboard.getKeyReleased()=="D" && characterAction=="Guard")
		{
			Keyboard.keyReleasedDone();
			if(characterLand == true){setAction("Idle");}
		}
		if(Keyboard.getKeyReleased()=="E" && characterAction=="Charge")
		{
			Keyboard.keyReleasedDone();
			if(characterLand == true){setAction("Idle");}
		}
	}
}