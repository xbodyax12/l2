package l2j.luceraV3.loginserver.network.loginserverpackets;

import l2j.luceraV3.loginserver.network.serverpackets.ServerBasePacket;

public class KickPlayer extends ServerBasePacket
{
	public KickPlayer(String account)
	{
		writeC(0x04);
		writeS(account);
	}
	
	@Override
	public byte[] getContent()
	{
		return getBytes();
	}
}