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
import ca.uqac.lif.cep.methods.MethodEvent.MethodCall;
import ca.uqac.lif.cep.methods.MethodEvent2.MethodCall2;

/**
 * Function that fetches the name of the method from a method event
 * @author Sylvain Hallé
 */
public class getObject extends UnaryFunction<MethodEvent2,String>
{
	/**
	 * A single instance of this function
	 */
	public static final getObject instance = new getObject();
	
	private getObject() 
	{
		super(MethodEvent2.class, String.class);
	}

	@Override
	public String getValue(MethodEvent2 x) throws NothingToReturnException
	{if (!(x instanceof MethodCall2))
	{
		throw new NothingToReturnException(this);
	}
		return ((MethodCall2)x).getobject().trim();
	}
}