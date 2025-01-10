package l2j.luceraV3.loginserver.network.loginserverpackets;

import l2j.luceraV3.loginserver.data.manager.GameServerManager;
import l2j.luceraV3.loginserver.network.serverpackets.ServerBasePacket;

public class AuthResponse extends ServerBasePacket
{
	public AuthResponse(int serverId)
	{
		writeC(0x02);
		writeC(serverId);
		writeS(GameServerManager.getInstance().getServerNames().get(serverId));
	}
	
	@Override
	public byte[] getContent()
	{
		return getBytes();
	}
}