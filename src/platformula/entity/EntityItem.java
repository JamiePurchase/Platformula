package platformula.entity;
import platformula.Game;
import platformula.graphics.Drawing;

import java.awt.Graphics;

public class EntityItem
{
	public String itemName;
	public int itemPosH;
	public int itemPosV;
	public int itemFrame;
	public int itemFrameMax;
	public int itemFrameTick;
	public int itemFrameTickMax;
	public int itemCollisionRadius;
	public String itemCollectName;
	public int itemCollectQuantity;
	public boolean itemRemove;
	public int itemRemoveTick;
	
	public EntityItem(String name, int posH, int posV, int frames)
	{
		itemName = name;
		itemPosH = posH;
		itemPosV = posV;
		itemFrame = 1;
		itemFrameMax = frames;
		itemFrameTick = 0;
		itemFrameTickMax = 10;
		itemCollisionRadius = 32;
		itemCollectName = name;
		itemCollectQuantity = 1;
		itemRemove = false;
		itemRemoveTick = 0;
	}
	
	public void collide()
	{
		if(!itemRemove)
		{
			// Play sound
			Game.world.inventoryAdd(itemCollectName, itemCollectQuantity);
			itemRemove = true;
			itemRemoveTick = 5;
		}
	}
	
	public void render(Graphics g)
	{
		if(!itemRemove)
		{
			String drawImage = "sprites/Item" + itemName + itemFrame + ".png";
			g.drawImage(Drawing.getImage(drawImage), itemPosH, itemPosV, null);
		}
	}
	
	public void tick()
	{
		if(!itemRemove){tickFrame();}
		else{itemRemoveTick -= 1;}
	}
	
	public void tickFrame()
	{
		itemFrameTick += 1;
		if(itemFrameTick>=itemFrameTickMax)
		{
			itemFrameTick = 0;
			itemFrame += 1;
			if(itemFrame>itemFrameMax)
			{
				itemFrame = 1;
			}
		}
	}
}