package gen.mat.util;

import gen.mat.expr.Expression;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class Util
{
	public static int getWidth(String string)
	{
		int count = 0;
		for (int i = 0; i < string.length(); i++)
		{
			if (string.charAt(i) == '\t')
			{
				count += 8;
			}
			else
			{
				count++;
			}
		}
		return count;
	}
	
	
	public static String pad(String string, int length)
	{
		int count = getWidth(string);
		
		StringBuffer buffer = new StringBuffer();
		for (int i = count; i < length; i++)
		{
			buffer.append('0');
		}
		buffer.append(string);
		return buffer.toString();
	}
	
	public static StringBuffer pad(StringBuffer buffer, String inner, int length)
	{
		int count = getWidth(inner);
		for (int i=count; i<length; i++)
		{
			buffer.append(' ');
		}
		return buffer.append(inner);
	}

	
	public static void die(String message)
	{
		System.err.println(message);
		System.exit(-1);
	}
	
	public static StringBuffer join(StringBuffer buffer, Collection<Expression> expressions, String middle)
	{
		int count = 0;

		buffer.append('(');
		for (Expression expr : expressions)
		{
			buffer.append('(');
			expr.appendText(buffer);
			buffer.append(')');
			
			if (count++ != expressions.size() - 1)
			{
				buffer.append(middle);
			}
		}
		buffer.append(')');
		
		return buffer;
	}
	
	public static StringBuffer indent(StringBuffer buffer, int level)
	{
		for (int i = 0; i < level; i++)
		{
			buffer.append('\t');
		}
		return buffer;
	}
	

	private static final int RANDOM_STRING_LENGTH = 20;
	private static final String RANDOM_STRINGS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final Random RANDOM_GENERATOR = new Random();
	private static final HashSet<String> GENERATED_STRINGS = new HashSet<>();
	public static String getRandomString()
	{
		StringBuilder bldr = new StringBuilder(RANDOM_STRING_LENGTH);
		for (int i = 0; i < RANDOM_STRING_LENGTH; i++)
		{
			bldr.append(RANDOM_STRINGS.charAt(RANDOM_GENERATOR.nextInt(RANDOM_STRINGS.length())));
		}
		return bldr.toString();
	}
	
	public static void ensureDirectoryFor(String file)
	{
		new File(file).getParentFile().mkdirs();
	}
	
	public static String readFromFile(String file, final FileFilter fltr)
	{
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(file)));)
		{
			MyStringBuilder builder = new MyStringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null)
			{
				if (fltr != null && fltr.skipLine(line))
				{
					continue;
				}
				builder.append(line).append('\n');
			}
			return builder.toString();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean writeToFile(String string, String file)
	{
		ensureDirectoryFor(file);
		try (FileWriter writer = new FileWriter(new File(file)))
		{
			writer.write(string);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static interface FileFilter
	{
		boolean skipLine(String line);
	}
}
