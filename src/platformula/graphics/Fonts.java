package platformula.graphics;

import java.awt.Font;

public class Fonts
{
	public static Font fontStandard, fontStandardBold;
	
	public static void init()
	{
		fontStandard = new Font("Andalus", Font.BOLD, 32);
		fontStandardBold = new Font("Andalus", Font.PLAIN, 32);
	}

}