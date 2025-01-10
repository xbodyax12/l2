package l2j.luceraV3.gameserver.enums;

public enum GeoType
{
	L2J("%d_%d.l2j"),
	L2OFF("%d_%d_conv.dat");
	
	private final String _filename;
	
	private GeoType(String filename)
	{
		_filename = filename;
	}
	
	public String getFilename()
	{
		return _filename;
	}
}