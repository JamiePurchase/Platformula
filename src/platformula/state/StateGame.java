package platformula.state;
import platformula.Game;

import java.awt.Graphics;

public class StateGame extends State
{
	
	public StateGame()
	{
	}
	
	public void render(Graphics g)
	{
		Game.world.render(g);
	}
	
	public void tick()
	{		
		Game.world.tick();
	}
}