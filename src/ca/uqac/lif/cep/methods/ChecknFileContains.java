package ca.uqac.lif.cep.methods;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import ca.uqac.lif.cep.functions.FunctionException;
import ca.uqac.lif.cep.functions.UnaryFunction;

public class ChecknFileContains extends UnaryFunction<Object,Boolean> 
{
	protected ArrayList <String> m_argumentsToWatch;
	protected String fileToWatch;
	protected Scanner scanner;

	public ChecknFileContains(String scanner)
	{
		super(Object.class,Boolean.class);
		this.fileToWatch= scanner;
	}

	ChecknFileContains()
	{
		this(null);
	}

	@Override
	public Boolean getValue(Object x) throws FunctionException 
	{
		try {
			this.scanner=new Scanner(SafeRemove.class.getResourceAsStream("objects.txt"));
			m_argumentsToWatch = new ArrayList<String>();
			if (scanner != null)
				readArgumentsToWatch(scanner);
			java.util.Iterator<String> it = m_argumentsToWatch.iterator();
			while (it.hasNext()) {
				String[] s= (((it.next()).replaceAll("\\[","")).replaceAll("\\]", "")).replaceAll("\\s+","").split(",");
				if (Arrays.asList(s).contains(x))
				{
					return true;
				}
			}
			return false;
		} 
		catch (Exception e) 
		{
			return false;
		// TODO: handle exception
		}
	}

	protected void readArgumentsToWatch(Scanner scanner)
	{
		while (scanner.hasNextLine())
			{
				String line = scanner.nextLine().trim();
				if (line.isEmpty() || line.startsWith("#"))
				{
					continue;
				}
				m_argumentsToWatch.add(line);
			}
		}
	}