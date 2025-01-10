package l2j.luceraV3.loginserver.network.clientpackets;

import l2j.luceraV3.Config;
import l2j.luceraV3.loginserver.model.Account;
import l2j.luceraV3.loginserver.network.SessionKey;
import l2j.luceraV3.loginserver.network.serverpackets.LoginFail;
import l2j.luceraV3.loginserver.network.serverpackets.PlayFail;
import l2j.luceraV3.loginserver.network.serverpackets.PlayOk;

public class RequestServerLogin extends L2LoginClientPacket
{
	private int _skey1;
	private int _skey2;
	private int _serverId;
	
	public int getSessionKey1()
	{
		return _skey1;
	}
	
	public int getSessionKey2()
	{
		return _skey2;
	}
	
	public int getServerID()
	{
		return _serverId;
	}
	
	@Override
	public boolean readImpl()
	{
		if (super._buf.remaining() >= 9)
		{
			_skey1 = readD();
			_skey2 = readD();
			_serverId = readC();
			return true;
		}
		return false;
	}
	
	@Override
	public void run()
	{
		final SessionKey sk = getClient().getSessionKey();
		
		// Check Show Licence window.
		if (Config.SHOW_LICENCE && !sk.checkLoginPair(_skey1, _skey2))
		{
			getClient().close(LoginFail.REASON_ACCESS_FAILED);
			return;
		}
		
		// Check Account integrity.
		final Account account = getClient().getAccount();
		if (account == null)
		{
			getClient().close(LoginFail.REASON_ACCESS_FAILED);
			return;
		}
		
		// Check possibility of login.
		if (!account.isLoginPossible(_serverId))
		{
			getClient().close(PlayFail.REASON_TOO_MANY_PLAYERS);
			return;
		}
		
		getClient().setJoinedGS(true);
		getClient().sendPacket(new PlayOk(sk));
	}
}
