package platformula.graphics;

import java.awt.Font;

public class Fonts
{
	public static Font fontSpecial, fontSpecialBold;
	public static Font fontStandard, fontStandardBold;
	public static Font fontLarge, fontLargeBold;
	
	public static void init()
	{
		fontSpecial = new Font("Ninja Naruto", Font.PLAIN, 28);
		fontSpecialBold = new Font("Ninja Naruto", Font.BOLD, 28);
		fontStandard = new Font("Andalus", Font.PLAIN, 32);
		fontStandardBold = new Font("Andalus", Font.BOLD, 32);
		fontLarge = new Font("Andalus", Font.PLAIN, 64);
		fontLargeBold = new Font("Andalus", Font.PLAIN, 64);
	}

}