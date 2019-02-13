package ca.uqac.lif.cep.methods;

import static ca.uqac.lif.cep.Connector.connect;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import ca.uqac.lif.cep.Connector.ConnectorException;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.Function;
import ca.uqac.lif.cep.functions.FunctionProcessor;
import ca.uqac.lif.cep.io.LineReader;
import ca.uqac.lif.cep.tmf.Fork;

public class testCon {
	
	public static void main(String[] args) throws NullPointerException ,ConnectorException, URISyntaxException, IOException, IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		HashMap<Integer, MethodEvent2> hm=new HashMap<Integer,MethodEvent2>();
		
		String filename = "traces/test.txt";
	
		Function save_Hs= new SaveCallHashMap(hm);
		Function setReturn_Hs= new SetReturnHm(hm);
		
		LineReader feeder = new LineReader(SafeEnum.class.getResourceAsStream(filename));
		
		FunctionProcessor converter = new FunctionProcessor(StringToEvent2.instance);
		FunctionProcessor get_CallsPros= new FunctionProcessor(GetCalls.instance);
		FunctionProcessor get_OutputsPros = new FunctionProcessor(GetOutputs.instance);
		FunctionProcessor saveHs = new FunctionProcessor(save_Hs);
		FunctionProcessor setReturnHs = new FunctionProcessor(setReturn_Hs);
		
		Fork fork1 = new Fork (4);
		
	

		connect(feeder, converter);
		connect(converter,fork1);
		connect (fork1,0,get_CallsPros,0);
		connect (fork1,1,get_OutputsPros,0);
		connect(get_CallsPros,saveHs);
		connect(get_OutputsPros,setReturnHs);
	
		Pullable saveOut = saveHs.getPullableOutput();
		Pullable setROut = get_CallsPros.getPullableOutput();
		
		for (int i =1;i<=100;i++){
		
			System.out.println(saveOut.pullSoft());
			System.out.println(setROut.pullSoft());
			try {
				
		//System.out.println(saveOut.pullSoft());
		
		//System.out.println(setROut.pullSoft());
		
			} catch (Exception e) {
				// TODO: handle exception
			}
		
		}
	
		}
	}

/*
 * 
URI uri = SafeEnum.class.getResource("call.txt").toURI();
URI uri2 = SafeEnum.class.getResource("return.txt").toURI();
String pathCall = Paths.get(uri).toString();
String pathReturn = Paths.get(uri2).toString();
Function call = new ChecknFile(pathCall);
Function Return = new ChecknFile(pathReturn);
	Fork fork2 = new Fork (2);
Fork fork3 = new Fork (3);
Filter filter1 =new Filter();
Filter filter2 =new Filter();
FunctionProcessor checkCall = new FunctionProcessor(call);
FunctionProcessor checkReturn = new FunctionProcessor(Return);
FunctionProcessor get_Type = new FunctionProcessor(GetEventType2.instance);
FunctionProcessor get_Type1 = new FunctionProcessor(GetEventType2.instance);
/*	connect (fork1,0,get_Type,0);
connect (fork1,1,get_Type1,0);
connect(get_Type,checkCall);
connect(get_Type1,checkReturn);
connect(fork1,2,filter1,0);
connect(checkCall,0,filter1,1);
connect(checkReturn,0,filter2,1);	
connect(fork1,3,filter2,0);
connect(filter1,saveHs);
connect(filter2,setReturnHs);*/

