package gen.mat.cas;

import gen.mat.elem.Variable;
import gen.mat.expr.Expression;

public class CASUtil
{
	public static boolean expressionContainsVariable(Expression expr, Variable variable)
	{
		CollectVariablesFunction f = new CollectVariablesFunction();
		expr.apply(f);
		return f.contains(variable);
	}

}
