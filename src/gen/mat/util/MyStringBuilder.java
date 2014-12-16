package gen.mat.util;

public final class MyStringBuilder
{
	private Link first;
	private Link last;
	private int length;
	
	public MyStringBuilder()
	{
		first = new Link(null, "");
		last = first;
	}
	
	public MyStringBuilder prepend(String val)
	{
		Link l = new Link(null, val);
		l.next = first;
		first = l;
		length += val.length();
		return this;
	}
	
	public MyStringBuilder append(String val)
	{
		last = new Link(last, val);
		length += val.length();
		return this;
	}

	public MyStringBuilder append(byte val)
	{
		return append(String.valueOf(val));
	}
	public MyStringBuilder append(char val)
	{
		return append(String.valueOf(val));
	}
	public MyStringBuilder append(short val)
	{
		return append(String.valueOf(val));
	}
	public MyStringBuilder append(int val)
	{
		return append(String.valueOf(val));
	}
	public MyStringBuilder append(long val)
	{
		return append(String.valueOf(val));
	}
	public MyStringBuilder append(boolean val)
	{
		return append(String.valueOf(val));
	}
	public MyStringBuilder append(double val)
	{
		return append(String.valueOf(val));
	}
	public MyStringBuilder append(float val)
	{
		return append(String.valueOf(val));
	}
	public MyStringBuilder append(Object val)
	{
		return append(String.valueOf(val));
	}
	
	
	
	
	

	public MyStringBuilder prepend(byte val)
	{
		return prepend(String.valueOf(val));
	}
	public MyStringBuilder prepend(char val)
	{
		return prepend(String.valueOf(val));
	}
	public MyStringBuilder prepend(short val)
	{
		return prepend(String.valueOf(val));
	}
	public MyStringBuilder prepend(int val)
	{
		return prepend(String.valueOf(val));
	}
	public MyStringBuilder prepend(long val)
	{
		return prepend(String.valueOf(val));
	}
	public MyStringBuilder prepend(boolean val)
	{
		return prepend(String.valueOf(val));
	}
	public MyStringBuilder prepend(double val)
	{
		return prepend(String.valueOf(val));
	}
	public MyStringBuilder prepend(float val)
	{
		return prepend(String.valueOf(val));
	}
	public MyStringBuilder prepend(Object val)
	{
		return prepend(String.valueOf(val));
	}
	
	
	
	
	
	public String toString()
	{
		StringBuilder bldr = new StringBuilder(length);
		for (Link l = first; l != null; l = l.next)
		{
			bldr.append(l.cur);
		}
		String value = bldr.toString();

		first = new Link(null, value);
		last = first;
		
		return value;
	}
	
	
	
	
	
	
	private static final class Link
	{
		private String cur;
		private Link next;
		
		public Link(Link prev, String cur)
		{
			this.cur = cur;
			if (prev != null)
			{
				prev.next = this;
			}
		}
	}
	
	
	
	
	
	

//	MyStringBuilder myStrBldr = new MyStringBuilder();
//	myStrBldr.append("This i");
//	myStrBldr.append("s a sent");
//	myStrBldr.append("ence.");
//	System.out.println(myStrBldr);
}
