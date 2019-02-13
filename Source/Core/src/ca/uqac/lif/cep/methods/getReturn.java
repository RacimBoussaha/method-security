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

import ca.uqac.lif.cep.functions.NothingToReturnException;
import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.cep.methods.MethodEvent2.MethodCall2;
import ca.uqac.lif.cep.methods.MethodEvent2.MethodReturn;

/**
 * Function that fetches the name of the method from a method event
 * @author Sylvain Hallé
 */
public class getReturn extends UnaryFunction<MethodEvent2,MethodReturn>
{
	/**
	 * A single instance of this function
	 */
	public static final getReturn instance = new getReturn();
	
	private getReturn() 
	{
		super(MethodEvent2.class, MethodReturn.class);
	}

	@Override
	public MethodReturn getValue(MethodEvent2 x) throws NothingToReturnException
	{
		if (!(x instanceof MethodCall2))
	{
		throw new NothingToReturnException(this);
	}try {		return ((MethodCall2)x).m_returnValue;

		
	} catch (Exception e) {
		return  new MethodReturn("NAN",((MethodCall2)x).getId());
		// TODO: handle exceptionreturn 
	}
		
	}
}