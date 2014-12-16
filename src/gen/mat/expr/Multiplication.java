package gen.mat.expr;

import gen.mat.cas.ExpressionFunction;
import gen.mat.elem.Element;
import gen.mat.util.Util;

import java.util.ArrayList;

public class Multiplication extends Expression
{
	private final ArrayList<Expression> expressions;

	public Multiplication() {
		expressions = new ArrayList<>();
	}
	public Multiplication(Expression left, Expression right) {
		expressions = new ArrayList<>();
		expressions.add(left);
		expressions.add(right);
	}

	@Override
	public StringBuffer appendText(StringBuffer buffer) {
		return Util.join(buffer, expressions, "*");
	}

	@Override
	public Expression copy()
	{
		Multiplication addition = new Multiplication();
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
			if (expressions.get(i) instanceof Multiplication)
			{
				Multiplication child = (Multiplication) expressions.get(i);
				ArrayList<Expression> expressions2 = child.expressions;
				expressions.remove(i);
				expressions.addAll(expressions2);
				i = 0;
				continue;
			}
			if (expressions.get(i) instanceof Division)
			{
				// could flatten division
			}
		}
		
		for (int i=0; i<expressions.size(); i++)
		{
			if (expressions.get(i).equals(Element.ONE))
			{
				expressions.remove(i);
				i = 0;
			}
		}

		for (Expression expr : expressions)
		{
			if (expr.equals(Element.ZERO))
			{
				return Element.ZERO;
			}
		}

		for (int i=0; i<expressions.size(); i++)
		{
			if (expressions.get(i) instanceof Addition)
			{
				return expand(i);
			}
		}
		
		if (expressions.size() == 1)
		{
			return expressions.get(0);
		}

		// expand all multiplications of sums...
		
		return this;
	}
	
	private Expression expand(int index)
	{
		Multiplication otherFactors = new Multiplication();
		for (int i=0; i<expressions.size(); i++)
		{
			if (i == index)
			{
				continue;
			}
			otherFactors.expressions.add(expressions.get(i));
		}
		
		Addition returnValue = new Addition();
		Addition child = (Addition) expressions.get(index);
		for (Expression children : child.getExpressions())
		{
			returnValue.addTerm(new Multiplication(children, otherFactors.copy()));
		}
		
		return returnValue.simplify();
	}

	@Override
	public StringBuffer appendXml(StringBuffer buffer, int depth)
	{
		Util.indent(buffer, depth).append("<multiplication>\n");
		for(Expression expr : expressions)
		{
			expr.appendXml(buffer, depth + 1);
		}
		Util.indent(buffer, depth).append("</multiplication>\n");
		return buffer;
	}
}
