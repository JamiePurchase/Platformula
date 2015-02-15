package platformula.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Drawing
{
	
	public static Color getColorRGB(int r, int g, int b)
	{
		float hsb[] = Color.RGBtoHSB(185,122,87,null);
		return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
	}
	
	public static BufferedImage getImage(String filepath)
	{
		filepath = "C:/Eclipse/Workspace/Platformula/res/graphics/" + filepath;
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(new File(filepath));
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
		return image;
	}
	
}