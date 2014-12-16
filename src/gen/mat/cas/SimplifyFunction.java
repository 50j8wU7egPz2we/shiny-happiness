package gen.mat.cas;

import gen.mat.expr.Expression;

public class SimplifyFunction extends ExpressionFunction
{	
	public Expression receive(Expression expr)
	{
		String oldOne = expr.toText();
		
		for (;;)
		{
			expr = expr.reduce();
			String value = expr.toText();
			if (value.equals(oldOne))
			{
				break;
			}
			oldOne = value;
		}
		
		return expr;
	}
}
