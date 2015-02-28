package platformula.world;
import platformula.Game;
import platformula.entity.EntityCharacter;
import platformula.entity.EntityConsumable;
import platformula.entity.EntityEffect;
import platformula.entity.EntityItem;
import platformula.entity.EntityStruct;
import platformula.entity.EntityUnit;
import platformula.graphics.Drawing;
import platformula.graphics.Fonts;
import platformula.input.Keyboard;

import java.awt.Color;
import java.awt.Graphics;

public class World
{
	// Background
	public String background;
	
	// Character
	public EntityCharacter character;
	
	// Damage
	public boolean damageActive;
	public int damageAmount;
	public int damageFrame;
	public int damageFrameTick;
	public int damagePosX;
	public int damagePosY;
	public String damageType;
	
	// Effects
	public EntityEffect[] effect = new EntityEffect[10];
	public int effectCount;
	
	// Inventory
	public EntityConsumable[] inventory = new EntityConsumable[10];
	public int inventoryCount;
	
	// Items
	public EntityItem[] item = new EntityItem[10];
	public int itemCount;
	
	// Mission
	public String missionMode;
	// Note: Objectives, time limit
	public int missionTick;
	public boolean missionVictory;
	public int missionVictoryTick;
	
	// Pause Menu
	public boolean pauseActive = false;
	public int pauseMenuPos;
	public int pauseMenuMax;
	
	// Structs
	public EntityStruct[] struct = new EntityStruct[100];
	public int structCount;
	
	// Units
	public EntityUnit[] unit = new EntityUnit[10];
	public int unitCount;

	public World()
	{
		// Background
		background = "bkg1";
		// Note: Consider animations in the background, animated scenery, weather, etc...
		
		// Character
		character  = new EntityCharacter("Itachi", 300, 400);
		
		// Damage
		damageActive = false;
		
		// Effects
		effectCount = 0;
		
		// Inventory
		inventoryCount = 0;
		inventoryAdd("Riceball", 3);
		
		// Items
		itemCount = 0;
		itemAdd(new EntityItem("Riceball", 500, 400, 4));
		
		// Units
		unitCount = 0;
		unitAdd(new EntityUnit("Jiraiya", 800, 400, "L"));
	}
	
	public void effectAdd(EntityEffect add)
	{
		effectCount += 1;
		effect[effectCount] = add;
	}
	
	public void effectDelete(int id)
	{
		// Create a temporary array to hold the remaining effects
		EntityEffect[] effectNew = new EntityEffect[10];
		
		// Loop through the effects and place them in the temporary array
		int eStart = id + 1;
		for(int e=eStart;e<=effectCount;e+=1)
		{
			int n = e;
			if(e>id){n = e - 1;}
			effectNew[e] = effect[n];
		}
		
		// Overwrite the effect array with the new data 
		effect = effectNew;
		effectCount -= 1;
	}
	
	public void inventoryAdd(String add, int quantity)
	{
		boolean itemExists = false;
		for(int item=1;item<=itemCount;item+=1)
		{
			if(add==inventory[item].getName())
			{
				itemExists = true;
				inventory[item].itemAdd(quantity);
			}
		}
		if(itemExists==false)
		{
			inventoryCount += 1;
			inventory[inventoryCount] = new EntityConsumable(add, quantity);
		}
	}
	
	public void itemAdd(EntityItem add)
	{
		itemCount += 1;
		item[itemCount] = add;
	}
	
	public void itemDelete(int id)
	{
		// Create a temporary array to hold the remaining items
		EntityItem[] itemNew = new EntityItem[10];
		
		// Loop through the items and place them in the temporary array
		int eStart = id + 1;
		for(int e=eStart;e<=itemCount;e+=1)
		{
			int n = e;
			if(e>id){n = e - 1;}
			itemNew[e] = item[n];
		}
		
		// Overwrite the item array with the new data 
		item = itemNew;
		itemCount -= 1;
	}
	
	public void render(Graphics g)
	{
		renderBackground(g);
		renderScenery(g);
		renderCharacter(g);
		renderEffect(g);
		renderInterface(g);
		renderItem(g);
		renderUnit(g);
		if(Game.development==true){renderStruct(g);}
		if(missionVictory==true){renderVictory(g);}
		else
		{
			if(damageActive == true){renderDamage(g);}
			if(pauseActive == true){renderPause(g);}
		}
	}
	
