
package gen.mat.elem;

import gen.mat.cas.ExpressionFunction;
import gen.mat.expr.Addition;
import gen.mat.expr.Expression;
import gen.mat.expr.Multiplication;
import gen.mat.util.Util;




public class Matrix extends Element
{
	private String name;
	private Expression[][] elements;
	
	
	private static final int INDEX_PAD_LENGTH = 1;
	
	public Matrix(String name, int rows)
	{
		this(name, rows, rows);
	}
	
	public Matrix(String name, int rows, int cols)
	{
		this.name = name;
		elements = new Expression[rows][cols];
		
		for (int i=0; i<rows; i++)
		{
			for (int j=0; j<cols; j++)
			{
				elements[i][j] = new Variable(name + Util.pad(String.valueOf(i), INDEX_PAD_LENGTH)
								+  Util.pad(String.valueOf(j), INDEX_PAD_LENGTH));
			}
		}
	}
	
	public int rows()
	{
		return elements.length;
	}
	
	public int cols()
	{
		return elements[0].length;
	}
	
	public Matrix multiply(Matrix other)
	{
		Matrix result = new Matrix("prod", elements.length, other.elements[0].length);
		final int r = rows();
		final int c = other.cols();
		final int mid = cols();
		if (mid != other.rows())
		{
			Util.die("Bad matrix multiplication dimensions");
		}
		
		for (int i=0; i<r; i++)
		{
			for (int j=0; j<c; j++)
			{
				Expression element = Element.ZERO;
				
				for (int k=0; k<mid; k++)
				{
					element = new Addition(element, new Multiplication(get(i,k), other.get(k, j)));
				}
				
				result.set(i, j, element);
			}
		}
		
		return result;
	}
	
	public Expression get(int i, int j)
	{
		return elements[i][j];
	}
	
	public void set(int i, int j, Expression expr)
	{
		elements[i][j] = expr;
	}

	@Override
	public StringBuffer appendText(StringBuffer buffer)
	{
		String[][] elementStrings = new String[rows()][cols()];
		
		int max = 0;
		
		for (int i=0; i<rows(); i++)
		{
			for (int j=0; j<cols(); j++)
			{
				elementStrings[i][j] = get(i, j).appendText(new StringBuffer()).toString();
				max = Math.max(max, elementStrings[i][j].length());
			}
		}

		for (int i=0; i<rows(); i++)
		{
			for (int j=0; j<cols(); j++)
			{
				Util.pad(buffer, elementStrings[i][j], max + 2);
			}
			buffer.append('\n');
		}
		
		return buffer;
	}
	
	public Matrix transpose()
	{
		Matrix transpose = new Matrix(name, cols(), rows());
		
		for (int i = 0; i < cols(); i++)
		{
			for (int j = 0; j < rows(); j++)
			{
				transpose.set(i, j, get(j, i).copy());
			}
		}
		
		return transpose;
	}
	
	public void makeTridiag()
	{
		for (int i=0;i<rows();i++)
		{
			for (int j=0;j<cols();j++)
			{
				if (Math.abs(i - j) >= 2)
				{
					set(i, j, Element.ZERO);
				}
			}
		}
	}
	
	public void makeUpper()
	{
		for (int i=0;i<rows();i++)
		{
			for (int j=0;j<cols();j++)
			{
				if (j < i)
				{
					set(i, j, Element.ZERO);
				}
			}
		}
	}
	
	public void makeSymmetric()
	{
		for (int i=0; i<rows(); i++)
		{
			for (int j=0; j<cols(); j++)
			{
				if (i > j)
				{
					set(i, j, get(j, i).copy());
				}
			}
		}
	}
	
	public void makeIdentity()
	{
		for (int i=0; i<rows(); i++)
		{
			for (int j=0; j<cols(); j++)
			{
				set(i, j, i == j ? Element.ONE : Element.ZERO);
			}
		}
	}

	@Override
	public Expression copy()
	{
		Matrix ret = new Matrix(name, rows(), cols());
		for (int i=0; i<rows(); i++)
		{
			for (int j=0; j<cols(); j++)
			{
				ret.set(i, j, get(i, j).copy());
			}
		}
		return ret;
	}

	public Expression apply(ExpressionFunction function)
	{
		for (int i=0; i<rows(); i++)
		{
			for (int j=0; j<cols(); j++)
			{
				set(i, j, get(i, j).apply(function));
			}
		}
		return function.receive(this);
	}

	@Override
	public StringBuffer appendXml(StringBuffer buffer, int depth)
	{
		Util.indent(buffer, depth).append("<matrix>\n");
		for (int i = 0; i < rows(); i++)
		{
			for (int j = 0; j < cols(); j++)
			{
				
				Util.indent(buffer, depth+1).append("<element row=\"").append(i).append("\" col=\"").append(j).append("\">\n");
				get(i, j).appendXml(buffer, depth + 2);
				Util.indent(buffer, depth+1).append("</element>\n");
			}
		}
		return Util.indent(buffer, depth).append("</matrix>\n");
	}
}
