package l2j.luceraV3.gameserver.data.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import l2j.luceraV3.commons.logging.CLogger;

import l2j.luceraV3.gameserver.data.DocumentItem;
import l2j.luceraV3.gameserver.model.item.kind.Armor;
import l2j.luceraV3.gameserver.model.item.kind.EtcItem;
import l2j.luceraV3.gameserver.model.item.kind.Item;
import l2j.luceraV3.gameserver.model.item.kind.Weapon;

/**
 * This class loads and stores all {@link Item} templates.
 */
public class ItemData
{
	private static final CLogger LOGGER = new CLogger(ItemData.class.getName());
	
	private Item[] _templates;
	
	protected ItemData()
	{
		load();
	}
	
	public void reload()
	{
		load();
	}
	
	private void load()
	{
		final File dir = new File("./data/xml/items");
		
		final Map<Integer, Armor> armors = new HashMap<>();
		final Map<Integer, EtcItem> etcItems = new HashMap<>();
		final Map<Integer, Weapon> weapons = new HashMap<>();
		
		int highest = 0;
		for (File file : dir.listFiles())
		{
			DocumentItem document = new DocumentItem(file);
			document.parse();
			
			for (Item item : document.getItemList())
			{
				if (highest < item.getItemId())
					highest = item.getItemId();
				
				if (item instanceof EtcItem)
					etcItems.put(item.getItemId(), (EtcItem) item);
				else if (item instanceof Armor)
					armors.put(item.getItemId(), (Armor) item);
				else
					weapons.put(item.getItemId(), (Weapon) item);
			}
		}
		
		// Feed an array with all items templates.
		_templates = new Item[highest + 1];
		
		for (Armor item : armors.values())
			_templates[item.getItemId()] = item;
		
		for (Weapon item : weapons.values())
			_templates[item.getItemId()] = item;
		
		for (EtcItem item : etcItems.values())
			_templates[item.getItemId()] = item;
		
		LOGGER.info("Loaded items.");
	}
	
	/**
	 * @param id : the item id to check.
	 * @return the {@link Item} corresponding to the item id.
	 */
	public Item getTemplate(int id)
	{
		return (id >= _templates.length) ? null : _templates[id];
	}
	
	public int getArraySize()
	{
		return _templates.length;
	}
	
	public static ItemData getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final ItemData INSTANCE = new ItemData();
	}
}