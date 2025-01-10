package l2j.luceraV3.gameserver.data.xml;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import l2j.luceraV3.commons.data.StatSet;
import l2j.luceraV3.commons.data.xml.IXmlReader;

import l2j.luceraV3.gameserver.model.holder.NewbieBuffHolder;

import org.w3c.dom.Document;

/**
 * This class loads and store {@link NewbieBuffHolder} into a {@link List}.
 */
public class NewbieBuffData implements IXmlReader
{
	private final List<NewbieBuffHolder> _buffs = new ArrayList<>();
	
	private int _magicLowestLevel = 100;
	private int _physicLowestLevel = 100;
	
	protected NewbieBuffData()
	{
		load();
	}
	
	@Override
	public void load()
	{
		parseFile("./data/xml/newbieBuffs.xml");
		LOGGER.info("Loaded {} newbie buffs.", _buffs.size());
	}
	
	@Override
	public void parseDocument(Document doc, Path path)
	{
		forEach(doc, "list", listNode -> forEach(listNode, "buff", buffNode ->
		{
			final StatSet set = parseAttributes(buffNode);
			final int lowerLevel = set.getInteger("lowerLevel");
			if (set.getBool("isMagicClass"))
			{
				if (lowerLevel < _magicLowestLevel)
					_magicLowestLevel = lowerLevel;
			}
			else
			{
				if (lowerLevel < _physicLowestLevel)
					_physicLowestLevel = lowerLevel;
			}
			_buffs.add(new NewbieBuffHolder(set));
		}));
	}
	
	/**
	 * @param isMage : If true, return buffs list associated to mage classes.
	 * @param level : Filter the list by the given level.
	 * @return The {@link List} of valid {@link NewbieBuffHolder}s for the given class type and level.
	 */
	public List<NewbieBuffHolder> getValidBuffs(boolean isMage, int level)
	{
		return _buffs.stream().filter(b -> b.isMagicClassBuff() == isMage && level >= b.getLowerLevel() && level <= b.getUpperLevel()).collect(Collectors.toList());
	}
	
	public int getLowestBuffLevel(boolean isMage)
	{
		return (isMage) ? _magicLowestLevel : _physicLowestLevel;
	}
	
	public static NewbieBuffData getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final NewbieBuffData INSTANCE = new NewbieBuffData();
	}
}