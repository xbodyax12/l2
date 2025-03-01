package l2j.luceraV3.loginserver.network.clientpackets;

import l2j.luceraV3.commons.logging.CLogger;
import l2j.luceraV3.commons.mmocore.ReceivablePacket;

import l2j.luceraV3.loginserver.network.LoginClient;

public abstract class L2LoginClientPacket extends ReceivablePacket<LoginClient>
{
	protected static final CLogger LOGGER = new CLogger(L2LoginClientPacket.class.getName());
	
	@Override
	protected final boolean read()
	{
		try
		{
			return readImpl();
		}
		catch (Exception e)
		{
			LOGGER.error("Failed reading {}. ", e, getClass().getSimpleName());
			return false;
		}
	}
	
	protected abstract boolean readImpl();
}
