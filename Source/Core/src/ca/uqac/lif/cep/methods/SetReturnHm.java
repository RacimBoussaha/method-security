package ca.uqac.lif.cep.methods;

import java.util.HashMap;
import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.cep.methods.MethodEvent2.MethodCall2;
import ca.uqac.lif.cep.methods.MethodEvent2.MethodReturn;

public class SetReturnHm extends UnaryFunction<MethodReturn,MethodCall2> {
	
	private HashMap<Integer,MethodEvent2> hm = new HashMap<Integer,MethodEvent2>();
	private Class<?> clazz;

	public SetReturnHm(HashMap<Integer,MethodEvent2> objects){
		super(MethodReturn.class, MethodCall2.class);	
		clazz = objects.getClass();
		this.hm = objects;	
		}
	
	@Override
	public MethodCall2 getValue(MethodReturn x){
		MethodCall2 call=null;
		if (hm.containsKey(Integer.parseInt((x.getId()).replaceAll("[^\\d.]", "")))){
			call=(MethodCall2)(hm.get(Integer.parseInt((x.getId()).replaceAll("[^\\d.]", ""))));
			call.SetReturnValue(x);
		}
		return call;
		}
	}
		
	
