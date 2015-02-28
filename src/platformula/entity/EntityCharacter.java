package platformula.entity;
import platformula.Game;
import platformula.graphics.Drawing;
import platformula.input.Keyboard;

import java.awt.Color;
import java.awt.Graphics;

public class EntityCharacter
{
	// Details
	public String characterName;
	
	// Action Details
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
	public int characterFrame;
	public int characterFrameMax;
	public int characterFrameTick;
	public int characterFrameTickMax;
	public int characterSpriteHeight;
	public int characterSpriteWidth;
	
	// Action Animation (behind character)
	public boolean characterActionAnim;
	public String characterActionAnimName;
	public int characterActionAnimFrame;
	public int characterActionAnimFrameMax;
	public int characterActionAnimFrameTick;
	public int characterActionAnimFrameTickMax;

	// Effect
	public boolean characterEffectAdd;
	public EntityEffect characterEffectEntity;
	public String characterEffectName;
	
	// Keyboard
	public boolean characterKeyLeft = false;
	public boolean characterKeyRight = false;
	
	// Location
	public int characterPosH;
	public int characterPosV;
	public String characterDirH;
	public String characterDirV;
	public int characterSpdH;
	public int characterSpdV;
	public boolean characterLand;
	public int characterCollisionRadius;
	
	// Stats
	public int characterStatHealthNow;
	public int characterStatHealthMax;
	public int characterStatHealthReduce;
	public int characterStatChakraNow;
	public int characterStatChakraMax;
	public int characterStatChakraReduce;
	
