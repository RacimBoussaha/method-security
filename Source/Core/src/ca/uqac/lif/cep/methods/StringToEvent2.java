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
import ca.uqac.lif.cep.methods.MethodEvent2.*;
/**
 * Function that parses a line of text and creates the corresponding method
 * event
 * @author Sylvain Hallé
 */
public class StringToEvent2 extends UnaryFunction<String,MethodEvent2>
{
	/**
	 * A single instance of this function
	 */
	public static final StringToEvent2 instance = new StringToEvent2();
	
	private StringToEvent2() 
	{
		super(String.class, MethodEvent2.class);
		
	}
int i=0;
	@Override
	public MethodEvent2 getValue(String x)
	{
		//i++;
		//System.out.println(i+" "+x);
		
		String[] parts = x.split(" // ");
		
		for (int i = 0; i < parts.length; i++) {
				
		}
		
		if (parts[0].compareToIgnoreCase("call") == 0)
		{	
			Object[] args = new Object[(parts.length - 6)/2];
			String [] types= new String[(parts.length - 6)/2];
			
			if (!parts[3].equals("NAN")){
				
				for (int i = 3, j=0,k=4 ; i < parts.length-k; i++, j++,k++)
				{	
					types[j] = parts[i];
				//System.out.print(" types "+types[j]);	
				}
				//System.out.println("");
				
				for (int i = 3+types.length,j=0,k=3; i < parts.length-k; i++,j++,k++)
				{	//System.out.print(" argument "+(parts.length-k)+" i :"+ i);			
					args[j] = parts[i];
					//System.out.print(" argument "+args[j]);	
				}
				//System.out.println("");
				
				}
			return new MethodCall2( parts[2], parts[1], null, parts[parts.length-3], parts[parts.length-2],types ,args,parts[parts.length-1]);
		}
		else 
		{
			if (parts[0].compareToIgnoreCase("output") == 0)
				return new MethodReturn(parts[1].trim(),parts[1]);
		
			else{
				
				Object[] args = new Object[(parts.length - 6)/2];
				String [] types= new String[(parts.length - 6)/2];
				int ind = Integer.parseInt(parts[1].trim());
				
				for (int i = 3; i < parts.length-3; i+=2)
				{				
					types[i - 3] = parts[i];
					
				}
				
				for (int i = 4; i < parts.length-3; i+=2)
				{			
					args[i - 4] = parts[i];
					
				}
				return new MethodKey2(parts[2], parts[1], null, parts[parts.length-2], parts[parts.length-2],ind, types,args,parts[parts.length-1]);
				}
			}
	}
}
