package ca.uqac.lif.cep.methods;

import java.nio.file.Path;

/*
BeepBeep palette for analyzing traces of method calls
Copyright (C) 2017 Raphaël Khoury, Sylvain Hallé

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published
by the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import ca.uqac.lif.cep.functions.FunctionException;
import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.cep.methods.MethodEvent.MethodCall;
import ca.uqac.lif.cep.methods.MethodEvent2.MethodCall2;

/**
* Function that checks the annotation of a method call a method
* call.
* 
* @author Boussaha Mohamed Racem
*/
public class CheckAnnotation extends UnaryFunction<Object,Boolean> 
{
private static Class<retourType> retourType;
/**
 * The map associating method signatures with the index of the
 * argument corresponding to the data being written
 */
protected Map<MethodSignature,Integer> m_argumentsToWatch;
protected Path fileToWatch;
/**
 * Creates a new byte count function instance.
 * @param scanner A scanner open on a source of text lines
 * describing the methods that write data.
 * @see #readArgumentsToWatch(Scanner)
 */
public CheckAnnotation(Scanner scanner)
{
	super(Object.class, Boolean.class);
	m_argumentsToWatch = new HashMap<MethodSignature,Integer>();
	if (scanner != null)
		readArgumentsToWatch(scanner);
}

CheckAnnotation()
{
	this(null);
	
}

@Override
public Boolean getValue(Object x) throws FunctionException 
{
	Boolean ret =false;
	
	if (!(x instanceof MethodCall2))
	{
		return ret;
	}
	MethodCall2 call = (MethodCall2) x;
	
	String[] arg_types = new String[call.argumentCount()];
	
	MethodSignature sig = new MethodSignature(call.getMethodName(), call.m_types);
	
	int index = -1;
	if (m_argumentsToWatch.containsKey(sig))
	{
		index = m_argumentsToWatch.get(sig);
		ret =true;
		return ret;
	}

	return ret;
}

/**
 * Attempts to guess the size of an object
 * @param o The object
 * @return The size, in bytes
 */
protected static int getSizeOf(Object o)
{
	if (o instanceof Number)
	{
		// For the moment, we don't care about variable sizes
		return 4;
	}
	if (o instanceof String)
	{
		return ((String) o).length();
	}
	if (o instanceof byte[])
	{
		return ((byte[]) o).length;
	}
	// Other objects: don't care for now
	return 0;
}

/**
 * Reads the list of method signatures from a text source.
 * The list should be formatted as follows:
 * <pre>
 * # Blank lines and lines that begin with # are ignored
 * 
 * Class.methodName type<sub>1</sub> type<sub>2</sub> ... type<sub>n</sub> k
 * Class.methodName type<sub>1</sub> type<sub>2</sub> ... type<sub>n</sub> k
 * ...
 * </pre>
 * Where type1 is the type of the first argument (and so on), and
 * <i>k</i> is the position of the argument that contains the data
 * 
 * @param scanner A scanner open on a text source formatted as above
 */
protected void readArgumentsToWatch(Scanner scanner)
{
	while (scanner.hasNextLine())
	{
		String line = scanner.nextLine().trim();
		if (line.isEmpty() || line.startsWith("#"))
		{
			continue;
		}
		//
		String[] parts = line.split("\\s+");
		String name = parts[0];
		int index = Integer.parseInt(parts[parts.length - 1]);
		String[] args = new String[parts.length - 2];
		for (int i = 1; i < parts.length - 1; i++)
		{
			args[i - 1] = parts[i];
		}
		MethodSignature sig = new MethodSignature(name, args);
		m_argumentsToWatch.put(sig, index);
	}
}

}
