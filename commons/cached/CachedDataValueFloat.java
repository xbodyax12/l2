package l2j.luceraV3.commons.cached;

public class CachedDataValueFloat extends CachedDataValue
{
	private volatile float value;
	
	CachedDataValueFloat(String name, float defaultValue, int charId)
	{
		super(name, Float.toString(defaultValue), charId);
		value = defaultValue;
	}
	
	@Override
	void load()
	{
		super.load();
		value = Float.parseFloat(getValue());
	}
	
	public float get()
	{
		return value;
	}
	
	public void set(float value)
	{
		this.value = value;
		setValue(Float.toString(value));
	}
}