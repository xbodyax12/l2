package l2j.luceraV3.commons.logging.formatter;

import java.util.logging.LogRecord;

import l2j.luceraV3.commons.lang.StringUtil;
import l2j.luceraV3.commons.logging.MasterFormatter;

public class ChatLogFormatter extends MasterFormatter
{
	@Override
	public String format(LogRecord record)
	{
		final StringBuilder sb = new StringBuilder();
		
		StringUtil.append(sb, "[", getFormatedDate(record.getMillis()), "] ");
		
		for (Object p : record.getParameters())
		{
			if (p == null)
				continue;
			
			StringUtil.append(sb, p, " ");
		}
		
		StringUtil.append(sb, record.getMessage(), CRLF);
		
		return sb.toString();
	}
}