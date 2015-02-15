package platformula.graphics;

import java.awt.image.BufferedImage;

public class Tileset
{
	
	public Tileset()
	{
	}
	
	public static BufferedImage[] getTileset(String file, int width, int height)
	{
		return getTileset(file, width, height, 96 ,96);
	}
	
	public static BufferedImage[] getTileset(String file, int width, int height, int tileW, int tileH)
	{
		int tileTotal = width * height;
		Spritesheet sheet = new Spritesheet(ImageLoader.loadImage(file));
		BufferedImage[] tileset = new BufferedImage[tileTotal];
		int posX = 1;
		int posY = 1;
		for(int tile=0;tile<tileTotal;tile+=1)
		{
			int cropX = tileW * posX - tileW;
			int cropY = tileH * posY - tileH;
			tileset[tile] = sheet.crop(cropX, cropY, tileW, tileH);
			posX+=1;
			if(posX>width)
			{
				posX=1;
				posY+=1;
			}
		}
		return tileset;
	}
}