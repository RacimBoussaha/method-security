package ca.uqac.lif.cep.methods;

import static ca.uqac.lif.cep.Connector.connect;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Scanner;

import ca.uqac.lif.cep.Connector.ConnectorException;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.Function;
import ca.uqac.lif.cep.functions.FunctionProcessor;
import ca.uqac.lif.cep.io.LineReader;
import ca.uqac.lif.cep.sets.ToList;
import ca.uqac.lif.cep.tmf.CountDecimate;
import ca.uqac.lif.cep.tmf.Filter;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Trim;

public class SafeEnum {

	public static void main(String[] args) throws ConnectorException, URISyntaxException, IOException {
		// TODO Auto-generated method stub
		String filename = "traces/SafeEnumTrace.txt";
		URI uri = SafeEnum.class.getResource("objects.txt").toURI();
		String pathObj = Paths.get(uri).toString();
		
		FunctionProcessor converter = new FunctionProcessor(StringToEvent2.instance);
		FunctionProcessor get_output = new FunctionProcessor(getReturn.instance);
		FunctionProcessor get_output2 = new FunctionProcessor(getReturn.instance);
		FunctionProcessor to_list = new FunctionProcessor(new ToList(Object.class,Object.class ));
		FunctionProcessor to_list1 = new FunctionProcessor(new ToList(Object.class,Object.class ));
		Function check_anno1 = new CheckAnnotation(new Scanner(SafeEnum.class.getResourceAsStream("MethodsSignatures/SafeEnum-signatures.txt")));
		Function check_anno2 = new CheckAnnotation(new Scanner(SafeEnum.class.getResourceAsStream("MethodsSignatures/SafeEnum-signatures1.txt")));
		Function check_anno3 = new CheckAnnotation(new Scanner(SafeEnum.class.getResourceAsStream("MethodsSignatures/SafeEnum-signatures2.txt")));
		Function checknF = new ChecknFileContains(pathObj);
		Function savenF = new SaveString(pathObj);
		Function delete = new deleteContains(pathObj);
		
		
		FunctionProcessor setRet = new FunctionProcessor(SetReturn.instance);
		FunctionProcessor getObj1 = new FunctionProcessor(getObject.instance);
		FunctionProcessor getObj2 = new FunctionProcessor(getObject.instance);
		FunctionProcessor getObj3 = new FunctionProcessor(getObject.instance);
		FunctionProcessor hexToInt = new FunctionProcessor(hexToInteger.instance);
		FunctionProcessor invers = new FunctionProcessor(Inverse.instance);
		FunctionProcessor ObjToBool= new FunctionProcessor(ObjectToBoolean.instance);
		FunctionProcessor check_anno_1 = new FunctionProcessor(check_anno1);
		FunctionProcessor check_anno_2 = new FunctionProcessor(check_anno2);
		FunctionProcessor check_anno_3 = new FunctionProcessor(check_anno3);
		FunctionProcessor check_Object_p = new FunctionProcessor(checknF);
		FunctionProcessor save_Object_p = new FunctionProcessor(savenF);
		FunctionProcessor remove = new FunctionProcessor(delete);
		
		LineReader feeder = new LineReader(SafeEnum.class.getResourceAsStream(filename));
		
		CountDecimate CD1= new CountDecimate(2);
		CountDecimate CD2= new CountDecimate(2);
		
		Trim tr1=new Trim(1);
		
		Fork f1 = new Fork(2);
		Fork f3 = new Fork(6);
		Fork f4 = new Fork(2);
		Fork f5 = new Fork(2);
		
		Filter fil1 = new Filter();
		Filter fil2 = new Filter();
		Filter fil3 = new Filter();
		Filter fil4 = new Filter();
		
		connect(feeder, converter);
		connect(converter, f1);
		// setting for each event the return value
		connect(f1,0,tr1,0);
		connect(f1,1,CD2,0);
		connect(tr1,0,CD1,0);
		connect(CD1,0,to_list,1);
		connect(CD2,0,to_list,0); 
		connect(to_list,setRet);
		//saving the object with the specific annot&tion
		connect(setRet,f3);
		connect(f3,0,fil1,0);
		connect(f3,1,check_anno_1,0);
		connect(check_anno_1,0,fil1,1);
		connect(fil1,f4);
		connect(f4,0,get_output,0);
		connect(get_output,0,hexToInt,0);
		connect(f4,1,getObj1,0);
		connect(getObj1,0,to_list1,0);
		connect(hexToInt,0,to_list1,1);
		connect(to_list1,0,save_Object_p,0);
		//Checking if the Object is stored 
		connect(f3,2,fil2,0);
		connect(f3,3,check_anno_3,0);
		connect(check_anno_3,0,fil2,1);
		connect(fil2,getObj2);
		connect(getObj2,check_Object_p);
		//Removing the object 
		connect(f3,4,check_anno_2,0);
		connect(f3,5,fil3,0);
		connect(check_anno_2,0,fil3,1);
		connect(fil3,f5);
		connect(f5,0,getObj3,0);
		connect(f5,1,get_output2,0);
		connect(get_output2,ObjToBool);
		connect(ObjToBool,invers);
		connect(invers,0,fil4,1);
		connect(getObj3,0,fil4,0);
		connect(fil4,remove);
		
		Pullable p = check_Object_p.getPullableOutput();
		Pullable e = save_Object_p.getPullableOutput();
		Pullable s = remove.getPullableOutput();
	
		for (int i=1;i<50;i++){
			
			Object x=e.pullSoft();
			 if (x==null )
					System.err.println(i+" Saved : "+x);
				else
					System.out.println(i+" Saved : "+x);
				
			
			Object y = p.pullSoft();
			if (y==null)
				System.err.println(i+" Exist : "+y);
			else
				{if((Boolean)y)
				{System.out.println("Constraint Not respected at line number "+i+", \ntap to continue.." );
				System.in.read();
				}
				else 
				System.out.println("Constraint respected at line number "+i);
				}
			 
			
				 Object z=s.pullSoft();
				  if (z==null)
					System.err.println(i+" Deleted : "+z);
				else
					System.out.println(i+" Deleted : "+z);		
			}
	}

}
