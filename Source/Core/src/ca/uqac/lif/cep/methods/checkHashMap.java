package ca.uqac.lif.cep.methods;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import ca.uqac.lif.cep.functions.FunctionException;
import ca.uqac.lif.cep.functions.NothingToReturnException;
import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.cep.methods.MethodEvent2.MethodCall2;

public class checkHashMap extends UnaryFunction<Object,Boolean> 
{
	protected ArrayList <String> m_argumentsToWatch;
	protected String fileToWatch;
	protected Scanner scanner;
	
	public checkHashMap(String pathObj)
	{
		super(Object.class,Boolean.class);
		this.fileToWatch=pathObj;
	}

	public checkHashMap()
	{
		this(null);
	}

	@Override
	
	public Boolean getValue(Object x) throws FunctionException ,NothingToReturnException
	{	
		try {
			this.scanner=new Scanner(SafeRemove.class.getResourceAsStream(fileToWatch));
			m_argumentsToWatch = new ArrayList<String>();
			if (scanner != null)
				readArgumentsToWatch(scanner);
			java.util.Iterator<String> it = m_argumentsToWatch.iterator();
			while (it.hasNext()) {
				String g = it.next();
				if (g.equals(x.toString().trim())){
					return true;}
				}
			return false;
			} catch (Exception e) {
				return false;
		// TODO: handle exception
				}
		}

	protected void readArgumentsToWatch(Scanner scanner)
	{
		while (scanner.hasNextLine()){
			String line = scanner.nextLine().trim();
			if (line.isEmpty() || line.startsWith("#")){
				continue;
			}
			String code = line;
			m_argumentsToWatch.add(line);
		}
	}

	}
