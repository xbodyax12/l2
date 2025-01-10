package l2j.luceraV3.loginserver.network.gameserverpackets;

import l2j.luceraV3.loginserver.network.clientpackets.ClientBasePacket;

public class ChangeAccessLevel extends ClientBasePacket
{
	private final int _level;
	private final String _account;
	
	public ChangeAccessLevel(byte[] decrypt)
	{
		super(decrypt);
		_level = readD();
		_account = readS();
	}
	
	public String getAccount()
	{
		return _account;
	}
	
	public int getLevel()
	{
		return _level;
	}
}