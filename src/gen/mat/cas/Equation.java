package gen.mat.cas;

import gen.mat.elem.Variable;
import gen.mat.expr.Expression;
import gen.mat.parse.SageParser;
import gen.mat.util.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Equation
{
	private Expression left;
	private Expression right;
	
	public Equation(Expression left, Expression right)
	{
		this.left = left;
		this.right = right;
	}
	
	public String toString()
	{
		return left.toString() + " == " + right.toString();
	}
	
	private CollectVariablesFunction getVariables()
	{
		CollectVariablesFunction f = new CollectVariablesFunction();
		left.apply(f);
		right.apply(f);
		return f;
	}
	
	public boolean hasVariable(Variable v)
	{
		return getVariables().contains(v);
	}
	
	public void confirm(Variable v)
	{
//		left.simplify();
//		right.simplify();
		CollectVariablesFunction f = getVariables();
		
		if (!f.contains(v))
		{
			Util.die("Trying to solve equation for variable that isn't in the expression");
		}
	}
	
	private static void addSolutionToList(List<Expression> soFar, String line)
	{
		line = line.trim();
		if (line.equals("[") || line.equals("]"))
		{
			return;
		}
		
		int truncate = line.indexOf(",");
		if (truncate >= 0)
		{
			line = line.substring(0, truncate);
		}
		
		int indexOf = line.indexOf("==");
		if (indexOf < 0)
		{
			System.out.println("Unexpected input: " + truncate);
			return;
		}
		
		String leftSide = line.substring(0, indexOf);
		String rightSide = line.substring(indexOf + 2);
		
		System.out.println("l: " + leftSide);
		System.out.println("r: " + rightSide);
		System.out.println();
		
		rightSide = rightSide + "\n";
		
		Expression parse = SageParser.parse(rightSide);
		if (parse == null)
		{
			Util.die("Unable to parse '" + rightSide + "'");
			return;
		}
		soFar.add(parse);
	}

	
	public List<Expression> solveFor(Variable v)
	{
		confirm(v);
		
		String cmdFile = "./sage_files/sage_command_" + Util.getRandomString() + ".sage";
		String outFile = "./sage_files/sage_output_" + Util.getRandomString() + ".txt";
		
		String cmd = getSolveCommand(v, outFile);
		
		if (!Util.writeToFile(cmd, cmdFile))
		{
			return null;
		}
		
		try
		{
			Process exec = Runtime.getRuntime().exec(new String[] {"sage", cmdFile});
			exec.waitFor();
			System.out.println("Return value: " + exec.exitValue());
		}
		catch (IOException e)
		{
			e.printStackTrace();
			Util.die("IO exception");
			return null;
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
			Util.die("IO exception");
			return null;
		}
		
		LinkedList<Expression> linkedList = new LinkedList<>();

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(outFile)));)
		{
			String line;
			while ((line = bufferedReader.readLine()) != null)
			{
				addSolutionToList(linkedList, line);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			Util.die("Unable to read output of sage");
			return null;
		}
		
		return linkedList;
	}
	
	private String getSolveCommand(Variable variable, String outputfile)
	{
		StringBuilder builder = new StringBuilder();
		
		CollectVariablesFunction f = new CollectVariablesFunction();
		left.apply(f);
		right.apply(f);
		
		StringBuilder l = new StringBuilder();
		int count = 0;
		for (Variable v : f.getVariables())
		{
			l.append(v.toText());
			if (count++ != f.count() - 1)
			{
				l.append(", ");
			}
		}
		
		builder.append(l);
		builder.append(" = var('");
		builder.append(l);
		builder.append("');");
		builder.append('\n');
		
		builder.append("eqn = ");
		builder.append(left);
		builder.append("==");
		builder.append(right);
		builder.append(";\n");
		
		builder.append("f = file('");
		builder.append(outputfile);
		builder.append("', 'w');\n");
		builder.append("f.write(str(eqn.solve(");
		builder.append(variable);
		builder.append(")));\n");
		builder.append("f.close();\n");
		
		return builder.toString();
	}

	public void simplify()
	{
		left = left.simplify();
		right = right.simplify();
	}

	public String toXml()
	{
		StringBuffer buffer = new StringBuffer();
		Util.indent(buffer, 0).append("<equation>\n");

		Util.indent(buffer, 1).append("<left>\n");
		left.appendXml(buffer, 2);
		Util.indent(buffer, 1).append("</left>\n");
		
		Util.indent(buffer, 1).append("<right>\n");
		right.appendXml(buffer, 2);
		Util.indent(buffer, 1).append("</right>\n");
		
		Util.indent(buffer, 0).append("</equation>\n");
		
		return buffer.toString();
	}
}
