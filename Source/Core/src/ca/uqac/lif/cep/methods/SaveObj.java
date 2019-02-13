package ca.uqac.lif.cep.methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.cep.methods.MethodEvent2.MethodCall2;

public class SaveObj extends UnaryFunction<Object,Boolean> {
	
	private ArrayList<Object> objects = new ArrayList<Object>();
	
	public SaveObj(ArrayList<Object> objects){
		super(Object.class, Boolean.class);	
		this.objects = objects;	
		}
	@Override
	public Boolean getValue(Object x){
		objects.add(x);
		return true;
	}
}
