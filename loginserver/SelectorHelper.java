package l2j.luceraV3.loginserver;

import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import l2j.luceraV3.commons.mmocore.IAcceptFilter;
import l2j.luceraV3.commons.mmocore.IClientFactory;
import l2j.luceraV3.commons.mmocore.IMMOExecutor;
import l2j.luceraV3.commons.mmocore.MMOConnection;
import l2j.luceraV3.commons.mmocore.ReceivablePacket;

import l2j.luceraV3.loginserver.data.manager.IpBanManager;
import l2j.luceraV3.loginserver.network.LoginClient;
import l2j.luceraV3.loginserver.network.serverpackets.Init;
import l2j.luceraV3.util.IPv4Filter;

public class SelectorHelper implements IMMOExecutor<LoginClient>, IClientFactory<LoginClient>, IAcceptFilter
{
	private final ThreadPoolExecutor _generalPacketsThreadPool;
	
	private final IPv4Filter _ipv4filter;
	
	public SelectorHelper()
	{
		_generalPacketsThreadPool = new ThreadPoolExecutor(4, 6, 15L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		_ipv4filter = new IPv4Filter();
	}
	
	@Override
	public boolean accept(Socket socket)
	{
		return _ipv4filter.accept(socket) && !IpBanManager.getInstance().isBannedAddress(socket.getInetAddress());
	}
	
	@Override
	public LoginClient create(MMOConnection<LoginClient> con)
	{
		LoginClient client = new LoginClient(con);
		client.sendPacket(new Init(client));
		return client;
	}
	
	@Override
	public void execute(ReceivablePacket<LoginClient> packet)
	{
		_generalPacketsThreadPool.execute(packet);
	}
}