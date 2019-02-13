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
package ca.uqac.lif.cep.methods;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import ca.uqac.lif.cep.functions.FunctionException;
import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.cep.methods.MethodEvent.MethodCall;

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
public class testpro1 extends UnaryFunction<Boolean,Boolean> 
{
	/**
	 * The map associating method signatures with the index of the
	 * argument corresponding to the data being written
	 */
	
	/**
	 * Creates a new byte count function instance.
	 * @param scanner A scanner open on a source of text lines
	 * describing the methods that write data.
	 * @see #readArgumentsToWatch(Scanner)
	 */
	public static final testpro1 instance = new testpro1();
	public testpro1()
	{
		super(Boolean.class, Boolean.class);
		
	}


	@Override
	public Boolean getValue(Boolean x) throws FunctionException 
	{
		System.out.println(x);
		return true;
	}

	/**
	 * Attempts to guess the size of an object
	 * @param o The object
	 * @return The size, in bytes
	 */

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


}
