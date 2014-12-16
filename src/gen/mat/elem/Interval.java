package gen.mat.elem;

import gen.mat.expr.Expression;
import gen.mat.util.Util;

public class Interval extends Element
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
	public StringBuffer appendXml(StringBuffer buffer, int depth)
	{
		Util.indent(buffer, depth).append("<interval>").append("</interval>\n");
		return buffer;
	}
}
