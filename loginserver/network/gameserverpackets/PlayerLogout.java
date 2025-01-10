package l2j.luceraV3.loginserver.network.gameserverpackets;

import l2j.luceraV3.loginserver.network.clientpackets.ClientBasePacket;

public class PlayerLogout extends ClientBasePacket
{
	private final String _account;
	
	public PlayerLogout(byte[] decrypt)
	{
		super(decrypt);
		_account = readS();
	}
	
	public String getAccount()
	{
		return _account;
	}
}