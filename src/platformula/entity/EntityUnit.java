package platformula.entity;
import platformula.Game;
import platformula.graphics.Drawing;

import java.awt.Color;
import java.awt.Graphics;

public class EntityUnit
{
	public String unitName;
	public String unitAction;
	public int unitActionTick;
	public String unitActionType;
	public String unitActionTypeChange;
	public int unitActionTypeFrame;
	public int unitPosH;
	public int unitPosV;
	public String unitDirH;
	public String unitDirV;
	public int unitFrame;
	public int unitFrameMax;
	public int unitFrameTick;
	public int unitFrameTickMax;
	public int unitStatHealthNow;
	public int unitStatHealthMax;
	public int unitStatChakraNow;
	public int unitStatChakraMax;
	public int unitCollisionRadius;
	
	public EntityUnit(String name, int posH, int posV, String dirH)
	{
		unitName = name;
		setAction("Idle");
		unitPosH = posH;
		unitPosV = posV;
		unitDirH = dirH;
		unitStatHealthNow = 100;
		unitStatHealthMax = 100;
		unitStatChakraNow = 100;
		unitStatChakraMax = 100;
		unitCollisionRadius = 64;
	}
	
	public void inflictDamage(int amount, String type)
	{
		unitStatHealthNow -= amount;
		if(unitStatHealthNow<1)
		{
			unitStatHealthNow = 0;
			//setActionNext("");
		}
	}
	
	public void render(Graphics g)
	{
		String drawImage = "sprites/" + unitName + unitAction + unitDirH + unitFrame + ".png";
		g.drawImage(Drawing.getImage(drawImage), unitPosH, unitPosV, null);
		if(Game.development==true){renderCollision(g);}
	}
	
	public void renderCollision(Graphics g)
	{
		int drawX = unitPosH + 16;
		int drawY = unitPosV + 16;
		g.setColor(Color.BLUE);
		g.drawOval(drawX, drawY, unitCollisionRadius, unitCollisionRadius);
		g.drawOval(drawX, drawY, unitCollisionRadius-1, unitCollisionRadius-1);
	}
	
	public void setAction(String action)
	{
		unitAction = action;
		unitActionTick = 0;
		unitFrame = 1;
		unitFrameTick = 0;
		unitFrameTickMax = 10;
		if(action=="Idle")
		{
			unitActionType = "Infinite";
			unitFrameMax = 4;
		}
		if(action=="DamageA")
		{
			unitActionType = "Change";
			unitActionTypeChange = "Idle";
			unitFrameMax = 4;
		}
	}

	public void tick()
	{
		tickAction();
		tickFrame();
	}
	
	public void tickAction()
	{
	}
	
	public void tickFrame()
	{
		unitFrameTick += 1;
		if(unitFrameTick>=unitFrameTickMax)
		{
			unitFrameTick = 0;
			unitFrame += 1;
			if(unitFrame>unitFrameMax)
			{				
				// If the action repeats indefinitely, rewind the action frame to the start
				if(unitActionType=="Infinite")
				{
					unitFrame = 1;
				}
				
				// If the action changes to another after the final frame, call set action with the action string specified
				else if(unitActionType=="Change")
				{
					setAction(unitActionTypeChange);
				}
				
				// If the action repeats until something else happens, rewind the action frame to the int specified
				else if(unitActionType=="Repeat")
				{
					unitFrame = unitActionTypeFrame;
				}
			}
		}
	}
}