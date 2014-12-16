
package gen.mat.expr;

import gen.mat.cas.ExpressionFunction;
import gen.mat.cas.SimplifyFunction;


public abstract class Expression 
{

	public abstract StringBuffer appendText(StringBuffer buffer);
	public abstract StringBuffer appendXml(StringBuffer buffer, int depth);
	public          StringBuffer appendLatex(StringBuffer buffer) { return buffer.append("Not implemented."); }
	public          StringBuffer appendMatlab(StringBuffer buffer) { return buffer.append("Not implemented."); }
	
	public String toText()
	{
		return appendText(new StringBuffer()).toString();
	}
	
	public String toXml()
	{
		return appendXml(new StringBuffer(), 0).toString();
	}
	
	public final Expression simplify()
	{
		return apply(new SimplifyFunction());
	}
	
	public abstract Expression copy();
	
	public abstract Expression apply(ExpressionFunction function);
	
	public Expression reduce()
	{
		return this;
	}
	
	public String toString()
	{
		return toText();
	}
	
	public int hashCode()
	{
		return toText().hashCode();
	}
	
	public boolean equals(Object other)
	{
		if (!(other instanceof Expression))
		{
			return false;
		}
		Expression expr = (Expression) other;
		
		// Could make this way faster...
		return expr.toText().equals(toText());
	}
}