	public void renderBackground(Graphics g)
	{
		/*String drawImage = "backgrounds/" + background + ".png";
		g.drawImage(Drawing.getImage(drawImage), 0, 0, null);*/
		g.drawImage(Drawing.getImage("backgrounds/sky1.png"), 0, 0, null);
		g.drawImage(Drawing.getImage("backgrounds/grass1.png"), 0, 530, null);
	}
	
	public void renderCharacter(Graphics g)
	{
		character.render(g);
	}
	
	public void renderDamage(Graphics g)
	{
		String drawText = "" + damageAmount;
		int drawX = damagePosX;
		int drawY = damagePosY - ((damageFrame * 5) + 5);
		// Note: Take into consideration the damage type (weakness resist?) (elements?)
		// Note: Consider critical hits? (font colour?)
		g.setFont(Fonts.fontSpecial);
		g.setColor(Color.GRAY);
		g.drawString(drawText, drawX-1, drawY-1);
		g.setColor(Color.BLACK);
		g.drawString(drawText, drawX, drawY);
	}
	
	public void renderEffect(Graphics g)
	{
		for(int e=1;e<=effectCount;e+=1)
		{
			effect[e].render(g);
		}
	}
	
	public void renderInterface(Graphics g)
	{
		renderInterfaceCharacter(g, 1);
		renderInterfaceInventory(g);
	}
	
	public void renderInterfaceCharacter(Graphics g, int player)
	{
		// Health Bar
		g.setColor(Color.GRAY);
		g.fillRect(133, 63, 400, 30);
		int drawHealth = character.getStatHealthPercent() * 4;
		g.setColor(Color.RED);
		g.fillRect(133, 63, drawHealth, 30);
		
		// Chakra Bar
		g.setColor(Color.GRAY);
		g.fillRect(131, 96, 402, 30);
		int drawChakra = (character.getStatChakraPercent() * 4) + 2;
		g.setColor(Color.BLUE);
		g.fillRect(131, 96, drawChakra, 30);
		
		// Debug
		//System.out.println("Health: " + character.characterStatHealthNow + "/" + character.characterStatHealthMax + "(" + character.getStatHealthPercent() + "%) = " + drawHealth);
		//System.out.println("Chakra: " + character.characterStatChakraNow + "/" + character.characterStatChakraMax + "(" + character.getStatChakraPercent() + "%) = " + drawChakra);

		// Frame
		g.drawImage(Drawing.getImage("interface/" + "StatBackground" + ".png"), 25, 25, null);
		
		// Portrait
		String drawImage = "interface/" + character.characterName + "Portrait.png";
		g.drawImage(Drawing.getImage(drawImage), 25, 25, null);
		
		// Name
		g.setFont(Fonts.fontSpecialBold);
		g.setColor(Color.GRAY);
		g.drawString(character.characterName, 151, 51);
		g.setColor(Color.BLACK);
		g.drawString(character.characterName, 150, 50);
		
		// Kunai
		
		// Temp
		g.setFont(Fonts.fontStandard);
		g.drawString(character.characterStatHealthNow + "/" + character.characterStatHealthMax, 550, 90);
		g.drawString(character.characterStatChakraNow + "/" + character.characterStatChakraMax, 550, 123);
	}
	
	public void renderInterfaceInventory(Graphics g)
	{
		g.setFont(Fonts.fontSmall);
		g.setColor(Color.BLACK);
		for(int i=1;i<=itemCount;i+=1)
		{
			String drawString = inventory[1].getName() + " (" + inventory[1].getCount() + ")";
			int drawPosY = (25 * i) + 575;
			g.drawString(drawString, 50, drawPosY);
		}
	}
	
	public void renderItem(Graphics g)
	{
		for(int i=1;i<=itemCount;i+=1)
		{
			item[i].render(g);
		}
	}
	
	public void renderPause(Graphics g)
	{
		Drawing.drawImageOpaque(g, Drawing.getImage("interface/PauseOverlay.png"), 0, 0, 0.75f);
		g.drawImage(Drawing.getImage("interface/PauseText.png"), 453, 150, null);
		
		// Temp
		/*g.setFont(Fonts.fontStandard);
		g.setColor(Color.GRAY);
		g.drawString("PAUSED", 601, 101);
		g.setColor(Color.WHITE);
		g.drawString("PAUSED", 600, 100);*/
	}
	
	public void renderScenery(Graphics g)
	{		
		/*for(int s=1;s<=sceneryCount;s+=1)
		{
			scenery[s].render(g);
		}*/
		g.drawImage(Drawing.getImage("scenery/build01house01.png"), 0, 192, null);
		g.drawImage(Drawing.getImage("scenery/build01house01.png"), 600, 160, null);
	}
	
