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

public abstract class MethodEvent2 
{
	/**
	 * The name of the method considered by this event
	 */
	protected final String m_methodName;
	protected String id;
	
	public MethodEvent2(String name,String id)
	{
		super();
		
		m_methodName = name;
		this.id=id;
				
	}
	
	public String getMethodName()
	{
		return m_methodName;
	}
	public String getId()
	{
		return id;
	}
	
	/**
	 * Event representing a method call
	 */
	public static class MethodCall2 extends MethodEvent2
	{
		/**
		 * The parameters associated to this method call
		 */
		protected  String m_returnType;
		protected MethodReturn m_returnValue;
		protected  String m_level;
		protected  String m_object;
		protected  Object[] m_arguments;
		protected  String[] m_types; 

		
		public MethodCall2(String name,String returnType,MethodReturn returnValue,String level,String object, String[] types ,Object [] parameters,String id)
		{
			super(name,id);
			
			m_level=level;
			m_returnType=returnType;
			m_returnValue=returnValue;
			m_object=object;
			//System.out.println((String)object);
			m_arguments = parameters;
			m_types=types;
			
		}
		public String getlevel()
		{
			return m_level;
		}
		public String getreturnType()
		{
			return m_returnType;
		}
		public MethodReturn getreturnValue()
		{
			return m_returnValue;
		}
		public void SetReturnValue(MethodReturn ret)
		{
			m_returnValue=ret;
		}
		public String getobject()
		{
			return m_object;
		}
		/**
		 * Gets the <i>i</i>-th argument of this method call
		 * @param index The index of the argument to retrieve
		 * @return The argument
		 */
		public Object getArgument(int index)
		{	
			return m_arguments[index];
			
		}
		
		/**
		 * Gets the number of arguments passed to this method
		 * @return The number of arguments
		 */
		public int argumentCount()
		{
			return m_arguments.length;
		}
		
		@Override
		public String toString()
		{
			return "call " + m_methodName; 
		}
	}

	/**
	 * Event representing a method return
	 */
	public static class MethodReturn extends MethodEvent2
	{
		public MethodReturn(String name,String id)
		{
			super(name,id);
		}
		
		@Override
		public String toString()
		{
			return m_methodName;
		}
	}
	public static class MethodKey2 extends MethodCall2
	{
		/**
		 * The parameters associated to this method call
		 */
		//protected final Object[] m_arguments;
		protected  int KeyIndex;
		public MethodKey2(String name,String returnType,MethodReturn returnValue,String level,String object,int Kindex,String[] types , Object [] parameters,String id)
		{
			
			super(name,returnType,returnValue,level,object,types,parameters,id);
			KeyIndex=Kindex;
			
			
		}
		
		/**
		 * Gets the <i>i</i>-th argument of this method call
		 * @param index The index of the argument to retrieve
		 * @return The argument
		 */
		
		
		public Object getKey()
		{
			return getArgument(KeyIndex);
		}
	}
}
