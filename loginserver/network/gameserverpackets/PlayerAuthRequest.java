package l2j.luceraV3.loginserver.network.gameserverpackets;

import l2j.luceraV3.loginserver.network.SessionKey;
import l2j.luceraV3.loginserver.network.clientpackets.ClientBasePacket;

public class PlayerAuthRequest extends ClientBasePacket
{
	private final String _account;
	private final SessionKey _sessionKey;
	
	public PlayerAuthRequest(byte[] decrypt)
	{
		super(decrypt);
		
		_account = readS();
		
		int playKey1 = readD();
		int playKey2 = readD();
		int loginKey1 = readD();
		int loginKey2 = readD();
		
		_sessionKey = new SessionKey(loginKey1, loginKey2, playKey1, playKey2);
	}
	
	public String getAccount()
	{
		return _account;
	}
	
	public SessionKey getKey()
	{
		return _sessionKey;
	}
}