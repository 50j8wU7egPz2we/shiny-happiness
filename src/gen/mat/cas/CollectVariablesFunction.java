package gen.mat.cas;

import gen.mat.elem.Element;
import gen.mat.elem.Variable;
import gen.mat.expr.Expression;

import java.util.Collection;
import java.util.HashSet;

public class CollectVariablesFunction extends SelectiveReceiver
{
	private final HashSet<Variable> variables;

	public CollectVariablesFunction()
	{
		variables = new HashSet<>();
	}
	
	public Collection<Variable> getVariables()
	{
		return variables;
	}
	
	public int count()
	{
		return variables.size();
	}
	
	boolean contains(Variable v)
	{
		return variables.contains(v);
	}

	public Expression receiveElement(Element expr)
	{
		if (!(expr instanceof Variable))
		{
			return expr;
		}
		
		Variable v = (Variable) expr;
		variables.add(v);
		
		return expr;
	}
}
