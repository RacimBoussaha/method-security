package ca.uqac.lif.cep.methods;

import java.nio.file.Path;
import java.util.ArrayList;

/*
BeepBeep palette for analyzing traces of method calls
Copyright (C) 2017 RaphaÃ«l Khoury, Sylvain HallÃ©

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

import javax.swing.text.html.HTMLDocument.Iterator;

import ca.uqac.lif.cep.functions.FunctionException;
import ca.uqac.lif.cep.functions.NothingToReturnException;
import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.cep.methods.MethodEvent.MethodCall;
import ca.uqac.lif.cep.methods.MethodEvent2.MethodCall2;

/**
* Function that returns the number of bytes written by a method call.
* This is done by loading a text file that stipulates all the method
* signatures (name and arguments) that perform write operations, and
* which of their arguments corresponds to the data being written in
* each case. The function returns 0 if no data is written by a method
* call.
* 
* @author Sylvain HallÃ©
*/
public class ChecknFile extends UnaryFunction<Object,Boolean> 
{
private static Class<Boolean> retourType;
/**
 * The map associating method signatures with the index of the
 * argument corresponding to the data being written
 */
protected ArrayList <String> m_argumentsToWatch;
protected String fileToWatch;
protected Scanner scanner;
/**
 * Creates a new byte count function instance.
 * @param scanner A scanner open on a source of text lines
 * describing the methods that write data.
 * @see #readArgumentsToWatch(Scanner)
 */
public ChecknFile(String pathObj)
{
	super(Object.class,Boolean.class);
	this.fileToWatch=pathObj;
	
}

ChecknFile()
{
	this(null);
	
}

@Override
public Boolean getValue(Object x) throws FunctionException ,NothingToReturnException
{//long startTime = System.nanoTime();
	
	

	try {
		this.scanner=new Scanner(SafeRemove.class.getResourceAsStream("objects.txt"));
		m_argumentsToWatch = new ArrayList<String>();
		if (scanner != null)
			readArgumentsToWatch(scanner);
			 
	java.util.Iterator<String> it = m_argumentsToWatch.iterator();
	//System.out.println(x);
	while (it.hasNext()) {
	 String g = it.next();
	
	if (g.equals(x.toString().trim())){
		System.out.println("Trouvé"+x);			 
		return true;}
	}
	System.out.println("non Trouvé"+x);
	return false;
	} catch (Exception e) {
		System.out.println("non Trouvé"+x);
	return false;
		// TODO: handle exception
	}
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
		
		String code = line;
		
		m_argumentsToWatch.add(line);
		/*java.util.Iterator<String> it = m_argumentsToWatch.iterator();
		 
		while (it.hasNext()) {
		      // System.out.print(it.next()+" ");
			}*/

	}
}

}
