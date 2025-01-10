package l2j.luceraV3.loginserver.network;

import java.nio.ByteBuffer;

import l2j.luceraV3.commons.logging.CLogger;
import l2j.luceraV3.commons.mmocore.IPacketHandler;
import l2j.luceraV3.commons.mmocore.ReceivablePacket;

import l2j.luceraV3.loginserver.enums.LoginClientState;
import l2j.luceraV3.loginserver.network.clientpackets.AuthGameGuard;
import l2j.luceraV3.loginserver.network.clientpackets.RequestAuthLogin;
import l2j.luceraV3.loginserver.network.clientpackets.RequestServerList;
import l2j.luceraV3.loginserver.network.clientpackets.RequestServerLogin;

/**
 * Handler for packets received by Login Server
 */
public final class LoginPacketHandler implements IPacketHandler<LoginClient>
{
	private static final CLogger LOGGER = new CLogger(LoginPacketHandler.class.getName());
	
	@Override
	public ReceivablePacket<LoginClient> handlePacket(ByteBuffer buf, LoginClient client)
	{
		int opcode = buf.get() & 0xFF;
		
		ReceivablePacket<LoginClient> packet = null;
		LoginClientState state = client.getState();
		
		switch (state)
		{
			case CONNECTED:
				if (opcode == 0x07)
					packet = new AuthGameGuard();
				else
					debugOpcode(opcode, state);
				break;
			
			case AUTHED_GG:
				if (opcode == 0x00)
					packet = new RequestAuthLogin();
				else
					debugOpcode(opcode, state);
				break;
			
			case AUTHED_LOGIN:
				if (opcode == 0x05)
					packet = new RequestServerList();
				else if (opcode == 0x02)
					packet = new RequestServerLogin();
				else
					debugOpcode(opcode, state);
				break;
		}
		return packet;
	}
	
	private static void debugOpcode(int opcode, LoginClientState state)
	{
		LOGGER.warn("Unknown Opcode: " + opcode + " for state: " + state.name());
	}
}