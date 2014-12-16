package gen.mat.elem;

import gen.mat.expr.Expression;
import gen.mat.util.Util;

public class Variable extends Element
{
	String name;

	public Variable(String name)
	{
		this.name = name;
	}

	@Override
	public StringBuffer appendText(StringBuffer buffer)
	{
		return buffer.append(name);
	}

	@Override
	public Expression copy()
	{
		return new Variable(name);
	}
	
	public int hashCode()
	{
		return name.hashCode();
	}
	
	public boolean equals(Object o)
	{
		if (!(o instanceof Variable))
		{
			return false;
		}
		Variable v = (Variable) o;
		return name.equals(v.name);
	}

	@Override
	public StringBuffer appendXml(StringBuffer buffer, int depth)
	{
		return Util.indent(buffer, depth).append("<variable>").append(name).append("</variable>\n");
	}
}
