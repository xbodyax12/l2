package l2j.luceraV3.loginserver.network.loginserverpackets;

import l2j.luceraV3.loginserver.LoginServer;
import l2j.luceraV3.loginserver.network.serverpackets.ServerBasePacket;

public class InitLS extends ServerBasePacket
{
	public InitLS(byte[] publickey)
	{
		writeC(0x00);
		writeD(LoginServer.PROTOCOL_REV);
		writeD(publickey.length);
		writeB(publickey);
	}
	
	@Override
	public byte[] getContent()
	{
		return getBytes();
	}
}