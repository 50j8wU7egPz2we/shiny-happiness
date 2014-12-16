package gen.mat.expr;

import gen.mat.cas.ExpressionFunction;
import gen.mat.util.Util;

public class Paren extends Expression
{
	private Expression child;
	
	public Paren(Expression child)
	{
		this.child = child;
	}

	@Override
	public StringBuffer appendText(StringBuffer buffer)
	{
		return buffer.append(child);
	}

	@Override
	public Expression copy()
	{
		return child.copy();
	}

	@Override
	public Expression apply(ExpressionFunction function)
	{
		child.apply(function);
		return function.receive(this);
	}
	
	public Expression reduce()
	{
		return child;
	}

	@Override
	public StringBuffer appendXml(StringBuffer buffer, int depth)
	{
		Util.indent(buffer, depth).append("<paren>\n");
		child.appendXml(buffer, depth + 1);
		Util.indent(buffer, depth).append("</paren>\n");
		return buffer;
	}
}
