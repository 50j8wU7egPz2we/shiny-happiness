package gen.mat.expr;

import gen.mat.cas.ExpressionFunction;
import gen.mat.util.Util;

public class Division extends Expression
{
	private Expression numerator;
	private Expression denominator;
	
	public Division(Expression numerator, Expression denominator)
	{
		this.numerator = numerator;
		this.denominator = denominator;
	}

	@Override
	public StringBuffer appendText(StringBuffer buffer) {
		return denominator.appendText(numerator.appendText(buffer.append('(')).append(")/(")).append(')');
	}
	@Override
	public Expression copy()
	{
		return new Division(numerator.copy(), denominator.copy());
	}

	public Expression apply(ExpressionFunction function)
	{
		numerator = numerator.apply(function);
		denominator = denominator.apply(function);
		return function.receive(this);
	}

	@Override
	public StringBuffer appendXml(StringBuffer buffer, int depth)
	{
		Util.indent(buffer, depth).append("<division>\n");
		numerator.appendXml(buffer, depth + 1);
		denominator.appendXml(buffer, depth + 1);
		Util.indent(buffer, depth).append("</division>\n");
		return buffer;
	}
}
