package gen.mat.expr;

import gen.mat.cas.ExpressionFunction;
import gen.mat.elem.Element;
import gen.mat.util.Util;

import java.util.ArrayList;
import java.util.Collection;

public class Addition extends Expression
{
	private ArrayList<Expression> expressions;
	
	public Addition(Expression left, Expression right)
	{
		expressions = new ArrayList<>(2);
		expressions.add(left);
		expressions.add(right);
	}
	
	public Addition(Collection<Expression> exprs)
	{
		expressions = new ArrayList<>(exprs);
	}
	
	public Addition()
	{
		expressions = new ArrayList<>();
	}
	
	public ArrayList<Expression> getExpressions()
	{
		return new ArrayList<>(expressions);
	}

	@Override
	public StringBuffer appendText(StringBuffer buffer)
	{
		return Util.join(buffer, expressions, "+");
	}

	@Override
	public Expression copy()
	{
		Addition addition = new Addition();
		for (Expression expr : expressions)
		{
			addition.expressions.add(expr.copy());
		}
		return addition;
	}

	public Expression apply(ExpressionFunction function)
	{
		for (int i=0; i<expressions.size(); i++)
		{
			expressions.set(i, expressions.get(i).apply(function));
		}
		return function.receive(this);
	}
	
	public Expression reduce()
	{
		// Flatten
		for (int i=0; i<expressions.size(); i++)
		{
			if (expressions.get(i) instanceof Addition)
			{
				Addition child = (Addition) expressions.get(i);
				ArrayList<Expression> expressions2 = child.expressions;
				expressions.remove(i);
				expressions.addAll(expressions2);
				i = 0;
				continue;
			}
			if (expressions.get(i) instanceof Subtraction)
			{
				// could flatten subtraction
			}
		}
		for (int i=0; i<expressions.size(); i++)
		{
			if (expressions.get(i).equals(Element.ZERO))
			{
				expressions.remove(i);
				i = 0;
			}
		}
		
		if (expressions.size() == 1)
		{
			return expressions.get(0);
		}
		
		return this;
	}
	
	public void addTerm(Expression expr)
	{
		expressions.add(expr);
	}

	@Override
	public StringBuffer appendXml(StringBuffer buffer, int depth)
	{
		Util.indent(buffer, depth).append("<addition>\n");
		for(Expression expr : expressions)
		{
			expr.appendXml(buffer, depth + 1);
		}
		Util.indent(buffer, depth).append("</addition>\n");
		return buffer;
	}
}
