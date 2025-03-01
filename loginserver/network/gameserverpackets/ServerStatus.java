package l2j.luceraV3.loginserver.network.gameserverpackets;

import l2j.luceraV3.commons.network.AttributeType;
import l2j.luceraV3.commons.network.ServerType;

import l2j.luceraV3.loginserver.data.manager.GameServerManager;
import l2j.luceraV3.loginserver.model.GameServerInfo;
import l2j.luceraV3.loginserver.network.clientpackets.ClientBasePacket;

public class ServerStatus extends ClientBasePacket
{
	private static final int ON = 0x01;
	
	public ServerStatus(byte[] decrypt, int serverId)
	{
		super(decrypt);
		
		GameServerInfo gsi = GameServerManager.getInstance().getRegisteredGameServers().get(serverId);
		if (gsi != null)
		{
			int size = readD();
			for (int i = 0; i < size; i++)
			{
				int type = readD();
				int value = readD();
				
				switch (AttributeType.VALUES[type])
				{
					case STATUS:
						gsi.setType(ServerType.VALUES[value]);
						break;
					
					case CLOCK:
						gsi.setShowingClock(value == ON);
						break;
					
					case BRACKETS:
						gsi.setShowingBrackets(value == ON);
						break;
					
					case AGE_LIMIT:
						gsi.setAgeLimit(value);
						break;
					
					case TEST_SERVER:
						gsi.setTestServer(value == ON);
						break;
					
					case PVP_SERVER:
						gsi.setPvp(value == ON);
						break;
					
					case MAX_PLAYERS:
						gsi.setMaxPlayers(value);
						break;
				}
			}
		}
	}
}