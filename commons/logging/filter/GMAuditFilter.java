package l2j.luceraV3.commons.logging.filter;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class GMAuditFilter implements Filter
{
	@Override
	public boolean isLoggable(LogRecord record)
	{
		return record.getLoggerName().equalsIgnoreCase("gmaudit");
	}
}