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

import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.cep.methods.MethodEvent2.MethodCall2;
import ca.uqac.lif.cep.methods.MethodEvent2.MethodReturn;

/**
 * Function that fetches the type of the event (call or return)
 * from a method event
 * @author Sylvain Hallé
 */
public class GetCalls extends UnaryFunction<MethodEvent2,MethodCall2>
{
	/**
	 * A single instance of this function
	 */
	public static final GetCalls instance = new GetCalls();
	
	private GetCalls() 
	{
		super(MethodEvent2.class, MethodCall2.class);
	}

	@Override
	public MethodCall2 getValue(MethodEvent2 x)
	{	System.out.println(x);
		if (x instanceof MethodCall2)
			return (MethodCall2)x;
		else
		return null;
	}
}