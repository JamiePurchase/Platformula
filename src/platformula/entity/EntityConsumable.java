package platformula.entity;
import platformula.Game;

public class EntityConsumable
{
	// Details
	private String itemName;
	private int itemCount;
	private String itemEffect;
	private int itemEffectAmount;
	
	public EntityConsumable(String name, int count)
	{
		itemName = name;
		itemCount = count;
	}
	
	public int getCount()
	{
		return itemCount;
	}
	
	public String getName()
	{
		return itemName;
	}
	
	public void itemAdd(int amount)
	{
		itemCount += amount;
	}
	
	public void itemUse()
	{
		itemCount -= 1;
		if(itemEffect=="Heal"){Game.world.character.heal(itemEffectAmount);}
	}
}