package ca.uqac.lif.cep.methods;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ca.uqac.lif.cep.functions.FunctionException;
import ca.uqac.lif.cep.functions.NothingToReturnException;
import ca.uqac.lif.cep.functions.UnaryFunction;

/**
* Function that checks if a string is present in a file.
* 
* @author Boussaha Mohamed Racem
*/
public class ChecknFile extends UnaryFunction<Object,Boolean> 
{
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
	 * 	describing the methods that write data.
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
	{
		try {
	
			Stream<String> stream = Files.lines(Paths.get(fileToWatch));
			m_argumentsToWatch = new ArrayList<String>();
			if (stream != null)
				readArgumentsToWatch(stream);
			java.util.Iterator<String> it = m_argumentsToWatch.iterator();
			
			while (it.hasNext()) {
				String g = it.next();
				if (g.equals(x.toString().trim())){			 
					return true;
					}
				}
			return false;
			} catch (Exception e) {
				e.printStackTrace();
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
	protected void readArgumentsToWatch(Stream strm)
	{	m_argumentsToWatch = (ArrayList<String>) strm.collect(Collectors.toList());
	}
}
