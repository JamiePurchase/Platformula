package platformula.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener
{
	private static String keyPressed = "None";
	private static String keyReleased = "None";
	
	public static String getKeyPressed()
	{
		return keyPressed;
	}
	
	public static String getKeyReleased()
	{
		return keyReleased;
	}

    public void keyTyped(KeyEvent e)
    {
        //System.out.println("Key typed: " + e.getKeyChar());
    }

    public void keyPressed(KeyEvent e)
    {
        //System.out.println("Key pressed: " + e.getKeyChar());
        if(e.getKeyCode() == KeyEvent.VK_ENTER){keyPressed = "Enter";}
        if(e.getKeyCode() == KeyEvent.VK_SPACE){keyPressed = "Space";}
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){keyPressed = "Escape";}
        if(e.getKeyCode() == KeyEvent.VK_UP){keyPressed = "Up";}
        if(e.getKeyCode() == KeyEvent.VK_DOWN){keyPressed = "Down";}
        if(e.getKeyCode() == KeyEvent.VK_LEFT){keyPressed = "Left";}
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){keyPressed = "Right";}
        if(e.getKeyCode() == KeyEvent.VK_D){keyPressed = "D";}
    }

    public void keyReleased(KeyEvent e)
    {
        //System.out.println("Key released: " + e.getKeyChar());
        if(e.getKeyCode() == KeyEvent.VK_UP){keyReleased = "Up";}
        if(e.getKeyCode() == KeyEvent.VK_DOWN){keyReleased = "Down";}
        if(e.getKeyCode() == KeyEvent.VK_LEFT){keyReleased = "Left";}
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){keyReleased = "Right";}
    }
    
	public static void keyPressedDone()
	{
		keyPressed = "none";
	}
    
	public static void keyReleasedDone()
	{
		keyReleased = "none";
	}
}