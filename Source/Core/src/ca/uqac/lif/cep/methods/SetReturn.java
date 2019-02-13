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
import ca.uqac.lif.cep.methods.MethodEvent2.MethodReturn;
import ca.uqac.lif.cep.sets.MathList;

/**
 * Function that fetches the name of the method from a method event
 * @author Sylvain Hallé
 */
public class SetReturn extends UnaryFunction<MathList,MethodEvent2>
{
	/**
	 * A single instance of this function
	 */
	public static final SetReturn instance = new SetReturn();
	
	private SetReturn() 
	{
		super(MathList.class,MethodEvent2.class);
	}

	@Override
	public MethodEvent2 getValue( MathList x) throws NothingToReturnException
	{
		MethodCall2 f ;
	f=(MethodCall2)x.get(0);
	/*if(x.get(1)==null)
		return f;*/
	f.m_returnValue=(MethodReturn)x.get(1);
		
		return f;
		
		
		
	}
}