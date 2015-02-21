package platformula.entity;
import platformula.Game;
import platformula.graphics.Drawing;

import java.awt.Color;
import java.awt.Graphics;

public class EntityEffect
{
	// Details
	public String effectName;
	
	// Location
	public int effectPosH;
	public int effectPosV;
	public String effectDirH;
	public String effectDirV;
	public int effectVelocityH;
	public int effectVelocityV;
	
	// Animation
	public String effectType;
	public int effectTypeFrame;
	public int effectFrame;
	public int effectFrameMax;
	public int effectFrameTick;
	public int effectFrameTickMax;
	
	// Collision Details
	public boolean effectCollideActive;
	public int effectCollideActiveID;
	public String effectCollideActiveType;
	public int effectCollisionRadius;
	
	// Collision Damage
	public boolean effectCollideDamage;
	public int effectCollideDamageAmount;
	public String effectCollideDamageType;
	// Note: Consider creating an event class to handle what happens when collision occurs
	// Note: The collision event could have multiple animations, effect multiple targets and have other capabilities
	
	// Collision Triggers
	public boolean effectCollideWithPlayer;
	public boolean effectCollideWithUnit;
	
	public EntityEffect(String name, int posH, int posV, String dirH)
	{
		effectName = name;
		effectPosH = posH;
		effectPosV = posV;
		effectFrame = 1;
		
		// Temp
		effectDirH = "R";
		effectDirV = "";
		effectType = "Repeat";
		effectTypeFrame = 5;
		effectFrameMax = 8;
		effectFrameTickMax = 5;
		effectVelocityH = 4;
		effectCollideActive = false;
		effectCollideDamage = true;
		effectCollideDamageAmount = 45;
		effectCollideDamageType = "Fire";
		effectCollideWithPlayer = false;
		effectCollideWithUnit = true;
		effectCollisionRadius = 48;
	}
	
	public void render(Graphics g)
	{
		String drawImage = "sprites/" + effectName + effectDirH + effectFrame + ".png";
		g.drawImage(Drawing.getImage(drawImage), effectPosH, effectPosV, null);
		if(Game.development==true){renderCollision(g);}
	}
	
	public void renderCollision(Graphics g)
	{
		int drawX = effectPosH + 24;
		int drawY = effectPosV + 24;
		g.setColor(Color.BLUE);
		g.drawOval(drawX, drawY, effectCollisionRadius, effectCollisionRadius);
		g.drawOval(drawX, drawY, effectCollisionRadius-1, effectCollisionRadius-1);
	}
	
	public void tick()
	{
		tickAction();
		tickFrame();
	}
	
	public void tickAction()
	{
		// Movement
		if(effectDirH=="R"){effectPosH+=effectVelocityH;}
		if(effectDirH=="L"){effectPosH-=effectVelocityH;}
		if(effectDirV=="U"){effectPosH+=effectVelocityV;}
		if(effectDirV=="D"){effectPosH-=effectVelocityV;}
		
		// Collision
		for(int unit=1;unit<=Game.world.unitCount;unit+=1)
		{
			// Calculate the center of the collision zone for this effect
			int collisionEffectX = effectPosH + 48;
			int collisionEffectY = effectPosV + 48;
			
			// Calculate the center of the collision zone for the unit
			int collisionUnitX = Game.world.unit[unit].unitPosH + 48;
			int collisionUnitY = Game.world.unit[unit].unitPosV + 48;
			
			// Calculate the maximum distance that will trigger a collision
			int limit = effectCollisionRadius + Game.world.unit[unit].unitCollisionRadius;
			
			// Calculate the distance between the effect and an item
			double distance = Math.sqrt(((collisionUnitX-collisionEffectX) * (collisionUnitX-collisionEffectX)) + ((collisionUnitX-collisionEffectX) * (collisionUnitX-collisionEffectX)));
			
			// Debug
			//System.out.println("Distance between effect (" + collisionEffectX + "," + collisionEffectY + ") and unit (" + collisionUnitX + "," + collisionUnitY + ") = " + distance);
			//System.out.println("Collision distance = " + limit);
			
			// Collision Occurs
			if(distance<=limit)
			{
				effectCollideActive = true;
				effectCollideActiveID = unit;
				effectCollideActiveType = "Unit";
				
				// Temp
				if(effectCollideDamage==true)
				{
					Game.world.unit[unit].setAction("DamageA");
					Game.world.setDamage(effectCollideDamageAmount, Game.world.unit[unit].unitPosH, Game.world.unit[unit].unitPosV, effectCollideDamageType);
					Game.world.unit[unit].inflictDamage(effectCollideDamageAmount, effectCollideDamageType);
				}
			}
		}
	}
	
	public void tickFrame()
	{
		effectFrameTick += 1;
		if(effectFrameTick>=effectFrameTickMax)
		{
			effectFrameTick = 0;
			effectFrame += 1;
			if(effectFrame>effectFrameMax)
			{				
				// If the action repeats indefinitely, rewind the action frame to the start
				if(effectType=="Infinite")
				{
					effectFrame = 1;
				}
				
				// If the action repeats until something else happens, rewind the action frame to the int specified
				else if(effectType=="Repeat")
				{
					effectFrame = effectTypeFrame;
				}
			}
		}
	}
}