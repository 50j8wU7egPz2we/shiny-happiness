
package gen.mat;

import gen.mat.cas.CollectVariablesFunction;
import gen.mat.cas.Equation;
import gen.mat.cas.SubstitutionFunction;
import gen.mat.elem.Matrix;
import gen.mat.elem.Variable;
import gen.mat.expr.Expression;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Main
{
	public static List<Equation> setEqual(Matrix m1, Matrix m2)
	{
		List<Equation> ret = new LinkedList<>();
		
		for (int i = 0; i < m1.rows(); i++)
		{
			for (int j = 0; j < m1.cols(); j++)
			{
				Equation eqn = new Equation(m1.get(i, j), m2.get(i, j));
				eqn.simplify();
				ret.add(eqn);
			}
		}
		return ret;
	}
	
	public static Variable  findVariable(Matrix m, Equation eqn)
	{
		CollectVariablesFunction collectVariablesFunction = new CollectVariablesFunction();
		m.apply(collectVariablesFunction);
		for (Variable v : collectVariablesFunction.getVariables())
		{
			if (eqn.hasVariable(v))
			{
				return v;
			}
		}
		return null;
	}

	private static int DIMENSION = 2;
	
	public static void main(String[] args) throws FileNotFoundException
	{
		Matrix m1 = new Matrix("a", DIMENSION);
		Matrix m2 = new Matrix("q", DIMENSION);
		Matrix ident = new Matrix("foo", DIMENSION);
		ident.makeIdentity();
		
		PrintWriter printWriter = new PrintWriter(new File("output.txt"));
		
		
//		System.out.println(SageParser.parse("-sqrt(-q01^2 - q02^2 + 1)\n").toXml());
		
//		new SageParser().run();
///		if(true) return;
		
		outer:
		while (true)
		{
			List<Equation> setEqual = setEqual(m2.multiply(m2.transpose()), ident);
			
			for (Equation eqn : setEqual)
			{
				printWriter.println("Using: ");
				printWriter.println(eqn.toXml());
				
				Variable v = findVariable(m2, eqn);
				if (v == null)
				{
					printWriter.println("Counld not find equation...");
					continue;
				}
				
				printWriter.println("Solving for " + v);
				List<Expression> solveFor = eqn.solveFor(v);
				
				if (solveFor.isEmpty())
				{
					break outer;
				}
				
				Expression expr = solveFor.get(0); 
				printWriter.println("Found: " + expr.toText());
				
				m2 = (Matrix) m2.apply(new SubstitutionFunction(v, expr));
				m2 = (Matrix) m2.simplify();
				
				printWriter.println(m2);
				printWriter.flush();
			}
			
		}
		
		printWriter.print(m2);
		System.out.println("Done");

		// (1+2+3)*(4+5+6)*(7+8+9)
		
//		m1.makeTridiag();
//		m1.makeSymmetric();
//		m1.makeUpper();
		
//		Matrix m3 = m1.multiply(m2);
//		m3.simplify();
		
//		Expression solveFor = eqn.solveFor(new Variable("a30"));
		
//		solveFor = solveFor.simplify();
//		
//		System.out.println("Solved: " + solveFor.toXml());
//		System.out.println("Solved: " + solveFor.toText());
		
		
//		eqn.useSage(new Variable("a23"));
//		eqn.solve(new Variable("a23"));
//		System.out.println(eqn);
		
//		SubstitutionFunction sf = new SubstitutionFunction(new Variable("a44"), Element.ZERO);
		
//		m3.apply(sf);
//		m3.simplify();
		
//		System.out.println(m3.toText());
	}
}
