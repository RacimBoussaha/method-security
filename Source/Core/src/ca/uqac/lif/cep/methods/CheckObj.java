package ca.uqac.lif.cep.methods;

import java.util.ArrayList;

import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.cep.methods.MethodEvent2.MethodCall2;
import ca.uqac.lif.cep.methods.MethodEvent2.MethodReturn;

public class CheckObj extends UnaryFunction<Object,Boolean> {
	
private ArrayList<Object> objects = new ArrayList<Object>();
	
	public CheckObj (ArrayList<Object> objects){
		
		super(Object.class, Boolean.class);	
		this.objects = objects;	
	}
	@Override
	public Boolean getValue(Object x){
		if (objects.contains(x)){
			return true;
		}
		return false;			
	}
}