	public EntityCharacter(String name, int posH, int posV)
	{
		characterName = name;
		characterDirV = "";
		characterLand = true;
		characterSpdH = 0;
		characterSpdV = 0;
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
	
	public int getCollisionCentreX()
	{
		return characterPosH + (characterSpriteWidth/2);
	}
	
	public int getCollisionCentreY()
	{
		return characterPosV + (characterSpriteHeight/2);
	}
	
	public boolean getCollisionEngage(int posX, int posY, int radius)
	{
		int distanceH = characterPosH - posX;
		int distanceV = characterPosV - posY;
		if(characterPosH<posX){distanceH = posX - characterPosH;}
		if(characterPosV<posY){distanceV = posY - characterPosV;}
		if(distanceH<=(characterCollisionRadius+radius) && distanceV<=(characterCollisionRadius+radius)){return true;}
		return false;
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
	
	public void heal(int amount)
	{
		characterStatHealthNow += amount;
		if(characterStatHealthNow>characterStatHealthMax){characterStatHealthNow = characterStatHealthMax;}
	}
	
	public void render(Graphics g)
	{
		if(characterActionAnim==true){renderEffect(g);}
		renderCharacter(g);
	}
	
	public void renderCharacter(Graphics g)
	{
		String drawImage = "sprites/" + characterName + characterAction + characterDirH + characterFrame + ".png";
		g.drawImage(Drawing.getImage(drawImage), characterPosH, characterPosV, null);
		if(Game.development==true){renderCollision(g);}
	}
	
	public void renderEffect(Graphics g)
	{
		String drawImage = "sprites/" + characterActionAnimName + characterActionAnimFrame + ".png";
		g.drawImage(Drawing.getImage(drawImage), characterPosH, characterPosV, null);
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
		characterActionAnim = false;
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
			characterSpdH = 0;
		}
		if(action=="Charge")
		{
			characterActionAnim = true;
			characterActionAnimName = "EffectCharge";
			characterActionAnimFrame = 1;
			characterActionAnimFrameMax = 10;
			characterActionAnimFrameTick = 0;
			characterActionAnimFrameTickMax = 5;
			characterActionBusy = false;
			characterActionType = "Repeat";
			characterActionTypeFrame = 2;
			characterFrameMax = 2;
			characterSpdH = 0;
		}
		if(action=="DamageA")
		{
			characterActionBusy = true;
			characterActionType = "Change";
			characterActionTypeChange = "Idle";
			characterFrameMax = 3;
			characterSpdH = 0;
		}
		if(action=="Dash")
		{
			characterActionBusy = true;
			characterActionType = "Change";
			characterActionTypeChange = "Idle";
			characterFrameMax = 3;
			characterFrameTickMax = 5;
			characterSpdH = 10;
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
			characterSpdH = 0;
		}
		if(action=="Hide")
		{
			characterActionBusy = false;
			characterActionType = "Infinite";
			characterFrameMax = 1;
			characterSpdH = 0;
		}
		if(action=="Idle")
		{
			characterActionBusy = false;
			characterActionType = "Infinite";
			characterFrameMax = 7;
			characterSpdH = 0;
		}
		if(action=="Jump")
		{
			characterActionBusy = false;
			characterActionType = "Change";
			characterActionTypeChange = "Float";
			characterDirV = "U";
			characterFrameMax = 3;
			characterLand = false;
			characterSpdV = 20;
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
		if(characterActionAnim==true){tickCharacterActionAnim();}
		tickCharacterStat();
		tickCharacterAction();
		tickCharacterCollide();
		tickCharacterFrame();
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
			if(characterDirH=="R"){characterPosH+=characterSpdH;}
			if(characterDirH=="L"){characterPosH-=characterSpdH;}
		}
		// Note: For the above, use a general velocity int and move accordingly (rather than looking for specific actions)
		
		if(characterAction=="Jump" || characterAction=="Float")
		{
			if(characterDirV=="U")
			{
				if(characterSpdH>0)
				{
					if(characterDirH=="L"){characterPosH-=characterSpdH;}
					if(characterDirH=="R"){characterPosH+=characterSpdH;}
					//characterSpdH-=1;
				}
				characterPosV -= characterSpdV;
				characterSpdV -= 1;
				if(characterSpdV<1)
				{
					characterDirV = "D";
					characterSpdV = 1;
				}
			}
			else
			{
				if(characterSpdH>0)
				{
					if(characterDirH=="L"){characterPosH-=characterSpdH;}
					if(characterDirH=="R"){characterPosH+=characterSpdH;}
					//characterSpdH-=1;
				}
				characterPosV += characterSpdV;
				characterSpdV += 1;
				// Note: We should change the hardcoded land height to a collection of solid areas 
				if(characterPosV>=400)
				{
					characterPosV = 400;
					characterDirV = "";
					characterSpdV = 0;
					characterLand = true;
					if(characterKeyRight==true || characterKeyLeft==true)
					{
						characterSpdH = 1;
						setAction("Run");
					}
					else
					{
						characterSpdH = 0;
						setAction("Idle");
					}
				}
			}
		}
	}
	
	public void tickCharacterActionAnim()
	{
		characterActionAnimFrameTick += 1;
		if(characterActionAnimFrameTick>=characterActionAnimFrameTickMax)
		{
			characterActionAnimFrameTick = 0;
			characterActionAnimFrame += 1;
			if(characterActionAnimFrame>characterActionAnimFrameMax)
			{
				characterActionAnimFrame = 1;
				// Note: Implement the usual types (repeat/infinite/change)
			}
		}
	}
	
	public void tickCharacterCollide()
	{
		tickCharacterCollideItems();
	}
	
	public void tickCharacterCollideItems()
	{
		for(int i=1;i<=Game.world.itemCount;i+=1)
		{
			// Calculate the center of the collision zone for the character
			int collisionCharacterX = characterPosH + 48;
			int collisionCharacterY = characterPosV + 48;
			
			// Calculate the center of the collision zone for the unit
			int collisionItemX = Game.world.item[i].itemPosH + 48;
			int collisionItemY = Game.world.item[i].itemPosV + 48;
			
			// Calculate the maximum distance that will trigger a collision
			int limit = characterCollisionRadius + Game.world.item[i].itemCollisionRadius;
			
			// Calculate the distance between the character and the item
			double distance = Math.sqrt(((collisionItemX-collisionCharacterX) * (collisionItemX-collisionCharacterX)) + ((collisionItemY-collisionCharacterX) * (collisionItemY-collisionCharacterX)));
			
			// Debug
			//System.out.println("Distance between character (" + collisionCharacterX + "," + collisionCharacterY + ") and item (" + collisionItemX + "," + collisionItemY + ") = " + distance);
			//System.out.println("Collision distance = " + limit);
			
			// Collision Occurs
			if(distance<=limit){Game.world.item[i].collide();}
		}
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
				if(characterLand == true)
				{
					setAction("Jump");
					if(characterAction=="Run"){characterSpdH = 10;}
				}
				else if(characterLand == false)
				{
					// Note: If the character has unlocked a skill, additional actions may be available (eg: double jump, air dash)
				}
			}
		}
		if(Keyboard.getKeyPressed()=="Right")
		{
			characterKeyRight = true;
			Keyboard.keyPressedDone();
			if(characterAction!="Run")
			{
				if(characterLand == true)
				{
					setAction("Run");
					characterDirH = "R";
					characterSpdH = 1;
				}
				// Note: Could allow some horizontal movement while in the air?
			}
		}
		if(Keyboard.getKeyPressed()=="Left")
		{
			characterKeyLeft = true;
			Keyboard.keyPressedDone();
			if(characterAction!="Run")
			{
				if(characterLand == true)
				{
					setAction("Run");
					characterDirH = "L";
					characterSpdH = 1;
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
			characterKeyRight = false;
			Keyboard.keyReleasedDone();
			if(characterLand == true){setAction("Idle");}
		}
		if(Keyboard.getKeyReleased()=="Left" && characterAction=="Run")
		{
			characterKeyLeft = false;
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