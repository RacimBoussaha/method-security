package ca.uqac.lif.cep.methods;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

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

import javax.swing.text.html.HTMLDocument.Iterator;

import ca.uqac.lif.cep.functions.FunctionException;
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
* @author Sylvain Hallé
*/
public class SaveToFile extends UnaryFunction<MethodEvent2,Boolean> 
{
private static Class<Boolean> retourType;
/**
 * The map associating method signatures with the index of the
 * argument corresponding to the data being written
 */
protected  BufferedWriter writer ;
protected String fileToWatch;
protected ArrayList <String> m_argumentsToWatch;
/**
 * Creates a new save object to file function instance.
 * @param scanner A scanner open on a source of text lines
 * describing the methods that write data.
 * @param pathObj A writer write an object hashcode to 
 * source of text lines
 * @see #readArgumentsToWatch(Scanner) 
 */
public SaveToFile(String pathObj,Scanner scanner)
{
	super(MethodEvent2.class, retourType);
	
	this.fileToWatch=pathObj; 
	m_argumentsToWatch = new ArrayList<String>();
	if (scanner != null)
		readArgumentsToWatch(scanner);

}

SaveToFile()
{
	this(null, null);
	
}

@Override
public Boolean getValue(MethodEvent2 x) throws FunctionException 
{//long startTime = System.nanoTime();
	
	if (!(x instanceof MethodCall2))
	{
		return false;
	}
	
	MethodCall2 call =  (MethodCall2) x;
	String[] arg_types = new String[call.argumentCount()];
	
	MethodSignature sig = new MethodSignature(call.getMethodName(), call.m_types);
	java.util.Iterator<String> it = m_argumentsToWatch.iterator();
	 
	while (it.hasNext()) {
	 
	if (it.next().equals(call.getobject().trim().toString())){
		
		return true;}
	}
try {
	 Files.write(Paths.get(fileToWatch), (call.getobject().toString()+System.getProperty("line.separator")).getBytes(), StandardOpenOption.APPEND);
	 	
	
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	return true;
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