	public void renderStruct(Graphics g)
	{
		for(int s=1;s<=structCount;s+=1)
		{
			struct[s].render(g);
		}
	}
	
	public void renderUnit(Graphics g)
	{
		for(int u=1;u<=unitCount;u+=1)
		{
			unit[u].render(g);
		}
		
		// Development
		if(Game.development==true)
		{
			g.setColor(Color.GRAY);
			g.fillRect(952, 52, 400, 300);
			g.setColor(Color.WHITE);
			g.fillRect(950, 50, 400, 300);
			g.setColor(Color.BLACK);
			g.drawRect(950, 50, 400, 300);
			g.drawRect(951, 51, 398, 298);
			g.setFont(Fonts.fontStandardBold);
			g.drawString("Development", 975, 85);
			g.setFont(Fonts.fontStandard);
			g.drawString(unit[1].unitName, 975, 120);
			g.drawString(unit[1].unitStatHealthNow + "/" + unit[1].unitStatHealthMax, 1200, 120);
		}
	}
	
	public void renderVictory(Graphics g)
	{
		if(missionVictoryTick>10)
		{
			float opacity = (float) ((missionVictoryTick - 10) * 0.05);
			if(opacity>1){opacity=1;}
			Drawing.drawImageOpaque(g, Drawing.getImage("interface/PauseOverlay.png"), 0, 0, opacity);
			g.drawImage(Drawing.getImage("interface/MissionComplete.png"), 300, 200, null);
		}
	}
	
	public void setDamage(int amount, int posX, int posY, String type)
	{
		damageActive = true;
		damageAmount = amount;
		damageFrame = 1;
		damageFrameTick = 0;
		damagePosX = posX;
		damagePosY = posY;
		damageType = type;
	}
	
	public void tick()
	{
		tickMission();
		if(missionVictory==false)
		{
			tickKey();
			if(pauseActive==false)
			{
				// Note: Could we create a tickBackground function that controls animations/weather/something?
				tickCharacter();
				tickEffect();
				tickItem();
				tickUnit();
				if(damageActive==true){tickDamage();}
			}
		}
	}
	
	public void tickCharacter()
	{
		character.tick();
	}
	
	public void tickDamage()
	{
		damageFrameTick += 1;
		if(damageFrameTick==10)
		{
			damageFrameTick = 0;
			damageFrame += 1;
			if(damageFrame>5)
			{
				damageActive = false;
			}
		}
	}
	
	public void tickEffect()
	{
		for(int e=1;e<=effectCount;e+=1)
		{
			effect[e].tick();
			if(effect[e].effectCollideActive==true)
			{
				effectDelete(e);
			}
		}
	}
	
	public void tickItem()
	{
		for(int i=1;i<=itemCount;i+=1)
		{
			item[i].tick();
			if(item[i].itemRemove==true && item[i].itemRemoveTick<1)
			{
				Game.world.itemDelete(i);
			}
		}
	}
	
	public void tickKey()
	{
		if(pauseActive==false && Keyboard.getKeyPressed()=="Enter")
		{
			Keyboard.keyPressedDone();
			Game.world.pauseActive = true;
		}
		else
		{
			tickKeyPause();
		}
	}
	
	public void tickMission()
	{
		missionTick += 1;
		if(missionVictory==false){tickMissionCheck();}
		else{tickMissionVictory();}
	}
	
	public void tickMissionCheck()
	{
		// Note: Consider objectives
		int unitAlive = 0;
		for(int u=1;u<=unitCount;u+=1)
		{
			if(unit[u].unitStatHealthNow>=1){unitAlive += 1;}
			// Note: Consider status effects
		}
		if(unitAlive==0)
		{
			missionVictory = true;
			missionVictoryTick = 0;
		}
	}
	
	public void tickMissionVictory()
	{
		missionVictoryTick += 1;
		if(missionVictoryTick>600)
		{
			System.exit(0);
		}
	}
	
	public void tickKeyPause()
	{
		if(Keyboard.getKeyPressed()=="Enter")
		{
			Keyboard.keyPressedDone();
			Game.world.pauseActive = false;
		}
	}
	
	public void tickUnit()
	{
		for(int u=1;u<=unitCount;u+=1)
		{
			unit[u].tick();
		}
	}
	
	public void unitAdd(EntityUnit add)
	{
		unitCount += 1;
		unit[unitCount] = add;
	}
}