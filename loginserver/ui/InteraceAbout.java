package l2j.luceraV3.loginserver.ui;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.Window.Type;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class InteraceAbout
{
	private JFrame frmAbout;
	private static final String URL = "www.l2jdev.com.br";
	final URI uri;
	
	public InteraceAbout()
	{
		initialize();
		uri = createURI(URL);
		frmAbout.setVisible(true);
	}
	
	private void initialize()
	{
		frmAbout = new JFrame();
		frmAbout.setResizable(false);
		frmAbout.setTitle("About");
		frmAbout.setBounds(100, 100, 297, 197);
		frmAbout.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frmAbout.setType(Type.UTILITY);
		frmAbout.getContentPane().setLayout(null);
		
		final JLabel lblLjmobius = new JLabel("BAN - L2JDEV");
		lblLjmobius.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lblLjmobius.setHorizontalAlignment(SwingConstants.CENTER);
		lblLjmobius.setBounds(10, 11, 271, 39);
		frmAbout.getContentPane().add(lblLjmobius);
		
		final JLabel lblData = new JLabel("2025-" + Calendar.getInstance().get(Calendar.YEAR));
		lblData.setHorizontalAlignment(SwingConstants.CENTER);
		lblData.setBounds(10, 44, 271, 14);
		frmAbout.getContentPane().add(lblData);
		
		final JLabel lblLoginServer = new JLabel("Login Server");
		lblLoginServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginServer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLoginServer.setBounds(10, 86, 271, 23);
		frmAbout.getContentPane().add(lblLoginServer);
		
		final JLabel site = new JLabel(URL);
		site.setText("<html><font color=\"#000099\"><u>" + URL + "</u></font></html>");
		site.setHorizontalAlignment(SwingConstants.CENTER);
		site.setBounds(76, 128, 140, 14);
		site.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				if (Desktop.isDesktopSupported())
				{
					try
					{
						Desktop.getDesktop().browse(uri);
					}
					catch (IOException e)
					{
						// Ignore.
					}
				}
			}
		});
		frmAbout.getContentPane().add(site);
		
		// Center frame to screen.
		frmAbout.setLocationRelativeTo(null);
	}
	
	private static URI createURI(String str)
	{
		try
		{
			return new URI(str);
		}
		catch (URISyntaxException x)
		{
			throw new IllegalArgumentException(x.getMessage(), x);
		}
	}
}