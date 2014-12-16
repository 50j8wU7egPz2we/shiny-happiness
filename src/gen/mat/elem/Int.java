package gen.mat.elem;

import gen.mat.expr.Expression;
import gen.mat.util.Util;

public class Int extends Element
{
	private int value;
	
	public Int(int val)
	{
		this.value = val;
	}

	@Override
	public StringBuffer appendText(StringBuffer buffer)
	{
		return buffer.append(String.valueOf(value));
	}

	@Override
	public Expression copy()
	{
		return new Int(value);
	}

	public Expression reduce()
	{
		if (value == 1)
		{
			return Element.ONE;
		}
		if (value == 0)
		{
			return Element.ZERO;
		}
		return this;
	}

	@Override
	public StringBuffer appendXml(StringBuffer buffer, int depth)
	{
		return Util.indent(buffer, depth).append("<integer>").append(value).append("</integer>\n");
	}
}
