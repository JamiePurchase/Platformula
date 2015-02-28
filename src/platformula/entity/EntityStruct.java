package platformula.entity;

import java.awt.Color;
import java.awt.Graphics;

public class EntityStruct
{
	// Collision
	private boolean collideEffect;
	private boolean collidePlayer;
	
	// Footsteps
	private boolean footstep;
	private String footstepType;
	
	// Location
	private int posX1;
	private int posY1;
	private int posX2;
	private int posY2;
	
	public EntityStruct(int x1, int y1, int x2, int y2)
	{
		collideEffect = true;
		collidePlayer = true;
		footstep = false;
		footstepType = "";
		posX1 = x1;
		posY1 = y1;
		posX2 = x2;
		posY2 = y2;
	}
	
	public boolean getCollision(int x1, int y1)
	{
		if(x1>=posX1 && x1<=posX2 && y1>=posY1 && y1<=posY2){return true;}
		return false;
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.BLUE);
		g.drawRect(posX1, posY1, posX2-posX1, posY2-posY1);
	}

}