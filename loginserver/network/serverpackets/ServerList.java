package l2j.luceraV3.loginserver.network.serverpackets;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import l2j.luceraV3.commons.network.ServerType;

import l2j.luceraV3.loginserver.data.manager.GameServerManager;
import l2j.luceraV3.loginserver.model.Account;
import l2j.luceraV3.loginserver.model.GameServerInfo;
import l2j.luceraV3.loginserver.model.ServerData;

public final class ServerList extends L2LoginServerPacket
{
	private final List<ServerData> _servers = new ArrayList<>();
	
	private final int _lastServer;
	
	public ServerList(Account account)
	{
		_lastServer = account.getLastServer();
		
		for (GameServerInfo gsi : GameServerManager.getInstance().getRegisteredGameServers().values())
		{
			final ServerType type = (account.getAccessLevel() < 0 || (gsi.getType() == ServerType.GM_ONLY && account.getAccessLevel() <= 0)) ? ServerType.DOWN : gsi.getType();
			final String hostName = (isLocalIp(account.getClientIp()) && gsi.getGameServerThread() != null) ? gsi.getGameServerThread().getConnectionIp() : gsi.getHostName();
			
			_servers.add(new ServerData(type, hostName, gsi));
		}
	}
	
	@Override
	public void write()
	{
		writeC(0x04);
		writeC(_servers.size());
		writeC(_lastServer);
		
		for (ServerData server : _servers)
		{
			writeC(server.getServerId());
			
			try
			{
				final byte[] raw = InetAddress.getByName(server.getHostName()).getAddress();
				writeC(raw[0] & 0xff);
				writeC(raw[1] & 0xff);
				writeC(raw[2] & 0xff);
				writeC(raw[3] & 0xff);
			}
			catch (UnknownHostException e)
			{
				e.printStackTrace();
				writeC(127);
				writeC(0);
				writeC(0);
				writeC(1);
			}
			
			writeD(server.getPort());
			writeC(server.getAgeLimit());
			writeC(server.isPvp() ? 0x01 : 0x00);
			writeH(server.getCurrentPlayers());
			writeH(server.getMaxPlayers());
			writeC(server.getType() == ServerType.DOWN ? 0x00 : 0x01);
			
			int bits = 0;
			if (server.isTestServer())
				bits |= 0x04;
			
			if (server.isShowingClock())
				bits |= 0x02;
			
			writeD(bits);
			writeC(server.isShowingBrackets() ? 0x01 : 0x00);
		}
	}
	
	public static boolean isLocalIp(InetAddress address)
	{
		return address == null || address.isLinkLocalAddress() || address.isLoopbackAddress() || address.isAnyLocalAddress() || address.isSiteLocalAddress();
	}
}