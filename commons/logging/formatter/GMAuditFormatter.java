package l2j.luceraV3.commons.logging.formatter;

import java.util.logging.LogRecord;

import l2j.luceraV3.commons.logging.MasterFormatter;

public class GMAuditFormatter extends MasterFormatter
{
	@Override
	public String format(LogRecord record)
	{
		return "[" + getFormatedDate(record.getMillis()) + "]" + SPACE + record.getMessage() + CRLF;
	}
}