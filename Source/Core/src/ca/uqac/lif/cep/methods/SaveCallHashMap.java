package ca.uqac.lif.cep.methods;

import java.util.HashMap;
import ca.uqac.lif.cep.functions.UnaryFunction;

public class SaveCallHashMap extends UnaryFunction<MethodEvent2,Boolean> {
	
	private HashMap<Integer,MethodEvent2> hm = new HashMap<Integer,MethodEvent2>();
	
	public SaveCallHashMap(HashMap<Integer,MethodEvent2> objects){
		super(MethodEvent2.class, Boolean.class);	
		this.hm = objects;	
		}
	@Override
	public Boolean getValue(MethodEvent2 x){
		hm.put(Integer.parseInt((x.getId()).replaceAll("[^\\d.]", "")), x);
		return true;
	}
}
