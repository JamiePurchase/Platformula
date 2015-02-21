package platformula.entity;
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
	}
	
	public void render(Graphics g)
	{
		String drawImage = "sprites/Item" + itemName + itemFrame + ".png";
		g.drawImage(Drawing.getImage(drawImage), itemPosH, itemPosV, null);
	}
	
	public void tick()
	{
		tickFrame();
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