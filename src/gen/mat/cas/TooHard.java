package gen.mat.cas;


public class TooHard
{

	
	/*
	void moveToLeft(Variable variable)
	{
		LinkedList<Expression> leftTerms = new LinkedList<>();
		LinkedList<Expression> rightTerms = new LinkedList<>(); 
		
		if (left instanceof Addition)
		{
			Addition add = (Addition) left;
			for (Expression expr : add.getExpressions())
			{
				if (CASUtil.expressionContainsVariable(expr, variable))
				{
					leftTerms.add(expr);
				}
				else
				{
					rightTerms.add(new Negation(expr));
				}
			}
		}
		else
		{
			leftTerms.add(left);
		}

		if (right instanceof Addition)
		{
			Addition add = (Addition) right;
			for (Expression expr : add.getExpressions())
			{
				if (CASUtil.expressionContainsVariable(expr, variable))
				{
					rightTerms.add(expr);
				}
				else
				{
					leftTerms.add(new Negation(expr));
				}
			}
		}
		else
		{
			rightTerms.add(right);
		}
		
		left = new Addition(leftTerms);
		right = new Addition(rightTerms);
	}*/
}
