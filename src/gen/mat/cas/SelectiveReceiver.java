package gen.mat.cas;

import gen.mat.elem.Element;
import gen.mat.expr.Addition;
import gen.mat.expr.Division;
import gen.mat.expr.Exponent;
import gen.mat.expr.Expression;
import gen.mat.expr.Multiplication;
import gen.mat.expr.Negation;
import gen.mat.expr.Paren;
import gen.mat.expr.Radical;
import gen.mat.expr.Subtraction;
import gen.mat.util.Util;

public class SelectiveReceiver extends ExpressionFunction
{
	public Expression receive(Expression expr)
	{
		if (expr instanceof Addition)
		{
			return receiveAddition((Addition) expr);
		}

		if (expr instanceof Element)
		{
			return receiveElement((Element) expr);
		}

		if (expr instanceof Division)
		{
			return receiveDivision((Division) expr);
		}

		if (expr instanceof Exponent)
		{
			return receiveExponent((Exponent) expr);
		}

		if (expr instanceof Multiplication)
		{
			return receiveMultiplication((Multiplication) expr);
		}

		if (expr instanceof Negation)
		{
			return receiveNegation((Negation) expr);
		}

		if (expr instanceof Radical)
		{
			return receiveRadical((Radical) expr);
		}

		if (expr instanceof Subtraction)
		{
			return receiveSubtraction((Subtraction) expr);
		}
		
		if (expr instanceof Paren)
		{
			return receiveParen((Paren) expr);
		}
		
		Util.die("Could not handle expression " + expr.toText());
		
		return expr;
	}
	
	protected Expression receiveParen(Paren expr)
	{
		return expr;
	}
	
	protected Expression receiveSubtraction(Subtraction expr)
	{
		return expr;
	}
	
	protected Expression receiveRadical(Radical expr)
	{
		return expr;
	}
	
	protected Expression receiveNegation(Negation expr)
	{
		return expr;
	}
	
	protected Expression receiveMultiplication(Multiplication expr)
	{
		return expr;
	}
	
	protected Expression receiveExponent(Exponent expr)
	{
		return expr;
	}
	
	protected Expression receiveDivision(Division expr)
	{
		return expr;
	}
	
	protected Expression receiveElement(Element expr)
	{
		return expr;
	}
	
	protected Expression receiveAddition(Addition expr)
	{
		return expr;
	}
}
