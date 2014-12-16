package gen.mat.elem;

import gen.mat.expr.Expression;
import gen.mat.util.Util;

public class Real extends Element
{
	private double value;
	
	public Real(double value)
	{
		this.value = value;
	}

	@Override
	public StringBuffer appendText(StringBuffer buffer)
	{
		return buffer.append(value);
	}

	@Override
	public Expression copy()
	{
		return new Real(value);
	}
	
	public Expression reduce()
	{
		if (value == 1.0)
		{
			return Element.ONE;
		}
		if (value == 0.0)
		{
			return Element.ZERO;
		}
		if (value == (int) value)
		{
			return new Int((int) value);
		}
		return this;
	}

	@Override
	public StringBuffer appendXml(StringBuffer buffer, int depth)
	{
		return Util.indent(buffer, depth).append("<real>").append(value).append("</real>\n");
	}
}
