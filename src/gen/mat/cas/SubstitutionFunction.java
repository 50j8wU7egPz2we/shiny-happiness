package gen.mat.cas;

import gen.mat.elem.Element;
import gen.mat.elem.Variable;
import gen.mat.expr.Expression;

public class SubstitutionFunction extends SelectiveReceiver
{
	private Variable variable;
	private Expression expression;
	
	public SubstitutionFunction(Variable v, Expression toSubstitute)
	{
		this.variable = v;
		this.expression = toSubstitute;
	}
	
	public Expression receiveElement(Element expr)
	{
		if (!(expr instanceof Variable))
		{
			return expr;
		}
		
		Variable v = (Variable) expr;
		if (!variable.equals(v))
		{
			return expr;
		}
		
		return expression.copy();
	}
}
