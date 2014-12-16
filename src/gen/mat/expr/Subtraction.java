package gen.mat.expr;

import gen.mat.cas.ExpressionFunction;
import gen.mat.util.Util;

public class Subtraction extends Expression
{
	private Expression left;
	private Expression right;

	public Subtraction(Expression left, Expression right)
	{
		this.left = left;
		this.right = right;
	}

	@Override
	public StringBuffer appendText(StringBuffer buffer)
	{
		return right.appendText(left.appendText(buffer.append("(")).append(") - (")).append(")");
	}

	@Override
	public Expression copy()
	{
		return new Subtraction(left.copy(), right.copy()); 
	}

	@Override
	public Expression apply(ExpressionFunction function)
	{
		left = left.apply(function);
		right = right.apply(function);
		
		return function.receive(this);
	}
	
	public Expression reduce()
	{
		return new Addition(left, new Negation(right));
	}

	@Override
	public StringBuffer appendXml(StringBuffer buffer, int depth)
	{
		Util.indent(buffer, depth).append("<subtraction>\n");
		left.appendXml(buffer, depth + 1);
		right.appendXml(buffer, depth + 1);
		Util.indent(buffer, depth).append("</subtraction>\n");
		return buffer;
	}
}
