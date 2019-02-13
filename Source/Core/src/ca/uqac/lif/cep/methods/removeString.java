package ca.uqac.lif.cep.methods;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
public class removeString extends UnaryFunction<String,Boolean> 
{

/**
 * The map associating method signatures with the index of the
 * argument corresponding to the data being written
 */
	protected  BufferedWriter writer ;
	protected String fileToWatch;
	protected ArrayList <String> m_argumentsToWatch;
	Scanner scanner;
/**
 * Creates a new save object to file function instance.
 * @param scanner A scanner open on a source of text lines
 * describing the methods that write data.
 * @param pathObj A writer write an object hashcode to 
 * source of text lines
 * @see #readArgumentsToWatch(Scanner) 
 */
public removeString(String pathObj)
{
	super(String.class, Boolean.class);
	this.fileToWatch=pathObj;

}

removeString()
{
	this(null);
	
}

@Override
public Boolean getValue(String x) throws FunctionException 
{	
	this.scanner=new Scanner(SafeRemove.class.getResourceAsStream("objects.txt"));
	m_argumentsToWatch = new ArrayList<String>();
	if (scanner != null)
		readArgumentsToWatch(scanner);
	java.util.Iterator<String> it = m_argumentsToWatch.iterator();
	 
	while (it.hasNext()) {
		 String g = it.next();
		
		if (g.equals(x.toString().trim())){
		m_argumentsToWatch.remove(g);
		java.util.Iterator<String> it2 = m_argumentsToWatch.iterator();
		
		try {
			PrintWriter writer = new PrintWriter(fileToWatch);
			writer.print("");
		
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (it2.hasNext()) {
			
			 try {
				Files.write(Paths.get(fileToWatch), (it2.next().toString()+System.getProperty("line.separator")).getBytes(), StandardOpenOption.APPEND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
			
			
		}
		System.out.println(x+ "Supprimé");
		return true;
		}
	}
	System.out.println(x+ "pas supprimé");
	return false;
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
