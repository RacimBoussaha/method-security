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
import ca.uqac.lif.cep.methods.MethodEvent2.MethodCall2;
import ca.uqac.lif.cep.sets.ToList;
import ca.uqac.lif.cep.tmf.CountDecimate;
import ca.uqac.lif.cep.tmf.Filter;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Trim;

public class NoSendAfterReading {

	public static void main(String[] args) throws ConnectorException, URISyntaxException, IOException {
		// TODO Auto-generated method stub
		String filename = "traces/NoSendAfterReadingTrace.txt";
		URI uri = SafeEnum.class.getResource("objects.txt").toURI();
		String pathObj = Paths.get(uri).toString();
		URI uri2 = SafeEnum.class.getResource("String.txt").toURI();
		String pathObj2 = Paths.get(uri2).toString();
		URI uri3 = SafeEnum.class.getResource("Annot1.txt").toURI();
		String pathObj3 = Paths.get(uri3).toString();
		
		Function check_anno1 = new CheckAnnotation(new Scanner(SafeEnum.class.getResourceAsStream("MethodsSignatures/NoSendAfterReading-Signature1.txt")));
		Function check_anno2 = new CheckAnnotation(new Scanner(SafeEnum.class.getResourceAsStream("MethodsSignatures/NoSendAfterReading-Signature2.txt")));
		Function check_anno3 = new CheckAnnotation(new Scanner(SafeEnum.class.getResourceAsStream("MethodsSignatures/NoSendAfterReading-Signature3.txt")));
		Function checknF = new ChecknFile("objects.txt");
		Function checknF2 = new ChecknFile("String.txt");
		Function savenF = new SaveString(pathObj);
		Function savenF2 = new SaveString(pathObj2);
		Function garg = new getArg(0);
		
		FunctionProcessor converter = new FunctionProcessor(StringToEvent2.instance);
		FunctionProcessor to_list = new FunctionProcessor(new ToList(Object.class,MethodCall2.class ));		
		FunctionProcessor setreturn = new FunctionProcessor(SetReturn.instance);
		FunctionProcessor getreturn = new FunctionProcessor(getReturn.instance);
		FunctionProcessor getreturn2 = new FunctionProcessor(getReturn.instance);
		FunctionProcessor getObj = new FunctionProcessor(getObject.instance);
		FunctionProcessor gArg = new FunctionProcessor(garg);
		FunctionProcessor check_anno_1 = new FunctionProcessor(check_anno1);
		FunctionProcessor check_anno_2 = new FunctionProcessor(check_anno2);
		FunctionProcessor check_anno_3 = new FunctionProcessor(check_anno3);
		FunctionProcessor hexToint = new FunctionProcessor(hexToInteger.instance);
		FunctionProcessor check_Object_p = new FunctionProcessor(checknF);
		FunctionProcessor save_Object_p = new FunctionProcessor(savenF);
		FunctionProcessor check_Object_p1 = new FunctionProcessor(checknF2);
		FunctionProcessor save_Object_p1 = new FunctionProcessor(savenF2);
		
		
		LineReader feeder = new LineReader(SafeEnum.class.getResourceAsStream(filename));
		connect(feeder, converter);

		CountDecimate CD1= new CountDecimate(2);
		CountDecimate CD2= new CountDecimate(2);
		
		Trim tr1=new Trim(1);
		
		Fork f0 = new Fork(3);
		Fork f1 = new Fork(2);
		Fork f2 = new Fork(6);
		Fork f3 = new Fork(2);
		Fork f4 = new Fork(2);
		
		
		Filter fil1=new Filter();
		Filter fil2=new Filter();
		Filter fil3=new Filter();
		Filter fil4=new Filter();
		
		connect(converter, f0);
		connect(f0,0,f1,0);
		connect(f1,0,tr1,0);
		connect(f1,1,CD2,0);
		connect(tr1,0,CD1,0);
		connect(CD1,0,to_list,1);
		connect(CD2,0,to_list,0); 
		connect(to_list,setreturn);
		
		connect(setreturn,f2);
		connect(f2,0,check_anno_1,0);
		connect(check_anno_1,0,fil1,1);
		connect(f2,1,fil1,0);
		connect(fil1,getreturn);
		connect(getreturn,save_Object_p);
		
		connect(f2,2,check_anno_2,0);
		connect(f2,3,fil2,0);
		connect(check_anno_2,0,fil2,1);
		connect(fil2,getreturn2);
		connect(getreturn2,hexToint);
		connect(hexToint,save_Object_p1);
		
		connect(f2,4,check_anno_3,0);
		connect(check_anno_3,0,fil3,1);
		connect(f2,5,fil3,0);
		connect(fil3,0,f3,0);
		connect(f3,1,getObj,0);
		connect(getObj,f4);
		connect(f4,0,check_Object_p1,0);
		connect(check_Object_p1,0,fil4,1);
		connect(f3,0,fil4,0);
		connect(fil4,gArg);
		connect(gArg,check_Object_p);
		
		/*connect(getObj,check_Object_p1);
		connect(check_Object_p1,0,fil4,1);
		/*connect(fil4,gArg);
		connect(gArg,check_Object_p);*/
		Pullable s = save_Object_p.getPullableOutput();
		Pullable q = save_Object_p1.getPullableOutput();
		Pullable p = check_Object_p.getPullableOutput();
		
for (int i=1;i<200;i++){
	
	Object x=s.pullSoft();
	 if (x==null )
			System.err.println(i+" Saving read data : "+x);
		else
			System.out.println(i+" Saving read data : "+x);
	 
		
	Object f=q.pullSoft();
			 if (x==null )
					System.err.println(i+" Sending data: "+x);
				else
					System.out.println(i+" Sending data : "+x);
			 
			
		Object y = p.pullSoft();
			if (y==null)
				System.err.println(i+" Check Data : "+y);
			else
				{if((Boolean)y)
				{System.out.println(" Constraint Not respected at line number \nTrying to send read data , \ntap to continue.." );
				System.in.read();
				}
				else 
				System.out.println(" Constraint respected ");
				}
			}
	}
}
