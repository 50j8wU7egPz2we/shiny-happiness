package gen.mat.elem;

import gen.mat.cas.ExpressionFunction;
import gen.mat.expr.Expression;
import gen.mat.util.Util;

public abstract class Element extends Expression
{
	@Override
	public Expression apply(ExpressionFunction function)
	{
		return function.receive(this);
	}
	
	public static final Element ONE      = new SpecialElement("1                         ".trim()) {};
	public static final Element ZERO     = new SpecialElement("0                         ".trim()) {};
	public static final Element NAN      = new SpecialElement("NAN                       ".trim()) {};
	public static final Element INFINITY = new SpecialElement("Infinity                  ".trim()) {};
	public static final Element DONTCARE = new SpecialElement("X                         ".trim()) {};

	public static class SpecialElement extends Element
	{
		String text;
		SpecialElement(String t)
		{
			this.text = t;
		}
		
		@Override
		public StringBuffer appendText(StringBuffer buffer) {
			return buffer.append(text);
		}

		@Override
		public Expression copy()
		{
			return this;
		}

		@Override
		public StringBuffer appendXml(StringBuffer buffer, int depth)
		{
			Util.indent(buffer, depth).append("<special>").append(text).append("</special>\n");
			return buffer;
		}
	}
}
