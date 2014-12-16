package gen.mat.expr;

import gen.mat.cas.ExpressionFunction;
import gen.mat.elem.Element;
import gen.mat.elem.Int;
import gen.mat.util.Util;

public class Exponent extends Expression
{
	private Expression base;
	private Expression power;
	
	public Exponent(Expression base, Expression power)
	{
		this.base = base;
		this.power = power;
	}

	@Override
	public StringBuffer appendText(StringBuffer buffer)
	{
		return power.appendText(base.appendText(buffer.append("(")).append(") ^ (")).append(")");
	}

	@Override
	public Expression copy()
	{
		return new Exponent(base.copy(), power.copy());
	}

	public Expression apply(ExpressionFunction function)
	{
		base = base.apply(function);
		power = power.apply(function);
		return function.receive(this);
	}

	@Override
	public StringBuffer appendXml(StringBuffer buffer, int depth)
	{
		Util.indent(buffer, depth).append("<exponent>\n");
		base.appendXml(buffer, depth + 1);
		power.appendXml(buffer, depth + 1);
		Util.indent(buffer, depth).append("</exponent>\n");
		return buffer;
	}
	
	public static Exponent squareRoot(Expression base)
	{
		return new Exponent(base, new Division(Element.ONE, new Int(2)));
	}
}
