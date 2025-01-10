package l2j.luceraV3.loginserver;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.LogManager;

import l2j.luceraV3.Config;
import l2j.luceraV3.commons.lang.StringUtil;
import l2j.luceraV3.commons.logging.CLogger;
import l2j.luceraV3.commons.mmocore.SelectorThread;
import l2j.luceraV3.commons.pool.ConnectionPool;
import l2j.luceraV3.loginserver.data.manager.GameServerManager;
import l2j.luceraV3.loginserver.data.manager.IpBanManager;
import l2j.luceraV3.loginserver.data.sql.AccountTable;
import l2j.luceraV3.loginserver.network.LoginClient;
import l2j.luceraV3.loginserver.network.LoginPacketHandler;
import l2j.luceraV3.loginserver.ui.InterfaceLS;

public class LoginServer
{
	private static final CLogger LOGGER = new CLogger(LoginServer.class.getName());
	
	public static final int PROTOCOL_REV = 0x0102;
	
	private static LoginServer _loginServer;
	
	private GameServerListener _gameServerListener;
	private SelectorThread<LoginClient> _selectorThread;
	
	public static void main(String[] args) throws Exception
	{
		_loginServer = new LoginServer();
	}
	
	public LoginServer() throws Exception
	{
		// Create log folder
		new File("./log").mkdir();
		new File("./log/console").mkdir();
		new File("./log/error").mkdir();
		
		// Create input stream for log file -- or store file data into memory
		try (InputStream is = new FileInputStream(new File("config/logging.properties")))
		{
			LogManager.getLogManager().readConfiguration(is);
		}
		
		// GUI
		if (!GraphicsEnvironment.isHeadless())
		{
			System.out.println("Login: Running in Interface GUI.");
			new InterfaceLS();
		}
		
		StringUtil.printSection("Config");
		Config.loadLoginServer();
		
		StringUtil.printSection("Poolers");
		ConnectionPool.init();
		
		AccountTable.getInstance();
		
		StringUtil.printSection("LoginController");
		LoginController.getInstance();
		
		StringUtil.printSection("GameServerManager");
		GameServerManager.getInstance();
		
		StringUtil.printSection("Ban List");
		IpBanManager.getInstance();
		
		StringUtil.printSection("IP, Ports & Socket infos");
		InetAddress bindAddress = null;
		if (!Config.LOGINSERVER_HOSTNAME.equals("*"))
		{
			try
			{
				bindAddress = InetAddress.getByName(Config.LOGINSERVER_HOSTNAME);
			}
			catch (UnknownHostException uhe)
			{
				LOGGER.error("The LoginServer bind address is invalid, using all available IPs.", uhe);
			}
		}
		
		final LoginPacketHandler lph = new LoginPacketHandler();
		final SelectorHelper sh = new SelectorHelper();
		try
		{
			_selectorThread = new SelectorThread<>(sh, lph, sh, sh);
		}
		catch (IOException ioe)
		{
			LOGGER.error("Failed to open selector.", ioe);
			
			System.exit(1);
		}
		
		try
		{
			_gameServerListener = new GameServerListener();
			_gameServerListener.start();
			
			LOGGER.info("Listening for gameservers on {}:{}.", Config.GAMESERVER_LOGIN_HOSTNAME, Config.GAMESERVER_LOGIN_PORT);
		}
		catch (IOException ioe)
		{
			LOGGER.error("Failed to start the gameserver listener.", ioe);
			
			System.exit(1);
		}
		
		try
		{
			_selectorThread.openServerSocket(bindAddress, Config.LOGINSERVER_PORT);
		}
		catch (IOException ioe)
		{
			LOGGER.error("Failed to open server socket.", ioe);
			
			System.exit(1);
		}
		_selectorThread.start();
		LOGGER.info("Loginserver ready on {}:{}.", (bindAddress == null) ? "*" : bindAddress.getHostAddress(), Config.LOGINSERVER_PORT);
		
		StringUtil.printSection("Waiting for gameserver answer");
	}
	
	public static LoginServer getInstance()
	{
		return _loginServer;
	}
	
	public GameServerListener getGameServerListener()
	{
		return _gameServerListener;
	}
	
	public void shutdown(boolean restart)
	{
		Runtime.getRuntime().exit(restart ? 2 : 0);
	}
}