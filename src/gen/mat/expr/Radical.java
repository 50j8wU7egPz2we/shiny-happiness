package gen.mat.expr;

import gen.mat.cas.ExpressionFunction;
import gen.mat.util.Util;

public class Radical extends Expression
{
	@Override
	public StringBuffer appendText(StringBuffer buffer) {
		return buffer;
	}

	@Override
	public Expression copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression apply(ExpressionFunction function) 
	{
//		argument = argument.apply(function);
		return function.receive(this);
	}

	@Override
	public StringBuffer appendXml(StringBuffer buffer, int depth)
	{
		Util.indent(buffer, depth).append("<radical>\n");
//		expr.appendXml(buffer, depth + 1);
		Util.indent(buffer, depth).append("</radical>\n");
		return buffer;
	}
}
