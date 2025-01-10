package l2j.luceraV3.commons.logging.filter;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class ChatFilter implements Filter
{
	@Override
	public boolean isLoggable(LogRecord record)
	{
		return record.getLoggerName().equals("chat");
	}
}