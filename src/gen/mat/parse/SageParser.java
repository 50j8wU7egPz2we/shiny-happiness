package gen.mat.parse;

import gen.mat.cas.SimplifyFunction;
import gen.mat.expr.Expression;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

public class SageParser
{
	public static void handleExpression(Expression expr)
	{
		expr = expr.apply(new SimplifyFunction());
		
		System.out.println(" = \n" + expr.toXml());
		System.out.println(" = " + expr.toText());
	}
	
	public void run()
	{
		String[] args = new String[0];
		
		System.out.println("BYACC/Java with JFlex Calculator Demo");
		
		Parser yyparser = null;
		if (args.length > 0)
		{
			// parse a file
			try
			{
				yyparser = new Parser(new FileReader(args[0]));
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			// interactive mode
			System.out.print("Expression: ");
			yyparser = new Parser(new InputStreamReader(System.in));
		}
		
		yyparser.yyparse();
		
		if (true)
		{
			System.out.println();
			System.out.println("Have a nice day");
		}
	}
	
	public static Expression parse(String result)
	{
		Parser parser = new Parser(new InputStreamReader(new ByteArrayInputStream((result).getBytes())));
		parser.yyparse();
		return parser.parsed;
	}
}
