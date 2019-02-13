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

import java.util.Scanner;
import ca.uqac.lif.cep.functions.FunctionException;
import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.cep.methods.MethodEvent2.MethodReturn;

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
public class ObjectToBoolean extends UnaryFunction<Object,Boolean> 
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
	public static final ObjectToBoolean instance = new ObjectToBoolean();
	public ObjectToBoolean()
	{
		super(Object.class,Boolean.class);
		
	}


	@Override
	public Boolean getValue(Object x) throws FunctionException 
	{
		if (x instanceof MethodReturn )
			return Boolean.valueOf(((MethodReturn)x).m_methodName);
	
		return (Boolean)x;
	}
}