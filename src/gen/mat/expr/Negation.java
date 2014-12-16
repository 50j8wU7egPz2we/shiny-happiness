package gen.mat.expr;

import gen.mat.cas.ExpressionFunction;
import gen.mat.util.Util;

public class Negation extends Expression
{
	private Expression argument;
	
	public Negation(Expression arg)
	{
		this.argument = arg;
	}

	@Override
	public StringBuffer appendText(StringBuffer buffer) {
		return argument.appendText(buffer.append("(-(")).append("))");
	}

	@Override
	public Expression copy()
	{
		return new Negation(argument.copy());
	}
	

	public Expression apply(ExpressionFunction function)
	{
		argument = argument.apply(function);
		return function.receive(this);
	}

	@Override
	public StringBuffer appendXml(StringBuffer buffer, int depth)
	{
		Util.indent(buffer, depth).append("<negation>\n");
		argument.appendXml(buffer, depth + 1);
		Util.indent(buffer, depth).append("</negation>\n");
		return buffer;
	}
}
